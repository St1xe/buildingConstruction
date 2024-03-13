package ru.sfedu.buildingconstruction.api;

import java.io.IOException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import ru.sfedu.buildingconstruction.model.*;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.Constants;

/**
 *
 * @author Mozogoy Maxim
 */
public interface DataProvider {

    static final Logger log = Logger.getLogger(DataProvider.class);

    /**
     * Method for preparing a building for construction.
     * The result of the method will be a record of the object
     * 
     * @param building   - the building we are going to build
     * @param client     - building owner
     * @param materials  - materials from which the building will be constructed
     * @param systems    - communications that will be in the house
     * @throws Exception - exception will be thrown if the building is not possible to add
     */
    public void preparationOfConstructionPlan(Building building, Client client, List<Material> materials, List<EngineeringSystem> systems) throws Exception;

    
    /**
     * Obtaining a list of materials entered by the user
     * 
     * @param str        - string entered by the user
     * @return           - list of materials
     * @throws Exception - exception will be thrown if the material is not found
     */
    public default List<Material> selectionOfMaterials(String str) throws Exception {

        List<Material> list = new ArrayList<>();

        Arrays.stream(str.split(" ")).forEach(el -> {

            try {
                list.add(getMaterial(el).get());
            } catch (IOException ex) {
                log.error(ex.getMessage());
            } catch (NoSuchElementException ex) {
                log.error("материал не найден");
                throw new NoSuchElementException();
            }

        });
        return list;
    }

    
    /**
     * Obtaining a list of engineering systems entered by the user
     * 
     * @param str        - string entered by the user
     * @return           -  list of engineering system
     * @throws Exception - exception will be thrown if the engineering system is not found
     */
    public default List<EngineeringSystem> selectionOfEngineeringSystems(String str) throws Exception {

        List<EngineeringSystem> list = new ArrayList<>();
        try {

            Arrays.stream(str.split(" ")).forEach(el -> {

                list.add(EngineeringSystem.valueOf(el));
            });
        } catch (IllegalArgumentException ex) {
            log.error("система не найдена");
            throw new IllegalArgumentException();
        }

        return list;
    }

    
    /**
     * A method for adding information about workers and equipment who will participate in construction.
     * And approximate completion dates for construction
     * 
     * @param building     - the building we are going to build
     * @throws IOException - exception will be thrown if it is not possible to update the building data
     */
    public void preparationForBuilding(Building building) throws IOException;

    
    /**
     * Distribution of workers for this construction
     * 
     * @param building     - building on the basis of which the number of employees will be calculated
     * @param path         - path to the file from which workers should be selected
     * @return             - list of employees
     * @throws IOException - exception will be thrown if the path is incorrect
     */
    public default List<Worker> distributionOfWorkers(Building building, String path) throws IOException{

        List<Worker> list = new ArrayList<>();

        int countOfWorkers = 0;

        if (building instanceof ApartmentHouse) {
            countOfWorkers = Constants.PEOPLE_FOR_BUILD_AN_APARTMENT_HOUSE;

        } else if (building instanceof House) {
            countOfWorkers = Constants.PEOPLE_FOR_BUILD_A_HOUSE;

        } else if (building instanceof Garage) {
            countOfWorkers = Constants.PEOPLE_FOR_BUILD_A_GARAGE;

        }

        try {

            getAllRecords(Worker.class, path)
                    .stream()
                    .limit(countOfWorkers)
                    .map(el -> (Worker) el)
                    .forEach(el -> list.add(el));

        } catch (IOException ex) {
            throw new IOException();
        }

        return list;
    }

    /**
     * Distribution of construction equipment for this construction
     * 
     * @param building     - building on the basis of which the number of construction equipment will be calculated
     * @param path         - path to the file from which workers should be selected
     * @return             - list of construction equipments
     * @throws IOException - exception will be thrown if the path is incorrect
     */
    public default List<ConstructionEquipment> distributionOfConstructionEquipment(Building building, String path) throws IOException{

        List<ConstructionEquipment> list = new ArrayList<>();

        int numberOfEquipments = 0;

        if (building instanceof ApartmentHouse) {
            numberOfEquipments = Constants.NUMBER_OF_EQUIPMENT_FOR_BUILD_AN_APARTMENT_HOUSE;

        } else if (building instanceof House) {
            numberOfEquipments = Constants.NUMBER_OF_EQUIPMENT_FOR_BUILD_A_HOUSE;

        } else if (building instanceof Garage) {
            numberOfEquipments = Constants.NUMBER_OF_EQUIPMENT_FOR_BUILD_A_GARAGE;

        }

        try {

            getAllRecords(ConstructionEquipment.class, path)
                    .stream()
                    .limit(numberOfEquipments)
                    .map(el -> (ConstructionEquipment) el)
                    .forEach(el -> list.add(el));

        } catch (IOException ex) {
            throw new IOException();
        }

        return list;
    }
    
    
    /**
     * Calculating the approximate construction time for a given building
     * 
     * @param building            - building for which the approximate completion date will be calculated
     * @return                    - construction completion date
     * @throws ClassCastException - exception will be thrown if the building type is indicated incorrectly
     */
    public default LocalDate coordinationOfConstructionTerms(Building building) throws ClassCastException{

        LocalDate date = LocalDate.now();

        switch (building.getClass().getSimpleName()) {

            case "ApartmentHouse" -> {
                date = LocalDate.now().plusMonths(Constants.TIME_IN_MONTH_FOR_BUILD_AN_APARTMENT_HOUSE);
            }
            case "House" -> {
                date = LocalDate.now().plusMonths(Constants.TIME_IN_MONTH_FOR_BUILD_A_HOUSE);
            }
            case "Garage" -> {
                date = LocalDate.now().plusMonths(Constants.TIME_IN_MONTH_FOR_BUILD_A_GARAGE);
            } default -> throw new ClassCastException();

        }
        return date;
    }

    
    
    /**
     * Method for calculating the total cost of building a building
     * 
     * @param building            - building for which the total amount is calculated
     * @return                    - total amount
     * @throws ClassCastException - exception will be thrown if the building type is indicated incorrectlyt
     */
    public default double calculationOfTheTotalCost(Building building) throws ClassCastException{

        int time = 0;
        double sum = 1;

        switch (building.getClass().getSimpleName()) {

            case "ApartmentHouse" -> {
                time = Constants.TIME_IN_MONTH_FOR_BUILD_AN_APARTMENT_HOUSE;
            }
            case "House" -> {
                time = Constants.TIME_IN_MONTH_FOR_BUILD_A_HOUSE;
                sum = 0.2;
            }
            case "Garage" -> {
                time = Constants.TIME_IN_MONTH_FOR_BUILD_A_GARAGE;
                sum = 0.1;
            } default -> throw new ClassCastException();

        }

        sum *= calculationCostOfMaterials(building);
        sum += calculationCostOfConstructionEquipment(building, time);
        sum += calculationCostOfJob(building, time);

        if (building.getSquare() > 300) {
            sum *= 1.5;
        } else if (building.getSquare() > 200) {
            sum *= 1.2;
        } else if (building.getSquare() > 100) {
            sum *= 1.1;
        }

        if (building.getNumberOfFloors() > 3) {
            sum *= 1.5;
        } else if (building.getNumberOfFloors() > 2) {
            sum *= 1.2;
        } else if (building.getNumberOfFloors() > 1) {
            sum *= 1.1;
        }

        sum *= 1.2;

        log.info("Стоимотсь постройки здания = " + sum);
        return sum;
    }

    
    /**
     * Calculating the cost of materials
     * 
     * @param building - building for which the calculation is being carried out
     * @return         - amount spent of materials
     */
    public default double calculationCostOfMaterials(Building building) {

        double sum = 0;

        List<Material> materials = building.getMaterials();
        

        sum = materials.stream()
                .reduce(0.0,
                        (accumulator, el) -> accumulator + el.getPrice() * el.getQuantityInStock(),
                        (accumulator, el) -> accumulator + el);

        log.info("calculationCostOfMaterials [1]: sum = " + sum);
        return sum;

    }

    /**
     * Calculating the cost of construction equipment
     * 
     * @param building - building for which the calculation is being carried out
     * @param time     - duration of construction in months
     * @return         - amount spent of construction equipment
     */
    public default double calculationCostOfConstructionEquipment(Building building, int time) {

        double sum = 0;

        List<ConstructionEquipment> equipments = building.getConstructionEquipments();

        sum = equipments.stream()
                .reduce(0.0,
                        (accumulator, el) -> accumulator + el.getPrice() * time * 10,
                        (accumulator, el) -> accumulator + el);

        log.info("calculationCostOfConstructionEquipment [1]: sum = " + sum);
        return sum;

    }

    /**
     * Calculating the cost of works
     * 
     * @param building - building for which the calculation is being carried out
     * @param time     - duration of construction in months
     * @return         - amount spent of works
     */
    public default double calculationCostOfJob(Building building, int time) {
        double sum = 0;

        List<Worker> workers = building.getWorkers();

        sum = workers.stream()
                .reduce(0.0,
                        (accumulator, el) -> accumulator + el.getSalary() * time,
                        (accumulator, el) -> accumulator + el);

        log.info("calculationCostOfJob [1]: sum = " + sum);
        return sum;
    }

    
    /**
     * Method for adding a new employee
     * 
     * @param worker       - new employee
     * @throws IOException - exception will be thrown if we can’t add an employee
     */
    public void addWorker(Worker worker) throws IOException;

    /**
     * Method for adding a new construction equipment
     * 
     * @param constructionEquipment - new construction equipment
     * @throws IOException          - exception will be thrown if we can’t add a construction equipment 
     */
    public void addConstructionEquipment(ConstructionEquipment constructionEquipment) throws IOException;

    /**
     * Method for adding a new material
     * 
     * @param material     - new material
     * @throws IOException - exception will be thrown if we can’t add a material 
     */
    public void addMaterial(Material material) throws IOException;

    /**
     * Method for adding a new client
     * 
     * @param client       - new client
     * @throws IOException - exception will be thrown if we can’t add a client 
     */
    public void addClient(Client client) throws IOException;

    /**
     * Method for adding a new building
     * 
     * @param building     - new building
     * @throws IOException - exception will be thrown if we can’t add a building
     */
    public void addBuilding(Building building) throws IOException;

    /**
     * Method for remove a worker
     * 
     * @param id           - id by which we will delete the employee
     * @throws IOException - exception will be thrown if we can’t delete an employee
     */
    public void deleteWorker(String id) throws IOException;

    /**
     * Method for remove a construction equipment
     * 
     * @param id            - id by which we will delete the construction equipment
     * @throws IOException  - exception will be thrown if we can’t delete a construction equipment
     */
    public void deleteConstructionEquipment(String id) throws IOException;

    /**
     * Method for remove a material
     * 
     * @param id           - id by which we will delete the material
     * @throws IOException - exception will be thrown if we can’t delete a material
     */
    public void deleteMaterial(String id) throws IOException;

    /**
     * Method for remove a client
     * 
     * @param id           - id by which we will delete the client
     * @throws IOException - exception will be thrown if we can’t delete a client
     */
    public void deleteClient(String id) throws IOException;

    /**
     * Method for remove a building
     * 
     * @param id           - id by which we will delete the building
     * @param clazz        - class to which the building belongs
     * @throws IOException - exception will be thrown if we can’t delete a building
     */
    public void deleteBuilding(String id, Class<? extends Building> clazz) throws IOException;

    /**
     * Method to get employee
     * 
     * @param id           - id by which we will get the worker
     * @return             - worker wrapped in optional
     * @throws IOException - exception will be thrown if we can’t get a worker
     */
    public Optional<Worker> getWorker(String id) throws IOException;

    /**
     * Method to get construction equipment
     * 
     * @param id           - id by which we will get the construction equipment
     * @return             - construction equipment wrapped in optional
     * @throws IOException - exception will be thrown if we can’t get a construction equipment
     */
    public Optional<ConstructionEquipment> getConstructionEquipment(String id) throws IOException;

    /**
     * Method to get material
     * 
     * @param id           - id by which we will get the material
     * @return             - material wrapped in optional
     * @throws IOException - exception will be thrown if we can’t get a material
     */
    public Optional<Material> getMaterial(String id) throws IOException;

    /**
     * Method to get client
     * 
     * @param id           - id by which we will get the client
     * @return             - client wrapped in optional
     * @throws IOException - exception will be thrown if we can’t get a client
     */
    public Optional<Client> getClient(String id) throws IOException;

    /**
     * Method to get building
     * 
     * @param id           - id by which we will get the building
     * @param clazz        - class to which the building belongs
     * @return             - building wrapped in optional
     * @throws IOException - exception will be thrown if we can’t get a building
     */
    public Optional<? extends Building> getBuilding(String id, Class<? extends Building> clazz) throws IOException;

    /**
     * Method to get all records from a file
     * 
     * @param <T>          - a list of what elements we expect to receive
     * @param clazz        - element class
     * @param path         - the path to the file
     * @return             - list of all values from file
     * @throws IOException - exception will be thrown if we can’t get all records
     */
    public <T> List<T> getAllRecords(Class clazz, String path) throws IOException;

    /**
     * Method to update worker
     * 
     * @param id           - id by which we will update the worker
     * @param worker       - the object we want to replace the old object with
     * @throws IOException - exception will be thrown if we can’t update worker record
     */
    public void updateWorker(String id, Worker worker) throws IOException;

    /**
     * Method to update construction equipment
     * 
     * @param id                    - id by which we will update the construction equipment
     * @param constructionEquipment - the object we want to replace the old object with
     * @throws IOException          - exception will be thrown if we can’t update construction equipment record
     */
    public void updateConstructionEquipment(String id, ConstructionEquipment constructionEquipment) throws IOException;

    /**
     * Method to update material
     * 
     * @param id           - id by which we will update the material
     * @param material     - the object we want to replace the old object with
     * @throws IOException - exception will be thrown if we can’t update material record
     */
    public void updateMaterial(String id, Material material) throws IOException;

    /**
     * Method to update client
     * 
     * @param id           -  id by which we will update the client
     * @param client       - the object we want to replace the old object with
     * @throws IOException - exception will be thrown if we can’t update client record
     */
    public void updateClient(String id, Client client) throws IOException;

    /**
     * Method to update building
     * 
     * @param id           - id by which we will update the building
     * @param building     - the object we want to replace the old object with
     * @throws IOException - exception will be thrown if we can’t update building record
     */
    public void updateBuilding(String id, Building building) throws IOException;

}
