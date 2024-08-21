package ru.sfedu.buildingconstruction.lab3_2.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.log4j.Logger;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.lab3_2.model.ApartmentHouse;
import ru.sfedu.buildingconstruction.lab3_2.model.Client;
import ru.sfedu.buildingconstruction.lab3_2.model.Garage;
import ru.sfedu.buildingconstruction.lab3_2.model.House;


public class HibernateProvider2Test {

    private static Logger log = Logger.getLogger(HibernateProvider2Test.class);
    static HibernateProvider2 provider = new HibernateProvider2();

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
        building1.setSquare(777);
        building1.setNumberOfFloors(7);
        building1.setCompletionDate(LocalDate.now());
        building1.setNumberOfApartments(3);

        Client owner = new Client();
        owner.setId("1");
        owner.setName("Max");
        owner.setPassport("47837483");
        owner.setEmail("XXX@ds.er");
        owner.setPhoneNumber("8647381821");

        building1.setOwner(owner);

        building2 = new ApartmentHouse();
        building2.setId("2");
        building2.setSquare(564);
        building2.setNumberOfFloors(43);
        building2.setCompletionDate(LocalDate.now());
        building2.setNumberOfApartments(5);

        Client owner2 = new Client();
        owner2.setId("2");
        owner2.setName("Ivan");
        owner2.setPassport("47837483");
        owner2.setEmail("YYY@ddss.er");
        owner2.setPhoneNumber("78327428");

        building2.setOwner(owner2);

        building3 = new House();
        building3.setId("3");
        building3.setSquare(31);
        building3.setNumberOfFloors(43);
        building3.setCompletionDate(LocalDate.now());
        building3.setNumberOfrooms(8);

        Client owner3 = new Client();
        owner3.setId("3");
        owner3.setName("Vova");
        owner3.setPassport("784274");
        owner3.setEmail("vova@mail.er");
        owner3.setPhoneNumber("8398391");

        building3.setOwner(owner3);

        building4 = new House();
        building4.setId("4");
        building4.setSquare(23);
        building4.setNumberOfFloors(2);
        building4.setCompletionDate(LocalDate.now());
        building4.setNumberOfrooms(1);

        Client owner4 = new Client();
        owner4.setId("4");
        owner4.setName("Oleg");
        owner4.setPassport("3827328");
        owner4.setEmail("oleja@mail.er");
        owner4.setPhoneNumber("8398391");

        building4.setOwner(owner4);

        building5 = new Garage();
        building5.setId("5");
        building5.setSquare(23);
        building5.setNumberOfFloors(2);
        building5.setCompletionDate(LocalDate.now());
        building5.setNumberOfCars(2);

        Client owner5 = new Client();
        owner5.setId("5");
        owner5.setName("Lesha");
        owner5.setPassport("3827328");
        owner5.setEmail("alesha@mail.er");
        owner5.setPhoneNumber("8398391");

        building5.setOwner(owner5);
    }

    @Test
    public void testAddBuildingPositive() {

        log.debug("testAddBuildingPositive [0]:");
        provider.addBuilding(building1);

        assertEquals(provider.getBuilding(building1.getId(), building1.getClass()).get(), building1);

    }

    @Test
    public void testAddBuildingNegative() {
        log.debug("testAddTestEntityNegative [0]:");
        provider.addBuilding(building2);
        assertThrows(EntityExistsException.class, () -> provider.addBuilding(building2));

    }

    @Test
    public void testGetBuildingPositive() {

        log.debug("testGetBuildingPositive [0]:");
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
        provider.addBuilding(building4);

        provider.deleteBuilding(building4.getId(), building4.getClass());

        assertEquals(provider.getBuilding(building4.getId(), building4.getClass()), Optional.empty());
    }

    @Test
    public void testDeleteBuildingNegative() {
        log.debug("testDeleteBuildingNegative [0]:");

        assertThrows(EntityNotFoundException.class, () -> provider.deleteBuilding(Constants.INCORRECTID, House.class));
    }

    @Test
    public void testUpdateBuildingPositive() {

        log.debug("testUpdateBuildingPositive [0]:");
        provider.addBuilding(building5);
        building5.setNumberOfFloors(4);
        building5.setNumberOfCars(6);

        provider.updateBuilding(building5, building5.getId(), building5.getClass());

        assertEquals(provider.getBuilding(building5.getId(), building5.getClass()).get(), building5);

    }

    @Test
    public void testUpdateBuildingNegative() {

        log.debug("testUpdateBuildingNegative [0]:");
        assertThrows(EntityNotFoundException.class,
                () -> provider.updateBuilding(building5, Constants.INCORRECTID, building5.getClass()));

    }

}
