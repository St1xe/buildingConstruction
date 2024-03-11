
package ru.sfedu.buildingconstruction.api;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import ru.sfedu.buildingconstruction.model.*;

/**
 *
 * @author maksim
 */
public interface DataProvider {
    
    public void preparationOfConstructionPlan(Building building, Client client, List<Material> materials, List<EngineeringSystem> systems) throws Exception;
    public void preparationForBuilding(Building building);
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
