package ru.sfedu.buildingconstruction.lab3_1.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.lab3_1.model.Building;
import ru.sfedu.buildingconstruction.lab3_1.util.HibernateUtil;


public class HibernateProvider {

    private static Logger log = Logger.getLogger(HibernateProvider.class);

    public void addBuilding(Building building) throws EntityExistsException {
        log.debug("addBuilding[0]: start method");
        log.info("addBuilding[1]: building = " + building);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.persist(building);

            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {
             log.error("addBuilding[2]: building with id = " + building.getId() + " already exist");
            throw new EntityExistsException("building with id = " + building.getId() + " already exist");
        }

    }

    public Optional<Building> getBuilding(String id, Class clazz) {
        log.debug("getTestEntity[0]: start method");
        log.info("getBuilding[1]: id = " + id);
        log.info("getBuilding[2]: class = " + clazz);
        Optional op;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            op = Optional.ofNullable(session.get(clazz, id));

            log.info("getTestEntity[3]: Optional<Building> = " + op);

            session.getTransaction().commit();
        }
        return op;

    }

    public void deleteBuilding(String id, Class clazz) throws EntityNotFoundException{
        log.debug("deleteBuilding[0]: start method");
        log.info("deleteBuilding[1]: id = " + id);
        log.info("deleteBuilding[2]: class = " + clazz);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            Optional<Building> building = getBuilding(id, clazz);
            if (building.isPresent()) {
                log.info("deleteBuilding[3]: delete building = " + building.get());
                session.remove(building.get());

            } else {
                log.error("deleteBuilding[4]: building with id = " + id + " isn't exist");
                throw new EntityNotFoundException("building with id = " + id + " isn't exist");
            }

            session.getTransaction().commit();
        }

    }

    public void updateBuilding(Building building, String id, Class clazz) throws EntityNotFoundException{
        log.debug("updateBuilding[0]: start method");
        log.info("updateBuilding[1]: Building = " + building);
        log.info("updateBuilding[2]: class = " + clazz);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            Optional<Building> oldBuilding = getBuilding(id, clazz);
            log.debug("updateBuilding[3]: oldBuilding = " + oldBuilding);

            if (oldBuilding.isPresent()) {
                log.info("updateBuilding[4]: update building = " + building);

                building.setId(oldBuilding.get().getId());
                session.merge(building);

            } else {
                log.error("updateBuilding[5]: building with id = " + id + " isn't exist");
                throw new EntityNotFoundException("building with id = " + id + " isn't exist");
            }

            session.getTransaction().commit();
        }

    }

    public void deleteAllRecordsFromTable() {

        log.debug("deleteAllRecordsFromTable[0]: start method");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.createMutationQuery(Constants.DELETE_ALL_RECORDS_FROM_LAB_3_1).executeUpdate();

            session.getTransaction().commit();
        }

    }

}
