package ru.sfedu.buildingconstruction.lab4.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import ru.sfedu.buildingconstruction.lab4.model.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.buildingconstruction.Constants;


public class HibernateProviderTest {

    private static Logger log = Logger.getLogger(HibernateProviderTest.class);
    static HibernateProvider provider = new HibernateProvider();

    static ApartmentHouse building1;
    static ApartmentHouse building2;
    static House building3;
    static House building4;
    static Garage building5;

    @BeforeAll
    public static void setUpClass() {
        log.debug("Before all[0]: starting tests");

        provider.deleteAllRecordsFromTable();

        building1 = new ApartmentHouse();
        building1.setId("1");
        building1.setNumberOfApartments(5);

        building2 = new ApartmentHouse();
        building2.setId("2");
        building2.setNumberOfApartments(7);

        building3 = new House();
        building3.setId("3");
        building3.setNumberOfRooms(13);

        building4 = new House();
        building4.setId("4");
        building4.setNumberOfRooms(4);

        building5 = new Garage();
        building5.setId("5");
        building5.setNumberOfCars(3);
    }

    @Test
    public void testAddBuildingPositive() {

        log.debug("testAddBuildingPositive [0]:");

        building1 = (ApartmentHouse) generateBuildingObjectWithoutId(building1, 2);

        provider.addBuilding(building1);

        assertEquals(provider.getBuilding(building1.getId(), building1.getClass()).get(), building1);

    }

    @Test
    public void testAddBuildingNegative() {
        log.debug("testAddTestEntityNegative [0]:");

        building2 = (ApartmentHouse) generateBuildingObjectWithoutId(building2, 2);

        provider.addBuilding(building2);
        assertThrows(EntityExistsException.class, () -> provider.addBuilding(building2));

    }

    @Test
    public void testGetBuildingPositive() {

        log.debug("testGetBuildingPositive [0]:");

        building3 = (House) generateBuildingObjectWithoutId(building3, 2);

        provider.addBuilding(building3);

        assertEquals(provider.getBuilding(building3.getId(), building3.getClass()), Optional.of(building3));

    }

    @Test
    public void testGetBuildingNegative() {
        log.debug("testGetBuildingNegative [0]:");

        assertEquals(provider.getBuilding(Constants.INCORRECTID, ApartmentHouse.class), Optional.empty());

    }

    @Test
    public void testDeleteBuildinPositive() {
        log.debug("testDeleteBuildinPositive [0]:");
        
        building4 = (House) generateBuildingObjectWithoutId(building4, 2);
        
        provider.addBuilding(building4);

        provider.deleteBuilding(building4.getId(), building4.getClass());

        assertEquals(provider.getBuilding(building4.getId(), building4.getClass()), Optional.empty());
    }

    @Test
    public void testDeleteBuildingNegative() {
        log.debug("testDeleteBuildingNegative [0]:");

        assertThrows(EntityNotFoundException.class, () -> provider.deleteBuilding(Constants.INCORRECTID, ApartmentHouse.class));
    }
    
    
    
     @Test
    public void testUpdateBuildingPositive() {

        log.debug("testUpdateBuildingPositive [0]:");
        
        building5 = (Garage) generateBuildingObjectWithoutId(building5, 2);
        
        provider.addBuilding(building5);
        building5.setNumberOfFloors(2);
        building5.setNumberOfCars(5);

        provider.updateBuilding(building5, building5.getId(), building5.getClass());

        assertEquals(provider.getBuilding(building5.getId(), building5.getClass()).get(), building5);

    }

    @Test
    public void testUpdateBuildingNegative() {

        log.debug("testUpdateBuildingNegative [0]:");
        assertThrows(EntityNotFoundException.class,
                () -> provider.updateBuilding(building5, Constants.INCORRECTID, building5.getClass()));

    }

    private Building generateBuildingObjectWithoutId(Building building, int numberOfCollectionElements) {

        log.debug("generateBuildingObjectWithoutId [0]:");

        Random rand = new Random();

        List<Material> materials = new ArrayList<>();
        Set<EngineeringSystem> systems = new HashSet<>();
        Map<ConstructionEquipment, Integer> map = new HashMap<>();
        List<Worker> workers = new ArrayList<>();

        Material material;
        ConstructionEquipment equipment;
        Worker worker;

        String uuid = UUID.randomUUID().toString();

        for (int i = 0; i < numberOfCollectionElements; i++) {

            material = new Material();
            equipment = new ConstructionEquipment();
            worker = new Worker();

            material.setId(i + uuid);
            material.setName(RandomStringUtils.randomAlphabetic(10));
            material.setPrice(rand.nextInt(100));
            material.setQuantityInStock(rand.nextInt(10));

            materials.add(material);

            equipment.setId(i + uuid);
            equipment.setName(RandomStringUtils.randomAlphabetic(10));
            equipment.setPrice(rand.nextInt(100));

            map.put(equipment, rand.nextInt(10));

            worker.setId(i + uuid);
            worker.setJobTitle(RandomStringUtils.randomAlphabetic(10));
            worker.setName(RandomStringUtils.randomAlphabetic(10));
            worker.setPhoneNumber(RandomStringUtils.randomAlphabetic(10));
            worker.setSalary(rand.nextInt(10));

            workers.add(worker);
        }

        EngineeringSystem system1 = EngineeringSystem.ELECTRICITY;
        EngineeringSystem system2 = EngineeringSystem.SEWERAGE;
        EngineeringSystem system3 = EngineeringSystem.HEATING;
        EngineeringSystem system4 = EngineeringSystem.WATER_SUPPLY;

        systems.add(system1);
        systems.add(system2);
        systems.add(system3);
        systems.add(system4);

        Client owner = new Client();
        owner.setId(RandomStringUtils.randomAlphabetic(20));
        owner.setName(RandomStringUtils.randomAlphabetic(10));
        owner.setPassport(RandomStringUtils.randomNumeric(10));
        owner.setEmail(RandomStringUtils.randomAlphabetic(10));
        owner.setPhoneNumber(RandomStringUtils.randomNumeric(10));

        building.setSquare(rand.nextInt(1000));
        building.setNumberOfFloors(rand.nextInt(10));
        building.setCompletionDate(LocalDate.now());

        building.setOwner(owner);
        building.setMaterials(materials);
        building.setEngineerinSystems(systems);
        building.setConstructionEqipMap(map);
        building.setWorker(workers);

        return building;
    }
}
