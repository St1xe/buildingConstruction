/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ru.sfedu.buildingconstruction.api;

import java.io.IOException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.model.*;
import static ru.sfedu.buildingconstruction.Constants.INCORRECTID;
import static ru.sfedu.buildingconstruction.Constants.INCORRECTPATH;

/**
 *
 * @author maksim
 */
public class DataProviderCSVTest extends BaseTest {

    private static Logger log = Logger.getLogger(DataProviderCSVTest.class);

    private static DataProviderCSV dataProviderCSV;

    @BeforeAll
    public static void setUpClass() throws IOException {
        log.debug("Before all[0]: starting tests");

        dataProviderCSV = new DataProviderCSV();
        clearAllFIles();
        createAllRecords();
        addNecessaryRecords(Class.class);

    }

    @Test
    public void testAddWorkerPositive() throws Exception {
        log.debug("testAddWorkerPositive[1]");
        dataProviderCSV.addWorker(worker);
        assertEquals(worker, dataProviderCSV.getWorker(worker.getId()).get());
    }

    @Test
    public void testAddWorkerNegative() throws IOException {
        log.debug("testAddWorkerNegative[2]");
        dataProviderCSV.addWorker(worker2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderCSV.addWorker(worker2));
    }

    @Test
    public void testAddConstructionEquipmentPositive() throws IOException {
        log.debug("testAddConstructionEquipmentPositive[3]");
        dataProviderCSV.addConstructionEquipment(constructionEquipment);
        assertEquals(constructionEquipment, dataProviderCSV.getConstructionEquipment(constructionEquipment.getId()).get());
    }

    @Test
    public void testAddConstructionEquipmentNegative() throws IOException {
        log.debug("testAddConstructionEquipmentNegative[3]");
        dataProviderCSV.addConstructionEquipment(constructionEquipment2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderCSV.addConstructionEquipment(constructionEquipment2));
    }

    @Test
    public void testAddMaterialPositive() throws IOException {
        log.debug("testAddMaterialPositive[5]");
        dataProviderCSV.addMaterial(material);
        assertEquals(material, dataProviderCSV.getMaterial(material.getId()).get());
    }

    @Test
    public void testAddMaterialNegative() throws IOException {
        log.debug("testAddMaterialNegative[6]");
        dataProviderCSV.addMaterial(material2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderCSV.addMaterial(material2));
    }

    @Test
    public void testAddClientPositive() throws IOException {
        log.debug("testAddClientPositive[7]");
        dataProviderCSV.addClient(client);
        assertEquals(client, dataProviderCSV.getClient(client.getId()).get());
    }

    @Test
    public void testAddClientNegative() throws IOException {
        log.debug("testAddClientNegative[8]");
        dataProviderCSV.addClient(client2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderCSV.addClient(client2));
    }

    @Test
    public void testAddApartmentHousePositive() throws IOException {
        log.debug("testAddApartmentHousePositive[9]");
        dataProviderCSV.addBuilding(apartmentHouse);
        assertEquals(apartmentHouse, dataProviderCSV.getBuilding(apartmentHouse.getId(), ApartmentHouse.class).get());
    }

    @Test
    public void testAddApartmentHouseNegative() throws IOException {
        log.debug("testAddApartmentHouseNegative[10]");
        dataProviderCSV.addBuilding(apartmentHouse2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderCSV.addBuilding(apartmentHouse2));
    }

    @Test
    public void testAddHousePositive() throws IOException {
        log.debug("testAddHousePositive[11]");
        dataProviderCSV.addBuilding(house);
        assertEquals(house, dataProviderCSV.getBuilding(house.getId(), House.class).get());
    }

    @Test
    public void testAddHouseNegative() throws IOException {
        log.debug("testAddHouseNegative[12]");
        dataProviderCSV.addBuilding(house2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderCSV.addBuilding(house2));
    }

    @Test
    public void testAddGaragePositive() throws IOException {
        log.debug("testAddGaragePositive[13]");
        dataProviderCSV.addBuilding(garage);
        assertEquals(garage, dataProviderCSV.getBuilding(garage.getId(), Garage.class).get());
    }

    @Test
    public void testAddGarageNegative() throws IOException {
        log.debug("testAddGarageNegative[14]");
        dataProviderCSV.addBuilding(garage2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderCSV.addBuilding(garage2));
    }

    @Test
    public void testAddBuildingNegative() throws IOException {
        log.debug("testAddBuildingNegative[15]");
        assertThrows(ClassCastException.class, () -> dataProviderCSV.addBuilding(new Building()));
    }

    @Test
    public void testDeleteWorkerPositive() throws Exception {
        log.debug("testDeleteWorkerPositive[16]");
        dataProviderCSV.addWorker(worker3);
        assertEquals(worker3, dataProviderCSV.getWorker(worker3.getId()).get());
        dataProviderCSV.deleteWorker(worker3.getId());
        assertEquals(Optional.empty(), dataProviderCSV.getWorker(worker3.getId()));
    }

    @Test
    public void testDeleteWorkerNegative() throws Exception {
        log.debug("testDeleteWorkerNeagtive[17]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.deleteWorker(INCORRECTID));
    }

    @Test
    public void testDeleteConstructionEquipmentPositive() throws Exception {
        log.debug("testDeleteConstructionEquipmentPositive[18]");
        dataProviderCSV.addConstructionEquipment(constructionEquipment3);
        assertEquals(constructionEquipment3, dataProviderCSV.getConstructionEquipment(constructionEquipment3.getId()).get());
        dataProviderCSV.deleteConstructionEquipment(constructionEquipment3.getId());
        assertEquals(Optional.empty(), dataProviderCSV.getConstructionEquipment(constructionEquipment3.getId()));
    }

    @Test
    public void testDeleteConstructionEquipmentNegative() throws Exception {
        log.debug("testDeleteConstructionEquipmentNegative[19]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.deleteConstructionEquipment(INCORRECTID));
    }

    @Test
    public void testDeleteMaterialPositive() throws Exception {
        log.debug("testDeleteMaterialPositive[20]");
        dataProviderCSV.addMaterial(material3);
        assertEquals(material3, dataProviderCSV.getMaterial(material3.getId()).get());
        dataProviderCSV.deleteMaterial(material3.getId());
        assertEquals(Optional.empty(), dataProviderCSV.getMaterial(material3.getId()));
    }

    @Test
    public void testDeleteMaterialNegative() throws Exception {
        log.debug("testDeleteMaterialNegative[21]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.deleteMaterial(INCORRECTID));
    }

    @Test
    public void testDeleteClientPositive() throws Exception {
        log.debug("testDeleteClientPositive[22]");
        dataProviderCSV.addClient(client3);
        assertEquals(client3, dataProviderCSV.getClient(client3.getId()).get());
        dataProviderCSV.deleteClient(client3.getId());
        assertEquals(Optional.empty(), dataProviderCSV.getClient(client3.getId()));
    }

    @Test
    public void testDeleteClientNegative() throws Exception {
        log.debug("testDeleteClientNegative[23]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.deleteClient(INCORRECTID));
    }

    @Test
    public void testDeleteApartmentHousePositive() throws Exception {
        log.debug("testDeleteApartmentHousePositive[24]");
        dataProviderCSV.addBuilding(apartmentHouse3);
        assertEquals(apartmentHouse3, dataProviderCSV.getBuilding(apartmentHouse3.getId(), ApartmentHouse.class).get());
        dataProviderCSV.deleteBuilding(apartmentHouse3.getId(), ApartmentHouse.class);
        assertEquals(Optional.empty(), dataProviderCSV.getBuilding(apartmentHouse3.getId(), ApartmentHouse.class));
    }

    @Test
    public void testDeleteApartmentHouseNegative() throws Exception {
        log.debug("testDeleteApartmentHouseNegative[25]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.deleteBuilding(INCORRECTID, ApartmentHouse.class));
    }

    @Test
    public void testDeleteHousePositive() throws Exception {
        log.debug("testDeleteHousePositive[26]");
        dataProviderCSV.addBuilding(house3);
        assertEquals(house3, dataProviderCSV.getBuilding(house3.getId(), House.class).get());
        dataProviderCSV.deleteBuilding(house3.getId(), House.class);
        assertEquals(Optional.empty(), dataProviderCSV.getBuilding(house3.getId(), House.class));
    }

    @Test
    public void testDeleteHouseNegative() throws Exception {
        log.debug("testDeleteHouseNegative[27]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.deleteBuilding(INCORRECTID, House.class));
    }

    @Test
    public void testDeleteGaragePositive() throws Exception {
        log.debug("testDeleteGaragePositive[28]");
        dataProviderCSV.addBuilding(garage3);
        assertEquals(garage3, dataProviderCSV.getBuilding(garage3.getId(), Garage.class).get());
        dataProviderCSV.deleteBuilding(garage3.getId(), Garage.class);
        assertEquals(Optional.empty(), dataProviderCSV.getBuilding(garage3.getId(), Garage.class));
    }

    @Test
    public void testDeleteGarageNegative() throws Exception {
        log.debug("testDeleteGarageNegative[29]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.deleteBuilding(INCORRECTID, Garage.class));
    }

    @Test
    public void testDeleteBuildingNegative() throws IOException {
        log.debug("testDeleteBuildingNegative[30]");
        assertThrows(ClassCastException.class, () -> dataProviderCSV.deleteBuilding(INCORRECTID, Building.class));
    }

    @Test
    public void testGetWorkerPositive() throws Exception {

        log.debug("testGetWorkerPositive[31]");
        dataProviderCSV.addWorker(worker4);
        assertEquals(worker4, dataProviderCSV.getWorker(worker4.getId()).get());

    }

    @Test
    public void testGetWorkerNegative() throws Exception {

        log.debug("testGetWorkerPositive[32]");
        dataProviderCSV.addWorker(worker5);
        assertEquals(Optional.empty(), dataProviderCSV.getWorker(INCORRECTID));

    }

    @Test
    public void testGetConstructionEquiomentPositive() throws Exception {

        log.debug("testGetConstructionEquiomentPositive[33]");
        dataProviderCSV.addConstructionEquipment(constructionEquipment4);
        assertEquals(constructionEquipment4, dataProviderCSV.getConstructionEquipment(constructionEquipment4.getId()).get());

    }

    @Test
    public void testGetConstructionEquipmentNegative() throws Exception {

        log.debug("testGetConstructionEquipmentNegative[34]");
        dataProviderCSV.addConstructionEquipment(constructionEquipment5);
        assertEquals(Optional.empty(), dataProviderCSV.getConstructionEquipment(INCORRECTID));

    }

    @Test
    public void testGetMaterialPositive() throws Exception {

        log.debug("testGetMaterialPositive[35]");
        dataProviderCSV.addMaterial(material4);
        assertEquals(material4, dataProviderCSV.getMaterial(material4.getId()).get());

    }

    @Test
    public void testGetMaterialNegative() throws Exception {

        log.debug("testGetMaterialNegative[36]");
        dataProviderCSV.addMaterial(material5);
        assertEquals(Optional.empty(), dataProviderCSV.getMaterial(INCORRECTID));

    }

    @Test
    public void testGetClientPositive() throws Exception {

        log.debug("testGetClientPositive[37]");
        dataProviderCSV.addClient(client4);
        assertEquals(client4, dataProviderCSV.getClient(client4.getId()).get());

    }

    @Test
    public void testGetClientNegative() throws Exception {

        log.debug("testGetClientNegative[38]");
        dataProviderCSV.addClient(client5);
        assertEquals(Optional.empty(), dataProviderCSV.getClient(INCORRECTID));

    }

    @Test
    public void testGetApartmentHousePositive() throws Exception {

        log.debug("testGetApartmentHousePositive[39]");
        dataProviderCSV.addBuilding(apartmentHouse4);
        assertEquals(apartmentHouse4, dataProviderCSV.getBuilding(apartmentHouse4.getId(), ApartmentHouse.class).get());

    }

    @Test
    public void testGetApartmentHouseNegative() throws Exception {

        log.debug("testGetApartmentHouseNegative[40]");
        dataProviderCSV.addBuilding(apartmentHouse5);
        assertEquals(Optional.empty(), dataProviderCSV.getBuilding(INCORRECTID, ApartmentHouse.class));

    }

    @Test
    public void testGetHousePositive() throws Exception {

        log.debug("testGetHousePositive[41]");
        dataProviderCSV.addBuilding(house4);
        assertEquals(house4, dataProviderCSV.getBuilding(house4.getId(), House.class).get());

    }

    @Test
    public void testGetHouseNegative() throws Exception {

        log.debug("testGetHouseNegative[42]");
        dataProviderCSV.addBuilding(house5);
        assertEquals(Optional.empty(), dataProviderCSV.getBuilding(INCORRECTID, House.class));

    }

    @Test
    public void testGetGaragePositive() throws Exception {

        log.debug("testGetGaragePositive[43]");
        dataProviderCSV.addBuilding(garage4);
        assertEquals(garage4, dataProviderCSV.getBuilding(garage4.getId(), Garage.class).get());

    }

    @Test
    public void testGetGarageNegative() throws Exception {

        log.debug("testGetGarageNegative[44]");
        dataProviderCSV.addBuilding(garage5);
        assertEquals(Optional.empty(), dataProviderCSV.getBuilding(INCORRECTID, Garage.class));

    }

    @Test
    public void testGetBuildingNegative() throws IOException {
        log.debug("testGetBuildingNegative[45]");
        assertThrows(ClassCastException.class, () -> dataProviderCSV.getBuilding(INCORRECTID, Building.class));
    }

    @Test
    public void testGetAllWorkerRecordsPositive() throws Exception {
        log.debug("testGetAllWorkerRecordsPositive[46]");
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE));
        List<Worker> list = new ArrayList<>();
        list.add(worker6);
        list.add(worker7);
        dataProviderCSV.addWorker(worker6);
        dataProviderCSV.addWorker(worker7);
        assertEquals(list,
                dataProviderCSV.getAllRecords(Worker.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE)));
        addNecessaryRecords(Worker.class);
    }

    @Test
    public void testGetAllWorkerRecordsNegative() throws Exception {
        log.debug("testGetAllWorkerRecordsNegative[47]");
        assertThrows(IOException.class, () -> dataProviderCSV.getAllRecords(Worker.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllConstructionEquipmentRecordsPositive() throws Exception {
        log.debug("testGetAllConstructionEquipmentRecordsPositive[48]");
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE));
        List<ConstructionEquipment> list = new ArrayList<>();
        list.add(constructionEquipment6);
        list.add(constructionEquipment7);
        dataProviderCSV.addConstructionEquipment(constructionEquipment6);
        dataProviderCSV.addConstructionEquipment(constructionEquipment7);
        assertEquals(list,
                dataProviderCSV.getAllRecords(ConstructionEquipment.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE)));
        addNecessaryRecords(ConstructionEquipment.class);
    }

    @Test
    public void testGetAllConstructionEquipmentsRecordsNegative() throws Exception {
        log.debug("testGetAllConstructionEquipmentsRecordsNegative[49]");
        assertThrows(IOException.class, () -> dataProviderCSV.getAllRecords(ConstructionEquipment.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllMaterialRecordsPositive() throws Exception {
        log.debug("testGetAllMaterialRecordsPositive[50]");
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE));
        List<Material> list = new ArrayList<>();
        list.add(material6);
        list.add(material7);
        dataProviderCSV.addMaterial(material6);
        dataProviderCSV.addMaterial(material7);
        assertEquals(list,
                dataProviderCSV.getAllRecords(Material.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE)));
        addNecessaryRecords(Material.class);
    }

    @Test
    public void testGetAllMaterialRecordsNegative() throws Exception {
        log.debug("testGetAllMaterialRecordsNegative[51]");
        assertThrows(IOException.class, () -> dataProviderCSV.getAllRecords(Material.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllClientRecordsPositive() throws Exception {
        log.debug("testGetAllClientRecordsPositive[52]");
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE));
        List<Client> list = new ArrayList<>();
        list.add(client6);
        list.add(client7);
        dataProviderCSV.addClient(client6);
        dataProviderCSV.addClient(client7);
        assertEquals(list,
                dataProviderCSV.getAllRecords(Client.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE)));
        addNecessaryRecords(Client.class);
    }

    @Test
    public void testGetAllClientRecordsNegative() throws Exception {
        log.debug("testGetAllClientRecordsNegative[53]");
        assertThrows(IOException.class, () -> dataProviderCSV.getAllRecords(Client.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllApartmentHouseRecordsPositive() throws Exception {
        log.debug("testGetAllApartmentHouseRecordsPositive[54]");
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE));
        List<ApartmentHouse> list = new ArrayList<>();
        list.add(apartmentHouse6);
        list.add(apartmentHouse7);
        dataProviderCSV.addBuilding(apartmentHouse6);
        dataProviderCSV.addBuilding(apartmentHouse7);
        assertEquals(list,
                dataProviderCSV.getAllRecords(ApartmentHouse.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE)));
    }

    @Test
    public void testGetAllApartmentHouseRecordsNegative() throws Exception {
        log.debug("testGetAllApartmentHouseRecordsNegative[55]");
        assertThrows(IOException.class, () -> dataProviderCSV.getAllRecords(ApartmentHouse.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllHouseRecordsPositive() throws Exception {
        log.debug("testGetAllHouseRecordsPositive[56]");
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE));
        List<House> list = new ArrayList<>();
        list.add(house6);
        list.add(house7);
        dataProviderCSV.addBuilding(house6);
        dataProviderCSV.addBuilding(house7);
        assertEquals(list,
                dataProviderCSV.getAllRecords(House.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE)));
    }

    @Test
    public void testGetAllHouseRecordsNegative() throws Exception {
        log.debug("testGetAllHouseRecordsNegative[57]");
        assertThrows(IOException.class, () -> dataProviderCSV.getAllRecords(House.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllGarageRecordsPositive() throws Exception {
        log.debug("testGetAllGarageRecordsPositive[58]");
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE));
        List<Garage> list = new ArrayList<>();
        list.add(garage6);
        list.add(garage7);
        dataProviderCSV.addBuilding(garage6);
        dataProviderCSV.addBuilding(garage7);
        assertEquals(list,
                dataProviderCSV.getAllRecords(Garage.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE)));
    }

    @Test
    public void testGetAllGarageRecordsNegative() throws Exception {
        log.debug("testGetAllGarageRecordsNegative[59]");
        assertThrows(IOException.class, () -> dataProviderCSV.getAllRecords(Garage.class, INCORRECTPATH));

    }

    @Test
    public void testUpdateWorkerPositive() throws Exception {
        log.debug("testUpdateWorkerPositive[60]");
        dataProviderCSV.addWorker(worker8);
        dataProviderCSV.updateWorker(worker8.getId(), worker9);
        assertEquals(worker9, dataProviderCSV.getWorker(worker9.getId()).get());
    }

    @Test
    public void testUpdateWorkerNegative() throws Exception {
        log.debug("testUpdateWorkerNegative[61]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.updateWorker(INCORRECTID, worker));
    }

    @Test
    public void testUpdateWorkerToAnExistingRecord() throws Exception {
        log.debug("testUpdateWorkerToAnExistingRecord[62]");
        dataProviderCSV.addWorker(worker10);
        dataProviderCSV.addWorker(worker11);
        assertThrows(IllegalArgumentException.class, () -> dataProviderCSV.updateWorker(worker10.getId(), worker11));
    }

    @Test
    public void testUpdateConstructionEquipmentPositive() throws Exception {
        log.debug("testUpdateConstructionEquipmentPositive[63]");
        dataProviderCSV.addConstructionEquipment(constructionEquipment8);
        dataProviderCSV.updateConstructionEquipment(constructionEquipment8.getId(), constructionEquipment9);
        assertEquals(constructionEquipment9, dataProviderCSV.getConstructionEquipment(constructionEquipment9.getId()).get());
    }

    @Test
    public void testUpdateConstructionEquipmentNegative() throws Exception {
        log.debug("testUpdateConstructionEquipmentNegative[64]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.updateConstructionEquipment(INCORRECTID, constructionEquipment));
    }

    @Test
    public void testUpdateConstructionEquipmentToAnExistingRecord() throws Exception {
        log.debug("testUpdateConstructionEquipmentToAnExistingRecord[65]");
        dataProviderCSV.addConstructionEquipment(constructionEquipment10);
        dataProviderCSV.addConstructionEquipment(constructionEquipment11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataProviderCSV.updateConstructionEquipment(constructionEquipment10.getId(), constructionEquipment11));
    }

    @Test
    public void testUpdateMaterialPositive() throws Exception {
        log.debug("testUpdateMaterialPositive[66]");
        dataProviderCSV.addMaterial(material8);
        dataProviderCSV.updateMaterial(material8.getId(), material9);
        assertEquals(material9, dataProviderCSV.getMaterial(material9.getId()).get());
    }

    @Test
    public void testUpdateMaterialNegative() throws Exception {
        log.debug("testUpdateMaterialNegative[67]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.updateMaterial(INCORRECTID, material));
    }

    @Test
    public void testUpdateMaterialToAnExistingRecord() throws Exception {
        log.debug("testUpdateMaterialToAnExistingRecord[68]");
        dataProviderCSV.addMaterial(material10);
        dataProviderCSV.addMaterial(material11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataProviderCSV.updateMaterial(material10.getId(), material11));
    }

    @Test
    public void testUpdateClientPositive() throws Exception {
        log.debug("testUpdateClientPositive[69]");
        dataProviderCSV.addClient(client8);
        dataProviderCSV.updateClient(client8.getId(), client9);
        assertEquals(client9, dataProviderCSV.getClient(client9.getId()).get());
    }

    @Test
    public void testUpdateClientNegative() throws Exception {
        log.debug("testUpdateClientNegative[70]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.updateClient(INCORRECTID, client));
    }

    @Test
    public void testUpdateClientToAnExistingRecord() throws Exception {
        log.debug("testUpdateClientToAnExistingRecord[71]");
        dataProviderCSV.addClient(client10);
        dataProviderCSV.addClient(client11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataProviderCSV.updateClient(client10.getId(), client11));
    }

    @Test
    public void testUpdateApartmentHousePositive() throws Exception {
        log.debug("testUpdateApartmentHousePositive[72]");
        dataProviderCSV.addBuilding(apartmentHouse8);
        dataProviderCSV.updateBuilding(apartmentHouse8.getId(), apartmentHouse9);
        assertEquals(apartmentHouse9, dataProviderCSV.getBuilding(apartmentHouse9.getId(), ApartmentHouse.class).get());
    }

    @Test
    public void testUpdateApartmentHouseNegative() throws Exception {
        log.debug("testUpdateApartmentHouseNegative[73]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.updateBuilding(INCORRECTID, apartmentHouse));
    }

    @Test
    public void testUpdateApartmentHouseToAnExistingRecord() throws Exception {
        log.debug("testUpdateApartmentHouseToAnExistingRecord[74]");
        dataProviderCSV.addBuilding(apartmentHouse10);
        dataProviderCSV.addBuilding(apartmentHouse11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataProviderCSV.updateBuilding(apartmentHouse10.getId(), apartmentHouse11));
    }

    @Test
    public void testUpdateHousePositive() throws Exception {
        log.debug("testUpdateHousePositive[75]");
        dataProviderCSV.addBuilding(house8);
        dataProviderCSV.updateBuilding(house8.getId(), house9);
        assertEquals(house9, dataProviderCSV.getBuilding(house9.getId(), House.class).get());
    }

    @Test
    public void testUpdateHouseNegative() throws Exception {
        log.debug("testUpdateHouseNegative[76]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.updateBuilding(INCORRECTID, house));
    }

    @Test
    public void testUpdateHouseToAnExistingRecord() throws Exception {
        log.debug("testUpdateHouseToAnExistingRecord[77]");
        dataProviderCSV.addBuilding(house10);
        dataProviderCSV.addBuilding(house11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataProviderCSV.updateBuilding(house10.getId(), house11));
    }

    @Test
    public void testUpdateGaragePositive() throws Exception {
        log.debug("testUpdateGaragePositive[78]");
        dataProviderCSV.addBuilding(garage8);
        dataProviderCSV.updateBuilding(garage8.getId(), garage9);
        assertEquals(garage9, dataProviderCSV.getBuilding(garage9.getId(), Garage.class).get());
    }

    @Test
    public void testUpdateGarageNegative() throws Exception {
        log.debug("testUpdateGarageNegative[79]");
        assertThrows(NoSuchElementException.class, () -> dataProviderCSV.updateBuilding(INCORRECTID, garage));
    }

    @Test
    public void testUpdateGarageToAnExistingRecord() throws Exception {
        log.debug("testUpdateGarageToAnExistingRecord[80]");
        dataProviderCSV.addBuilding(garage10);
        dataProviderCSV.addBuilding(garage11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataProviderCSV.updateBuilding(garage10.getId(), garage11));
    }

    @Test
    public void testUpdateBuildingNegative() throws Exception {
        log.debug("testUpdateBuildingNegative[81]");
        assertThrows(ClassCastException.class, () -> dataProviderCSV.updateBuilding(INCORRECTID, new Building()));
    }

    @Test
    public void preparationOfConstructionPlanPositive() throws Exception {
        log.debug("preparationOfConstructionPlanPositive[82]");

        dataProviderCSV.addClient(client31);
        dataProviderCSV.addMaterial(material31);

        List<Material> materials = new ArrayList<>();
        materials.add(material31);

        List<EngineeringSystem> systems = new ArrayList<>();
        systems.add(EngineeringSystem.HEATING);

        dataProviderCSV.preparationOfConstructionPlan(apartmentHouse31, client31, materials, systems);

        ApartmentHouse ah = apartmentHouse31;
        ah.setMaterials(materials);
        ah.setOwner(client31);
        ah.setEngineeringSystems(systems);

        assertEquals(ah, dataProviderCSV.getBuilding("31", ApartmentHouse.class).get());

    }

    @Test
    public void preparationOfConstructionPlanNegative() throws Exception {
        log.debug("preparationOfConstructionPlanNegative[83]");

        assertThrows(ClassCastException.class,
                () -> dataProviderCSV.preparationOfConstructionPlan(new Building(), client, null, null));

    }

    @Test
    public void selectionOfMaterialsPositive() throws Exception {
        log.debug("selectionOfMaterialsPositive[84]");

        dataProviderCSV.addMaterial(material32);
        dataProviderCSV.addMaterial(material33);

        List<Material> list = new ArrayList<>();
        list.add(material32);
        list.add(material33);

        assertEquals(list, dataProviderCSV.selectionOfMaterials("32 33"));
    }

    @Test
    public void selectionOfMaterialsNegative() throws Exception {
        log.debug("selectionOfMaterialsNegative[85]");

        assertThrows(NoSuchElementException.class,
                () -> dataProviderCSV.selectionOfMaterials(INCORRECTID));
    }

    @Test
    public void selectionOfEngineeringSystemsPositive() throws Exception {
        log.debug("selectionOfEngineeringSystemsPositive[86]");

        List<EngineeringSystem> list = new ArrayList<>();
        list.add(EngineeringSystem.HEATING);
        list.add(EngineeringSystem.SEWERAGE);

        assertEquals(list, dataProviderCSV.selectionOfEngineeringSystems("HEATING SEWERAGE"));
    }

    @Test
    public void selectionOfEngineeringSystemsNegative() throws Exception {
        log.debug("selectionOfEngineeringSystemsNegative[87]");

        assertThrows(IllegalArgumentException.class,
                () -> dataProviderCSV.selectionOfEngineeringSystems(INCORRECTID));
    }

    @Test
    public void preparationForBuildingPositive() throws Exception {
        log.debug("preparationForBuildingPositive[88]");

        Worker w = new Worker("xsaxxxs", "893746273821", "xsaxsa", 32767);
        Client c = new Client("name", "+78372839102", "sss@mail.ru", "7362782349");
        ConstructionEquipment ce = new ConstructionEquipment("axsa", 3333);

        dataProviderCSV.addWorker(w);
        dataProviderCSV.addClient(c);
        dataProviderCSV.addConstructionEquipment(ce);
        apartmentHouse33.setOwner(c);
        dataProviderCSV.addBuilding(apartmentHouse33);

        dataProviderCSV.preparationForBuilding(apartmentHouse33);

        List<Worker> workers = List.of(w);
        List<ConstructionEquipment> equipments = List.of(ce);

        ApartmentHouse ah = apartmentHouse33;
        ah.setWorkers(workers);
        ah.setConstructionEquipments(equipments);
        ah.setCompletionDate(LocalDate.now().plusMonths(Constants.TIME_IN_MONTH_FOR_BUILD_AN_APARTMENT_HOUSE));
        ah.setOwner(c);

        assertEquals(ah, dataProviderCSV.getBuilding("33", ApartmentHouse.class).get());

    }

    @Test
    public void preparationForBuildingNegative() throws Exception {
        log.debug("preparationForBuildingNegative[89]");
        assertThrows(ClassCastException.class, () -> dataProviderCSV.preparationForBuilding(new Building()));

    }

    @Test
    public void distributionOfWorkersPositive() throws Exception {

        log.debug("distributionOfWorkersPositive[90]");
        
        dataProviderCSV.addWorker(new Worker());
        dataProviderCSV.addWorker(new Worker());
        dataProviderCSV.addWorker(new Worker());
        dataProviderCSV.addWorker(new Worker());
        dataProviderCSV.addWorker(new Worker());

        assertEquals(4, dataProviderCSV.distributionOfWorkers(new Garage(), Constants.PATH_TO_WORKER_CSV_FILE).size());
    }

    @Test
    public void distributionOfWorkersNegative() throws Exception {
        log.debug("distributionOfWorkersNegative[91]");
        assertThrows(IOException.class, () -> dataProviderCSV.distributionOfWorkers(new Garage(), INCORRECTPATH));
    }

    @Test
    public void distributionOfConstructionEquipmentPositive() throws Exception {
        log.debug("distributionOfConstructionEquipmentPositive[92]");
        

        dataProviderCSV.addConstructionEquipment(new ConstructionEquipment());
        dataProviderCSV.addConstructionEquipment(new ConstructionEquipment());
        dataProviderCSV.addConstructionEquipment(new ConstructionEquipment());
        dataProviderCSV.addConstructionEquipment(new ConstructionEquipment());
        dataProviderCSV.addConstructionEquipment(new ConstructionEquipment());
        dataProviderCSV.addConstructionEquipment(new ConstructionEquipment());

        assertEquals(5, dataProviderCSV.distributionOfConstructionEquipment(new Garage(), Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE).size());
    }

    @Test
    public void distributionOfConstructionEquipmentNegative() throws Exception {
        log.debug("distributionOfConstructionEquipmentNegative[93]");
        assertThrows(IOException.class, () -> dataProviderCSV.distributionOfConstructionEquipment(new Garage(), INCORRECTPATH));
    }

    @Test
    public void coordinationOfConstructionTermsPositive() throws Exception {
        log.debug("coordinationOfConstructionTermsPositive[94]");
        assertEquals(LocalDate.now().plusMonths(6), dataProviderCSV.coordinationOfConstructionTerms(house));
    }

    @Test
    public void coordinationOfConstructionTermsNegative() throws Exception {
        log.debug("coordinationOfConstructionTermsNegative[95]");
        assertThrows(ClassCastException.class, () -> dataProviderCSV.coordinationOfConstructionTerms(new Building()));
    }

    @Test
    public void calculationOfTheTotalCostPositive() throws Exception {
        log.debug("calculationOfTheTotalCostPositive[96]");

        dataProviderCSV.addMaterial(material34);
        dataProviderCSV.addConstructionEquipment(constructionEquipment34);
        dataProviderCSV.addWorker(worker34);

        List<Material> materials = List.of(material34);
        List<ConstructionEquipment> equipments = List.of(constructionEquipment34);
        List<Worker> workers = List.of(worker34);

        House house = new House();
        house.setMaterials(materials);
        house.setConstructionEquipments(equipments);
        house.setWorkers(workers);
        house.setNumberOfFloors(2);
        house.setSquare(56);

        assertEquals(506276.496, dataProviderCSV.calculationOfTheTotalCost(house));

    }

    @Test
    public void calculationOfTheTotalCostNegative() throws Exception {
        log.debug("calculationOfTheTotalCostNegative[97]");
        assertThrows(ClassCastException.class, () -> dataProviderCSV.calculationOfTheTotalCost(new Building()));

    }

    @Test
    public void calculationCostOfMaterialsPositive() throws Exception {
        log.debug("calculationCostOfMaterialsPositive[98]");

        dataProviderCSV.addMaterial(material35);

        List<Material> list = List.of(material35);

        House house = new House();
        house.setMaterials(list);

        assertEquals(11100, dataProviderCSV.calculationCostOfMaterials(house));

    }

    @Test
    public void calculationCostOfMaterialsNegative() throws Exception {
        log.debug("calculationCostOfMaterialsNegative[99]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataProviderCSV.calculationCostOfMaterials(house));

    }

    @Test
    public void calculationCostOfConstructionEquipmentPositive() throws Exception {
        log.debug("calculationCostOfConstructionEquipmentPositive[100]");

        dataProviderCSV.addConstructionEquipment(constructionEquipment35);

        List<ConstructionEquipment> list = List.of(constructionEquipment35);

        House house = new House();
        house.setConstructionEquipments(list);

        assertEquals(10000, dataProviderCSV.calculationCostOfConstructionEquipment(house, 1));

    }

    @Test
    public void calculationCostOfConstructionEquipmentNegative() throws Exception {
        log.debug("calculationCostOfConstructionEquipmentNegative[101]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataProviderCSV.calculationCostOfConstructionEquipment(house, 1));

    }

    @Test
    public void calculationCostOfJobPositive() throws Exception {
        log.debug("calculationCostOfJobPositive[102]");

        dataProviderCSV.addWorker(worker35);

        List<Worker> list = List.of(worker35);

        House house = new House();
        house.setWorkers(list);

        assertEquals(10000, dataProviderCSV.calculationCostOfJob(house, 1));
    }

    @Test
    public void calculationCostOfJobNegative() throws Exception {
        log.debug("calculationCostOfJobNegative[103]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataProviderCSV.calculationCostOfJob(house, 1));

    }

    private static void clearAllFIles() throws IOException {
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_CSV_FILE));
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_CSV_FILE));
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_CSV_FILE));
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_CSV_FILE));
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_CSV_FILE));
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_CSV_FILE));
        dataProviderCSV.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_CSV_FILE));
    }

    private static void addNecessaryRecords(Class clazz) throws IOException {
        log.debug("addNecessaryRecords [1]: class = " + clazz);

        switch (clazz.getSimpleName()) {

            case "Worker" ->
                dataProviderCSV.addWorker(worker21);
            case "ConstructionEquipment" ->
                dataProviderCSV.addConstructionEquipment(constructionEquipment21);
            case "Material" ->
                dataProviderCSV.addMaterial(material21);
            case "Client" ->
                dataProviderCSV.addClient(client21);
            default -> {
                dataProviderCSV.addWorker(worker21);
                dataProviderCSV.addConstructionEquipment(constructionEquipment21);
                dataProviderCSV.addMaterial(material21);
                dataProviderCSV.addClient(client21);
            }

        }

    }
}
