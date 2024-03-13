package ru.sfedu.buildingconstruction;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.api.DataBaseProvider;
import ru.sfedu.buildingconstruction.api.DataProvider;
import ru.sfedu.buildingconstruction.api.DataProviderCSV;
import ru.sfedu.buildingconstruction.api.DataProviderXML;

import ru.sfedu.buildingconstruction.model.ApartmentHouse;
import ru.sfedu.buildingconstruction.model.Building;
import ru.sfedu.buildingconstruction.model.Client;
import ru.sfedu.buildingconstruction.model.ConstructionEquipment;
import ru.sfedu.buildingconstruction.model.EngineeringSystem;
import ru.sfedu.buildingconstruction.model.Garage;
import ru.sfedu.buildingconstruction.model.House;
import ru.sfedu.buildingconstruction.model.Material;
import ru.sfedu.buildingconstruction.model.Worker;

import ru.sfedu.buildingconstruction.util.EntityConfugurationUtil;

/**
 *
 * @author maksim
 */
public class BuildingConstructionClient {

    private static Logger log = Logger.getLogger(BuildingConstructionClient.class);

    public BuildingConstructionClient() {
        log.debug("BuildingConstructionClient[0]: starting application.....");
    }

    public static void main(String[] args) throws Exception {

        DataProvider provider;

        if (args.length == 0) {
            log.debug("main [1]: данные не были получены");
            return;
        }

        String[] arguments = null;

        switch (args[0]) {

            case "CSV" -> {
                provider = new DataProviderCSV();
                arguments = Arrays.copyOfRange(args, 1, args.length);
                log.info("main [2]: Выбран CSV дата провайдер");
            }
            case "XML" -> {
                provider = new DataProviderXML();
                arguments = Arrays.copyOfRange(args, 1, args.length);
                ((DataProviderXML) provider).createAllFiles();
                log.info("main [3]: Выбран XML дата провайдер");
            }
            case "DB" -> {
                provider = new DataBaseProvider();
                arguments = Arrays.copyOfRange(args, 1, args.length);
                ((DataBaseProvider) provider).createAllTables();
                log.info("main [4]: Выбран DB дата провайдер");

            }
            default -> {
                provider = new DataProviderCSV();
                ((DataProviderCSV) provider).createAllFiles();
                log.info("main [5]: Дата провайдер выбран по умолчанию");
                arguments = args;
            }
        }

        log.info("main [6]: Дата провайдер установлен  " + provider.getClass().getSimpleName());

        switch (arguments[0]) {
            case "-owner" -> {

                if (arguments.length < 6) {
                    log.error("main [7]: Данные введены некорректно");
                    return;
                }
                Client client = new Client();
                client.setId(arguments[1]);
                client.setName(arguments[2]);

                if (EntityConfugurationUtil.phoneNumberIsCorrect(arguments[3])) {
                    client.setPhoneNumber(arguments[3]);
                } else {
                    return;
                }

                if (EntityConfugurationUtil.emailIsCorrect(arguments[4])) {
                    client.setEmail(arguments[4]);
                } else {
                    return;
                }

                if (EntityConfugurationUtil.passportIsCorrect(arguments[5])) {
                    client.setPassport(arguments[5]);
                } else {
                    return;
                }

                try {
                    provider.addClient(client);
                    log.info("main [8]: Запись " + client + " добавлена");
                } catch (IllegalArgumentException ex) {
                    log.error("main [9]: Запись " + client + " уже создана");
                }
            }

            case "-material" -> {
                if (arguments.length < 5) {
                    log.error("main [10]: Данные введены некорректно");
                    return;
                }
                Material material = new Material();
                material.setId(arguments[1]);
                material.setName(arguments[2]);

                if (EntityConfugurationUtil.numberIsPositive(Integer.parseInt(arguments[3]))) {
                    material.setPrice(Integer.parseInt(arguments[3]));
                } else {
                    return;
                }
                if (EntityConfugurationUtil.numberIsPositive(Double.parseDouble(arguments[4]))) {
                    material.setQuantityInStock(Double.parseDouble(arguments[4]));
                } else {
                    return;
                }

                try {
                    provider.addMaterial(material);
                    log.info("main [11]: Запись " + material + " добавлена");
                } catch (IllegalArgumentException ex) {
                    log.error("main [12]: Запись " + material + " уже создана");
                }

            }

            case "-worker" -> {
                if (arguments.length < 6) {
                    log.error("main [13]: Данные введены некорректно");
                    return;
                }
                Worker worker = new Worker();
                worker.setId(arguments[1]);
                worker.setName(arguments[2]);
                worker.setJobTitle(arguments[3]);

                if (EntityConfugurationUtil.phoneNumberIsCorrect((arguments[4]))) {
                    worker.setPhoneNumber(arguments[4]);
                } else {
                    return;
                }
                if (EntityConfugurationUtil.numberIsPositive(Double.parseDouble(arguments[5]))) {
                    worker.setSalary(Double.parseDouble(arguments[5]));
                } else {
                    return;
                }

                try {
                    provider.addWorker(worker);
                    log.info("main [14]: Запись " + worker + " добавлена");
                } catch (IllegalArgumentException ex) {
                    log.error("main [15]: Запись " + worker + " уже создана");
                }
            }
            case "-equipment" -> {
                if (arguments.length < 4) {
                    log.error("main [16]: Данные введены некорректно");
                    return;
                }
                ConstructionEquipment equipment = new ConstructionEquipment();
                equipment.setId(arguments[1]);
                equipment.setName(arguments[2]);

                if (EntityConfugurationUtil.numberIsPositive((Double.parseDouble(arguments[3])))) {
                    equipment.setPrice(Double.parseDouble(arguments[3]));
                } else {
                    return;
                }

                try {
                    provider.addConstructionEquipment(equipment);
                    log.info("main [17]: Запись " + equipment + " добавлена");
                } catch (IllegalArgumentException ex) {
                    log.error("main [18]: Запись " + equipment + " уже создана");
                }
            }

            case "-plan" -> {
                if (arguments.length < 7) {
                    log.error("main [19]: Данные введены некорректно");
                    return;
                }

                Building building;

                switch (arguments[1]) {

                    case "ApartmentHouse" -> {
                        building = new ApartmentHouse();
                        ((ApartmentHouse) building).setNumberOfApartments(Integer.parseInt(arguments[4]));
                    }
                    case "House" -> {
                        building = new House();
                        ((House) building).setNumberOfRooms(Integer.parseInt(arguments[4]));
                    }
                    case "Garage" -> {
                        building = new Garage();
                        ((Garage) building).setNumberOfCars(Integer.parseInt(arguments[4]));
                    }
                    default -> {
                        log.error("main [20]: Данные введены некорректно");
                        return;
                    }
                }

                log.debug("main [21]: " + building.getClass());

                building.setSquare(Double.parseDouble(arguments[2]));
                building.setNumberOfFloors(Integer.parseInt(arguments[3]));

                if (provider.getClient(arguments[5]).isEmpty()) {
                    log.error("main [21]:Клиент не найден");
                    return;
                }
                Client client = (Client) provider.getClient(arguments[5]).get();

                List<Material> list = provider.selectionOfMaterials(arguments[6]);

                List<EngineeringSystem> systems = null;

                if (arguments.length > 7) {
                    systems = provider.selectionOfEngineeringSystems(arguments[7]);
                }

                try {
                    provider.preparationOfConstructionPlan(building, client, list, systems);
                } catch (Exception ex) {
                    log.error("main [22]:Невозможно добавить новую постройку");
                    return;
                }

            }

            case "-build" -> {
                if (arguments.length < 3) {
                    log.error("main [23]:Данные введены некорректно");
                    return;
                }

                Building building;

                switch (arguments[2].toLowerCase()) {

                    case "apartmenthouse" -> {

                        if (provider.getBuilding(arguments[1], ApartmentHouse.class).isEmpty()) {
                            log.error("main [24]:Здание с id = " + arguments[1] + " не найдено");
                            System.exit(0);
                        }

                        building = provider.getBuilding(arguments[1], ApartmentHouse.class).get();

                    }
                    case "house" -> {
                        if (provider.getBuilding(arguments[1], House.class).isEmpty()) {
                            log.error("main [25]:Здание с id = " + arguments[1] + " не найдено");
                            System.exit(0);
                        }

                        building = provider.getBuilding(arguments[1], House.class).get();
                    }
                    case "garage" -> {
                        if (provider.getBuilding(arguments[1], Garage.class).isEmpty()) {
                            log.error("main [26]:Здание с id = " + arguments[1] + " не найдено");
                            System.exit(0);
                        }

                        building = provider.getBuilding(arguments[1], Garage.class).get();
                    }
                    default -> {
                        log.error("main [27]:Данные введены некорректно");
                        return;
                    }

                }
                provider.preparationForBuilding(building);

            }

            case "-cost" -> {

                if (arguments.length < 3) {
                    log.error("main [28]:Данные введены некорректно");
                    return;
                }

                Building building;

                switch (arguments[2]) {

                    case "ApartmentHouse" -> {

                        if (provider.getBuilding(arguments[1], ApartmentHouse.class).isEmpty()) {
                            log.error("main [29]:Здание с id = " + arguments[1] + " не найдено");
                            System.exit(0);
                        }

                        building = provider.getBuilding(arguments[1], ApartmentHouse.class).get();

                    }
                    case "House" -> {
                        if (provider.getBuilding(arguments[1], House.class).isEmpty()) {
                            log.error("main [30]:Здание с id = " + arguments[1] + " не найдено");
                            System.exit(0);
                        }

                        building = provider.getBuilding(arguments[1], House.class).get();
                    }
                    case "Garage" -> {
                        if (provider.getBuilding(arguments[1], Garage.class).isEmpty()) {
                            log.error("main [31]:Здание с id = " + arguments[1] + " не найдено");
                            System.exit(0);
                        }

                        building = provider.getBuilding(arguments[1], Garage.class).get();
                    }
                    default -> {
                        log.error("main [32]:Данные введены некорректно");
                        return;
                    }

                }

                provider.calculationOfTheTotalCost(building);

            }
            default -> {
                log.error("main [33]:Команда не найдена");
                return;
            }
        }

    }
}
