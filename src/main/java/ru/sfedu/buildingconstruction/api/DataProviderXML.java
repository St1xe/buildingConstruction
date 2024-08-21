package ru.sfedu.buildingconstruction.api;

import java.io.File;
import java.io.IOException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.Constants;

import ru.sfedu.buildingconstruction.model.*;


public class DataProviderXML implements DataProvider {

    private static Logger log = Logger.getLogger(DataProviderXML.class);
    private MongoProvider mongo = new MongoProvider();

    public DataProviderXML() {
        log.debug("DataProviderXML[0]:starting application.....");
        
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
        addBuilding(building);

        log.info("id добавленного здания = " + building.getId());

    }

    @Override
    public void preparationForBuilding(Building building) throws IOException{
        
        log.debug("preparationForBuilding [1]: ");

        List<Worker> workers = distributionOfWorkers(building, Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));
        List<ConstructionEquipment> equipments = distributionOfConstructionEquipment(building, Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));
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
        List<Worker> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));
            log.debug("addWorker [1]: Список получен, длина = " + list.size());
            recordValidationList(worker, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));
        } catch (JAXBException ex) {
            log.error("addWorker [2]: " + ex.getMessage());
        }

    }

    @Override
    public void addConstructionEquipment(ConstructionEquipment constructionEquipment) throws IOException {
        List<ConstructionEquipment> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));
            log.debug("addConstructionEquipment [1]: Список получен, длина = " + list.size());
            recordValidationList(constructionEquipment, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));
        } catch (JAXBException ex) {
            log.error("addConstructionEquipment [2]: " + ex.getMessage());
        }
    }

    @Override
    public void addMaterial(Material material) throws IOException {
        List<Material> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));
            log.debug("addMaterial [1]: Список получен, длина = " + list.size());
            recordValidationList(material, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));
        } catch (JAXBException ex) {
            log.error("addMaterial [2]: " + ex.getMessage());
        }
    }

    @Override
    public void addClient(Client client) throws IOException {
        List<Client> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));
            log.debug("addClient [1]: Список получен, длина = " + list.size());
            recordValidationList(client, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));
        } catch (JAXBException ex) {
            log.error("addClient [2]: " + ex.getMessage());
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

    private void addApartmentHouse(ApartmentHouse apartmentHouse) throws IOException {

        List<ApartmentHouse> list;
        try {

            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));
            log.debug("addApartmentHouse [2]: Список получен, длина = " + list.size());
            recordValidationList(apartmentHouse, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));
        } catch (JAXBException ex) {
            log.error("addApartmentHouse [3]: " + ex.getMessage());
        }
    }

    private void addHouse(House house) throws IOException {
        List<House> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));
            log.debug("addHouse [2]: Список получен, длина = " + list.size());
            recordValidationList(house, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));
        } catch (JAXBException ex) {
            log.error("addHouse [3]: " + ex.getMessage());
        }
    }

    private void addGarage(Garage garage) throws IOException {
        List<Garage> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));
            log.debug("addGarage [2]: Список получен, длина = " + list.size());
            recordValidationList(garage, list,
                    Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));
        } catch (JAXBException ex) {
            log.error("addGarage [3]: " + ex.getMessage());
        }

    }

    @Override
    public void deleteWorker(String id) throws IOException {
        List<Worker> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));
            log.debug("deleteWorker [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                serialize(list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));

                log.info("deleteWorker [2]: Запись с id = " + id + " удалена");
            } else {

                log.error("deleteWorker [3]: Worker с id = " + id + " не найден, удаление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("deleteWorker [4]: " + ex.getMessage());
        }

    }

    @Override
    public void deleteConstructionEquipment(String id) throws IOException {
        List<ConstructionEquipment> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));
            log.debug("deleteConstructionEquipment [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                serialize(list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));

                log.info("deleteConstructionEquipment [2]: Запись с id = " + id + " удалена");
            } else {

                log.error("deleteConstructionEquipment [3]: ConstructionEquipment с id = " + id + " не найден, удаление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("deleteConstructionEquipment [4]: " + ex.getMessage());
        }
    }

    @Override
    public void deleteMaterial(String id) throws IOException {
        List<Material> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));
            log.debug("deleteMaterial [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                serialize(list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));

                log.info("deleteMaterial [2]: Запись с id = " + id + " удалена");
            } else {

                log.error("deleteMaterial [3]: Material с id = " + id + " не найден, удаление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("deleteMaterial [4]: " + ex.getMessage());
        }
    }

    @Override
    public void deleteClient(String id) throws IOException {
        List<Client> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));
            log.debug("deleteClient [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                serialize(list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));

                log.info("deleteClient [2]: Запись с id = " + id + " удалена");
            } else {

                log.error("deleteClient [3]: Client с id = " + id + " не найден, удаление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("deleteClient [4]: " + ex.getMessage());
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
        List<ApartmentHouse> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));
            log.debug("deleteApartmentHouse [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                serialize(list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));

                log.info("deleteApartmentHouse [2]: Запись с id = " + id + " удалена");
            } else {

                log.error("deleteApartmentHouse [3]: ApartmentHouse с id = " + id + " не найден, удаление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("deleteApartmentHouse [4]: " + ex.getMessage());
        }
    }

    private void deleteHouse(String id) throws IOException {
        List<House> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));
            log.debug("deleteHouse [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                serialize(list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));

                log.info("deleteHouse [2]: Запись с id = " + id + " удалена");
            } else {

                log.error("deleteHouse [3]: House с id = " + id + " не найден, удаление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("deleteHouse [4]: " + ex.getMessage());
        }
    }

    private void deleteGarage(String id) throws IOException {
        List<Garage> list;
        try {
            list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));
            log.debug("deleteGarage [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                serialize(list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));

                log.info("deleteGarage [2]: Запись с id = " + id + " удалена");
            } else {

                log.error("deleteGarage [3]: Garage с id = " + id + " не найден, удаление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("deleteGarage [4]: " + ex.getMessage());
        }
    }

    @Override
    public Optional<Worker> getWorker(String id) throws IOException {

        Optional<Worker> optional = Optional.empty();
        try {
            List<Worker> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));
            log.debug("getWorker [1]: Список получен, длина = " + list.size());
            optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
            log.info("getWorker [2]: optional = " + optional);
        } catch (JAXBException ex) {
            log.error("getWorker [3]: " + ex.getMessage());
        }

        return optional;
    }

    @Override
    public Optional<ConstructionEquipment> getConstructionEquipment(String id) throws IOException {
        Optional<ConstructionEquipment> optional = Optional.empty();
        try {
            List<ConstructionEquipment> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));
            log.debug("getConstructionEquipment [1]: Список получен, длина = " + list.size());
            optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
            log.info("getConstructionEquipment [2]: optional = " + optional);
        } catch (JAXBException ex) {
            log.error("getConstructionEquipment [3]: " + ex.getMessage());
        }

        return optional;
    }

    @Override
    public Optional<Material> getMaterial(String id) throws IOException {
        Optional<Material> optional = Optional.empty();
        try {
            List<Material> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));
            log.debug("getMaterial [1]: Список получен, длина = " + list.size());
            optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
            log.info("getMaterial [2]: optional = " + optional);
        } catch (JAXBException ex) {
            log.error("getMaterial [3]: " + ex.getMessage());
        }

        return optional;
    }

    @Override
    public Optional<Client> getClient(String id) throws IOException {
        Optional<Client> optional = Optional.empty();
        try {
            List<Client> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));
            log.debug("getClient [1]: Список получен, длина = " + list.size());
            optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
            log.info("getClient [2]: optional = " + optional);
        } catch (JAXBException ex) {
            log.error("getClient [3]: " + ex.getMessage());
        }

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

    private Optional<ApartmentHouse> getApartmentHouse(String id) throws IOException {
        Optional<ApartmentHouse> optional = Optional.empty();
        try {
            List<ApartmentHouse> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));
            log.debug("getApartmentHouse [1]: Список получен, длина = " + list.size());
            optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
            log.info("getApartmentHouse [2]: optional = " + optional);
        } catch (JAXBException ex) {
            log.error("getApartmentHouse [3]: " + ex.getMessage());
        }

        return optional;
    }

    private Optional<House> getHouse(String id) throws IOException {
        Optional<House> optional = Optional.empty();
        try {
            List<House> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));
            log.debug("getHouse [1]: Список получен, длина = " + list.size());
            optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
            log.info("getHouse [2]: optional = " + optional);
        } catch (JAXBException ex) {
            log.error("getHouse [3]: " + ex.getMessage());
        }

        return optional;
    }

    private Optional<Garage> getGarage(String id) throws IOException {
        Optional<Garage> optional = Optional.empty();
        try {
            List<Garage> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));
            log.debug("getGarage [1]: Список получен, длина = " + list.size());
            optional = list.stream().filter(el -> el.getId().equals(id)).findFirst();
            log.info("getGarage [2]: optional = " + optional);
        } catch (JAXBException ex) {
            log.error("getGarage [3]: " + ex.getMessage());
        }

        return optional;
    }

    @Override
    public <T> List<T> getAllRecords(Class clazz, String path) throws IOException {
        List<T> list = null;
        try {
            list = deserialize(path);
            log.debug("getAllRecords [1]: class = " + clazz + ", путь = " + path + ", список получен, длина = " + list.size());
        } catch (JAXBException ex) {
            log.error("getGarage [2]: " + ex.getMessage());
        }
        return list;
    }

    @Override
    public void updateWorker(String id, Worker worker) throws IOException {

        try {
            List<Worker> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));

            log.debug("updateWorker [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                recordValidationList(worker, list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));

                log.info("updateWorker [2]: Запись с id = " + id + " обновлена на " + worker);
            } else {

                log.error("updateWorker [3]: Worker с id = " + id + " не найден, обновление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("updateWorker [4]: " + ex.getMessage());
        }

    }

    @Override
    public void updateConstructionEquipment(String id, ConstructionEquipment constructionEquipment) throws IOException {
        try {
            List<ConstructionEquipment> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));

            log.debug("updateConstructionEquipment [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                recordValidationList(constructionEquipment, list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));

                log.info("updateConstructionEquipment [2]: Запись с id = " + id + " обновлена на " + constructionEquipment);
            } else {

                log.error("updateConstructionEquipment [3]: ConstructionEquipment с id = " + id + " не найден, обновление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("updateConstructionEquipment [4]: " + ex.getMessage());
        }
    }

    @Override
    public void updateMaterial(String id, Material material) throws IOException {
        try {
            List<Material> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));

            log.debug("updateMaterial [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                recordValidationList(material, list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));

                log.info("updateMaterial [2]: Запись с id = " + id + " обновлена на " + material);
            } else {

                log.error("updateMaterial [3]: Material с id = " + id + " не найден, обновление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("updateMaterial [4]: " + ex.getMessage());
        }
    }

    @Override
    public void updateClient(String id, Client client) throws IOException {
        try {
            List<Client> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));

            log.debug("updateClient [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                recordValidationList(client, list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));

                log.info("updateClient [2]: Запись с id = " + id + " обновлена на " + client);
            } else {

                log.error("updateClient [3]: Client с id = " + id + " не найден, обновление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("updateClient [4]: " + ex.getMessage());
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

    private void updateApartmentHouse(String id, ApartmentHouse apartmentHouse) throws IOException {
        try {
            List<ApartmentHouse> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));

            log.debug("updateApartmentHouse [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                recordValidationList(apartmentHouse, list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));

                log.info("updateApartmentHouse [2]: Запись с id = " + id + " обновлена на " + apartmentHouse);
            } else {

                log.error("updateApartmentHouse [3]: ApartmentHouse с id = " + id + " не найден, обновление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("updateApartmentHouse [4]: " + ex.getMessage());
        }

    }

    private void updateHouse(String id, House house) throws IOException {
        try {
            List<House> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));

            log.debug("updateHouse [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                recordValidationList(house, list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));

                log.info("updateHouse [2]: Запись с id = " + id + " обновлена на " + house);
            } else {

                log.error("updateHouse [3]: House с id = " + id + " не найден, обновление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("updateHouse [4]: " + ex.getMessage());
        }
    }

    public void clearFile(String path) throws IOException {
        try {
            serialize(new ArrayList(), path);
            log.debug("clearFile [1]: Файл " + path + " пуст");
        } catch (JAXBException ex) {
            log.error("clearFile [2]: " + ex.getMessage());
        }

    }

    private void updateGarage(String id, Garage garage) throws IOException {
        try {
            List<Garage> list = deserialize(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));

            log.debug("updateGarage [1]: Список получен, длина = " + list.size());

            if (list.removeIf(el -> el.getId().equals(id))) {
                recordValidationList(garage, list,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));

                log.info("updateGarage [2]: Запись с id = " + id + " обновлена на " + garage);
            } else {

                log.error("updateGarage [3]: Garage с id = " + id + " не найден, обновление не удалось");
                throw new NoSuchElementException("Запись не найдена");
            }
        } catch (JAXBException ex) {
            log.error("updateGarage [4]: " + ex.getMessage());
        }
    }

    private <T> void serialize(List<T> list, String path) throws JAXBException {

        try {
            JAXBContext context = JAXBContext.newInstance(WrapperXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File result = new File(path);
            WrapperXML<T> w = new WrapperXML<>();
            w.list.addAll(list);
            marshaller.marshal(w, result);
            log.info("serialize [1]: Список добавлен");
        } catch (JAXBException ex) {
            log.error("serialize [2]: Ошибка ввода");
            throw new JAXBException(path);
        }

    }

    private <T> List<T> deserialize(String path) throws JAXBException, IOException {

        JAXBContext context;
        WrapperXML<T> wrap;
        Unmarshaller unmarshaller;
        File file;
        try {
            context = JAXBContext.newInstance(WrapperXML.class);
            unmarshaller = context.createUnmarshaller();
            file = new File(path);

        } catch (JAXBException ex) {
            log.error("deserialize [1]: Ошибка ввода");
            throw new JAXBException(ex);
        }

        try {
            wrap = (WrapperXML<T>) unmarshaller.unmarshal(file);
            log.info("deserialize [2]: список получен");
        } catch (IllegalArgumentException ex) {
            log.error("deserialize [3]: Неправильно указан путь к файлу");
            throw new IOException(ex);
        } catch (JAXBException ex) {
            log.debug("deserialize [4]: Файл " + file + " пуст");
            return new WrapperXML<T>().list;
        }
        return wrap.list;

    }

    private <T> void recordValidationList(T t, List<T> list, String path) throws IOException, JAXBException {

        if (!IsAlreadyCreated(t, list)) {
            list.add(t);
            serialize(list, path);
            log.info("recordValidationList [1]: Запись добавлена " + t);
            mongo.logHistory(UUID.randomUUID().toString(), t, "recordValidationList", Status.SUCCES);

        } else {
            log.error("recordValidationList [2]: Запись уже есть " + t);
            mongo.logHistory(UUID.randomUUID().toString(), t, "recordValidationList", Status.FAULT);
            throw new IllegalArgumentException("Запись уже есть");
        }

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
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE)));
        files.add(new File(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE)));

        log.debug("createAllFiles [1]: количество файлов = " + files.size());

        checkFiles(files);

    }

    private void checkFiles(List<File> file) {

        file.stream().forEach(el -> {
            try {
                if (el.createNewFile()) {
                    log.debug("checkFiles [1]: Файл " + el.getName() + " создан");
                } else {
                    log.debug("checkFiles [2]: Файл " + el.getName() + " существует");
                }
            } catch (IOException ex) {
                log.error("checkFiles [3]: " + ex);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            log.error("checkFiles [4]: " + ex);
        }
    }

   

}
