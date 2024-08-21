package ru.sfedu.buildingconstruction.api;

import java.time.LocalDate;
import java.time.Month;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.model.*;


public class BaseTest {

    private static Logger log = Logger.getLogger(BaseTest.class);
    
    public static Worker worker;
    public static Worker worker2;
    public static Worker worker3;
    public static Worker worker4;
    public static Worker worker5;
    public static Worker worker6;
    public static Worker worker7;
    public static Worker worker8;
    public static Worker worker9;
    public static Worker worker10;
    public static Worker worker11;
    public static Worker worker21;
    public static Worker worker22;
    public static Worker worker23;
    public static Worker worker31;
    public static Worker worker34;
    public static Worker worker35;

    public static Material material;
    public static Material material2;
    public static Material material3;
    public static Material material4;
    public static Material material5;
    public static Material material6;
    public static Material material7;
    public static Material material8;
    public static Material material9;
    public static Material material10;
    public static Material material11;
    public static Material material21;
    public static Material material22;
    public static Material material23;
    public static Material material31;
    public static Material material32;
    public static Material material33;
    public static Material material34;
    public static Material material35;

    public static ConstructionEquipment constructionEquipment;
    public static ConstructionEquipment constructionEquipment2;
    public static ConstructionEquipment constructionEquipment3;
    public static ConstructionEquipment constructionEquipment4;
    public static ConstructionEquipment constructionEquipment5;
    public static ConstructionEquipment constructionEquipment6;
    public static ConstructionEquipment constructionEquipment7;
    public static ConstructionEquipment constructionEquipment8;
    public static ConstructionEquipment constructionEquipment9;
    public static ConstructionEquipment constructionEquipment10;
    public static ConstructionEquipment constructionEquipment11;
    public static ConstructionEquipment constructionEquipment21;
    public static ConstructionEquipment constructionEquipment22;
    public static ConstructionEquipment constructionEquipment23;
    public static ConstructionEquipment constructionEquipment31;
    public static ConstructionEquipment constructionEquipment32;
    public static ConstructionEquipment constructionEquipment33;
    public static ConstructionEquipment constructionEquipment34;
    public static ConstructionEquipment constructionEquipment35;

    public static Client client;
    public static Client client2;
    public static Client client3;
    public static Client client4;
    public static Client client5;
    public static Client client6;
    public static Client client7;
    public static Client client8;
    public static Client client9;
    public static Client client10;
    public static Client client11;
    public static Client client21;
    public static Client client22;
    public static Client client23;
    public static Client client31;

    public static ApartmentHouse apartmentHouse;
    public static ApartmentHouse apartmentHouse2;
    public static ApartmentHouse apartmentHouse3;
    public static ApartmentHouse apartmentHouse4;
    public static ApartmentHouse apartmentHouse5;
    public static ApartmentHouse apartmentHouse6;
    public static ApartmentHouse apartmentHouse7;
    public static ApartmentHouse apartmentHouse8;
    public static ApartmentHouse apartmentHouse9;
    public static ApartmentHouse apartmentHouse10;
    public static ApartmentHouse apartmentHouse11;
    public static ApartmentHouse apartmentHouse31;
    public static ApartmentHouse apartmentHouse33;

    public static House house;
    public static House house2;
    public static House house3;
    public static House house4;
    public static House house5;
    public static House house6;
    public static House house7;
    public static House house8;
    public static House house9;
    public static House house10;
    public static House house11;
    public static House house31;

    public static Garage garage;
    public static Garage garage2;
    public static Garage garage3;
    public static Garage garage4;
    public static Garage garage5;
    public static Garage garage6;
    public static Garage garage7;
    public static Garage garage8;
    public static Garage garage9;
    public static Garage garage10;
    public static Garage garage11;
    public static Garage garage31;

    public static void createAllRecords() {
        log.debug("createAllRecords [1]:");
        createWorkerRecords();
        createMaterialRecords();
        createConstructionEquipmentRecords();
        createClientRecords();
        createApartmentHouseRecords();
        createHouseRecords();
        createGarageRecords();
    }

    public static void createWorkerRecords() {
        log.debug("createWorkerRecords [1]:");
        worker = new Worker("Max", "89081950533", "Инженер", 1500000);
        worker2 = new Worker("Oleg", "+79843748324", "Строитель", 60777);
        worker3 = new Worker("Misha", "+79937831234", "Монтажник", 75000);
        worker4 = new Worker("Vasya", "+79871647302", "Каменщик", 62000);
        worker5 = new Worker("Denis", "+7986574932", "Строитель", 67000);
        worker6 = new Worker("Viktor", "+79176543728", "Строитель", 76000);
        worker7 = new Worker("Boris", "89764345611", "Слесарь", 45000);
        worker8 = new Worker("Ivan", "89043456721", "Сварщик", 76000);
        worker9 = new Worker("Nikita", "89525143728", "Сварщик", 78000);
        worker10 = new Worker("Aleksey", "89764324565", "Строитель", 62000);
        worker11 = new Worker("Vova", "89187456732", "Строитель", 63000);
        
        worker21 = new Worker("Aleksey", "+78949349322", "Строитель", 63000);
        worker22 = new Worker("Denis", "88005553535", "Строитель", 63000);
        worker23 = new Worker("Bori", "88003456745", "Строитель", 63000);
        
        worker31 = new Worker("XX", "89993948212", "", 31333);
        worker34 = new Worker("xsa", "84393948212", "xsa", 31333);
        worker35 = new Worker("aaa", "84393943312", "xsass", 10000);
        
        worker.setId("1");
        worker2.setId("2");
        worker3.setId("3");
        worker4.setId("4");
        worker5.setId("5");
        worker6.setId("6");
        worker7.setId("7");
        worker8.setId("8");
        worker9.setId("9");
        worker10.setId("10");
        worker11.setId("11");
        
        worker21.setId("21");
        worker22.setId("22");
        worker23.setId("23");
        
        worker31.setId("31");
        worker34.setId("34");
        worker35.setId("35");
    }

    public static void createMaterialRecords() {
        log.debug("createMaterialRecords [1]:");
        material = new Material("Кирпич", 12150, 14);
        material2 = new Material("Бетон", 15054, 14);
        material3 = new Material("Кровля", 1506, 14);
        material4 = new Material("Газоблок", 350, 14);
        material5 = new Material("Керамоблок", 150, 14);
        material6 = new Material("Брус", 1750, 14);
        material7 = new Material("SIP", 15430, 14);
        material8 = new Material("Арболитовый блок", 7150, 14);
        material9 = new Material("Лафет", 1540, 14);
        material10 = new Material("ЛСТК", 1530, 14);
        material11 = new Material("Силикатный блок", 1150, 14);
        
        material21 = new Material("ЛДСП", 750, 14);
        material22 = new Material("Белый кирпич", 879, 14);
        material23 = new Material("Гипс", 456, 14);
        
        material31 = new Material("XXX", 432, 16);
        material32 = new Material("YYY", 312, 14);
        material33 = new Material("ZZZ", 342, 12);
        material34 = new Material("AAA", 3342, 122);
        material35 = new Material("iuhuiahgdi", 111, 100);
        
        
        material.setId("1");
        material2.setId("2");
        material3.setId("3");
        material4.setId("4");
        material5.setId("5");
        material6.setId("6");
        material7.setId("7");
        material8.setId("8");
        material9.setId("9");
        material10.setId("10");
        material11.setId("11");
        
        material21.setId("21");
        material22.setId("22");
        material23.setId("23");
        
        material31.setId("31");
        material32.setId("32");
        material33.setId("33");
        material34.setId("34");
        material35.setId("35");
        
        
    }

    public static void createConstructionEquipmentRecords() {
        log.debug("createConstructionEquipmentRecords [1]:");
        constructionEquipment = new ConstructionEquipment("Кран", 14500);
        constructionEquipment2 = new ConstructionEquipment("Трактор", 314);
        constructionEquipment3 = new ConstructionEquipment("Экскаватор", 4121);
        constructionEquipment4 = new ConstructionEquipment("Погрузчик", 313212);
        constructionEquipment5 = new ConstructionEquipment("Бетономешалка", 3124);
        constructionEquipment6 = new ConstructionEquipment("Башенный кран", 646);
        constructionEquipment7 = new ConstructionEquipment("Асфальтоукладчик", 422);
        constructionEquipment8 = new ConstructionEquipment("Бульдозер", 6464);
        constructionEquipment9 = new ConstructionEquipment("Самосвал", 3535);
        constructionEquipment10 = new ConstructionEquipment("Каток", 6464);
        constructionEquipment11 = new ConstructionEquipment("Грейдер", 42425);
        
        constructionEquipment21 = new ConstructionEquipment("Грейдер", 18000);
        constructionEquipment22 = new ConstructionEquipment("Кран", 42425);
        constructionEquipment23 = new ConstructionEquipment("Погрузчик", 1500);
        
        constructionEquipment31 = new ConstructionEquipment("Авто", 1800);
        
        constructionEquipment34 = new ConstructionEquipment("Машина", 1900);
        constructionEquipment35 = new ConstructionEquipment("bjdsbjc", 1000);
        
        constructionEquipment.setId("1");
        constructionEquipment2.setId("2");
        constructionEquipment3.setId("3");
        constructionEquipment4.setId("4");
        constructionEquipment5.setId("5");
        constructionEquipment6.setId("6");
        constructionEquipment7.setId("7");
        constructionEquipment8.setId("8");
        constructionEquipment9.setId("9");
        constructionEquipment10.setId("10");
        constructionEquipment11.setId("11");

        constructionEquipment21.setId("21");
        constructionEquipment22.setId("22");
        constructionEquipment23.setId("23");
        
        constructionEquipment31.setId("31");
        constructionEquipment34.setId("34");
        constructionEquipment35.setId("35");
    }

    public static void createClientRecords() {
        log.debug("createClientRecords [1]:");
        client = new Client("Максим", "89757345627", "mail.ner@mail.ru", "1234567890");
        client2 = new Client("Иван", "89756745627", "maivegh@mail.ru", "8738291053");
        client3 = new Client("Алексей", "89757343427", "cegcyuev@mail.ru", "9843569134");
        client4 = new Client("Борис", "89757393627", "pkpokop@mail.ru", "7463925167");
        client5 = new Client("Дмитрий", "897545645627", "cdcdscdsxx@mail.ru", "6015439853");
        client6 = new Client("Александр", "89757387627", "rwrwt@mail.ru", "90846378291");
        client7 = new Client("Василий", "89758715627", "jrhih@mail.ru", "90876438291");
        client8 = new Client("Евгений", "89757378927", "ruieuiei@mail.ru", "9087456290");
        client9 = new Client("Ростислав", "89751352627", "iewdewdjew@mail.ru", "2317859403");
        client10 = new Client("Никита", "89757345907", "oiejwioji@mail.ru", "7584926402");
        client11 = new Client("Сергей", "89757345937", "opojicjiooi@mail.ru", "95730154617");
        
        client21 = new Client("Сергей", "875469873456", "cbfhdbvjfi@mail.ru", "7658673495");
        client22 = new Client("Виктор", "89067853485", "vaet@mail.ru", "8759036213");
        client23 = new Client("Андрей", "+76548934567", "cjhvcvdv@mail.ru", "67549387234");
        
        client31 = new Client("Андрей", "+79083748322", "bjsbxd@mail.ru", "6037823475");
        
        
        client.setId("1");
        client2.setId("2");
        client3.setId("3");
        client4.setId("4");
        client5.setId("5");
        client6.setId("6");
        client7.setId("7");
        client8.setId("8");
        client9.setId("9");
        client10.setId("10");
        client11.setId("11");

        client21.setId("21");
        client22.setId("22");
        client23.setId("23");
        
        client31.setId("31");
    }

    public static void createApartmentHouseRecords() {
        log.debug("createApartmentHouseRecords [1]:");
        Building building = createBuilding();

        apartmentHouse = new ApartmentHouse(building, 2);
        apartmentHouse2 = new ApartmentHouse(building, 3);
        apartmentHouse3 = new ApartmentHouse(building, 15);
        apartmentHouse4 = new ApartmentHouse(building, 26);
        apartmentHouse5 = new ApartmentHouse(building, 3);
        apartmentHouse6 = new ApartmentHouse(building, 7);
        apartmentHouse7 = new ApartmentHouse(building, 5);
        apartmentHouse8 = new ApartmentHouse(building, 1);
        apartmentHouse9 = new ApartmentHouse(building, 5);
        apartmentHouse10 = new ApartmentHouse(building, 7);
        apartmentHouse11 = new ApartmentHouse(building, 10);
        
        apartmentHouse31 = new ApartmentHouse();
        apartmentHouse31.setNumberOfApartments(2);
        apartmentHouse31.setNumberOfFloors(3);
        
        apartmentHouse33 = new ApartmentHouse();
        apartmentHouse33.setNumberOfApartments(2);
        apartmentHouse33.setNumberOfFloors(3);

        apartmentHouse.setId("1");
        apartmentHouse2.setId("2");
        apartmentHouse3.setId("3");
        apartmentHouse4.setId("4");
        apartmentHouse5.setId("5");
        apartmentHouse6.setId("6");
        apartmentHouse7.setId("7");
        apartmentHouse8.setId("8");
        apartmentHouse9.setId("9");
        apartmentHouse10.setId("10");
        apartmentHouse11.setId("11");
        
        apartmentHouse31.setId("31");
        apartmentHouse33.setId("33");

    }

    public static void createHouseRecords() {
        log.debug("createHouseRecords [1]:");
        Building building = createBuilding();

        house = new House(building, 3);
        house2 = new House(building, 7);
        house3 = new House(building, 2);
        house4 = new House(building, 11);
        house5 = new House(building, 8);
        house6 = new House(building, 4);
        house7 = new House(building, 8);
        house8 = new House(building, 4);
        house9 = new House(building, 6);
        house10 = new House(building, 2);
        house11 = new House(building, 5);

        house.setId("1");
        house2.setId("2");
        house3.setId("3");
        house4.setId("4");
        house5.setId("5");
        house6.setId("6");
        house7.setId("7");
        house8.setId("8");
        house9.setId("9");
        house10.setId("10");
        house11.setId("11");

    }

    public static void createGarageRecords() {
        log.debug("createGarageRecords [1]:");
        Building building = createBuilding();

        garage = new Garage(building, 1);
        garage2 = new Garage(building, 2);
        garage3 = new Garage(building, 3);
        garage4 = new Garage(building, 5);
        garage5 = new Garage(building, 1);
        garage6 = new Garage(building, 3);
        garage7 = new Garage(building, 4);
        garage8 = new Garage(building, 2);
        garage9 = new Garage(building, 7);
        garage10 = new Garage(building, 1);
        garage11 = new Garage(building, 2);
        
        garage31 = new Garage(building, 2);

        garage.setId("1");
        garage2.setId("2");
        garage3.setId("3");
        garage4.setId("4");
        garage5.setId("5");
        garage6.setId("6");
        garage7.setId("7");
        garage8.setId("8");
        garage9.setId("9");
        garage10.setId("10");
        garage11.setId("11");
        
        garage31.setId("31");

    }

    public static Building createBuilding() {
        log.debug("createBuilding [1]:");

        List<ConstructionEquipment> equipments = new ArrayList<>();
        equipments.add(constructionEquipment21);

        List<EngineeringSystem> engineeringSystems = new ArrayList<>();
        engineeringSystems.add(EngineeringSystem.HEATING);
        engineeringSystems.add(EngineeringSystem.ELECTRICITY);
        engineeringSystems.add(EngineeringSystem.SEWERAGE);

        List<Material> materials = new ArrayList<>();
        materials.add(material21);

        List<Worker> workers = new ArrayList<>();
        workers.add(worker21);
        

        Building building = new Building.Builder()
                .setSquare(58)
                .setOwner(client21)
                .setCompletionDate(LocalDate.of(2024, Month.MARCH, 16))
                .setConstructionEquipments(equipments)
                .setEngineeringSystems(engineeringSystems)
                .setMaterials(materials)
                .setNummberOfFloors(2)
                .setWorkers(workers)
                .build();

        return building;
    }

}
