package ru.sfedu.buildingconstruction.lab1;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.api.HibernateUtil;


public class HibernateDataProvider {

    private static Logger log = Logger.getLogger(HibernateDataProvider.class);

    public List<String> getTableList() {
        log.debug("getTableList [0]: start method");

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        List<String> tableList = session
                .createNativeQuery(Constants.SELECT_ALL_TABLES_FROM_DB, String.class)
                .getResultList();

        log.info("getTableList [1]: tableList = " + tableList);
        session.getTransaction().commit();

        return tableList;

    }

    public List<String> getRoleList() {
        log.debug("getRoleList [0]: start method");

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        List<String> roleList = session
                .createNativeQuery(Constants.SELECT_ALL_ROLES_FROM_DB, String.class)
                .getResultList();

        log.info("getRoleList [1]: roleList = " + roleList);
        session.getTransaction().commit();

        return roleList;

    }

    public String getCurrentDatabase() {
        log.debug("getCurrentDatabase [0]: start method");

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        String currentDB = session
                .createNativeQuery(Constants.SELECT_CURRENT_DB, String.class)
                .getSingleResult();

        log.info("getCurrentDatabase [1]: currentDB = " + currentDB);
        session.getTransaction().commit();

        return currentDB;

    }

    public List<String> getAllColumnsFromTable(String tableName) {
        log.debug("getAllColumnsFromTable [0]: start method");
        log.debug("getAllColumnsFromTable [1]: tableName = " + tableName);

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        List<String> columnsList = session
                .createNativeQuery(tableName, String.class)
                .getResultList();

        log.info("getAllColumnsFromTable [2]: columnsList = " + columnsList);
        session.getTransaction().commit();

        return columnsList;

    }

}
