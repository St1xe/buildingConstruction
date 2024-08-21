package ru.sfedu.buildingconstruction.lab1;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.sfedu.buildingconstruction.Constants;


public class HibernateDataProviderTest {

    private static Logger log = Logger.getLogger(HibernateDataProviderTest.class);
    HibernateDataProvider provider = new HibernateDataProvider();

    @Test
    public void testGetTableList() {
        log.debug("testGetTableList [0]:");
        List<String> list = new ArrayList<>();
        list.add(Constants.WORKER_TABLE);
        list.add(Constants.GARAGE_TABLE);
        list.add(Constants.MATERIAL_TABLE);
        list.add(Constants.CLIENT_TABLE);
        list.add(Constants.CONSTRUCTION_EQUIPMENT_TABLE);
        list.add(Constants.HOUSE_TABLE);
        list.add(Constants.APARTMENT_HOUSE_TABLE);

        assertEquals(list, provider.getTableList());

    }

    @Test
    public void testGetRoleList() {
        log.debug("testGetRoleList [0]:");
        List<String> list = new ArrayList<>();
        list.add("postgres");
        list.add("root");
        assertEquals(list, provider.getRoleList());
    }

    @Test
    public void testGetCurrentDatabase() {
        log.debug("testGetCurrentDatabase [0]:");

        assertEquals(Constants.CURRENT_DB, provider.getCurrentDatabase());
    }

    @Test
    public void testGetAllColumnsFromTableWorker() {

        log.debug("testGetAllColumnsFromTableWorker [0]:");
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("name");
        list.add("phonenumber");
        list.add("jobtittle");
        list.add("salary");

        assertEquals(list, provider.getAllColumnsFromTable(Constants.SELECT_COLUMN_NAME_FROM_WORKER_TABLE));
    }

}
