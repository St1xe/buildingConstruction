package ru.sfedu.buildingconstruction.lab2.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import ru.sfedu.buildingconstruction.lab2.model.TestEntity;
import ru.sfedu.buildingconstruction.lab2.model.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.sfedu.buildingconstruction.Constants;


public class HibernateProviderTest {

    private static Logger log = Logger.getLogger(HibernateProviderTest.class);
    static HibernateProvider provider = new HibernateProvider();

    static TestEntity testEntity1;
    static TestEntity testEntity2;
    static TestEntity testEntity3;
    static TestEntity testEntity4;
    static TestEntity testEntity5;
    static User user1;
    static User user2;
    static User user3;
    static User user4;
    static User user5;

    @BeforeAll
    public static void setUpClass() {
        log.debug("Before all[0]: starting tests");

        provider.deleteAllRecordsFromTable();

        testEntity1 = new TestEntity();
        testEntity1.setName("Entity1");
        testEntity1.setDescription("some description");
        testEntity1.setDateCreated(LocalDate.now());
        testEntity1.setCheck(true);

        user1 = new User();
        user1.setName("Maxim");
        user1.setSurname("Mozgovoy");
        user1.setPhone("859895");
        testEntity1.setUser(user1);

        testEntity2 = new TestEntity();
        testEntity2.setName("Entity2");
        testEntity2.setDescription("SMTH");
        testEntity2.setDateCreated(LocalDate.now());
        testEntity2.setCheck(false);

        user2 = new User();
        user2.setName("Oleg");
        user2.setSurname("Ivanov");
        user2.setPhone("88392893");
        testEntity2.setUser(user2);

        testEntity3 = new TestEntity();
        testEntity3.setName("Entity3");
        testEntity3.setDescription("SMTH");
        testEntity3.setDateCreated(LocalDate.now());
        testEntity3.setCheck(true);

        user3 = new User();
        user3.setName("Petr");
        user3.setSurname("Pirogov");
        user3.setPhone("883929");
        testEntity3.setUser(user3);

        testEntity4 = new TestEntity();
        testEntity4.setName("Entity4");
        testEntity4.setDescription("SMTH");
        testEntity4.setDateCreated(LocalDate.now());
        testEntity4.setCheck(false);

        user4 = new User();
        user4.setName("Oleg");
        user4.setSurname("Mishin");
        user4.setPhone("8942742");
        testEntity4.setUser(user4);

        testEntity5 = new TestEntity();
        testEntity5.setName("Entity5");
        testEntity5.setDescription("SMTH");
        testEntity5.setDateCreated(LocalDate.now());
        testEntity5.setCheck(false);

        user5 = new User();
        user5.setName("Oleg");
        user5.setSurname("Ivanonv");
        user5.setPhone("893299");
        testEntity5.setUser(user5);

    }

    @Test
    public void testAddTestEntityPositive() {
        log.debug("testAddTestEntityPositive [0]:");
        provider.addTestEntity(testEntity1);

        assertEquals(provider.getTestEntity(testEntity1.getId()).get(), testEntity1);

    }

    @Test
    public void testAddTestEntityNegative() {
        log.debug("testAddTestEntityNegative [0]:");
        provider.addTestEntity(testEntity2);
        assertThrows(EntityExistsException.class, () -> provider.addTestEntity(testEntity2));

    }

    @Test
    public void testGetTestEntityPositive() {

        log.debug("testGetTestEntityPositive [0]:");
        provider.addTestEntity(testEntity3);

        assertEquals(provider.getTestEntity(testEntity3.getId()), Optional.of(testEntity3));

    }

    @Test
    public void testGetTestEntityNegative() {
        log.debug("testGetTestEntityNegative [0]:");

        assertEquals(provider.getTestEntity(Constants.INCORRECT_LONG_ID), Optional.empty());

    }

    @Test
    public void testDeleteTestEntityPositive() {

        log.debug("testDeleteTestEntityPositive [0]:");
        provider.addTestEntity(testEntity4);

        provider.deleteTestEntity(testEntity4.getId());

        assertEquals(provider.getTestEntity(testEntity4.getId()), Optional.empty());

    }

    @Test
    public void testDeleteTestEntityNegative() {

        log.debug("testDeleteTestEntityNegative [0]:");

        assertThrows(EntityNotFoundException.class, () -> provider.deleteTestEntity(Constants.INCORRECT_LONG_ID));

    }

    @Test
    public void testUpdateTestEntityPositive() {
        log.debug("testUpdateTestEntityPositive [0]:");
        provider.addTestEntity(testEntity5);
        testEntity5.setName("Other name");
        testEntity5.setDescription("Smth description");

        provider.updateTestEntity(testEntity5, testEntity5.getId());

        assertEquals(provider.getTestEntity(testEntity5.getId()).get(), testEntity5);

    }

    @Test
    public void testUpdateTestEntityNegative() {
        log.debug("testUpdateTestEntityPositive [0]:");
        assertThrows(EntityNotFoundException.class,
                () -> provider.updateTestEntity(testEntity5, Constants.INCORRECT_LONG_ID));
    }

}
