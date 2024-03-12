package ru.sfedu.buildingconstruction.api;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.model.*;

/**
 *
 * @author maksim
 */
public class DataProviderCSV implements DataProvider {

    private static Logger log = Logger.getLogger(DataProviderCSV.class);
    private MongoProvider mongo = new MongoProvider();

    public DataProviderCSV() {
        log.debug("DataProviderCSV [0]:starting application.....");
    }

    @Override
    public void preparationOfConstructionPlan(Building building, Client client, List<Material> materials, List<EngineeringSystem> systems) throws IOException {
        building.setMaterials(materials);
        building.setOwner(client);
        building.setEngineeringSystems(systems);
        building.setId(UUID.randomUUID().toString());
        addBuilding(building);

        log.info("id добавленного здания = " + building.getId());

    }

    @Override
    public void preparationForBuilding(Building building) {

        List<Worker> workers = distributionOfWorkers(building, Constants.PATH_TO_WORKER_CSV_FILE);
        List<ConstructionEquipment> equipments = distributionOfConstructionEquipment(building, Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE);
        LocalDate date = coordinationOfConstructionTerms(building);


        building.setWorkers(workers);
        building.setConstructionEquipments(equipments);
        building.setCompletionDate(date);

        try {
            updateBuilding(building.getId(), building);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            System.exit(1);
        }

    }

    
    @Override
    public void calculationOfTheTotalCost(Building building) {

        long sum = 0l;

        switch (building.getClass().getSimpleName()) {

            case "ApartmentHouse" -> {
                sum = Constants.PRICE_TO_BUILD_AN_APARTMENT_HOUSE;
            }
            case "House" -> {
                sum = Constants.PRICE_TO_BUILD_A_HOUSE;
            }
            case "Garage" -> {
                sum = Constants.PRICE_TO_BUILD_A_GARAGE;
            }

        }

        log.info("Стоимотсь постройки дома = " + sum);

    }

    @Override
    public void addWorker(Worker worker) throws IOException {

        List<Worker> list = deserialize(Worker.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE));

        log.info("addWorker [1]: Список получен, длина = " + list.size());
        recordValidation(worker, list,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE));
    }

    @Override
    public void addConstructionEquipment(ConstructionEquipment constructionEquipment) throws IOException {
        List<ConstructionEquipment> list = deserialize(ConstructionEquipment.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE));

        log.info("addConstructionEquipment [1]: Список получен, длина = " + list.size());
        recordValidation(constructionEquipment, list,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE));
    }

    @Override
    public void addMaterial(Material material) throws IOException {
        List<Material> list = deserialize(Material.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE));

        log.info("addMaterial [1]: Список получен, длина = " + list.size());
        recordValidation(material, list,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE));
    }

    @Override
    public void addClient(Client client) throws IOException {
        List<Client> list = deserialize(Client.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE));

        log.info("addClient [1]: Список получен, длина = " + list.size());
        recordValidation(client, list,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE));
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

    @Override
    public void deleteWorker(String id) throws IOException {
        List<Worker> list = deserialize(Worker.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE));

        log.debug("deleteWorker [1]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            serializeList(list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE), Worker.class);

            log.info("deleteWorker [2]: Запись с id = " + id + " удалена");
        } else {

            log.error("deleteWorker [3]: Worker с id = " + id + " не найден, удаление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }
    }

    @Override
    public void deleteConstructionEquipment(String id) throws IOException {
        List<ConstructionEquipment> list = deserialize(ConstructionEquipment.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE));

        log.debug("deleteConstructionEquipment [1]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            serializeList(list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE), ConstructionEquipment.class);

            log.info("deleteConstructionEquipment [2]: Запись с id = " + id + " удалена");
        } else {

            log.error("deleteConstructionEquipment [3]: ConstructionEquipment с id = " + id + " не найден, удаление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }

    }

    @Override
    public void deleteMaterial(String id) throws IOException {
        List<Material> list = deserialize(Material.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE));

        log.debug("deleteMaterial [1]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            serializeList(list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE), Material.class);

            log.info("deleteMaterial [2]:Запись с id = " + id + " удалена");
        } else {

            log.error("deleteMaterial [3]: Material с id = " + id + " не найден, удаление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }
    }

    @Override
    public void deleteClient(String id) throws IOException {
        List<Client> list = deserialize(Client.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE));

        log.debug("deleteClient [1]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            serializeList(list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE), Client.class);

            log.info("deleteClient [2]: Запись с id = " + id + " удалена");
        } else {

            log.error("deleteClient [3]: Client с id = " + id + " не найден, удаление не удалось");
            throw new NoSuchElementException("Запись не найдена");
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

    @Override
    public Optional<Material> getMaterial(String id) throws IOException {
        List<Material> list = deserialize(Material.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE));

        log.debug("getMaterial [1]: Список получен, длина = " + list.size());
        Optional<Material> optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
        log.info("getMaterial [2]: optional = " + optional);
        return optional;
    }

    @Override
    public Optional<Worker> getWorker(String id) throws IOException {
        List<Worker> list = deserialize(Worker.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE));

        log.debug("getWorker [1]: Список получен, длина = " + list.size());
        Optional<Worker> optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
        log.info("getWorker [2]: optional = " + optional);
        return optional;
    }

    @Override
    public Optional<ConstructionEquipment> getConstructionEquipment(String id) throws IOException {
        List<ConstructionEquipment> list = deserialize(ConstructionEquipment.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE));

        log.debug("getConstructionEquipment [1]: Список получен, длина = " + list.size());
        Optional<ConstructionEquipment> optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
        log.info("getConstructionEquipment [2]: optional = " + optional);
        return optional;
    }

    @Override
    public Optional<Client> getClient(String id) throws IOException {
        List<Client> list = deserialize(Client.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE));

        log.debug("getClient [1]: Список получен, длина = " + list.size());
        Optional<Client> optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
        log.info("getClient [2]: optional = " + optional);
        return optional;
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

    @Override
    public <T> List<T> getAllRecords(Class clazz, String path) throws IOException {

        List<T> list = deserialize(clazz, path);
        log.debug("getAllRecords [1]: class = " + clazz + ", путь = " + path + ", список получен, длина = " + list.size());
        return list;
    }

    @Override
    public void updateWorker(String id, Worker worker) throws IOException {
        List<Worker> list = deserialize(Worker.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE));

        log.debug("updateWorker [1]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            recordValidationList(worker, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE));

            log.info("updateWorker [2]: Запись с id = " + id + " обновлена на " + worker);
        } else {

            log.error("updateWorker [3]: Worker с id = " + id + " не найден, обновление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }
    }

    @Override
    public void updateConstructionEquipment(String id, ConstructionEquipment constructionEquipment) throws IOException {
        List<ConstructionEquipment> list = deserialize(ConstructionEquipment.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE));

        log.debug("updateConstructionEquipment [1]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            recordValidationList(constructionEquipment, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE));

            log.info("updateConstructionEquipment [2]: Запись с id = " + id + " обновлена на " + constructionEquipment);
        } else {

            log.error("updateConstructionEquipment [3]: ConstructionEquipment с id = " + id + " не найден, обновление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }

    }

    @Override
    public void updateMaterial(String id, Material material) throws IOException {
        List<Material> list = deserialize(Material.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE));

        log.debug("updateMaterial [1]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            recordValidationList(material, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE));
            log.info("updateMaterial [2]: Запись с id = " + id + " обновлена на " + material);
        } else {

            log.error("updateMaterial [3]: Material с id = " + id + " не найден, обновление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }

    }

    @Override
    public void updateClient(String id, Client client) throws IOException {
        List<Client> list = deserialize(Client.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE));

        log.debug("updateClient [1]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            recordValidationList(client, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE));
            log.info("updateClient [2]: Запись с id = " + id + " обновлена на " + client);
        } else {

            log.error("updateClient [3]: Client с id = " + id + " не найден, обновление не удалось");
            throw new NoSuchElementException("Запись не найдена");
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
            default -> {
                log.error("updateBuilding [2]: недопустимый класс");
                throw new ClassCastException("Недопустимый класс");
            }
        }
    }

    public void clearFile(String path) throws IOException {
        serializeList(null, path, Class.class);
        log.debug("clearFile [1]: Файл " + path + " пуст");

    }

    private void addApartmentHouse(ApartmentHouse apartmentHouse) throws IOException {

        List<ApartmentHouse> list = deserialize(ApartmentHouse.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE));

        log.debug("addApartmentHouse [2]: Список получен, длина = " + list.size());
        recordValidation(apartmentHouse, list,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE));
    }

    private void addHouse(House house) throws IOException {
        List<House> list = deserialize(House.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE));

        log.debug("addHouse [2]: Список получен, длина = " + list.size());
        recordValidation(house, list,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE));
    }

    private void addGarage(Garage garage) throws IOException {
        List<Garage> list = deserialize(Garage.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE));

        log.debug("addGarage [2]: Список получен, длина = " + list.size());
        recordValidation(garage, list,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE));

    }

    private void deleteApartmentHouse(String id) throws IOException {
        List<ApartmentHouse> list = deserialize(ApartmentHouse.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE));

        log.debug("deleteApartmentHouse [2]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            serializeList(list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE), ApartmentHouse.class);

            log.info("deleteApartmentHouse [3]: Запись с id = " + id + " удалена");
        } else {

            log.error("deleteApartmentHouse [4]: ApartmentHouse с id = " + id + " не найден, удаление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }
    }

    private void deleteHouse(String id) throws IOException {
        List<House> list = deserialize(House.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE));

        log.debug("deleteHouse [2]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            serializeList(list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE), House.class);

            log.info("deleteHouse [3]: Запись с id = " + id + " удалена");
        } else {

            log.error("deleteHouse [4]: House с id = " + id + " не найден, удаление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }
    }

    private void deleteGarage(String id) throws IOException {
        List<Garage> list = deserialize(Garage.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE));

        log.debug("deleteGarage [2]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            serializeList(list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE), Garage.class);
            log.info("deleteGarage [3]: Запись с id = " + id + " удалена");
        } else {

            log.error("deleteGarage [4]: Garage с id = " + id + " не найден, удаление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }
    }

    private Optional<ApartmentHouse> getApartmentHouse(String id) throws IOException {
        List<ApartmentHouse> list = deserialize(ApartmentHouse.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE));

        log.debug("getApartmentHouse [2]: Список получен, длина = " + list.size());
        Optional<ApartmentHouse> optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
        log.info("getApartmentHouse [3]: optional = " + optional);
        return optional;
    }

    private Optional<House> getHouse(String id) throws IOException {
        List<House> list = deserialize(House.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE));

        log.debug("getHouse [2]: Список получен, длина = " + list.size());
        Optional<House> optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
        log.info("getHouse [3]: optional = " + optional);
        return optional;
    }

    private Optional<Garage> getGarage(String id) throws IOException {
        List<Garage> list = deserialize(Garage.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE));

        log.debug("getGarage [2]: Список получен");
        Optional<Garage> optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
        log.info("getGarage [3]: optional = " + optional);
        return optional;
    }

    private void updateApartmentHouse(String id, ApartmentHouse apartmentHouse) throws IOException {
        List<ApartmentHouse> list = deserialize(ApartmentHouse.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE));

        log.debug("updateApartmentHouse [2]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            recordValidationList(apartmentHouse, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE));

            log.info("updateApartmentHouse [3]: Запись с id = " + id + " обновлена на " + apartmentHouse);
        } else {

            log.error("updateApartmentHouse [4]: ApartmentHouse с id = " + id + " не найден, обновление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }

    }

    private void updateHouse(String id, House house) throws IOException {
        List<House> list = deserialize(House.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE));

        log.debug("updateHouse [2]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            recordValidationList(house, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE));
            log.info("updateHouse [2]: Запись с id = " + id + " обновлена на " + house);
        } else {

            log.error("updateHouse [2]: House с id = " + id + " не найден, обновление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }
    }

    private void updateGarage(String id, Garage garage) throws IOException {
        List<Garage> list = deserialize(Garage.class,
                Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE));

        log.debug("updateGarage [2]: Список получен, длина = " + list.size());

        if (list.removeIf(el -> el.getId().equals(id))) {
            recordValidationList(garage, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE));
            log.info("updateGarage [2]: Запись с id = " + id + " обновлена на " + garage);
        } else {

            log.error("updateGarage [2]: Garage с id = " + id + " не найден, обновление не удалось");
            throw new NoSuchElementException("Запись не найдена");
        }
    }

    private <T> void recordValidation(T t, List<T> list, String path) throws IOException {

        if (!IsAlreadyCreated(t, list)) {
            serialize(t, path, t.getClass());
            log.info("recordValidation [1]: Запись добавлена " + t);
            mongo.logHistory(UUID.randomUUID().toString(), t, "recordValidation", Status.SUCCES);
        } else {
            log.error("recordValidation [2]: Запись уже есть " + t);
            mongo.logHistory(UUID.randomUUID().toString(), t, "recordValidation", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }

    }

    private <T> void recordValidationList(T t, List<T> list, String path) throws IOException {

        if (!IsAlreadyCreated(t, list)) {
            list.add(t);
            serializeList(list, path, t.getClass());
            log.info("recordValidationList [1]: Запись добавлена " + t);
            mongo.logHistory(UUID.randomUUID().toString(), t, "recordValidationList", Status.SUCCES);
        } else {
            log.error("recordValidationList [2]: Запись уже есть " + t);
            mongo.logHistory(UUID.randomUUID().toString(), t, "recordValidationList", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }

    }

    private <T> void serialize(T t, String path, Class clazz) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter(path, true))) {

            ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(clazz);

            StatefulBeanToCsv<T> statefulBeanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .build();
            statefulBeanToCsv.write(t);
            log.info("serialize [1]: Запись добавлена " + t);

        } catch (IOException ex) {
            log.error("serialize [2]: Ошибка ввода");
            throw new IOException(ex);

        } catch (CsvDataTypeMismatchException ex) {
            log.error("serialize [3]: " + ex.getMessage());
            System.exit(1);

        } catch (CsvRequiredFieldEmptyException ex) {
            log.error("serialize [4]: " + ex.getMessage());
            System.exit(1);

        }

    }

    private <T> void serializeList(List<T> list, String path, Class clazz) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {

            ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(clazz);

            StatefulBeanToCsv<T> statefulBeanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .build();
            statefulBeanToCsv.write(list);
            log.info("serializeList [1]: Список добавлен " + list);

        } catch (IOException ex) {
            log.error("serializeList [2]: Ошибка ввода");
            throw new IOException();

        } catch (CsvDataTypeMismatchException ex) {
            log.error("serializeList [3]:" + ex);
            System.exit(1);

        } catch (CsvRequiredFieldEmptyException ex) {
            log.error("serializeList [4]: " + ex);
            System.exit(1);
        }
    }

    private <T> List<T> deserialize(Class clazz, String path) throws IOException {

        List<T> list = new ArrayList<T>();

        Path myPath = Paths.get(path);

        try (Reader reader = Files.newBufferedReader(myPath)) {
            CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withSeparator(',')
                    .build();

            list = cb.parse();
            log.info("deserialize [1]: список получен");

        } catch (IOException ex) {
            log.error("deserialize [2]: Неправильно указан путь к файлу");
            throw new IOException(ex);
        }

        return list;
    }

    private <T> boolean IsAlreadyCreated(T t, List<T> list) {

        if (list.size() > 0) {
            if (list.contains(t)) {
                return true;
            }
        }
        return false;
    }

    public void createAllFiles() {

        List<File> files = new ArrayList<>();
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE)));

        log.debug("createAllFiles [1]: количество файлов = " + files.size());

        checkFiles(files);

    }

    private void checkFiles(List<File> file) {

        file.stream().forEach(el -> {
            try {
                if (el.createNewFile()) {
                    log.debug("checkFiles [1]: Файл " + el.getName() + " создан");
                } else {
                    log.debug("checkFiles[2]: Файл " + el.getName() + " существует");
                }
            } catch (IOException ex) {
                log.error("checkFiles [3]: " + ex.getMessage());
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            log.error("checkFiles [4]: " + ex.getMessage());
        }
    }
}
