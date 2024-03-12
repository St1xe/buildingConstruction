package ru.sfedu.buildingconstruction.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import ru.sfedu.buildingconstruction.model.*;
import org.apache.log4j.Logger;

/**
 *
 * @author maksim
 */
public interface DataProvider {

    static Logger log = Logger.getLogger(DataProvider.class);

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

    public void preparationOfConstructionPlan(Building building, Client client, List<Material> materials, List<EngineeringSystem> systems) throws Exception;

    public void preparationForBuilding(Building building);

    public  List<Worker> distributionOfWorkers(Building building);
    
    public void calculationOfTheTotalCost(Building building);
    
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
