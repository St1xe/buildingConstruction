package ru.sfedu.buildingconstruction.api;

import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.Month;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;

import org.postgresql.util.PSQLException;

import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.model.*;
import ru.sfedu.buildingconstruction.util.ConfigurationUtil;

/**
 *
 * @author maksim
 */
public class DataBaseProvider implements DataProvider {

    private static ConfigurationUtil configurationUtil;
    private static Logger log = Logger.getLogger(DataBaseProvider.class);

    private MongoProvider mongo = new MongoProvider();

    private Connection connection;

    public DataBaseProvider() {
        log.debug("DataBaseProvider [0]:starting application.....");
        dataBaseConnection();
    }
    
    @Override
    public void preparationOfConstructionPlan(Building building, Client client, List<Material> materials, List<EngineeringSystem> systems) throws IOException {
       
        log.debug("preparationOfConstructionPlan [1]:");
        
        building.setMaterials(materials);
        building.setOwner(client);
        building.setEngineeringSystems(systems);
        if (building.getId() == null) {
            building.setId(UUID.randomUUID().toString());
        }
        building.setCompletionDate(LocalDate.of(0, Month.JANUARY, 1));
        addBuilding(building);

        log.info("id добавленного здания = " + building.getId());

    }

    @Override
    public void preparationForBuilding(Building building) throws IOException{

        log.debug("preparationForBuilding [1]:");
        
        List<Worker> workers = distributionOfWorkers(building, Constants.WORKER_TABLE);
        List<ConstructionEquipment> equipments = distributionOfConstructionEquipment(building, Constants.CONSTRUCTION_EQUIPMENT_TABLE);
        LocalDate date = coordinationOfConstructionTerms(building);

        building.setWorkers(workers);
        building.setConstructionEquipments(equipments);
        building.setCompletionDate(date);

        try {
            updateBuilding(building.getId(), building);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new IOException();
        }

    }

    @Override
    public void addWorker(Worker worker) throws IOException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_NEW_WORKER);
            preparedStatement.setString(1, worker.getId());
            preparedStatement.setString(2, worker.getName());
            preparedStatement.setString(3, worker.getPhoneNumber());
            preparedStatement.setString(4, worker.getJobTitle());
            preparedStatement.setDouble(5, worker.getSalary());
            preparedStatement.execute();
            log.info("addWorker [1]: Запись добавлена " + worker);
            mongo.logHistory(UUID.randomUUID().toString(), worker, "addWorker", Status.SUCCES);

        } catch (SQLException ex) {
            log.error("addWorker [2]: запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), worker, "addWorker", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    @Override
    public void addConstructionEquipment(ConstructionEquipment constructionEquipment) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_NEW_CONSTRUCTION_EQUIPMENT);
            preparedStatement.setString(1, constructionEquipment.getId());
            preparedStatement.setString(2, constructionEquipment.getName());
            preparedStatement.setDouble(3, constructionEquipment.getPrice());
            preparedStatement.execute();
            log.info("addConstructionEquipment [1]: Запись добавлена " + constructionEquipment);
            mongo.logHistory(UUID.randomUUID().toString(), constructionEquipment, "addConstructionEquipment", Status.SUCCES);

        } catch (SQLException ex) {
            log.error("addConstructionEquipment [2]: запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), constructionEquipment, "addConstructionEquipment", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    @Override
    public void addMaterial(Material material) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_NEW_MATERIAL);
            preparedStatement.setString(1, material.getId());
            preparedStatement.setString(2, material.getName());
            preparedStatement.setDouble(3, material.getPrice());
            preparedStatement.setDouble(4, material.getQuantityInStock());
            preparedStatement.execute();
            log.info("addMaterial [1]: Запись добавлена " + material);
            mongo.logHistory(UUID.randomUUID().toString(), material, "addMaterial", Status.SUCCES);

        } catch (SQLException ex) {
            log.error("addMaterial [2]: запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), material, "addMaterial", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    @Override
    public void addClient(Client client) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_NEW_CLIENT);
            preparedStatement.setString(1, client.getId());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getPhoneNumber());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getPassport());
            preparedStatement.execute();
            log.info("addClient [1]: Запись добавлена " + client);
            mongo.logHistory(UUID.randomUUID().toString(), client, "addClient", Status.SUCCES);

        } catch (SQLException ex) {
            log.error("addClient [2]: запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), client, "addClient", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    @Override
    public void addBuilding(Building building) throws IOException {
        log.debug("addBuilding [1]: class = " + building.getClass());

        switch (building.getClass().getSimpleName()) {
            case "ApartmentHouse" ->
                addApartmentHouse((ApartmentHouse) building);
            case "House" ->
                addHouse((House) building);
            case "Garage" ->
                addGarage((Garage) building);
            default -> {
                log.error("addBuilding [2]: недопустимый класс");
                throw new ClassCastException("Недопустимый класс");
            }
        }
    }

    private void addApartmentHouse(ApartmentHouse building) throws IOException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_NEW_APARTMENT_HOUSE);
            preparedStatement.setString(1, building.getId());
            preparedStatement.setInt(2, building.getNumberOfApartments());
            preparedStatement.setDouble(3, building.getSquare());
            preparedStatement.setInt(4, building.getNumberOfFloors());
            preparedStatement.setDate(5, Date.valueOf(building.getCompletionDate()));
            preparedStatement.setString(6, building.getOwner().getId());
            preparedStatement.setString(7, convertObjectToStrindId(building.getMaterials(), Material.class));
            preparedStatement.setString(8, convertListToString(building.getEngineeringSystems()));
            preparedStatement.setString(9, convertObjectToStrindId(building.getConstructionEquipments(), ConstructionEquipment.class));
            preparedStatement.setString(10, convertObjectToStrindId(building.getWorkers(), Worker.class));
            preparedStatement.execute();
            log.info("addApartmentHouse [1]: Запись добавлена " + building);
            mongo.logHistory(UUID.randomUUID().toString(), building, "addApartmentHouse", Status.SUCCES);

        } catch (SQLException ex) {
            log.error("addApartmentHouse [2]: запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), building, "addApartmentHouse", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    private void addHouse(House building) throws IOException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_NEW_HOUSE);
            preparedStatement.setString(1, building.getId());
            preparedStatement.setInt(2, building.getNumberOfRooms());
            preparedStatement.setDouble(3, building.getSquare());
            preparedStatement.setInt(4, building.getNumberOfFloors());
            preparedStatement.setDate(5, Date.valueOf(building.getCompletionDate()));
            preparedStatement.setString(6, building.getOwner().getId());
            preparedStatement.setString(7, convertObjectToStrindId(building.getMaterials(), Material.class));
            preparedStatement.setString(8, convertListToString(building.getEngineeringSystems()));
            preparedStatement.setString(9, convertObjectToStrindId(building.getConstructionEquipments(), ConstructionEquipment.class));
            preparedStatement.setString(10, convertObjectToStrindId(building.getWorkers(), Worker.class));
            preparedStatement.execute();
            log.info("addHouse [1]: Запись добавлена " + building);
            mongo.logHistory(UUID.randomUUID().toString(), building, "addHouse", Status.SUCCES);

        } catch (SQLException ex) {
            log.error("addHouse [2]: запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), building, "addHouse", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    private void addGarage(Garage building) throws IOException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_NEW_GARAGE);
            preparedStatement.setString(1, building.getId());
            preparedStatement.setInt(2, building.getNumberOfCars());
            preparedStatement.setDouble(3, building.getSquare());
            preparedStatement.setInt(4, building.getNumberOfFloors());
            preparedStatement.setDate(5, Date.valueOf(building.getCompletionDate()));
            preparedStatement.setString(6, building.getOwner().getId());
            preparedStatement.setString(7, convertObjectToStrindId(building.getMaterials(), Material.class));
            preparedStatement.setString(8, convertListToString(building.getEngineeringSystems()));
            preparedStatement.setString(9, convertObjectToStrindId(building.getConstructionEquipments(), ConstructionEquipment.class));
            preparedStatement.setString(10, convertObjectToStrindId(building.getWorkers(), Worker.class));
            preparedStatement.execute();
            log.info("addGarage [1]: Запись добавлена " + building);
            mongo.logHistory(UUID.randomUUID().toString(), building, "addGarage", Status.SUCCES);

        } catch (SQLException ex) {
            log.error("addGarage [2]: запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), building, "addGarage", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    @Override
    public void deleteWorker(String id) throws IOException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.DELETE_WORKER);
            preparedStatement.setString(1, id);

            if (preparedStatement.executeUpdate() == 0) {
                log.error("deleteWorker [1]: Запись с id = " + id + " для удаления не найдена");
                throw new NoSuchElementException("Запись не найдена");
            } else {
                log.info("deleteWorker [2]: Запись с id = " + id + " успешно удалена");
            }
        } catch (SQLException ex) {
            log.error("deleteWorker [3]: " + ex.getMessage());
        }

    }

    @Override
    public void deleteConstructionEquipment(String id) throws IOException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.DELETE_CONSTRUCTION_EQUIPMENT);
            preparedStatement.setString(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                log.error("deleteConstructionEquipment [1]: Запись с id = " + id + " для удаления не найдена");
                throw new NoSuchElementException("Запись не найдена");
            } else {
                log.info("deleteConstructionEquipment [2]: Запись с id = " + id + " успешно удалена");
            }
        } catch (SQLException ex) {
            log.error("deleteConstructionEquipment [3]: " + ex.getMessage());
        }
    }

    @Override
    public void deleteMaterial(String id) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.DELETE_MATERIAL);
            preparedStatement.setString(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                log.error("deleteMaterial [1]: Запись с id = " + id + " для удаления не найдена");
                throw new NoSuchElementException("Запись не найдена");
            } else {
                log.info("deleteMaterial [2]: Запись с id = " + id + " успешно удалена");
            }
        } catch (SQLException ex) {
            log.error("deleteMaterial [3]: " + ex.getMessage());
        }
    }

    @Override
    public void deleteClient(String id) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.DELETE_CLIENT);
            preparedStatement.setString(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                log.error("deleteClient [1]: Запись с id = " + id + " для удаления не найдена");
                throw new NoSuchElementException("Запись не найдена");
            } else {
                log.info("deleteClient [2]: Запись с id = " + id + " успешно удалена");
            }
        } catch (SQLException ex) {
            log.error("deleteClient [3]: " + ex.getMessage());
        }
    }

    @Override
    public void deleteBuilding(String id, Class<? extends Building> clazz) throws IOException {
        log.debug("deleteBuilding [1]: class = " + clazz.getSimpleName());

        switch (clazz.getSimpleName()) {
            case "ApartmentHouse" ->
                deleteApartmentHouse(id);
            case "House" ->
                deleteHouse(id);
            case "Garage" ->
                deleteGarage(id);
            default -> {
                log.error("deleteBuilding [2]: недопустимый класс");
                throw new ClassCastException("Недопустимый класс");
            }
        }
    }

    private void deleteApartmentHouse(String id) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.DELETE_APARTMENT_HOUSE);
            preparedStatement.setString(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                log.error("deleteApartmentHouse [1]: Запись с id = " + id + " для удаления не найдена");
                throw new NoSuchElementException("Запись не найдена");
            } else {
                log.info("deleteApartmentHouse [2]: Запись с id = " + id + " успешно удалена");
            }
        } catch (SQLException ex) {
            log.error("deleteApartmentHouse [3]: " + ex.getMessage());
        }
    }

    private void deleteHouse(String id) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.DELETE_HOUSE);
            preparedStatement.setString(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                log.error("deleteHouse [1]: Запись с id = " + id + " для удаления не найдена");
                throw new NoSuchElementException("Запись не найдена");
            } else {
                log.info("deleteHouse [2]: Запись с id = " + id + " успешно удалена");
            }
        } catch (SQLException ex) {
            log.error("deleteHouse [3]: " + ex.getMessage());
        }
    }

    private void deleteGarage(String id) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.DELETE_GARAGE);
            preparedStatement.setString(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                log.error("deleteGarage [1]: Запись с id = " + id + " для удаления не найдена");
                throw new NoSuchElementException("Запись не найдена");
            } else {
                log.info("deleteGarage [2]: Запись с id = " + id + " успешно удалена");
            }
        } catch (SQLException ex) {
            log.error("deleteGarage [3]: " + ex.getMessage());
        }
    }

    @Override
    public Optional<Worker> getWorker(String id) throws IOException {
        Worker object = new Worker();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_WORKER);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            object.setId(resultSet.getString(Constants.ID));
            object.setName(resultSet.getString(Constants.NAME));
            object.setPhoneNumber(resultSet.getString(Constants.PHONE_NUMBER));
            object.setJobTitle(resultSet.getString(Constants.JOB_TITTLE));
            object.setSalary(resultSet.getDouble(Constants.SALARY));

        } catch (SQLException ex) {
            log.debug("getWorker [1]: Запись c id = " + id + " не найдена");
            return Optional.empty();
        }
        log.info("getWorker [2]: optional = " + Optional.of(object));
        return Optional.of(object);
    }

    @Override
    public Optional<ConstructionEquipment> getConstructionEquipment(String id) throws IOException {
        ConstructionEquipment object = new ConstructionEquipment();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_CONSTRUCTION_EQUIPMENT);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            object.setId(resultSet.getString(Constants.ID));
            object.setName(resultSet.getString(Constants.NAME));
            object.setPrice(resultSet.getDouble(Constants.PRICE));

        } catch (SQLException ex) {
            log.debug("getConstructionEquipment [1]: Запись c id = " + id + " не найдена");
            return Optional.empty();
        }
        log.info("getConstructionEquipment [2]: optional = " + Optional.of(object));
        return Optional.of(object);
    }

    @Override
    public Optional<Material> getMaterial(String id) throws IOException {
        Material object = new Material();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_MATERIAL);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            object.setId(resultSet.getString(Constants.ID));
            object.setName(resultSet.getString(Constants.NAME));
            object.setPrice(resultSet.getDouble(Constants.PRICE));
            object.setQuantityInStock(resultSet.getDouble(Constants.QUANTITY_IN_STOCK));

        } catch (SQLException ex) {
            log.debug("getMaterial [1]: Запись c id = " + id + " не найдена");
            return Optional.empty();
        }
        log.info("getMaterial [2]: optional = " + Optional.of(object));
        return Optional.of(object);
    }

    @Override
    public Optional<Client> getClient(String id) throws IOException {
        Client object = new Client();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_CLIENT);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            object.setId(resultSet.getString(Constants.ID));
            object.setName(resultSet.getString(Constants.NAME));
            object.setPhoneNumber(resultSet.getString(Constants.PHONE_NUMBER));
            object.setEmail(resultSet.getString(Constants.EMAIL));
            object.setPassport(resultSet.getString(Constants.PASSPORT));

        } catch (SQLException ex) {
            log.debug("getClient [1]: Запись c id = " + id + " не найдена");
            return Optional.empty();
        }
        log.info("getClient [2]: optional = " + Optional.of(object));
        return Optional.of(object);
    }

    @Override
    public Optional<? extends Building> getBuilding(String id, Class<? extends Building> clazz) throws IOException {
        log.debug("getBuilding [1]: class = " + clazz.getSimpleName());
        Optional<? extends Building> optional = null;

        switch (clazz.getSimpleName()) {
            case "ApartmentHouse" ->
                optional = getApartmentHouse(id);
            case "House" ->
                optional = getHouse(id);
            case "Garage" ->
                optional = getGarage(id);
            default -> {
                log.error("getBuilding [2]: недопустимый класс");
                throw new ClassCastException("Недопустимый класс");
            }
        }
        return optional;
    }

    private Optional<ApartmentHouse> getApartmentHouse(String id) throws IOException {
        ApartmentHouse object = new ApartmentHouse();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_APARTMENT_HOUSE);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            object.setId(resultSet.getString(Constants.ID));
            object.setNumberOfApartments(resultSet.getInt(Constants.SQUARE));
            object.setSquare(resultSet.getDouble(Constants.NUMBER_OF_APARTMENTS));
            object.setNumberOfFloors(resultSet.getInt(Constants.NUMBER_OF_FLOORS));
            object.setCompletionDate(resultSet.getDate(Constants.COMPLETION_DATE).toLocalDate());
            object.setEngineeringSystems(convertStringToListOfEngineeringSystem(
                    resultSet.getString(Constants.ENGINEERING_SYSTEMS)));

            object.setOwner(getClient(resultSet.getString(Constants.OWNER_ID)).get());

            object.setMaterials(
                    (List<Material>) convertStringIdToListObject(
                            resultSet.getString(Constants.MATERIALS_ID), Material.class));

            object.setConstructionEquipments(
                    (List<ConstructionEquipment>) convertStringIdToListObject(
                            resultSet.getString(Constants.CONSTRUCTION_EQUIPMENTS_ID), ConstructionEquipment.class));

            object.setWorkers(
                    (List<Worker>) convertStringIdToListObject(
                            resultSet.getString(Constants.WORKERS_ID), Worker.class));

        } catch (SQLException ex) {
            log.debug("getApartmentHouse [1]: Запись c id = " + id + " не найдена");
            return Optional.empty();
        } catch (NoSuchElementException ex) {
            log.error("getApartmentHouse [2]: Запись c id = " + id + " не найдена");
            throw new NoSuchElementException("Запись не найдена");
        }
        log.info("getApartmentHouse [3]: optional = " + Optional.of(object));
        return Optional.of(object);
    }

    private Optional<House> getHouse(String id) throws IOException {
        House object = new House();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_HOUSE);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            object.setId(resultSet.getString(Constants.ID));
            object.setNumberOfRooms(resultSet.getInt(Constants.NUMBER_OF_ROOMS));
            object.setSquare(resultSet.getDouble(Constants.SQUARE));
            object.setNumberOfFloors(resultSet.getInt(Constants.NUMBER_OF_FLOORS));
            object.setCompletionDate(resultSet.getDate(Constants.COMPLETION_DATE).toLocalDate());
            object.setEngineeringSystems(convertStringToListOfEngineeringSystem(
                    resultSet.getString(Constants.ENGINEERING_SYSTEMS)));

            object.setOwner(getClient(resultSet.getString(Constants.OWNER_ID)).get());

            object.setMaterials(
                    (List<Material>) convertStringIdToListObject(
                            resultSet.getString(Constants.MATERIALS_ID), Material.class));

            object.setConstructionEquipments(
                    (List<ConstructionEquipment>) convertStringIdToListObject(
                            resultSet.getString(Constants.CONSTRUCTION_EQUIPMENTS_ID), ConstructionEquipment.class));

            object.setWorkers(
                    (List<Worker>) convertStringIdToListObject(
                            resultSet.getString(Constants.WORKERS_ID), Worker.class));

        } catch (SQLException ex) {
            log.debug("getHouse [1]: Запись c id = " + id + " не найдена");
            return Optional.empty();
        } catch (NoSuchElementException ex) {
            log.error("getHouse [2]: Запись c id = " + id + " не найдена");
            throw new NoSuchElementException("Запись не найдена");
        }
        log.info("getHouse [3]: optional = " + Optional.of(object));

        return Optional.of(object);
    }

    private Optional<Garage> getGarage(String id) throws IOException {
        Garage object = new Garage();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_GARAGE);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            object.setId(resultSet.getString(Constants.ID));
            object.setNumberOfCars(resultSet.getInt(Constants.NUMBER_OF_CARS));
            object.setSquare(resultSet.getDouble(Constants.SQUARE));
            object.setNumberOfFloors(resultSet.getInt(Constants.NUMBER_OF_FLOORS));
            object.setCompletionDate(resultSet.getDate(Constants.COMPLETION_DATE).toLocalDate());
            object.setEngineeringSystems(convertStringToListOfEngineeringSystem(
                    resultSet.getString(Constants.ENGINEERING_SYSTEMS)));

            object.setOwner(getClient(resultSet.getString(Constants.OWNER_ID)).get());

            object.setMaterials(
                    (List<Material>) convertStringIdToListObject(
                            resultSet.getString(Constants.MATERIALS_ID), Material.class));

            object.setConstructionEquipments(
                    (List<ConstructionEquipment>) convertStringIdToListObject(
                            resultSet.getString(Constants.CONSTRUCTION_EQUIPMENTS_ID), ConstructionEquipment.class));

            object.setWorkers(
                    (List<Worker>) convertStringIdToListObject(
                            resultSet.getString("workersId"), Worker.class));

        } catch (SQLException ex) {
            log.debug("getGarage [1]: Запись c id = " + id + " не найдена");
            return Optional.empty();
        } catch (NoSuchElementException ex) {
            log.error("getGarage [2]: Запись c id = " + id + " не найдена");
            throw new NoSuchElementException("Запись не найдена");
        }
        log.info("getHouse [3]: optional = " + Optional.of(object));

        return Optional.of(object);
    }

    @Override
    public <T> List<T> getAllRecords(Class clazz, String path) throws IOException {

        log.debug("getAllRecords [1]: class = " + clazz.getSimpleName());

        String str = Constants.SELECT_ALL_FROM.concat(path);

        List list;
        switch (clazz.getSimpleName()) {

            case "Worker" ->
                list = getAllWorkerRecords(str);
            case "ConstructionEquipment" ->
                list = getAllConstructionEquipmentsRecords(str);
            case "Material" ->
                list = getAllMaterialRecords(str);
            case "Client" ->
                list = getAllClientRecords(str);
            case "ApartmentHouse" ->
                list = getAllApartmentHouseRecords(str);
            case "House" ->
                list = getAllHouseRecords(str);
            case "Garage" ->
                list = getAllGarageRecords(str);
            default ->
                throw new ClassCastException("Недопустимый класс");

        }
        return list;
    }

    private List<Worker> getAllWorkerRecords(String path) throws IOException {
        List<Worker> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(path);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Worker object = new Worker();

                object.setId(resultSet.getString(Constants.ID));
                object.setName(resultSet.getString(Constants.NAME));
                object.setPhoneNumber(resultSet.getString(Constants.PHONE_NUMBER));
                object.setJobTitle(resultSet.getString(Constants.JOB_TITTLE));
                object.setSalary(resultSet.getDouble(Constants.SALARY));

                list.add(object);

            }
        } catch (SQLException ex) {
            log.error("getAllWorkerRecords [1]: некорректный запрос " + path);
            throw new IOException("некорректный запрос " + path);
        }
        return list;
    }

    private List<ConstructionEquipment> getAllConstructionEquipmentsRecords(String path) throws IOException {
        List<ConstructionEquipment> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(path);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ConstructionEquipment object = new ConstructionEquipment();

                object.setId(resultSet.getString(Constants.ID));
                object.setName(resultSet.getString(Constants.NAME));
                object.setPrice(resultSet.getDouble(Constants.PRICE));

                list.add(object);

            }
        } catch (SQLException ex) {
            log.error("getAllConstructionEquipmentsRecords [1]: некорректный запрос " + path);
            throw new IOException("некорректный запрос " + path);
        }
        return list;
    }

    private List<Material> getAllMaterialRecords(String path) throws IOException {
        List<Material> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(path);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Material object = new Material();

                object.setId(resultSet.getString(Constants.ID));
                object.setName(resultSet.getString(Constants.NAME));
                object.setPrice(resultSet.getDouble(Constants.PRICE));
                object.setQuantityInStock(resultSet.getDouble(Constants.QUANTITY_IN_STOCK));

                list.add(object);

            }
        } catch (SQLException ex) {
            log.error("getAllMaterialRecords [1]: некорректный запрос " + path);
            throw new IOException("некорректный запрос " + path);
        }
        return list;
    }

    private List<Client> getAllClientRecords(String path) throws IOException {
        List<Client> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(path);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Client object = new Client();

                object.setId(resultSet.getString(Constants.ID));
                object.setName(resultSet.getString(Constants.NAME));
                object.setPhoneNumber(resultSet.getString(Constants.PHONE_NUMBER));
                object.setEmail(resultSet.getString(Constants.EMAIL));
                object.setPassport(resultSet.getString(Constants.PASSPORT));

                list.add(object);

            }
        } catch (SQLException ex) {
            log.error("getAllClientRecords [1]: некорректный запрос " + path);
            throw new IOException("некорректный запрос " + path);
        }
        return list;
    }

    private List<ApartmentHouse> getAllApartmentHouseRecords(String path) throws IOException {
        List<ApartmentHouse> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(path);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ApartmentHouse object = new ApartmentHouse();

                object.setId(resultSet.getString(Constants.ID));
                object.setNumberOfApartments(resultSet.getInt(Constants.SQUARE));
                object.setSquare(resultSet.getDouble(Constants.NUMBER_OF_APARTMENTS));
                object.setNumberOfFloors(resultSet.getInt(Constants.NUMBER_OF_FLOORS));
                object.setCompletionDate(resultSet.getDate(Constants.COMPLETION_DATE).toLocalDate());
                object.setEngineeringSystems(convertStringToListOfEngineeringSystem(
                        resultSet.getString(Constants.ENGINEERING_SYSTEMS)));

                object.setOwner(getClient(resultSet.getString(Constants.OWNER_ID)).get());

                object.setMaterials(
                        (List<Material>) convertStringIdToListObject(
                                resultSet.getString(Constants.MATERIALS_ID), Material.class));

                object.setConstructionEquipments(
                        (List<ConstructionEquipment>) convertStringIdToListObject(
                                resultSet.getString(Constants.CONSTRUCTION_EQUIPMENTS_ID), ConstructionEquipment.class));

                object.setWorkers(
                        (List<Worker>) convertStringIdToListObject(
                                resultSet.getString(Constants.WORKERS_ID), Worker.class));

                list.add(object);

            }
        } catch (SQLException ex) {
            log.error("getAllApartmentHouseRecords [1]: некорректный запрос " + path);
            throw new IOException("некорректный запрос " + path);
        }
        return list;
    }

    private List<House> getAllHouseRecords(String path) throws IOException {
        List<House> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(path);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                House object = new House();

                object.setId(resultSet.getString(Constants.ID));
                object.setNumberOfRooms(resultSet.getInt(Constants.NUMBER_OF_ROOMS));
                object.setSquare(resultSet.getDouble(Constants.SQUARE));
                object.setNumberOfFloors(resultSet.getInt(Constants.NUMBER_OF_FLOORS));
                object.setCompletionDate(resultSet.getDate(Constants.COMPLETION_DATE).toLocalDate());
                object.setEngineeringSystems(convertStringToListOfEngineeringSystem(
                        resultSet.getString(Constants.ENGINEERING_SYSTEMS)));

                object.setOwner(getClient(resultSet.getString(Constants.OWNER_ID)).get());

                object.setMaterials(
                        (List<Material>) convertStringIdToListObject(
                                resultSet.getString(Constants.MATERIALS_ID), Material.class));

                object.setConstructionEquipments(
                        (List<ConstructionEquipment>) convertStringIdToListObject(
                                resultSet.getString(Constants.CONSTRUCTION_EQUIPMENTS_ID), ConstructionEquipment.class));

                object.setWorkers(
                        (List<Worker>) convertStringIdToListObject(
                                resultSet.getString(Constants.WORKERS_ID), Worker.class));

                list.add(object);

            }
        } catch (SQLException ex) {
            log.error("getAllHouseRecords [1]: некорректный запрос " + path);
            throw new IOException("некорректный запрос " + path);
        }
        return list;
    }

    private List<Garage> getAllGarageRecords(String path) throws IOException {
        List<Garage> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(path);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Garage object = new Garage();

                object.setId(resultSet.getString(Constants.ID));
                object.setNumberOfCars(resultSet.getInt(Constants.NUMBER_OF_CARS));
                object.setSquare(resultSet.getDouble(Constants.SQUARE));
                object.setNumberOfFloors(resultSet.getInt(Constants.NUMBER_OF_FLOORS));
                object.setCompletionDate(resultSet.getDate(Constants.COMPLETION_DATE).toLocalDate());
                object.setEngineeringSystems(convertStringToListOfEngineeringSystem(
                        resultSet.getString(Constants.ENGINEERING_SYSTEMS)));

                object.setOwner(getClient(resultSet.getString(Constants.OWNER_ID)).get());

                object.setMaterials(
                        (List<Material>) convertStringIdToListObject(
                                resultSet.getString(Constants.MATERIALS_ID), Material.class));

                object.setConstructionEquipments(
                        (List<ConstructionEquipment>) convertStringIdToListObject(
                                resultSet.getString(Constants.CONSTRUCTION_EQUIPMENTS_ID), ConstructionEquipment.class));

                object.setWorkers(
                        (List<Worker>) convertStringIdToListObject(
                                resultSet.getString("workersId"), Worker.class));

                list.add(object);

            }
        } catch (SQLException ex) {
            log.error("getAllGarageRecords [1]: некорректный запрос " + path);
            throw new IOException("некорректный запрос " + path);
        }
        return list;
    }

    @Override
    public void updateWorker(String id, Worker object) throws IOException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_WORKER);
            preparedStatement.setString(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setString(3, object.getPhoneNumber());
            preparedStatement.setString(4, object.getJobTitle());
            preparedStatement.setDouble(5, object.getSalary());
            preparedStatement.setString(6, id);

            if (preparedStatement.executeUpdate() > 0) {
                log.info("updateWorker [1]: Запись с id = " + id + " обновлена на " + object);
                mongo.logHistory(UUID.randomUUID().toString(), object, "updateWorker", Status.SUCCES);
            } else {

                log.error("updateWorker [2]: Object с id = " + id + " не найден, обновление не удалось");
                mongo.logHistory(UUID.randomUUID().toString(), object, "updateWorker", Status.FAULT);
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (SQLException ex) {
            log.error("updateWorker [3]: такая запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), object, "updateWorker", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }

    }

    @Override
    public void updateConstructionEquipment(String id, ConstructionEquipment object) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_CONSTRUCTION_EQUIPMENT);
            preparedStatement.setString(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setDouble(3, object.getPrice());
            preparedStatement.setString(4, id);

            if (preparedStatement.executeUpdate() > 0) {
                log.info("updateConstructionEquipment [1]: Запись с id = " + id + " обновлена на " + object);
                mongo.logHistory(UUID.randomUUID().toString(), object, "updateConstructionEquipment", Status.SUCCES);
            } else {

                log.error("updateConstructionEquipment [2]: Object с id = " + id + " не найден, обновление не удалось");
                mongo.logHistory(UUID.randomUUID().toString(), object, "updateConstructionEquipment", Status.FAULT);
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (SQLException ex) {
            log.error("updateConstructionEquipment [3]: такая запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), object, "updateConstructionEquipment", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    @Override
    public void updateMaterial(String id, Material object) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_MATERIAL);
            preparedStatement.setString(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setDouble(3, object.getPrice());
            preparedStatement.setDouble(4, object.getQuantityInStock());
            preparedStatement.setString(5, id);

            if (preparedStatement.executeUpdate() > 0) {
                log.info("updateMaterial [1]: Запись с id = " + id + " обновлена на " + object);
                mongo.logHistory(UUID.randomUUID().toString(), object, "updateMaterial", Status.SUCCES);
            } else {

                log.error("updateMaterial [2]: Object с id = " + id + " не найден, обновление не удалось");
                mongo.logHistory(UUID.randomUUID().toString(), object, "updateMaterial", Status.FAULT);
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (SQLException ex) {
            log.error("updateMaterial [3]: такая запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), object, "updateMaterial", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    @Override
    public void updateClient(String id, Client object) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_CLIENT);
            preparedStatement.setString(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setString(3, object.getPhoneNumber());
            preparedStatement.setString(4, object.getEmail());
            preparedStatement.setString(5, object.getPassport());
            preparedStatement.setString(6, id);

            if (preparedStatement.executeUpdate() > 0) {
                log.info("updateClient [1]: Запись с id = " + id + " обновлена на " + object);
                mongo.logHistory(UUID.randomUUID().toString(), object, "updateClient", Status.SUCCES);
            } else {

                log.error("updateClient [2]: Object с id = " + id + " не найден, обновление не удалось");
                mongo.logHistory(UUID.randomUUID().toString(), object, "updateClient", Status.FAULT);
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (SQLException ex) {
            log.error("updateClient [3]: такая запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), object, "updateClient", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    @Override
    public void updateBuilding(String id, Building building) throws IOException {

        log.debug("updateBuilding [1]: class = " + building.getClass());
        switch (building.getClass().getSimpleName()) {
            case "ApartmentHouse" ->
                updateApartmentHouse(id, (ApartmentHouse) building);
            case "House" ->
                updateHouse(id, (House) building);
            case "Garage" ->
                updateGarage(id, (Garage) building);
            default ->
                throw new ClassCastException("Недопустимый класс");
        }

    }

    public void updateApartmentHouse(String id, ApartmentHouse building) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_APARTMENT_HOUSE);
            preparedStatement.setString(1, building.getId());
            preparedStatement.setInt(2, building.getNumberOfApartments());
            preparedStatement.setDouble(3, building.getSquare());
            preparedStatement.setInt(4, building.getNumberOfFloors());
            preparedStatement.setDate(5, Date.valueOf(building.getCompletionDate()));
            preparedStatement.setString(6, building.getOwner().getId());
            preparedStatement.setString(7, convertObjectToStrindId(building.getMaterials(), Material.class));
            preparedStatement.setString(8, convertListToString(building.getEngineeringSystems()));
            preparedStatement.setString(9, convertObjectToStrindId(building.getConstructionEquipments(), ConstructionEquipment.class));
            preparedStatement.setString(10, convertObjectToStrindId(building.getWorkers(), Worker.class));
            preparedStatement.setString(11, id);

            if (preparedStatement.executeUpdate() > 0) {
                log.info("updateApartmentHouse [1]: Запись с id = " + id + " обновлена на " + building);
                mongo.logHistory(UUID.randomUUID().toString(), building, "updateApartmentHouse", Status.SUCCES);
            } else {

                log.error("updateApartmentHouse [2]: Object с id = " + id + " не найден, обновление не удалось");
                mongo.logHistory(UUID.randomUUID().toString(), building, "updateApartmentHouse", Status.FAULT);
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (SQLException ex) {
            log.error("updateApartmentHouse [3]: такая запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), building, "updateApartmentHouse", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    public void updateHouse(String id, House building) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_HOUSE);
            preparedStatement.setString(1, building.getId());
            preparedStatement.setInt(2, building.getNumberOfRooms());
            preparedStatement.setDouble(3, building.getSquare());
            preparedStatement.setInt(4, building.getNumberOfFloors());
            preparedStatement.setDate(5, Date.valueOf(building.getCompletionDate()));
            preparedStatement.setString(6, building.getOwner().getId());
            preparedStatement.setString(7, convertObjectToStrindId(building.getMaterials(), Material.class));
            preparedStatement.setString(8, convertListToString(building.getEngineeringSystems()));
            preparedStatement.setString(9, convertObjectToStrindId(building.getConstructionEquipments(), ConstructionEquipment.class));
            preparedStatement.setString(10, convertObjectToStrindId(building.getWorkers(), Worker.class));
            preparedStatement.setString(11, id);

            if (preparedStatement.executeUpdate() > 0) {
                log.info("updateHouse [1]: Запись с id = " + id + " обновлена на " + building);
                mongo.logHistory(UUID.randomUUID().toString(), building, "updateHouse", Status.SUCCES);
            } else {

                log.error("updateHouse [2]: Object с id = " + id + " не найден, обновление не удалось");
                mongo.logHistory(UUID.randomUUID().toString(), building, "updateHouse", Status.FAULT);
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (SQLException ex) {
            log.error("updateHouse [3]: такая запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), building, "updateHouse", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    public void updateGarage(String id, Garage building) throws IOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_GARAGE);
            preparedStatement.setString(1, building.getId());
            preparedStatement.setInt(2, building.getNumberOfCars());
            preparedStatement.setDouble(3, building.getSquare());
            preparedStatement.setInt(4, building.getNumberOfFloors());
            preparedStatement.setDate(5, Date.valueOf(building.getCompletionDate()));
            preparedStatement.setString(6, building.getOwner().getId());
            preparedStatement.setString(7, convertObjectToStrindId(building.getMaterials(), Material.class));
            preparedStatement.setString(8, convertListToString(building.getEngineeringSystems()));
            preparedStatement.setString(9, convertObjectToStrindId(building.getConstructionEquipments(), ConstructionEquipment.class));
            preparedStatement.setString(10, convertObjectToStrindId(building.getWorkers(), Worker.class));
            preparedStatement.setString(11, id);

            if (preparedStatement.executeUpdate() > 0) {
                log.info("updateGarage [1]: Запись с id = " + id + " обновлена на " + building);
                mongo.logHistory(UUID.randomUUID().toString(), building, "updateGarage", Status.SUCCES);
            } else {

                log.error("updateGarage [2]: Object с id = " + id + " не найден, обновление не удалось");
                mongo.logHistory(UUID.randomUUID().toString(), building, "updateGarage", Status.FAULT);
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (SQLException ex) {
            log.error("updateGarage [3]: такая запись уже есть");
            mongo.logHistory(UUID.randomUUID().toString(), building, "updateGarage", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }
    }

    private void dataBaseConnection() {
        log.debug("dataBaseConnection [1]:");
        try {
            connection = DriverManager.getConnection(
                    configurationUtil.getConfigurationValue(Constants.PATH_TO_DB),
                    configurationUtil.getConfigurationValue(Constants.USERNAME_TO_DB),
                    configurationUtil.getConfigurationValue(Constants.PASSWORD_TO_DB));
            log.info("dataBaseConnection [2]: Соединение успешно");
        } catch (SQLException ex) {
            log.error("dataBaseConnection [3]: " + ex.getMessage());
            System.exit(1);
        }
    }

    private void dataBaseCloseConnection() {
        try {
            connection.close();
            log.info("dataBaseCloseConnection [1]: Соединение закрыто");
        } catch (SQLException ex) {
            log.error("dataBaseCloseConnection [2]: " + ex.getMessage());
        }
    }

    private String convertListToString(List<?> list) {
        String str;
        try {
            str = list.toString();
            str = str.substring(1, str.length() - 1);
        } catch (NullPointerException ex) {
            return "";
        }
        return str;
    }

    private List<String> convertStringToListOfString(String str) {
        List list = Arrays.asList((str.split(", ")));
        return list;
    }

    private List<EngineeringSystem> convertStringToListOfEngineeringSystem(String str) {

        if (str.length() == 0) {
            return null;
        }

        List<EngineeringSystem> list;
        List l = Arrays.asList((str.split(", ")));
        list = l.stream().map(el -> EngineeringSystem.valueOf(el.toString())).toList();

        return list;
    }

    private <T> String convertObjectToStrindId(List<T> obj, Class clazz) {

        log.debug("convertObjectToStrindId [1]: class = " + clazz.getSimpleName());

        if (obj == null || obj.size() == 0) {
            log.debug("convertObjectToStrindId [2]: список пуст");
            return "";
        }

        String str = "s";

        switch (clazz.getSimpleName()) {

            case "Worker" -> {
                List<Worker> list = (List<Worker>) obj;
                str = list.stream().map(el -> el.getId().concat("/")).toList()
                        .stream().reduce("", (accumulator, el) -> accumulator += el);

            }
            case "ConstructionEquipment" -> {
                List<ConstructionEquipment> list = (List<ConstructionEquipment>) obj;
                str = list.stream().map(el -> el.getId().concat("/")).toList()
                        .stream().reduce("", (accumulator, el) -> accumulator += el);

            }
            case "Material" -> {
                List<Material> list = (List<Material>) obj;
                str = list.stream().map(el -> el.getId().concat("/")).toList()
                        .stream().reduce("", (accumulator, el) -> accumulator += el);

            }

            default -> {
                log.error("convertObjectToStrindId [3]: недопустимый класс");
                throw new ClassCastException("Недопустимый класс");
            }

        }

        return str;

    }

    private List<?> convertStringIdToListObject(String string, Class clazz) {

        log.debug("convertListIdToListObject [1]: class = " + clazz.getSimpleName());

        if (string.length() == 0) {
            log.debug("convertListIdToListObject [2]: список пуст");
            return null;
        }

        String[] str = string.split("/");

        switch (clazz.getSimpleName()) {

            case "Worker" -> {

                List<Worker> list = new ArrayList<>();

                Arrays.stream(str).forEach(el -> {
                    try {
                        list.add(getWorker(el).get());
                    } catch (IOException ex) {
                        log.error("convertStringIdToListObject [3]: " + ex.getMessage());
                    } catch (NoSuchElementException x) {
                        log.info("convertStringIdToListObject [4]: работник с id = " + el + " не найден");
                        throw new NoSuchElementException("работник не найден");
                    }
                });
                return list;

            }

            case "Material" -> {
                List<Material> list = new ArrayList<>();

                Arrays.stream(str).forEach(el -> {
                    try {
                        list.add(getMaterial(el).get());
                    } catch (IOException ex) {
                        log.error("convertStringIdToListObject [3]: " + ex.getMessage());
                    } catch (NoSuchElementException x) {
                        log.info("convertStringIdToListObject [4]: материал с id = " + el + " не найден");
                        throw new NoSuchElementException("материал не найден");
                    }
                });
                return list;
            }

            case "ConstructionEquipment" -> {
                List<ConstructionEquipment> list = new ArrayList<>();

                Arrays.stream(str).forEach(el -> {
                    try {
                        list.add(getConstructionEquipment(el).get());
                    } catch (IOException ex) {
                        log.error("convertStringIdToListObject [3]: " + ex.getMessage());
                    } catch (NoSuchElementException x) {
                        log.info("convertStringIdToListObject [4]: строительное оборудование с id = " + el + " не найдено");
                        throw new NoSuchElementException("строительное оборудование не найдено");
                    }
                });
                return list;
            }
            default -> {
                log.error("convertStringIdToListObject [5]: недопустимый класс");
                throw new ClassCastException("Недопустимый класс");
            }

        }

    }

    public void createAllTables() {
        log.debug("createAllTables [1]:");
        createWorkerTable();
        createConstructioEquipmentTable();
        createMaterialTable();
        createClientTable();
        createApartmentHouseTable();
        createHouseTable();
        createGarageTable();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            log.error("createAllTables [1]: " + ex.getMessage());
        }
    }

    public void createWorkerTable() {
        log.debug("createWorkerTable [1]:");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.CREATE_WORKER_TABLE);
            try {
                preparedStatement.execute();
                log.debug("createWorkerTable [2]: таблица создана");
            } catch (PSQLException ex) {
                log.debug("createWorkerTable [3]: Таблица уже существует");
            }

        } catch (SQLException ex) {
            log.error("createWorkerTable [4]: " + ex.getMessage());
        }

    }

    public void createConstructioEquipmentTable() {
        log.debug("createConstructioEquipmentTable [1]:");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.CREATE_CONSTRUCTION_EQIPMENT_TABLE);
            try {
                preparedStatement.execute();
                log.debug("createConstructioEquipmentTable [2]: таблица создана");
            } catch (PSQLException ex) {
                log.debug("createConstructioEquipmentTable [3]: Таблица уже существует");
            }

        } catch (SQLException ex) {
            log.error("createConstructioEquipmentTable [4]: " + ex.getMessage());
        }
    }

    public void createMaterialTable() {
        log.debug("createMaterialTable [1]:");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.CREATE_MATERIAL_TABLE);
            try {
                preparedStatement.execute();
                log.debug("createMaterialTable [2]: таблица создана");
            } catch (PSQLException ex) {
                log.debug("createMaterialTable [3]: Таблица уже существует");
            }

        } catch (SQLException ex) {
            log.error("createMaterialTable [4]: " + ex.getMessage());
        }
    }

    public void createClientTable() {
        log.debug("createClientTable [1]:");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.CREATE_CLIENT_TABLE);
            try {
                preparedStatement.execute();
                log.debug("createClientTable [2]: таблица создана");
            } catch (PSQLException ex) {
                log.debug("createClientTable [3]: Таблица уже существует");
            }

        } catch (SQLException ex) {
            log.error("createClientTable [4]: " + ex.getMessage());
        }
    }

    public void createApartmentHouseTable() {
        log.debug("createApartmentHouseTable [1]:");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.CREATE_APARTMENT_HOUSE_TABLE);
            try {
                preparedStatement.execute();
                log.debug("createApartmentHouseTable [2]: таблица создана");
            } catch (PSQLException ex) {
                log.debug("createApartmentHouseTable [3]: Таблица уже существует");
            }

        } catch (SQLException ex) {
            log.error("createApartmentHouseTable [4]: " + ex.getMessage());
        }
    }

    public void createHouseTable() {
        log.debug("createHouseTable [1]:");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.CREATE_HOUSE_TABLE);
            try {
                preparedStatement.execute();
                log.debug("createHouseTable [2]: таблица создана");
            } catch (PSQLException ex) {
                log.debug("createHouseTable [3]: Таблица уже существует");
            }

        } catch (SQLException ex) {
            log.error("createHouseTable [4]: " + ex.getMessage());
        }
    }

    public void createGarageTable() {
        log.debug("createGarageTable [1]:");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.CREATE_GARAGE_TABLE);
            try {
                preparedStatement.execute();
                log.debug("createGarageTable [2]: таблица создана");
            } catch (PSQLException ex) {
                log.debug("createGarageTable [3]: Таблица уже существует");
            }

        } catch (SQLException ex) {
            log.error("createGarageTable [4]: " + ex.getMessage());
        }
    }

    public void deleteTable(String tableName) {
        log.debug("deleteTable [1]:");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE " + tableName);
            preparedStatement.execute();
            log.info("deleteTable [2]: Таблица " + tableName + " удалена");

        } catch (SQLException ex) {
            log.error("deleteTable [3]: " + ex.getMessage());
        }
    }
}
