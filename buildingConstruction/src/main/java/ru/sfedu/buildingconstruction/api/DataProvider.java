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
 * @author maksim
 */
public interface DataProvider {

    static Logger log = Logger.getLogger(DataProvider.class);

    public void preparationOfConstructionPlan(Building building, Client client, List<Material> materials, List<EngineeringSystem> systems) throws Exception;

    public default List<Material> selectionOfMaterials(String str) {

        List<Material> list = new ArrayList<>();

        Arrays.stream(str.split(" ")).forEach(el -> {

            try {
                list.add(getMaterial(el).get());
            } catch (IOException ex) {
                log.error(ex.getMessage());
            } catch (NoSuchElementException ex) {
                log.error("материал не найден");
                System.exit(0);
            }

        });
        return list;
    }

    public default List<EngineeringSystem> selectionOfEngineeringSystems(String str) {

        List<EngineeringSystem> list = new ArrayList<>();

        Arrays.stream(str.split(" ")).forEach(el -> {

            list.add(EngineeringSystem.valueOf(el));
        });

        return list;
    }

    public void preparationForBuilding(Building building);

    public default List<Worker> distributionOfWorkers(Building building, String path) {

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

            getAllRecords(Worker.class, Constants.PATH_TO_RESOURCES.concat(path))
                    .stream()
                    .limit(countOfWorkers)
                    .map(el -> (Worker) el)
                    .forEach(el -> list.add(el));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public default List<ConstructionEquipment> distributionOfConstructionEquipment(Building building, String path) {

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

            getAllRecords(ConstructionEquipment.class, Constants.PATH_TO_RESOURCES.concat(path))
                    .stream()
                    .limit(numberOfEquipments)
                    .map(el -> (ConstructionEquipment) el)
                    .forEach(el -> list.add(el));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public default LocalDate coordinationOfConstructionTerms(Building building) {

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
            }

        }
        return date;
    }

    public default void calculationOfTheTotalCost(Building building) {

        int time = 0;
        double sum = 0;

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
            }

        }

        sum *= calculationCostOfMaterials(building);
        sum += calculationCostOfConstructionEquipment(building, time);
        sum += calculationCostOfJob(building, time);
        
        
        if(building.getSquare() > 300) {
            sum *= 1.5;
        } else if(building.getSquare() > 200) {
            sum *= 1.2;
        } else if (building.getSquare() > 100) {
            sum *= 1.1;
        }
        
        if(building.getNumberOfFloors()> 3) {
            sum *= 1.5;
        } else if(building.getNumberOfFloors()> 2) {
            sum *= 1.2;
        } else if (building.getNumberOfFloors()> 1) {
            sum *= 1.1;
        }
        
        sum *= 1.2;
        
        log.info("Стоимотсь постройки здания = " + sum);
    }

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
    
    
    
    
    
    
    
    public void addWorker(Worker worker) throws IOException;

    public void addConstructionEquipment(ConstructionEquipment constructionEquipment) throws IOException;

    public void addMaterial(Material material) throws IOException;

    public void addClient(Client client) throws IOException;

    public void addBuilding(Building building) throws IOException;

    public void deleteWorker(String id) throws IOException;

    public void deleteConstructionEquipment(String id) throws IOException;

    public void deleteMaterial(String id) throws IOException;

    public void deleteClient(String id) throws IOException;

    public void deleteBuilding(String id, Class<? extends Building> clazz) throws IOException;

    public Optional<Worker> getWorker(String id) throws IOException;

    public Optional<ConstructionEquipment> getConstructionEquipment(String id) throws IOException;

    public Optional<Material> getMaterial(String id) throws IOException;

    public Optional<Client> getClient(String id) throws IOException;

    public Optional<? extends Building> getBuilding(String id, Class<? extends Building> clazz) throws IOException;

    public <T> List<T> getAllRecords(Class clazz, String path) throws IOException;

    public void updateWorker(String id, Worker worker) throws IOException;

    public void updateConstructionEquipment(String id, ConstructionEquipment constructionEquipment) throws IOException;

    public void updateMaterial(String id, Material material) throws IOException;

    public void updateClient(String id, Client client) throws IOException;

    public void updateBuilding(String id, Building building) throws IOException;

}
