package ru.sfedu.buildingconstruction.lab2.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.lab2.model.TestEntity;
import ru.sfedu.buildingconstruction.lab2.util.HibernateUtil;


public class HibernateProvider {

    private static Logger log = Logger.getLogger(HibernateProvider.class);

    public void addTestEntity(TestEntity testEntity) throws EntityExistsException{
        log.debug("addTestEntity[0]: start method");
        log.info("addTestEntity[1]: testEntity = " + testEntity);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.persist(testEntity);

            session.getTransaction().commit();
        } catch (EntityExistsException ex) {
            log.error("addTestEntity[1]: entity exists");
            throw ex;
        }

    }

    public Optional<TestEntity> getTestEntity(long id) {
        log.debug("getTestEntity[0]: start method");
        log.info("getTestEntity[1]: id = " + id);
        Optional optionalRecord;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            optionalRecord = Optional.ofNullable(session.get(TestEntity.class, id));

            log.info("getTestEntity[2]: Optional<TestEntity> = " + optionalRecord);

            session.getTransaction().commit();
        }
        return optionalRecord;

    }

    public void deleteTestEntity(long id) throws EntityNotFoundException{
        log.debug("deleteTestEntity[0]: start method");
        log.info("deleteTestEntity[1]: id = " + id);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            Optional<TestEntity> testEntity = getTestEntity(id);
            if (testEntity.isPresent()) {
                log.info("deleteTestEntity[2]: delete testEntity = " + testEntity.get());
                session.remove(testEntity.get());

            } else {
                log.error("deleteTestEntity[3]: testEntity with id = " + id + " isn't exist");
                throw new EntityNotFoundException("testEntity with id = " + id + " isn't exist");
            }
            

            session.getTransaction().commit();
        }

    }

    public void updateTestEntity(TestEntity testEntity, long id) throws EntityNotFoundException{
        log.debug("updateTestEntity[0]: start method");
        log.info("updateTestEntity[1]: TestEntity = " + testEntity);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            Optional<TestEntity> oldTestEntity = getTestEntity(id);
            log.debug("updateTestEntity[2]: oldTestEntity = " + oldTestEntity);

            if (oldTestEntity.isPresent()) {
                log.info("updateTestEntity[3]: update testEntity = " + testEntity);

                testEntity.setId(oldTestEntity.get().getId());
                session.merge(testEntity);

            } else {
                log.error("updateTestEntity[4]: testEntity with id = " + id + " isn't exist");
                throw new EntityNotFoundException("testEntity with id = " + id + " isn't exist");
            }

            session.getTransaction().commit();
        }

    }

    public void deleteAllRecordsFromTable() {

        log.debug("deleteAllRecordsFromTable[0]: start method");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.createMutationQuery(Constants.DELETE_ALL_RECORDS_FROM_TEST_ENTITY).executeUpdate();

            session.getTransaction().commit();
        }

    }

}
