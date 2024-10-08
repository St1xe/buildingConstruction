package ru.sfedu.buildingconstruction;


public class Constants {

    public static final String PATH = "config";
    public static final String ACTOR = "system";

    public static final int PEOPLE_FOR_BUILD_A_GARAGE = 4;
    public static final int PEOPLE_FOR_BUILD_A_HOUSE = 6;
    public static final int PEOPLE_FOR_BUILD_AN_APARTMENT_HOUSE = 30;

    public static final int NUMBER_OF_EQUIPMENT_FOR_BUILD_A_GARAGE = 5;
    public static final int NUMBER_OF_EQUIPMENT_FOR_BUILD_A_HOUSE = 7;
    public static final int NUMBER_OF_EQUIPMENT_FOR_BUILD_AN_APARTMENT_HOUSE = 15;

    public static final int TIME_IN_MONTH_FOR_BUILD_AN_APARTMENT_HOUSE = 18;
    public static final int TIME_IN_MONTH_FOR_BUILD_A_HOUSE = 6;
    public static final int TIME_IN_MONTH_FOR_BUILD_A_GARAGE = 3;

    public static final String MONGO_ID = "id";
    public static final String MONGO_CLASS_NAME = "className";
    public static final String MONGO_CREATED_DATA = "createdData";
    public static final String MONGO_ACTOR = "actor";
    public static final String MONGO_METHOD_NAME = "methodName";
    public static final String MONGO_STATUS = "status";
    public static final String MONGO_OBJECT = "Object";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String JOB_TITTLE = "jobTittle";
    public static final String SALARY = "salary";
    public static final String PRICE = "price";
    public static final String QUANTITY_IN_STOCK = "quantityInStock";
    public static final String EMAIL = "email";
    public static final String PASSPORT = "passport";
    public static final String SQUARE = "square";
    public static final String NUMBER_OF_APARTMENTS = "numberOfApartments";
    public static final String NUMBER_OF_FLOORS = "numberOfFloors";
    public static final String COMPLETION_DATE = "completionDate";
    public static final String ENGINEERING_SYSTEMS = "engineeringSystems";
    public static final String OWNER_ID = "ownerID";
    public static final String MATERIALS_ID = "materialsId";
    public static final String CONSTRUCTION_EQUIPMENTS_ID = "constructionEquipmentsId";
    public static final String WORKERS_ID = "workersId";
    public static final String NUMBER_OF_ROOMS = "numberOfRooms";
    public static final String NUMBER_OF_CARS = "numberOfCars";

    public static final String MONGO_DB_NAME = "MONGO_DB_NAME";
    public static final String MONGO_TABLE_NAME = "MONGO_TABLE_NAME";
    public static final String MONGO_URI = "MONGO_URI";

    public static final String PATH_TO_RESOURCES = "src/main/resources/";
    public static final String DEFAULT_PROPERTY_CONFIG_PATH = "environment.properties";
    public static final String DEFAULT_XML_CONFIG_PATH = "environment.xml";
    public static final String DEFAULT_YAML_CONFIG_PATH = "environment.yml";

    public static final String REGEX_TO_CHECK_XML_FILE = "\\S+\\.xml";
    public static final String REGEX_TO_CHECK_PROPERTY_FILE = "\\S+\\.properties";
    public static final String REGEX_TO_CHECK_YML_FILE = "\\S+\\.yml";

    public static final String REGEX_TO_CHECK_PHONE_NUMBER = "\\+?[1-9]\\d{10}";
    public static final String REGEX_TO_CHECK_EMAIL = "((\\w)|(\\.)|(\\-))+\\@[a-z]+\\.[a-z]+";
    public static final String REGEX_TO_CHECK_PASSPORT = "\\d{4}\\s?\\d{6}";

    public static final String PATH_TO_WORKER_CSV_FILE = "worker.csv";
    public static final String PATH_TO_MATERIAL_CSV_FILE = "material.csv";
    public static final String PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE = "constructionEquipment.csv";
    public static final String PATH_TO_CLIENT_CSV_FILE = "client.csv";
    public static final String PATH_TO_HOUSE_CSV_FILE = "house.csv";
    public static final String PATH_TO_GARAGE_CSV_FILE = "garage.csv";
    public static final String PATH_TO_APARTMENT_HOUSE_CSV_FILE = "apartmentHouse.csv";

    public static final String PATH_TO_WORKER_XML_FILE = "worker.xml";
    public static final String PATH_TO_MATERIAL_XML_FILE = "material.xml";
    public static final String PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE = "constructionEquipment.xml";
    public static final String PATH_TO_CLIENT_XML_FILE = "client.xml";
    public static final String PATH_TO_HOUSE_XML_FILE = "house.xml";
    public static final String PATH_TO_GARAGE_XML_FILE = "garage.xml";
    public static final String PATH_TO_APARTMENT_HOUSE_XML_FILE = "apartmentHouse.xml";

    public static final String INCORRECTID = "XXXXXXXXXXXXXXXXXX";
    public static final String INCORRECTPATH = "PATH";

    public static final String PATH_TO_DB = "PATH_TO_DB";
    public static final String USERNAME_TO_DB = "USERNAME_TO_DB";
    public static final String PASSWORD_TO_DB = "USERNAME_TO_DB";

    public static final String CREATE_DB = "SELECT 'CREATE DATABASE databaseprovider'"
            + "WHERE NOT EXISTS ("
            + "SELECT FROM pg_database"
            + " WHERE datname = 'databaseprovider')\\gexec";

    public final static String INSERT_NEW_WORKER = "INSERT INTO worker VALUES(?,?,?,?,?)";
    public final static String INSERT_NEW_CONSTRUCTION_EQUIPMENT = "INSERT INTO constructionEquipment VALUES(?,?,?)";
    public final static String INSERT_NEW_MATERIAL = "INSERT INTO material VALUES(?,?,?,?)";
    public final static String INSERT_NEW_CLIENT = "INSERT INTO client VALUES(?,?,?,?,?)";
    public final static String INSERT_NEW_HOUSE = "INSERT INTO house VALUES(?,?,?,?,?,?,?,?,?,?)";
    public final static String INSERT_NEW_GARAGE = "INSERT INTO garage VALUES(? ,?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public final static String INSERT_NEW_APARTMENT_HOUSE = "INSERT INTO apartmenthouse VALUES(?,?,?,?,?,?,?,?,?,?)";

    public final static String CREATE_WORKER_TABLE = "CREATE TABLE " + "worker" + " ("
            + "id VARCHAR(40) PRIMARY KEY, "
            + "name VARCHAR(50) NOT NULL ,"
            + "phoneNumber VARCHAR(12) NOT NULL UNIQUE,"
            + "jobTittle VARCHAR(40) NOT NULL,"
            + "salary NUMERIC(15,2) NOT NULL);";

    public final static String CREATE_CONSTRUCTION_EQIPMENT_TABLE = "CREATE TABLE " + "constructionEquipment" + " ("
            + "id VARCHAR(40) PRIMARY KEY, "
            + "name VARCHAR(50) NOT NULL,"
            + "price NUMERIC(15,2) NOT NULL);";

    public final static String CREATE_MATERIAL_TABLE = "CREATE TABLE " + "material" + " ("
            + "id VARCHAR(40) PRIMARY KEY, "
            + "name VARCHAR(50) NOT NULL UNIQUE,"
            + "price NUMERIC(15,2) NOT NULL,"
            + "quantityInStock NUMERIC(15,2) NOT NULL);";

    public final static String CREATE_CLIENT_TABLE = "CREATE TABLE " + "client" + " ("
            + "id VARCHAR(40) PRIMARY KEY, "
            + "name VARCHAR(50) NOT NULL ,"
            + "phoneNumber VARCHAR(12) NOT NULL,"
            + "email VARCHAR(50) NOT NULL,"
            + "passport VARCHAR(12) NOT NULL UNIQUE);";

    public final static String CREATE_APARTMENT_HOUSE_TABLE = "CREATE TABLE " + "apartmentHouse" + " ("
            + "id VARCHAR(40) PRIMARY KEY, "
            + "numberOfApartments int, "
            + "square NUMERIC(15,2),"
            + "numberOfFloors int,"
            + "completionDate date,"
            + "ownerId VARCHAR(40),"
            + "materialsId text,"
            + "engineeringSystems text,"
            + "constructionEquipmentsId text,"
            + "workersId text);";

    public final static String CREATE_GARAGE_TABLE = "CREATE TABLE " + "garage" + " ("
            + "id VARCHAR(40) PRIMARY KEY, "
            + "numberOfCars int, "
            + "square NUMERIC(15,2),"
            + "numberOfFloors int,"
            + "completionDate date,"
            + "ownerId VARCHAR(40),"
            + "materialsId text,"
            + "engineeringSystems text,"
            + "constructionEquipmentsId text,"
            + "workersId text);";

    public final static String CREATE_HOUSE_TABLE = "CREATE TABLE " + "house" + " ("
            + "id VARCHAR(40) PRIMARY KEY, "
            + "numberOfRooms int, "
            + "square NUMERIC(15,2),"
            + "numberOfFloors int,"
            + "completionDate date,"
            + "ownerId VARCHAR(40),"
            + "materialsId text,"
            + "engineeringSystems text,"
            + "constructionEquipmentsId text,"
            + "workersId text);";

    public final static String DELETE_WORKER = "DELETE FROM worker WHERE id = ?";
    public final static String DELETE_CONSTRUCTION_EQUIPMENT = "DELETE FROM constructionEquipment WHERE id = ?";
    public final static String DELETE_MATERIAL = "DELETE FROM material WHERE id = ?";
    public final static String DELETE_CLIENT = "DELETE FROM client WHERE id = ?";
    public final static String DELETE_APARTMENT_HOUSE = "DELETE FROM apartmentHouse WHERE id = ?";
    public final static String DELETE_HOUSE = "DELETE FROM house WHERE id = ?";
    public final static String DELETE_GARAGE = "DELETE FROM garage WHERE id = ?";

    public final static String GET_WORKER = "SELECT * FROM worker WHERE id = ?";
    public final static String GET_CONSTRUCTION_EQUIPMENT = "SELECT * FROM constructionEquipment WHERE id = ?";
    public final static String GET_MATERIAL = "SELECT * FROM material WHERE id = ?";
    public final static String GET_CLIENT = "SELECT * FROM client WHERE id = ?";
    public final static String GET_APARTMENT_HOUSE = "SELECT * FROM apartmentHouse WHERE id = ?";
    public final static String GET_HOUSE = "SELECT * FROM house WHERE id = ?;";
    public final static String GET_GARAGE = "SELECT * FROM garage WHERE id = ?";

    public final static String GET_ALL_WORKERS = "SELECT * FROM worker";
    public final static String GET_ALL_CONSTRUCTION_EQUIPMENT = "SELECT * FROM constructionEquipment";
    public final static String GET_ALL_MATERIAL = "SELECT * FROM material";
    public final static String GET_ALL_CLIENT = "SELECT * FROM client";
    public final static String GET_ALL_APARTMENT_HOUSE = "SELECT * FROM apartmentHouse";
    public final static String GET_ALL_HOUSE = "SELECT * FROM house";
    public final static String GET_ALL_GARAGE = "SELECT * FROM garage";

    public final static String UPDATE_WORKER = "UPDATE worker SET id=?,"
            + " name = ?,"
            + " phoneNumber = ?,"
            + "jobtittle = ?,"
            + "salary = ?"
            + " WHERE id = ?";

    public final static String UPDATE_CONSTRUCTION_EQUIPMENT = "UPDATE constructionEquipment SET id=?,"
            + "name = ? ,"
            + "price = ?"
            + " WHERE id = ?";

    public final static String UPDATE_MATERIAL = "UPDATE material SET id=?,"
            + "name = ?,"
            + "price = ?,"
            + "quantityInStock = ?"
            + " WHERE id = ?";

    public final static String UPDATE_CLIENT = "UPDATE client SET id=?,"
            + "name = ?, "
            + "phoneNumber = ?,"
            + "email = ?,"
            + "passport = ?"
            + " WHERE id = ?";

    public final static String UPDATE_APARTMENT_HOUSE = "UPDATE apartmentHouse SET id=?,"
            + "numberOfApartments = ?,"
            + "square = ?,"
            + "numberOfFloors = ?,"
            + "completionDate = ?,"
            + "ownerId = ?,"
            + "materialsId = ?,"
            + "engineeringSystems = ?,"
            + "constructionEquipmentsId = ?,"
            + "workersId = ?"
            + " WHERE id = ?";

    public final static String UPDATE_HOUSE = "UPDATE house SET id=?,"
            + "numberOfRooms = ?,"
            + "square = ?,"
            + "numberOfFloors = ?,"
            + "completionDate = ?,"
            + "ownerId = ?,"
            + "materialsId = ?,"
            + "engineeringSystems = ?,"
            + "constructionEquipmentsId = ?,"
            + "workersId = ?"
            + " WHERE id = ?";

    public final static String UPDATE_GARAGE = "UPDATE garage SET id=?,"
            + "numberOfCars = ?,"
            + "square = ?,"
            + "numberOfFloors = ?,"
            + "completionDate = ?,"
            + "ownerId = ?,"
            + "materialsId = ?,"
            + "engineeringSystems = ?,"
            + "constructionEquipmentsId = ?,"
            + "workersId = ?"
            + " WHERE id = ?";

    public static final String WORKER_TABLE = "worker";
    public static final String CONSTRUCTION_EQUIPMENT_TABLE = "constructionequipment";
    public static final String MATERIAL_TABLE = "material";
    public static final String CLIENT_TABLE = "client";
    public static final String APARTMENT_HOUSE_TABLE = "apartmenthouse";
    public static final String HOUSE_TABLE = "house";
    public static final String GARAGE_TABLE = "garage";

    public static final String SELECT_ALL_FROM = "SELECT * FROM ";

    public static final String SELECT_ALL_TABLES_FROM_DB = "SELECT table_name\n"
            + "FROM information_schema.tables\n"
            + "WHERE table_schema = 'public'";

    public static final String SELECT_ALL_ROLES_FROM_DB = "SELECT usename FROM pg_catalog.pg_user";
    public static final String SELECT_CURRENT_DB = "SELECT current_catalog";

    public static final String SELECT_COLUMN_NAME_FROM_WORKER_TABLE = "select column_name \n"
            + "from information_schema.columns \n"
            + "where table_schema='public' and table_name = 'worker';";
    
    public static final String SELECT_COLUMN_NAME_FROM_CONSTRUCTION_EQUIPMENT_TABLE = "select column_name \n"
            + "from information_schema.columns \n"
            + "where table_schema='public' and table_name = 'constructionequipment';";
    
    public static final String SELECT_COLUMN_NAME_FROM_MATERIAL_TABLE = "select column_name \n"
            + "from information_schema.columns \n"
            + "where table_schema='public' and table_name = 'material';";
    
    public static final String SELECT_COLUMN_NAME_FROM_CLIENT_TABLE = "select column_name \n"
            + "from information_schema.columns \n"
            + "where table_schema='public' and table_name = 'CLIENT';";
    
    public static final String SELECT_COLUMN_NAME_FROM_APARTMENT_HOUSE_TABLE = "select column_name \n"
            + "from information_schema.columns \n"
            + "where table_schema='public' and table_name = 'apartmenthouse';";
  
    public static final String SELECT_COLUMN_NAME_FROM_HOUSE_TABLE = "select column_name \n"
            + "from information_schema.columns \n"
            + "where table_schema='public' and table_name = 'house';";
  
    public static final String SELECT_COLUMN_NAME_FROM_GARAGE_TABLE = "select column_name \n"
            + "from information_schema.columns \n"
            + "where table_schema='public' and table_name = 'garage';";
    
    public static final String CURRENT_DB = "databaseprovider";
  

    public static final String HIBERNATE_CONFIG = "HIBERNATE_CONFIG";
    public static final String HIBERNATE_CONFIG_LAB_2 = "HIBERNATE_CONFIG_LAB_2";
    
    public static final String DELETE_ALL_RECORDS_FROM_TEST_ENTITY = "DELETE TestEntity";
    public static final String DELETE_ALL_RECORDS_FROM_LAB_3_1 = "DELETE ru.sfedu.buildingconstruction.lab3_1.model.Building";
    public static final String DELETE_ALL_RECORDS_FROM_LAB_3_2 = "DELETE ru.sfedu.buildingconstruction.lab3_2.model.Building";
    public static final String DELETE_ALL_RECORDS_FROM_LAB_3_3 = "DELETE ru.sfedu.buildingconstruction.lab3_3.model.Building";
    public static final String DELETE_ALL_RECORDS_FROM_LAB_3_4 = "DELETE ru.sfedu.buildingconstruction.lab3_4.model.Building";
    public static final String DELETE_ALL_RECORDS_FROM_LAB_4 = "DELETE ru.sfedu.buildingconstruction.lab4.model.Building";
    
    public static final String DELETE_BUILDING_RECORDS = "DELETE ru.sfedu.buildingconstruction.lab5.model.Building";
    public static final String DELETE_MATERIAL_RECORDS = "DELETE ru.sfedu.buildingconstruction.lab5.model.Material";
    public static final String DELETE_CONSTRUCTION_EQUIPMENT_RECORDS = "DELETE ru.sfedu.buildingconstruction.lab5.model.ConstructionEquipment";
    public static final String DELETE_WORKER_RECORDS = "DELETE ru.sfedu.buildingconstruction.lab5.model.Worker";
    public static final String DELETE_CLIENT_RECORDS = "DELETE ru.sfedu.buildingconstruction.lab5.model.Client";
    
    public static final String NATIVE_SELECT_FROM_BUILDING_WHERE_ID_1 = "SELECT * FROM lab5_building where id = '1'";
    public static final String HQL_SELECT_FROM_BUILDING_WHERE_ID_1 = "FROM Building where id = '1'";
    
    
    
    public static final long INCORRECT_LONG_ID = -1;

}
