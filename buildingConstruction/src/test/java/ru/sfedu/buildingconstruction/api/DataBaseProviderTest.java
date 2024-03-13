package ru.sfedu.buildingconstruction.api;

import java.io.IOException;

import java.time.LocalDate;
import java.time.Month;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.log4j.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.sfedu.buildingconstruction.Constants;

import static ru.sfedu.buildingconstruction.Constants.INCORRECTID;
import static ru.sfedu.buildingconstruction.Constants.INCORRECTPATH;

import static ru.sfedu.buildingconstruction.api.BaseTest.*;

import ru.sfedu.buildingconstruction.model.*;

/**
 *
 * @author maksim
 */
public class DataBaseProviderTest {

    private static Logger log = Logger.getLogger(DataBaseProviderTest.class);

    private static DataBaseProvider dataBaseProvider;

    @BeforeAll
    public static void setUpClass() throws IOException {
        log.debug("Before all[0]: starting tests");

        dataBaseProvider = new DataBaseProvider();
        clearAllTables();
        dataBaseProvider.createAllTables();
        createAllRecords();
        addNecessaryRecords(Class.class);

    }

    @Test
    public void testAddWorkerPositive() throws Exception {
        log.debug("testAddWorkerPositive[1]");
        dataBaseProvider.addWorker(worker);
        assertEquals(worker, dataBaseProvider.getWorker(worker.getId()).get());
    }

    @Test
    public void testAddWorkerNegative() throws IOException {
        log.debug("testAddWorkerNegative[2]");
        dataBaseProvider.addWorker(worker2);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.addWorker(worker2));
    }

    @Test
    public void testAddConstructionEquipmentPositive() throws IOException {
        log.debug("testAddConstructionEquipmentPositive[3]");
        dataBaseProvider.addConstructionEquipment(constructionEquipment);
        assertEquals(constructionEquipment, dataBaseProvider.getConstructionEquipment(constructionEquipment.getId()).get());
    }

    @Test
    public void testAddConstructionEquipmentNegative() throws IOException {
        log.debug("testAddConstructionEquipmentNegative[3]");
        dataBaseProvider.addConstructionEquipment(constructionEquipment2);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.addConstructionEquipment(constructionEquipment2));
    }

    @Test
    public void testAddMaterialPositive() throws IOException {
        log.debug("testAddMaterialPositive[5]");
        dataBaseProvider.addMaterial(material);
        assertEquals(material, dataBaseProvider.getMaterial(material.getId()).get());
    }

    @Test
    public void testAddMaterialNegative() throws IOException {
        log.debug("testAddMaterialNegative[6]");
        dataBaseProvider.addMaterial(material2);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.addMaterial(material2));
    }

    @Test
    public void testAddClientPositive() throws IOException {
        log.debug("testAddClientPositive[7]");
        dataBaseProvider.addClient(client);
        assertEquals(client, dataBaseProvider.getClient(client.getId()).get());
    }

    @Test
    public void testAddClientNegative() throws IOException {
        log.debug("testAddClientNegative[8]");
        dataBaseProvider.addClient(client2);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.addClient(client2));
    }

    @Test
    public void testAddApartmentHousePositive() throws IOException {
        log.debug("testAddApartmentHousePositive[9]");
        dataBaseProvider.addBuilding(apartmentHouse);
        assertEquals(apartmentHouse, dataBaseProvider.getBuilding(apartmentHouse.getId(), ApartmentHouse.class).get());
    }

    @Test
    public void testAddApartmentHouseNegative() throws IOException {
        log.debug("testAddApartmentHouseNegative[10]");
        dataBaseProvider.addBuilding(apartmentHouse2);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.addBuilding(apartmentHouse2));
    }

    @Test
    public void testAddHousePositive() throws IOException {
        log.debug("testAddHousePositive[11]");
        dataBaseProvider.addBuilding(house);
        assertEquals(house, dataBaseProvider.getBuilding(house.getId(), House.class).get());
    }

    @Test
    public void testAddHouseNegative() throws IOException {
        log.debug("testAddHouseNegative[12]");
        dataBaseProvider.addBuilding(house2);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.addBuilding(house2));
    }

    @Test
    public void testAddGaragePositive() throws IOException {
        log.debug("testAddGaragePositive[13]");
        dataBaseProvider.addBuilding(garage);
        assertEquals(garage, dataBaseProvider.getBuilding(garage.getId(), Garage.class).get());
    }

    @Test
    public void testAddGarageNegative() throws IOException {
        log.debug("testAddGarageNegative[14]");
        dataBaseProvider.addBuilding(garage2);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.addBuilding(garage2));
    }

    @Test
    public void testAddBuildingNegative() throws IOException {
        log.debug("testAddBuildingNegative[15]");
        assertThrows(ClassCastException.class, () -> dataBaseProvider.addBuilding(new Building()));
    }

    @Test
    public void testDeleteWorkerPositive() throws Exception {
        log.debug("testDeleteWorkerPositive[16]");
        dataBaseProvider.addWorker(worker3);
        assertEquals(worker3, dataBaseProvider.getWorker(worker3.getId()).get());
        dataBaseProvider.deleteWorker(worker3.getId());
        assertEquals(Optional.empty(), dataBaseProvider.getWorker(worker3.getId()));
    }

    @Test
    public void testDeleteWorkerNegative() throws Exception {
        log.debug("testDeleteWorkerNeagtive[17]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.deleteWorker(INCORRECTID));
    }

    @Test
    public void testDeleteConstructionEquipmentPositive() throws Exception {
        log.debug("testDeleteConstructionEquipmentPositive[18]");
        dataBaseProvider.addConstructionEquipment(constructionEquipment3);
        assertEquals(constructionEquipment3, dataBaseProvider.getConstructionEquipment(constructionEquipment3.getId()).get());
        dataBaseProvider.deleteConstructionEquipment(constructionEquipment3.getId());
        assertEquals(Optional.empty(), dataBaseProvider.getConstructionEquipment(constructionEquipment3.getId()));
    }

    @Test
    public void testDeleteConstructionEquipmentNegative() throws Exception {
        log.debug("testDeleteConstructionEquipmentNegative[19]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.deleteConstructionEquipment(INCORRECTID));
    }

    @Test
    public void testDeleteMaterialPositive() throws Exception {
        log.debug("testDeleteMaterialPositive[20]");
        dataBaseProvider.addMaterial(material3);
        assertEquals(material3, dataBaseProvider.getMaterial(material3.getId()).get());
        dataBaseProvider.deleteMaterial(material3.getId());
        assertEquals(Optional.empty(), dataBaseProvider.getMaterial(material3.getId()));
    }

    @Test
    public void testDeleteMaterialNegative() throws Exception {
        log.debug("testDeleteMaterialNegative[21]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.deleteMaterial(INCORRECTID));
    }

    @Test
    public void testDeleteClientPositive() throws Exception {
        log.debug("testDeleteClientPositive[22]");
        dataBaseProvider.addClient(client3);
        assertEquals(client3, dataBaseProvider.getClient(client3.getId()).get());
        dataBaseProvider.deleteClient(client3.getId());
        assertEquals(Optional.empty(), dataBaseProvider.getClient(client3.getId()));
    }

    @Test
    public void testDeleteClientNegative() throws Exception {
        log.debug("testDeleteClientNegative[23]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.deleteClient(INCORRECTID));
    }

    @Test
    public void testDeleteApartmentHousePositive() throws Exception {
        log.debug("testDeleteApartmentHousePositive[24]");
        dataBaseProvider.addBuilding(apartmentHouse3);
        assertEquals(apartmentHouse3, dataBaseProvider.getBuilding(apartmentHouse3.getId(), ApartmentHouse.class).get());
        dataBaseProvider.deleteBuilding(apartmentHouse3.getId(), ApartmentHouse.class);
        assertEquals(Optional.empty(), dataBaseProvider.getBuilding(apartmentHouse3.getId(), ApartmentHouse.class));
    }

    @Test
    public void testDeleteApartmentHouseNegative() throws Exception {
        log.debug("testDeleteApartmentHouseNegative[25]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.deleteBuilding(INCORRECTID, ApartmentHouse.class));
    }

    @Test
    public void testDeleteHousePositive() throws Exception {
        log.debug("testDeleteHousePositive[26]");
        dataBaseProvider.addBuilding(house3);
        assertEquals(house3, dataBaseProvider.getBuilding(house3.getId(), House.class).get());
        dataBaseProvider.deleteBuilding(house3.getId(), House.class);
        assertEquals(Optional.empty(), dataBaseProvider.getBuilding(house3.getId(), House.class));
    }

    @Test
    public void testDeleteHouseNegative() throws Exception {
        log.debug("testDeleteHouseNegative[27]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.deleteBuilding(INCORRECTID, House.class));
    }

    @Test
    public void testDeleteGaragePositive() throws Exception {
        log.debug("testDeleteGaragePositive[28]");
        dataBaseProvider.addBuilding(garage3);
        assertEquals(garage3, dataBaseProvider.getBuilding(garage3.getId(), Garage.class).get());
        dataBaseProvider.deleteBuilding(garage3.getId(), Garage.class);
        assertEquals(Optional.empty(), dataBaseProvider.getBuilding(garage3.getId(), Garage.class));
    }

    @Test
    public void testDeleteGarageNegative() throws Exception {
        log.debug("testDeleteGarageNegative[29]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.deleteBuilding(INCORRECTID, Garage.class));
    }

    @Test
    public void testDeleteBuildingNegative() throws IOException {
        log.debug("testDeleteBuildingNegative[30]");
        assertThrows(ClassCastException.class, () -> dataBaseProvider.deleteBuilding(INCORRECTID, Building.class));
    }

    @Test
    public void testGetWorkerPositive() throws Exception {

        log.debug("testGetWorkerPositive[31]");
        dataBaseProvider.addWorker(worker4);
        assertEquals(worker4, dataBaseProvider.getWorker(worker4.getId()).get());

    }

    @Test
    public void testGetWorkerNegative() throws Exception {

        log.debug("testGetWorkerPositive[32]");
        dataBaseProvider.addWorker(worker5);
        assertEquals(Optional.empty(), dataBaseProvider.getWorker(INCORRECTID));

    }

    @Test
    public void testGetConstructionEquiomentPositive() throws Exception {

        log.debug("testGetConstructionEquiomentPositive[33]");
        dataBaseProvider.addConstructionEquipment(constructionEquipment4);
        assertEquals(constructionEquipment4, dataBaseProvider.getConstructionEquipment(constructionEquipment4.getId()).get());

    }

    @Test
    public void testGetConstructionEquipmentNegative() throws Exception {

        log.debug("testGetConstructionEquipmentNegative[34]");
        dataBaseProvider.addConstructionEquipment(constructionEquipment5);
        assertEquals(Optional.empty(), dataBaseProvider.getConstructionEquipment(INCORRECTID));

    }

    @Test
    public void testGetMaterialPositive() throws Exception {

        log.debug("testGetMaterialPositive[35]");
        dataBaseProvider.addMaterial(material4);
        assertEquals(material4, dataBaseProvider.getMaterial(material4.getId()).get());

    }

    @Test
    public void testGetMaterialNegative() throws Exception {

        log.debug("testGetMaterialNegative[36]");
        dataBaseProvider.addMaterial(material5);
        assertEquals(Optional.empty(), dataBaseProvider.getMaterial(INCORRECTID));

    }

    @Test
    public void testGetClientPositive() throws Exception {

        log.debug("testGetClientPositive[37]");
        dataBaseProvider.addClient(client4);
        assertEquals(client4, dataBaseProvider.getClient(client4.getId()).get());

    }

    @Test
    public void testGetClientNegative() throws Exception {

        log.debug("testGetClientNegative[38]");
        dataBaseProvider.addClient(client5);
        assertEquals(Optional.empty(), dataBaseProvider.getClient(INCORRECTID));

    }

    @Test
    public void testGetApartmentHousePositive() throws Exception {

        log.debug("testGetApartmentHousePositive[39]");
        dataBaseProvider.addBuilding(apartmentHouse4);
        assertEquals(apartmentHouse4, dataBaseProvider.getBuilding(apartmentHouse4.getId(), ApartmentHouse.class).get());

    }

    @Test
    public void testGetApartmentHouseNegative() throws Exception {

        log.debug("testGetApartmentHouseNegative[40]");
        dataBaseProvider.addBuilding(apartmentHouse5);
        assertEquals(Optional.empty(), dataBaseProvider.getBuilding(INCORRECTID, ApartmentHouse.class));

    }

    @Test
    public void testGetHousePositive() throws Exception {

        log.debug("testGetHousePositive[41]");
        dataBaseProvider.addBuilding(house4);
        assertEquals(house4, dataBaseProvider.getBuilding(house4.getId(), House.class).get());

    }

    @Test
    public void testGetHouseNegative() throws Exception {

        log.debug("testGetHouseNegative[42]");
        dataBaseProvider.addBuilding(house5);
        assertEquals(Optional.empty(), dataBaseProvider.getBuilding(INCORRECTID, House.class));

    }

    @Test
    public void testGetGaragePositive() throws Exception {

        log.debug("testGetGaragePositive[43]");
        dataBaseProvider.addBuilding(garage4);
        assertEquals(garage4, dataBaseProvider.getBuilding(garage4.getId(), Garage.class).get());

    }

    @Test
    public void testGetGarageNegative() throws Exception {

        log.debug("testGetGarageNegative[44]");
        dataBaseProvider.addBuilding(garage5);
        assertEquals(Optional.empty(), dataBaseProvider.getBuilding(INCORRECTID, Garage.class));

    }

    @Test
    public void testGetBuildingNegative() throws IOException {
        log.debug("testGetBuildingNegative[45]");
        assertThrows(ClassCastException.class, () -> dataBaseProvider.getBuilding(INCORRECTID, Building.class));
    }

    @Test
    public void testGetAllWorkerRecordsPositive() throws Exception {
        log.debug("testGetAllWorkerRecordsPositive[46]");
        dataBaseProvider.deleteTable("worker");
        dataBaseProvider.createWorkerTable();
        List<Worker> list = new ArrayList<>();
        list.add(worker6);
        list.add(worker7);
        dataBaseProvider.addWorker(worker6);
        dataBaseProvider.addWorker(worker7);
        assertEquals(list,
                dataBaseProvider.getAllRecords(Worker.class, Constants.WORKER_TABLE));
        addNecessaryRecords(Worker.class);
    }

    @Test
    public void testGetAllWorkerRecordsNegative() throws Exception {
        log.debug("testGetAllWorkerRecordsNegative[47]");
        assertThrows(IOException.class, () -> dataBaseProvider.getAllRecords(Worker.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllConstructionEquipmentRecordsPositive() throws Exception {
        log.debug("testGetAllConstructionEquipmentRecordsPositive[48]");
        dataBaseProvider.deleteTable("constructionEquipment");
        dataBaseProvider.createConstructioEquipmentTable();
        List<ConstructionEquipment> list = new ArrayList<>();
        list.add(constructionEquipment6);
        list.add(constructionEquipment7);
        dataBaseProvider.addConstructionEquipment(constructionEquipment6);
        dataBaseProvider.addConstructionEquipment(constructionEquipment7);
        assertEquals(list,
                dataBaseProvider.getAllRecords(ConstructionEquipment.class,
                        Constants.CONSTRUCTION_EQUIPMENT_TABLE));
        addNecessaryRecords(ConstructionEquipment.class);
    }

    @Test
    public void testGetAllConstructionEquipmentsRecordsNegative() throws Exception {
        log.debug("testGetAllConstructionEquipmentsRecordsNegative[49]");
        assertThrows(IOException.class, () -> dataBaseProvider.getAllRecords(ConstructionEquipment.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllMaterialRecordsPositive() throws Exception {
        log.debug("testGetAllMaterialRecordsPositive[50]");
        dataBaseProvider.deleteTable("material");
        dataBaseProvider.createMaterialTable();
        List<Material> list = new ArrayList<>();
        list.add(material6);
        list.add(material7);
        dataBaseProvider.addMaterial(material6);
        dataBaseProvider.addMaterial(material7);
        assertEquals(list,
                dataBaseProvider.getAllRecords(Material.class,
                        Constants.MATERIAL_TABLE));
        addNecessaryRecords(Material.class);
    }

    @Test
    public void testGetAllMaterialRecordsNegative() throws Exception {
        log.debug("testGetAllMaterialRecordsNegative[51]");
        assertThrows(IOException.class, () -> dataBaseProvider.getAllRecords(Material.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllClientRecordsPositive() throws Exception {
        log.debug("testGetAllClientRecordsPositive[52]");
        dataBaseProvider.deleteTable("client");
        dataBaseProvider.createClientTable();
        List<Client> list = new ArrayList<>();
        list.add(client6);
        list.add(client7);
        dataBaseProvider.addClient(client6);
        dataBaseProvider.addClient(client7);
        assertEquals(list,
                dataBaseProvider.getAllRecords(Client.class,
                        Constants.CLIENT_TABLE));
        addNecessaryRecords(Client.class);
    }

    @Test
    public void testGetAllClientRecordsNegative() throws Exception {
        log.debug("testGetAllClientRecordsNegative[53]");
        assertThrows(IOException.class, () -> dataBaseProvider.getAllRecords(Client.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllApartmentHouseRecordsPositive() throws Exception {
        log.debug("testGetAllApartmentHouseRecordsPositive[54]");
        dataBaseProvider.deleteTable("apartmentHouse");
        dataBaseProvider.createApartmentHouseTable();
        List<ApartmentHouse> list = new ArrayList<>();
        list.add(apartmentHouse6);
        list.add(apartmentHouse7);
        dataBaseProvider.addBuilding(apartmentHouse6);
        dataBaseProvider.addBuilding(apartmentHouse7);
        assertEquals(list,
                dataBaseProvider.getAllRecords(ApartmentHouse.class,
                        Constants.APARTMENT_HOUSE_TABLE));
    }

    @Test
    public void testGetAllApartmentHouseRecordsNegative() throws Exception {
        log.debug("testGetAllApartmentHouseRecordsNegative[55]");
        assertThrows(IOException.class, () -> dataBaseProvider.getAllRecords(ApartmentHouse.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllHouseRecordsPositive() throws Exception {
        log.debug("testGetAllHouseRecordsPositive[56]");
        dataBaseProvider.deleteTable("house");
        dataBaseProvider.createHouseTable();
        List<House> list = new ArrayList<>();
        list.add(house6);
        list.add(house7);
        dataBaseProvider.addBuilding(house6);
        dataBaseProvider.addBuilding(house7);
        assertEquals(list,
                dataBaseProvider.getAllRecords(House.class,
                        Constants.HOUSE_TABLE));
    }

    @Test
    public void testGetAllHouseRecordsNegative() throws Exception {
        log.debug("testGetAllHouseRecordsNegative[57]");
        assertThrows(IOException.class, () -> dataBaseProvider.getAllRecords(House.class, INCORRECTPATH));

    }

    @Test
    public void testGetAllGarageRecordsPositive() throws Exception {
        log.debug("testGetAllGarageRecordsPositive[58]");
        dataBaseProvider.deleteTable("garage");
        dataBaseProvider.createGarageTable();
        List<Garage> list = new ArrayList<>();
        list.add(garage6);
        list.add(garage7);
        dataBaseProvider.addBuilding(garage6);
        dataBaseProvider.addBuilding(garage7);
        assertEquals(list,
                dataBaseProvider.getAllRecords(Garage.class,
                        Constants.GARAGE_TABLE));
    }

    @Test
    public void testGetAllGarageRecordsNegative() throws Exception {
        log.debug("testGetAllGarageRecordsNegative[59]");
        assertThrows(IOException.class, () -> dataBaseProvider.getAllRecords(Garage.class, INCORRECTPATH));

    }

    @Test
    public void testUpdateWorkerPositive() throws Exception {
        log.debug("testUpdateWorkerPositive[60]");
        dataBaseProvider.addWorker(worker8);
        dataBaseProvider.updateWorker(worker8.getId(), worker9);
        assertEquals(worker9, dataBaseProvider.getWorker(worker9.getId()).get());
    }

    @Test
    public void testUpdateWorkerNegative() throws Exception {
        log.debug("testUpdateWorkerNegative[61]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.updateWorker(INCORRECTID, worker));
    }

    @Test
    public void testUpdateWorkerToAnExistingRecord() throws Exception {
        log.debug("testUpdateWorkerToAnExistingRecord[62]");
        dataBaseProvider.addWorker(worker10);
        dataBaseProvider.addWorker(worker11);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.updateWorker(worker10.getId(), worker11));
    }

    @Test
    public void testUpdateConstructionEquipmentPositive() throws Exception {
        log.debug("testUpdateConstructionEquipmentPositive[63]");
        dataBaseProvider.addConstructionEquipment(constructionEquipment8);
        dataBaseProvider.updateConstructionEquipment(constructionEquipment8.getId(), constructionEquipment9);
        assertEquals(constructionEquipment9, dataBaseProvider.getConstructionEquipment(constructionEquipment9.getId()).get());
    }

    @Test
    public void testUpdateConstructionEquipmentNegative() throws Exception {
        log.debug("testUpdateConstructionEquipmentNegative[64]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.updateConstructionEquipment(INCORRECTID, constructionEquipment));
    }

    @Test
    public void testUpdateConstructionEquipmentToAnExistingRecord() throws Exception {
        log.debug("testUpdateConstructionEquipmentToAnExistingRecord[65]");
        dataBaseProvider.addConstructionEquipment(constructionEquipment10);
        dataBaseProvider.addConstructionEquipment(constructionEquipment11);
        assertThrows(IllegalArgumentException.class, () -> dataBaseProvider.updateConstructionEquipment(constructionEquipment10.getId(), constructionEquipment11));
    }

    @Test
    public void testUpdateMaterialPositive() throws Exception {
        log.debug("testUpdateMaterialPositive[66]");
        dataBaseProvider.addMaterial(material8);
        dataBaseProvider.updateMaterial(material8.getId(), material9);
        assertEquals(material9, dataBaseProvider.getMaterial(material9.getId()).get());
    }

    @Test
    public void testUpdateMaterialNegative() throws Exception {
        log.debug("testUpdateMaterialNegative[67]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.updateMaterial(INCORRECTID, material));
    }

    @Test
    public void testUpdateMaterialToAnExistingRecord() throws Exception {
        log.debug("testUpdateMaterialToAnExistingRecord[68]");
        dataBaseProvider.addMaterial(material10);
        dataBaseProvider.addMaterial(material11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataBaseProvider.updateMaterial(material10.getId(), material11));
    }

    @Test
    public void testUpdateClientPositive() throws Exception {
        log.debug("testUpdateClientPositive[69]");
        dataBaseProvider.addClient(client8);
        dataBaseProvider.updateClient(client8.getId(), client9);
        assertEquals(client9, dataBaseProvider.getClient(client9.getId()).get());
    }

    @Test
    public void testUpdateClientNegative() throws Exception {
        log.debug("testUpdateClientNegative[70]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.updateClient(INCORRECTID, client));
    }

    @Test
    public void testUpdateClientToAnExistingRecord() throws Exception {
        log.debug("testUpdateClientToAnExistingRecord[71]");
        dataBaseProvider.addClient(client10);
        dataBaseProvider.addClient(client11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataBaseProvider.updateClient(client10.getId(), client11));
    }

    @Test
    public void testUpdateApartmentHousePositive() throws Exception {
        log.debug("testUpdateApartmentHousePositive[72]");
        dataBaseProvider.addBuilding(apartmentHouse8);
        dataBaseProvider.updateBuilding(apartmentHouse8.getId(), apartmentHouse9);
        assertEquals(apartmentHouse9, dataBaseProvider.getBuilding(apartmentHouse9.getId(), ApartmentHouse.class).get());
    }

    @Test
    public void testUpdateApartmentHouseNegative() throws Exception {
        log.debug("testUpdateApartmentHouseNegative[73]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.updateBuilding(INCORRECTID, apartmentHouse));
    }

    @Test
    public void testUpdateApartmentHouseToAnExistingRecord() throws Exception {
        log.debug("testUpdateApartmentHouseToAnExistingRecord[74]");
        dataBaseProvider.addBuilding(apartmentHouse10);
        dataBaseProvider.addBuilding(apartmentHouse11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataBaseProvider.updateBuilding(apartmentHouse10.getId(), apartmentHouse11));
    }

    @Test
    public void testUpdateHousePositive() throws Exception {
        log.debug("testUpdateHousePositive[75]");
        dataBaseProvider.addBuilding(house8);
        dataBaseProvider.updateBuilding(house8.getId(), house9);
        assertEquals(house9, dataBaseProvider.getBuilding(house9.getId(), House.class).get());
    }

    @Test
    public void testUpdateHouseNegative() throws Exception {
        log.debug("testUpdateHouseNegative[76]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.updateBuilding(INCORRECTID, house));
    }

    @Test
    public void testUpdateHouseToAnExistingRecord() throws Exception {
        log.debug("testUpdateHouseToAnExistingRecord[77]");
        dataBaseProvider.addBuilding(house10);
        dataBaseProvider.addBuilding(house11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataBaseProvider.updateBuilding(house10.getId(), house11));
    }

    @Test
    public void testUpdateGaragePositive() throws Exception {
        log.debug("testUpdateGaragePositive[78]");
        dataBaseProvider.addBuilding(garage8);
        dataBaseProvider.updateBuilding(garage8.getId(), garage9);
        assertEquals(garage9, dataBaseProvider.getBuilding(garage9.getId(), Garage.class).get());
    }

    @Test
    public void testUpdateGarageNegative() throws Exception {
        log.debug("testUpdateGarageNegative[79]");
        assertThrows(NoSuchElementException.class, () -> dataBaseProvider.updateBuilding(INCORRECTID, garage));
    }

    @Test
    public void testUpdateGarageToAnExistingRecord() throws Exception {
        log.debug("testUpdateGarageToAnExistingRecord[80]");
        dataBaseProvider.addBuilding(garage10);
        dataBaseProvider.addBuilding(garage11);
        assertThrows(IllegalArgumentException.class, ()
                -> dataBaseProvider.updateBuilding(garage10.getId(), garage11));
    }

    @Test
    public void testUpdateBuildingNegative() throws Exception {
        log.debug("testUpdateBuildingNegative[81]");
        assertThrows(ClassCastException.class, () -> dataBaseProvider.updateBuilding(INCORRECTID, new Building()));
    }


    @Test
    public void preparationOfConstructionPlanPositive() throws Exception {
        log.debug("preparationOfConstructionPlanPositive[82]");

        dataBaseProvider.addClient(client31);
        dataBaseProvider.addMaterial(material31);

        List<Material> materials = new ArrayList<>();
        materials.add(material31);

        List<EngineeringSystem> systems = new ArrayList<>();
        systems.add(EngineeringSystem.HEATING);

        dataBaseProvider.preparationOfConstructionPlan(apartmentHouse31, client31, materials, systems);

        ApartmentHouse ah = apartmentHouse31;
        ah.setMaterials(materials);
        ah.setOwner(client31);
        ah.setEngineeringSystems(systems);
        ah.setCompletionDate(LocalDate.MIN);

        assertEquals(ah, dataBaseProvider.getBuilding("31", ApartmentHouse.class).get());

    }

    @Test
    public void preparationOfConstructionPlanNegative() throws Exception {
        log.debug("preparationOfConstructionPlanNegative[83]");

        assertThrows(ClassCastException.class,
                () -> dataBaseProvider.preparationOfConstructionPlan(new Building(), client, null, null));

    }

    @Test
    public void selectionOfMaterialsPositive() throws Exception {
        log.debug("selectionOfMaterialsPositive[84]");

        dataBaseProvider.addMaterial(material32);
        dataBaseProvider.addMaterial(material33);

        List<Material> list = new ArrayList<>();
        list.add(material32);
        list.add(material33);

        assertEquals(list, dataBaseProvider.selectionOfMaterials("32 33"));
    }

    @Test
    public void selectionOfMaterialsNegative() throws Exception {
        log.debug("selectionOfMaterialsNegative[85]");

        assertThrows(NoSuchElementException.class,
                () -> dataBaseProvider.selectionOfMaterials(INCORRECTID));
    }

    @Test
    public void selectionOfEngineeringSystemsPositive() throws Exception {
        log.debug("selectionOfEngineeringSystemsPositive[86]");

        List<EngineeringSystem> list = new ArrayList<>();
        list.add(EngineeringSystem.HEATING);
        list.add(EngineeringSystem.SEWERAGE);

        assertEquals(list, dataBaseProvider.selectionOfEngineeringSystems("HEATING SEWERAGE"));
    }

    @Test
    public void selectionOfEngineeringSystemsNegative() throws Exception {
        log.debug("selectionOfEngineeringSystemsNegative[87]");

        assertThrows(IllegalArgumentException.class,
                () -> dataBaseProvider.selectionOfEngineeringSystems(INCORRECTID));
    }

    @Test
    public void preparationForBuildingPositive() throws Exception {
        log.debug("preparationForBuildingPositive[88]");

        Worker w = new Worker("xsaxxxs", "893746273821", "xsaxsa", 32767);
        Client c = new Client("name", "+78372839102", "sss@mail.ru", "7362782349");
        ConstructionEquipment ce = new ConstructionEquipment("axsa", 3333);

        dataBaseProvider.addWorker(w);
        dataBaseProvider.addClient(c);
        dataBaseProvider.addConstructionEquipment(ce);
        apartmentHouse33.setOwner(c);
        apartmentHouse33.setCompletionDate(LocalDate.of(0, Month.JANUARY, 1));

        dataBaseProvider.addBuilding(apartmentHouse33);

        dataBaseProvider.preparationForBuilding(apartmentHouse33);

        List<Worker> workers = List.of(w);
        List<ConstructionEquipment> equipments = List.of(ce);

        ApartmentHouse ah = apartmentHouse33;
        ah.setWorkers(workers);
        ah.setConstructionEquipments(equipments);
        ah.setCompletionDate(LocalDate.now().plusMonths(Constants.TIME_IN_MONTH_FOR_BUILD_AN_APARTMENT_HOUSE));
        ah.setOwner(c);

        assertEquals(ah, dataBaseProvider.getBuilding("33", ApartmentHouse.class).get());

    }

    @Test
    public void preparationForBuildingNegative() throws Exception {
        log.debug("preparationForBuildingNegative[89]");
        assertThrows(ClassCastException.class, () -> dataBaseProvider.preparationForBuilding(new Building()));

    }

    @Test
    public void distributionOfWorkersPositive() throws Exception {

        log.debug("distributionOfWorkersPositive[90]");

        dataBaseProvider.addWorker(new Worker("cdsbc", "894839283911", "xsx", 333232));
        dataBaseProvider.addWorker(new Worker("isisi", "89384728391", "xsxaasaq", 83838));
        dataBaseProvider.addWorker(new Worker("ooow", "89382903922", "ppqpq", 26090));
        dataBaseProvider.addWorker(new Worker("pwnc", "89384194371", "soppox", 83922));
        dataBaseProvider.addWorker(new Worker("oeiwoidj", "84928391031", "xsaxx21", 21000));

        assertEquals(4, dataBaseProvider.distributionOfWorkers(new Garage(), Constants.WORKER_TABLE).size());
    }

    @Test
    public void distributionOfWorkersNegative() throws Exception {
        log.debug("distributionOfWorkersNegative[91]");
        assertThrows(IOException.class, () -> dataBaseProvider.distributionOfWorkers(new Garage(), INCORRECTPATH));
    }

    @Test
    public void distributionOfConstructionEquipmentPositive() throws Exception {
        log.debug("distributionOfConstructionEquipmentPositive[92]");

        dataBaseProvider.addConstructionEquipment(new ConstructionEquipment("xsa", 21));
        dataBaseProvider.addConstructionEquipment(new ConstructionEquipment("spapxosa", 31));
        dataBaseProvider.addConstructionEquipment(new ConstructionEquipment("xwoo", 31));
        dataBaseProvider.addConstructionEquipment(new ConstructionEquipment("xkjwoijd", 3131));
        dataBaseProvider.addConstructionEquipment(new ConstructionEquipment("ppwpxxxsq", 23));
        dataBaseProvider.addConstructionEquipment(new ConstructionEquipment("cndshciuds", 21));

        assertEquals(5, dataBaseProvider.distributionOfConstructionEquipment(new Garage(), Constants.CONSTRUCTION_EQUIPMENT_TABLE).size());
    }

    @Test
    public void distributionOfConstructionEquipmentNegative() throws Exception {
        log.debug("distributionOfConstructionEquipmentNegative[93]");
        assertThrows(IOException.class, () -> dataBaseProvider.distributionOfConstructionEquipment(new Garage(), INCORRECTPATH));
    }

    @Test
    public void coordinationOfConstructionTermsPositive() throws Exception {
        log.debug("coordinationOfConstructionTermsPositive[94]");
        assertEquals(LocalDate.now().plusMonths(6), dataBaseProvider.coordinationOfConstructionTerms(house));
    }

    @Test
    public void coordinationOfConstructionTermsNegative() throws Exception {
        log.debug("coordinationOfConstructionTermsNegative[95]");
        assertThrows(ClassCastException.class, () -> dataBaseProvider.coordinationOfConstructionTerms(new Building()));
    }

    @Test
    public void calculationOfTheTotalCostPositive() throws Exception {
        log.debug("calculationOfTheTotalCostPositive[96]");

        dataBaseProvider.addMaterial(material34);
        dataBaseProvider.addConstructionEquipment(constructionEquipment34);
        dataBaseProvider.addWorker(worker34);

        List<Material> materials = List.of(material34);
        List<ConstructionEquipment> equipments = List.of(constructionEquipment34);
        List<Worker> workers = List.of(worker34);

        House house = new House();
        house.setMaterials(materials);
        house.setConstructionEquipments(equipments);
        house.setWorkers(workers);
        house.setNumberOfFloors(2);
        house.setSquare(56);

        assertEquals(506276.496, dataBaseProvider.calculationOfTheTotalCost(house));

    }

    @Test
    public void calculationOfTheTotalCostNegative() throws Exception {
        log.debug("calculationOfTheTotalCostNegative[97]");
        assertThrows(ClassCastException.class, () -> dataBaseProvider.calculationOfTheTotalCost(new Building()));

    }

    @Test
    public void calculationCostOfMaterialsPositive() throws Exception {
        log.debug("calculationCostOfMaterialsPositive[98]");

        dataBaseProvider.addMaterial(material35);

        List<Material> list = List.of(material35);

        House house = new House();
        house.setMaterials(list);

        assertEquals(11100, dataBaseProvider.calculationCostOfMaterials(house));

    }

    @Test
    public void calculationCostOfMaterialsNegative() throws Exception {
        log.debug("calculationCostOfMaterialsNegative[99]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataBaseProvider.calculationCostOfMaterials(house));

    }

    @Test
    public void calculationCostOfConstructionEquipmentPositive() throws Exception {
        log.debug("calculationCostOfConstructionEquipmentPositive[100]");

        dataBaseProvider.addConstructionEquipment(constructionEquipment35);

        List<ConstructionEquipment> list = List.of(constructionEquipment35);

        House house = new House();
        house.setConstructionEquipments(list);

        assertEquals(10000, dataBaseProvider.calculationCostOfConstructionEquipment(house, 1));

    }

    @Test
    public void calculationCostOfConstructionEquipmentNegative() throws Exception {
        log.debug("calculationCostOfConstructionEquipmentNegative[101]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataBaseProvider.calculationCostOfConstructionEquipment(house, 1));

    }

    @Test
    public void calculationCostOfJobPositive() throws Exception {
        log.debug("calculationCostOfJobPositive[102]");

        dataBaseProvider.addWorker(worker35);

        List<Worker> list = List.of(worker35);

        House house = new House();
        house.setWorkers(list);

        assertEquals(10000, dataBaseProvider.calculationCostOfJob(house, 1));
    }

    @Test
    public void calculationCostOfJobNegative() throws Exception {
        log.debug("calculationCostOfJobNegative[103]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataBaseProvider.calculationCostOfJob(house, 1));

    }

    private static void clearAllTables() throws IOException {

        dataBaseProvider.deleteTable("worker");
        dataBaseProvider.deleteTable("constructionEquipment");
        dataBaseProvider.deleteTable("material");
        dataBaseProvider.deleteTable("client");
        dataBaseProvider.deleteTable("apartmentHouse");
        dataBaseProvider.deleteTable("house");
        dataBaseProvider.deleteTable("garage");

    }

    private static void addNecessaryRecords(Class clazz) throws IOException {
        log.debug("addNecessaryRecords [1]: class = " + clazz);

        switch (clazz.getSimpleName()) {

            case "Worker" ->
                dataBaseProvider.addWorker(worker21);
            case "ConstructionEquipment" ->
                dataBaseProvider.addConstructionEquipment(constructionEquipment21);
            case "Material" ->
                dataBaseProvider.addMaterial(material21);
            case "Client" ->
                dataBaseProvider.addClient(client21);
            default -> {
                dataBaseProvider.addWorker(worker21);
                dataBaseProvider.addConstructionEquipment(constructionEquipment21);
                dataBaseProvider.addMaterial(material21);
                dataBaseProvider.addClient(client21);
            }

        }

    }

}
