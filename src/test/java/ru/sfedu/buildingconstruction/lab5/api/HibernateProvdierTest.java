package ru.sfedu.buildingconstruction.lab5.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.buildingconstruction.lab5.model.ApartmentHouse;
import ru.sfedu.buildingconstruction.lab5.model.Building;
import ru.sfedu.buildingconstruction.lab5.model.Garage;
import ru.sfedu.buildingconstruction.lab5.model.House;
import static org.junit.jupiter.api.Assertions.*;
import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.lab5.api.HibernateProvdier;
import ru.sfedu.buildingconstruction.lab5.model.Client;
import ru.sfedu.buildingconstruction.lab5.model.ConstructionEquipment;
import ru.sfedu.buildingconstruction.lab5.model.EngineeringSystem;
import ru.sfedu.buildingconstruction.lab5.model.Material;
import ru.sfedu.buildingconstruction.lab5.model.Worker;
import ru.sfedu.buildingconstruction.lab5.util.HibernateUtil;


public class HibernateProvdierTest {

    private static Logger log = Logger.getLogger(HibernateProvdierTest.class);
    static HibernateProvdier provider = new HibernateProvdier();

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
        building3.setNumberOfRooms(7);

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
    public void testDeleteBuildingPositive() {
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

    @AfterAll
    public static void testNativeSQLTimeCheck() {

        log.info("testNativeSqlTimeCheck [0]:");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            long startTime = System.currentTimeMillis();

            Building testBuilding = session
                    .createNativeQuery(Constants.NATIVE_SELECT_FROM_BUILDING_WHERE_ID_1, Building.class)
                    .getSingleResult();

            long endTime = System.currentTimeMillis();
            log.info("testNativeSqlTimeCheck [1]: building = " + testBuilding);
            log.info("testNativeSqlTimeCheck [2]: Time for native SQL:" + (endTime - startTime));

            session.getTransaction().commit();
        }

    }

    @AfterAll
    public static void testHQLTimeCheck() {

        log.info("testHQLTimeCheck [0]:");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            long startTime = System.currentTimeMillis();

            Building testBuilding = session
                    .createQuery(Constants.HQL_SELECT_FROM_BUILDING_WHERE_ID_1, Building.class)
                    .getSingleResult();

            long endTime = System.currentTimeMillis();
            log.info("testHQLTimeCheck [1]: building = " + testBuilding);
            log.info("testHQLTimeCheck [2]: Time for HQL:" + (endTime - startTime));

            session.getTransaction().commit();
        }

    }

    @AfterAll
    public static void testCriteriaTimeCheck() {

        log.info("testCriteriaTimeCheck [0]:");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Building> criteriaQuery = builder.createQuery(Building.class);

            Root<Building> root = criteriaQuery.from(Building.class);

            long startTime = System.currentTimeMillis();

            Building testBuilding = session
                    .createQuery(criteriaQuery.select(root).where(builder.equal(root.get("id"), "1")))
                    .getSingleResult();

            long endTime = System.currentTimeMillis();
            log.info("testCriteriaTimeCheck [1]: building = " + testBuilding);
            log.info("testCriteriaTimeCheck [2]: Time for Criteria:" + (endTime - startTime));

            session.getTransaction().commit();
        }

    }

    private Building generateBuildingObjectWithoutId(Building building, int numberOfCollectionElements) {

        log.debug("generateBuildingObjectWithoutId [0]:");

        Random rand = new Random();

        List<Material> materials = new ArrayList<>();
        Set<EngineeringSystem> systems = new HashSet<>();
        List<ConstructionEquipment> equioments = new ArrayList<>();
        List<Worker> workers = new ArrayList<>();

        Material material;
        ConstructionEquipment equipment;
        Worker worker;

        String uuid = UUID.randomUUID().toString();

        for (int i = 0; i < numberOfCollectionElements; i++) {

            material = new Material();
            equipment = new ConstructionEquipment();
            worker = new Worker();

            material.setName(RandomStringUtils.randomAlphabetic(10));
            material.setPrice(rand.nextInt(100));
            material.setQuantityInStock(rand.nextInt(10));

            materials.add(material);

            equipment.setName(RandomStringUtils.randomAlphabetic(10));
            equipment.setPrice(rand.nextInt(100));

            equioments.add(equipment);

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
        building.setConstructionEqipment(equioments);
        building.setWorker(workers);

        return building;
    }

}
