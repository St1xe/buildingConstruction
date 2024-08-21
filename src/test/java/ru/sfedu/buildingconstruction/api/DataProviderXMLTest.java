package ru.sfedu.buildingconstruction.api;

import java.io.IOException;
import java.time.LocalDate;

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

import ru.sfedu.buildingconstruction.model.*;

import static ru.sfedu.buildingconstruction.api.BaseTest.apartmentHouse31;
import static ru.sfedu.buildingconstruction.api.BaseTest.apartmentHouse33;
import static ru.sfedu.buildingconstruction.api.BaseTest.client;
import static ru.sfedu.buildingconstruction.api.BaseTest.client31;
import static ru.sfedu.buildingconstruction.api.BaseTest.constructionEquipment34;
import static ru.sfedu.buildingconstruction.api.BaseTest.constructionEquipment35;
import static ru.sfedu.buildingconstruction.api.BaseTest.material31;
import static ru.sfedu.buildingconstruction.api.BaseTest.material32;
import static ru.sfedu.buildingconstruction.api.BaseTest.material33;
import static ru.sfedu.buildingconstruction.api.BaseTest.material34;
import static ru.sfedu.buildingconstruction.api.BaseTest.material35;
import static ru.sfedu.buildingconstruction.api.BaseTest.worker34;
import static ru.sfedu.buildingconstruction.api.BaseTest.worker35;


public class DataProviderXMLTest extends BaseTest{
    
    
    
    private static Logger log = Logger.getLogger(DataProviderXMLTest.class);

    private static DataProviderXML dataProviderXML;

    

    @BeforeAll
    public static void setUpClass() throws IOException {
        log.debug("Before all[0]: starting tests");

        dataProviderXML = new DataProviderXML();
        clearAllFIles();
        createAllRecords();
        addNecessaryRecords(Class.class);

    }

    
    @Test
    public void testAddWorkerPositive() throws Exception {
        log.debug("testAddWorkerPositive[1]");
        dataProviderXML.addWorker(worker);
        assertEquals(worker, dataProviderXML.getWorker(worker.getId()).get());
    }

    @Test
    public void testAddWorkerNegative() throws IOException {
        log.debug("testAddWorkerNegative[2]");
        dataProviderXML.addWorker(worker2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderXML.addWorker(worker2));
    }
    
    @Test
    public void testAddConstructionEquipmentPositive() throws IOException {
        log.debug("testAddConstructionEquipmentPositive[3]");
        dataProviderXML.addConstructionEquipment(constructionEquipment);
        assertEquals(constructionEquipment, dataProviderXML.getConstructionEquipment(constructionEquipment.getId()).get());
    }

    @Test
    public void testAddConstructionEquipmentNegative() throws IOException {
        log.debug("testAddConstructionEquipmentNegative[3]");
        dataProviderXML.addConstructionEquipment(constructionEquipment2);
        assertThrows(IllegalArgumentException.class, () -> dataProviderXML.addConstructionEquipment(constructionEquipment2));
    }
    
    @Test
    public void testAddMaterialPositive() throws IOException {
        log.debug("testAddMaterialPositive[5]");
        dataProviderXML.addMaterial(material);
        assertEquals(material, dataProviderXML.getMaterial(material.getId()).get());
    }

    @Test
    public void testAddMaterialNegative() throws IOException {
        log.debug("testAddMaterialNegative[6]");
        dataProviderXML.addMaterial(material2);
        assertThrows(IllegalArgumentException.class, () ->  dataProviderXML.addMaterial(material2));
    }
    
    @Test
    public void testAddClientPositive() throws IOException {
        log.debug("testAddClientPositive[7]");
        dataProviderXML.addClient(client);
        assertEquals(client, dataProviderXML.getClient(client.getId()).get());
    }

    @Test
    public void testAddClientNegative() throws IOException {
        log.debug("testAddClientNegative[8]");
        dataProviderXML.addClient(client2);
        assertThrows(IllegalArgumentException.class, () ->  dataProviderXML.addClient(client2));
    }
    
    
    @Test
    public void testAddApartmentHousePositive() throws IOException {
        log.debug("testAddApartmentHousePositive[9]");
        dataProviderXML.addBuilding(apartmentHouse);
        assertEquals(apartmentHouse, dataProviderXML.getBuilding(apartmentHouse.getId(), ApartmentHouse.class).get());
    }

    @Test
    public void testAddApartmentHouseNegative() throws IOException {
        log.debug("testAddApartmentHouseNegative[10]");
        dataProviderXML.addBuilding(apartmentHouse2);
        assertThrows(IllegalArgumentException.class, () ->  dataProviderXML.addBuilding(apartmentHouse2));
    }
    
    @Test
    public void testAddHousePositive() throws IOException {
        log.debug("testAddHousePositive[11]");
        dataProviderXML.addBuilding(house);
        assertEquals(house, dataProviderXML.getBuilding(house.getId(), House.class).get());
    }

    @Test
    public void testAddHouseNegative() throws IOException {
        log.debug("testAddHouseNegative[12]");
        dataProviderXML.addBuilding(house2);
        assertThrows(IllegalArgumentException.class, () ->  dataProviderXML.addBuilding(house2));
    }
    
    @Test
    public void testAddGaragePositive() throws IOException {
        log.debug("testAddGaragePositive[13]");
        dataProviderXML.addBuilding(garage);
        assertEquals(garage, dataProviderXML.getBuilding(garage.getId(), Garage.class).get());
    }

    @Test
    public void testAddGarageNegative() throws IOException {
        log.debug("testAddGarageNegative[14]");
        dataProviderXML.addBuilding(garage2);
        assertThrows(IllegalArgumentException.class, () ->  dataProviderXML.addBuilding(garage2));
    }

    @Test
    public void testAddBuildingNegative() throws IOException {
        log.debug("testAddBuildingNegative[15]");
        assertThrows(ClassCastException.class, () ->  dataProviderXML.addBuilding(new Building()));
    }
    
    
    
    @Test
    public void testDeleteWorkerPositive() throws Exception {
        log.debug("testDeleteWorkerPositive[16]");
        dataProviderXML.addWorker(worker3);
        assertEquals(worker3, dataProviderXML.getWorker(worker3.getId()).get());
        dataProviderXML.deleteWorker(worker3.getId());
        assertEquals(Optional.empty(), dataProviderXML.getWorker(worker3.getId()));
    }

    @Test
    public void testDeleteWorkerNegative() throws Exception {
        log.debug("testDeleteWorkerNeagtive[17]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.deleteWorker(INCORRECTID));
    }
    
    
    @Test
    public void testDeleteConstructionEquipmentPositive() throws Exception {
        log.debug("testDeleteConstructionEquipmentPositive[18]");
        dataProviderXML.addConstructionEquipment(constructionEquipment3);
        assertEquals(constructionEquipment3, dataProviderXML.getConstructionEquipment(constructionEquipment3.getId()).get());
        dataProviderXML.deleteConstructionEquipment(constructionEquipment3.getId());
        assertEquals(Optional.empty(), dataProviderXML.getConstructionEquipment(constructionEquipment3.getId()));
    }

    @Test
    public void testDeleteConstructionEquipmentNegative() throws Exception {
        log.debug("testDeleteConstructionEquipmentNegative[19]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.deleteConstructionEquipment(INCORRECTID));
    }
    
    @Test
    public void testDeleteMaterialPositive() throws Exception {
        log.debug("testDeleteMaterialPositive[20]");
        dataProviderXML.addMaterial(material3);
        assertEquals(material3, dataProviderXML.getMaterial(material3.getId()).get());
        dataProviderXML.deleteMaterial(material3.getId());
        assertEquals(Optional.empty(), dataProviderXML.getMaterial(material3.getId()));
    }

    @Test
    public void testDeleteMaterialNegative() throws Exception {
        log.debug("testDeleteMaterialNegative[21]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.deleteMaterial(INCORRECTID));
    }
    
    @Test
    public void testDeleteClientPositive() throws Exception {
        log.debug("testDeleteClientPositive[22]");
        dataProviderXML.addClient(client3);
        assertEquals(client3, dataProviderXML.getClient(client3.getId()).get());
        dataProviderXML.deleteClient(client3.getId());
        assertEquals(Optional.empty(), dataProviderXML.getClient(client3.getId()));
    }

    @Test
    public void testDeleteClientNegative() throws Exception {
        log.debug("testDeleteClientNegative[23]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.deleteClient(INCORRECTID));
    }
    
    @Test
    public void testDeleteApartmentHousePositive() throws Exception {
        log.debug("testDeleteApartmentHousePositive[24]");
        dataProviderXML.addBuilding(apartmentHouse3);
        assertEquals(apartmentHouse3, dataProviderXML.getBuilding(apartmentHouse3.getId(), ApartmentHouse.class).get());
        dataProviderXML.deleteBuilding(apartmentHouse3.getId(), ApartmentHouse.class);
        assertEquals(Optional.empty(), dataProviderXML.getBuilding(apartmentHouse3.getId(), ApartmentHouse.class));
    }

    @Test
    public void testDeleteApartmentHouseNegative() throws Exception {
        log.debug("testDeleteApartmentHouseNegative[25]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.deleteBuilding(INCORRECTID, ApartmentHouse.class));
    }
    
    @Test
    public void testDeleteHousePositive() throws Exception {
        log.debug("testDeleteHousePositive[26]");
        dataProviderXML.addBuilding(house3);
        assertEquals(house3, dataProviderXML.getBuilding(house3.getId(), House.class).get());
        dataProviderXML.deleteBuilding(house3.getId(), House.class);
        assertEquals(Optional.empty(), dataProviderXML.getBuilding(house3.getId(), House.class));
    }

    @Test
    public void testDeleteHouseNegative() throws Exception {
        log.debug("testDeleteHouseNegative[27]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.deleteBuilding(INCORRECTID, House.class));
    }
    
    @Test
    public void testDeleteGaragePositive() throws Exception {
        log.debug("testDeleteGaragePositive[28]");
        dataProviderXML.addBuilding(garage3);
        assertEquals(garage3, dataProviderXML.getBuilding(garage3.getId(), Garage.class).get());
        dataProviderXML.deleteBuilding(garage3.getId(), Garage.class);
        assertEquals(Optional.empty(), dataProviderXML.getBuilding(garage3.getId(), Garage.class));
    }

    @Test
    public void testDeleteGarageNegative() throws Exception {
        log.debug("testDeleteGarageNegative[29]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.deleteBuilding(INCORRECTID, Garage.class));
    }
    
    
    @Test
    public void testDeleteBuildingNegative() throws IOException {
        log.debug("testDeleteBuildingNegative[30]");
        assertThrows(ClassCastException.class, () ->  dataProviderXML.deleteBuilding(INCORRECTID, Building.class));
    }


    @Test
    public void testGetWorkerPositive() throws Exception {

        log.debug("testGetWorkerPositive[31]");
        dataProviderXML.addWorker(worker4);
        assertEquals(worker4, dataProviderXML.getWorker(worker4.getId()).get());

    }

    @Test
    public void testGetWorkerNegative() throws Exception {

        log.debug("testGetWorkerPositive[32]");
        dataProviderXML.addWorker(worker5);
        assertEquals(Optional.empty(), dataProviderXML.getWorker(INCORRECTID));

    }

    @Test
    public void testGetConstructionEquiomentPositive() throws Exception {

        log.debug("testGetConstructionEquiomentPositive[33]");
        dataProviderXML.addConstructionEquipment(constructionEquipment4);
        assertEquals(constructionEquipment4, dataProviderXML.getConstructionEquipment(constructionEquipment4.getId()).get());

    }

    @Test
    public void testGetConstructionEquipmentNegative() throws Exception {

        log.debug("testGetConstructionEquipmentNegative[34]");
        dataProviderXML.addConstructionEquipment(constructionEquipment5);
        assertEquals(Optional.empty(), dataProviderXML.getConstructionEquipment(INCORRECTID));

    }
    
    

    @Test
    public void testGetMaterialPositive() throws Exception {

        log.debug("testGetMaterialPositive[35]");
        dataProviderXML.addMaterial(material4);
        assertEquals(material4, dataProviderXML.getMaterial(material4.getId()).get());

    }

    @Test
    public void testGetMaterialNegative() throws Exception {

        log.debug("testGetMaterialNegative[36]");
        dataProviderXML.addMaterial(material5);
        assertEquals(Optional.empty(), dataProviderXML.getMaterial(INCORRECTID));

    }
    @Test
    public void testGetClientPositive() throws Exception {

        log.debug("testGetClientPositive[37]");
        dataProviderXML.addClient(client4);
        assertEquals(client4, dataProviderXML.getClient(client4.getId()).get());

    }

    @Test
    public void testGetClientNegative() throws Exception {

        log.debug("testGetClientNegative[38]");
        dataProviderXML.addClient(client5);
        assertEquals(Optional.empty(), dataProviderXML.getClient(INCORRECTID));

    }
    
    @Test
    public void testGetApartmentHousePositive() throws Exception {

        log.debug("testGetApartmentHousePositive[39]");
        dataProviderXML.addBuilding(apartmentHouse4);
        assertEquals(apartmentHouse4, dataProviderXML.getBuilding(apartmentHouse4.getId(), ApartmentHouse.class).get());

    }

    @Test
    public void testGetApartmentHouseNegative() throws Exception {

        log.debug("testGetApartmentHouseNegative[40]");
        dataProviderXML.addBuilding(apartmentHouse5);
        assertEquals(Optional.empty(), dataProviderXML.getBuilding(INCORRECTID, ApartmentHouse.class));

    }
    @Test
    public void testGetHousePositive() throws Exception {

        log.debug("testGetHousePositive[41]");
        dataProviderXML.addBuilding(house4);
        assertEquals(house4, dataProviderXML.getBuilding(house4.getId(), House.class).get());

    }

    @Test
    public void testGetHouseNegative() throws Exception {

        log.debug("testGetHouseNegative[42]");
        dataProviderXML.addBuilding(house5);
        assertEquals(Optional.empty(), dataProviderXML.getBuilding(INCORRECTID, House.class));

    }
    
    @Test
    public void testGetGaragePositive() throws Exception {

        log.debug("testGetGaragePositive[43]");
        dataProviderXML.addBuilding(garage4);
        assertEquals(garage4, dataProviderXML.getBuilding(garage4.getId(), Garage.class).get());

    }

    @Test
    public void testGetGarageNegative() throws Exception {

        log.debug("testGetGarageNegative[44]");
        dataProviderXML.addBuilding(garage5);
        assertEquals(Optional.empty(), dataProviderXML.getBuilding(INCORRECTID, Garage.class));

    }
    
      @Test
    public void testGetBuildingNegative() throws IOException {
        log.debug("testGetBuildingNegative[45]");
        assertThrows(ClassCastException.class, () ->  dataProviderXML.getBuilding(INCORRECTID, Building.class));
    }

    
    
    @Test
    public void testGetAllWorkerRecordsPositive() throws Exception {
        log.debug("testGetAllWorkerRecordsPositive[46]");
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));
        List<Worker> list = new ArrayList<>();
        list.add(worker6);
        list.add(worker7);
        dataProviderXML.addWorker(worker6);
        dataProviderXML.addWorker(worker7);
        assertEquals(list,
                dataProviderXML.getAllRecords(Worker.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE)));
        addNecessaryRecords(Worker.class);
    }

    @Test
    public void testGetAllWorkerRecordsNegative() throws Exception {
        log.debug("testGetAllWorkerRecordsNegative[47]");
        assertThrows(IOException.class, () -> dataProviderXML.getAllRecords(Worker.class, INCORRECTPATH));

    }
    
    @Test
    public void testGetAllConstructionEquipmentRecordsPositive() throws Exception {
        log.debug("testGetAllConstructionEquipmentRecordsPositive[48]");
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));
        List<ConstructionEquipment> list = new ArrayList<>();
        list.add(constructionEquipment6);
        list.add(constructionEquipment7);
        dataProviderXML.addConstructionEquipment(constructionEquipment6);
        dataProviderXML.addConstructionEquipment(constructionEquipment7);
        assertEquals(list,
                dataProviderXML.getAllRecords(ConstructionEquipment.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE)));
        addNecessaryRecords(ConstructionEquipment.class);
    }

    @Test
    public void testGetAllConstructionEquipmentsRecordsNegative() throws Exception {
        log.debug("testGetAllConstructionEquipmentsRecordsNegative[49]");
        assertThrows(IOException.class, () -> dataProviderXML.getAllRecords(ConstructionEquipment.class, INCORRECTPATH));

    }
    
    @Test
    public void testGetAllMaterialRecordsPositive() throws Exception {
        log.debug("testGetAllMaterialRecordsPositive[50]");
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));
        List<Material> list = new ArrayList<>();
        list.add(material6);
        list.add(material7);
        dataProviderXML.addMaterial(material6);
        dataProviderXML.addMaterial(material7);
        assertEquals(list,
                dataProviderXML.getAllRecords(Material.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE)));
        addNecessaryRecords(Material.class);
    }

    @Test
    public void testGetAllMaterialRecordsNegative() throws Exception {
        log.debug("testGetAllMaterialRecordsNegative[51]");
        assertThrows(IOException.class, () -> dataProviderXML.getAllRecords(Material.class, INCORRECTPATH));

    }
    
    @Test
    public void testGetAllClientRecordsPositive() throws Exception {
        log.debug("testGetAllClientRecordsPositive[52]");
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));
        List<Client> list = new ArrayList<>();
        list.add(client6);
        list.add(client7);
        dataProviderXML.addClient(client6);
        dataProviderXML.addClient(client7);
        assertEquals(list,
                dataProviderXML.getAllRecords(Client.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE)));
        addNecessaryRecords(Client.class);
    }

    @Test
    public void testGetAllClientRecordsNegative() throws Exception {
        log.debug("testGetAllClientRecordsNegative[53]");
        assertThrows(IOException.class, () -> dataProviderXML.getAllRecords(Client.class, INCORRECTPATH));

    }
    
    @Test
    public void testGetAllApartmentHouseRecordsPositive() throws Exception {
        log.debug("testGetAllApartmentHouseRecordsPositive[54]");
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));
        List<ApartmentHouse> list = new ArrayList<>();
        list.add(apartmentHouse6);
        list.add(apartmentHouse7);
        dataProviderXML.addBuilding(apartmentHouse6);
        dataProviderXML.addBuilding(apartmentHouse7);
        assertEquals(list,
                dataProviderXML.getAllRecords(ApartmentHouse.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE)));
    }

    @Test
    public void testGetAllApartmentHouseRecordsNegative() throws Exception {
        log.debug("testGetAllApartmentHouseRecordsNegative[55]");
        assertThrows(IOException.class, () -> dataProviderXML.getAllRecords(ApartmentHouse.class, INCORRECTPATH));

    }
    
    @Test
    public void testGetAllHouseRecordsPositive() throws Exception {
        log.debug("testGetAllHouseRecordsPositive[56]");
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));
        List<House> list = new ArrayList<>();
        list.add(house6);
        list.add(house7);
        dataProviderXML.addBuilding(house6);
        dataProviderXML.addBuilding(house7);
        assertEquals(list,
                dataProviderXML.getAllRecords(House.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE)));
    }

    @Test
    public void testGetAllHouseRecordsNegative() throws Exception {
        log.debug("testGetAllHouseRecordsNegative[57]");
        assertThrows(IOException.class, () -> dataProviderXML.getAllRecords(House.class, INCORRECTPATH));

    }
    
    @Test
    public void testGetAllGarageRecordsPositive() throws Exception {
        log.debug("testGetAllGarageRecordsPositive[58]");
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));
        List<Garage> list = new ArrayList<>();
        list.add(garage6);
        list.add(garage7);
        dataProviderXML.addBuilding(garage6);
        dataProviderXML.addBuilding(garage7);
        assertEquals(list,
                dataProviderXML.getAllRecords(Garage.class,
                        Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE)));
    }

    @Test
    public void testGetAllGarageRecordsNegative() throws Exception {
        log.debug("testGetAllGarageRecordsNegative[59]");
        assertThrows(IOException.class, () -> dataProviderXML.getAllRecords(Garage.class, INCORRECTPATH));

    }
    

    @Test
    public void testUpdateWorkerPositive() throws Exception {
        log.debug("testUpdateWorkerPositive[60]");
        dataProviderXML.addWorker(worker8);
        dataProviderXML.updateWorker(worker8.getId(), worker9);
        assertEquals(worker9, dataProviderXML.getWorker(worker9.getId()).get());
    }

    @Test
    public void testUpdateWorkerNegative() throws Exception {
        log.debug("testUpdateWorkerNegative[61]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.updateWorker(INCORRECTID, worker));
    }

  
    @Test
    public void testUpdateWorkerToAnExistingRecord() throws Exception {
        log.debug("testUpdateWorkerToAnExistingRecord[62]");
        dataProviderXML.addWorker(worker10);
        dataProviderXML.addWorker(worker11);
        assertThrows(IllegalArgumentException.class, () -> dataProviderXML.updateWorker(worker10.getId(), worker11));
    }

    @Test
    public void testUpdateConstructionEquipmentPositive() throws Exception {
        log.debug("testUpdateConstructionEquipmentPositive[63]");
        dataProviderXML.addConstructionEquipment(constructionEquipment8);
        dataProviderXML.updateConstructionEquipment(constructionEquipment8.getId(), constructionEquipment9);
        assertEquals(constructionEquipment9, dataProviderXML.getConstructionEquipment(constructionEquipment9.getId()).get());
    }

    @Test
    public void testUpdateConstructionEquipmentNegative() throws Exception {
        log.debug("testUpdateConstructionEquipmentNegative[64]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.updateConstructionEquipment(INCORRECTID, constructionEquipment));
    }

  
    @Test
    public void testUpdateConstructionEquipmentToAnExistingRecord() throws Exception {
        log.debug("testUpdateConstructionEquipmentToAnExistingRecord[65]");
        dataProviderXML.addConstructionEquipment(constructionEquipment10);
        dataProviderXML.addConstructionEquipment(constructionEquipment11);
        assertThrows(IllegalArgumentException.class, () -> 
                dataProviderXML.updateConstructionEquipment(constructionEquipment10.getId(), constructionEquipment11));
    }

    @Test
    public void testUpdateMaterialPositive() throws Exception {
        log.debug("testUpdateMaterialPositive[66]");
        dataProviderXML.addMaterial(material8);
        dataProviderXML.updateMaterial(material8.getId(), material9);
        assertEquals(material9, dataProviderXML.getMaterial(material9.getId()).get());
    }

    @Test
    public void testUpdateMaterialNegative() throws Exception {
        log.debug("testUpdateMaterialNegative[67]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.updateMaterial(INCORRECTID, material));
    }

  
    @Test
    public void testUpdateMaterialToAnExistingRecord() throws Exception {
        log.debug("testUpdateMaterialToAnExistingRecord[68]");
        dataProviderXML.addMaterial(material10);
        dataProviderXML.addMaterial(material11);
        assertThrows(IllegalArgumentException.class, () -> 
                dataProviderXML.updateMaterial(material10.getId(), material11));
    }

    @Test
    public void testUpdateClientPositive() throws Exception {
        log.debug("testUpdateClientPositive[69]");
        dataProviderXML.addClient(client8);
        dataProviderXML.updateClient(client8.getId(), client9);
        assertEquals(client9, dataProviderXML.getClient(client9.getId()).get());
    }

    @Test
    public void testUpdateClientNegative() throws Exception {
        log.debug("testUpdateClientNegative[70]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.updateClient(INCORRECTID, client));
    }

  
    @Test
    public void testUpdateClientToAnExistingRecord() throws Exception {
        log.debug("testUpdateClientToAnExistingRecord[71]");
        dataProviderXML.addClient(client10);
        dataProviderXML.addClient(client11);
        assertThrows(IllegalArgumentException.class, () -> 
                dataProviderXML.updateClient(client10.getId(), client11));
    }
    
    @Test
    public void testUpdateApartmentHousePositive() throws Exception {
        log.debug("testUpdateApartmentHousePositive[72]");
        dataProviderXML.addBuilding(apartmentHouse8);
        dataProviderXML.updateBuilding(apartmentHouse8.getId(), apartmentHouse9);
        assertEquals(apartmentHouse9, dataProviderXML.getBuilding(apartmentHouse9.getId(), ApartmentHouse.class).get());
    }

    @Test
    public void testUpdateApartmentHouseNegative() throws Exception {
        log.debug("testUpdateApartmentHouseNegative[73]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.updateBuilding(INCORRECTID, apartmentHouse));
    }

  
    @Test
    public void testUpdateApartmentHouseToAnExistingRecord() throws Exception {
        log.debug("testUpdateApartmentHouseToAnExistingRecord[74]");
        dataProviderXML.addBuilding(apartmentHouse10);
        dataProviderXML.addBuilding(apartmentHouse11);
        assertThrows(IllegalArgumentException.class, () -> 
                dataProviderXML.updateBuilding(apartmentHouse10.getId(), apartmentHouse11));
    }
    
    @Test
    public void testUpdateHousePositive() throws Exception {
        log.debug("testUpdateHousePositive[75]");
        dataProviderXML.addBuilding(house8);
        dataProviderXML.updateBuilding(house8.getId(), house9);
        assertEquals(house9, dataProviderXML.getBuilding(house9.getId(), House.class).get());
    }

    @Test
    public void testUpdateHouseNegative() throws Exception {
        log.debug("testUpdateHouseNegative[76]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.updateBuilding(INCORRECTID, house));
    }

  
    @Test
    public void testUpdateHouseToAnExistingRecord() throws Exception {
        log.debug("testUpdateHouseToAnExistingRecord[77]");
        dataProviderXML.addBuilding(house10);
        dataProviderXML.addBuilding(house11);
        assertThrows(IllegalArgumentException.class, () -> 
                dataProviderXML.updateBuilding(house10.getId(), house11));
    }
    
    @Test
    public void testUpdateGaragePositive() throws Exception {
        log.debug("testUpdateGaragePositive[78]");
        dataProviderXML.addBuilding(garage8);
        dataProviderXML.updateBuilding(garage8.getId(), garage9);
        assertEquals(garage9, dataProviderXML.getBuilding(garage9.getId(), Garage.class).get());
    }

    @Test
    public void testUpdateGarageNegative() throws Exception {
        log.debug("testUpdateGarageNegative[79]");
        assertThrows(NoSuchElementException.class, () -> dataProviderXML.updateBuilding(INCORRECTID, garage));
    }

  
    @Test
    public void testUpdateGarageToAnExistingRecord() throws Exception {
        log.debug("testUpdateGarageToAnExistingRecord[80]");
        dataProviderXML.addBuilding(garage10);
        dataProviderXML.addBuilding(garage11);
        assertThrows(IllegalArgumentException.class, () -> 
                dataProviderXML.updateBuilding(garage10.getId(), garage11));
    }
    
    @Test
    public void testUpdateBuildingNegative() throws Exception {
        log.debug("testUpdateBuildingNegative[81]");
        assertThrows(ClassCastException.class, () ->  dataProviderXML.updateBuilding(INCORRECTID, new Building()));
    }
    
    @Test
    public void preparationOfConstructionPlanPositive() throws Exception {
        log.debug("preparationOfConstructionPlanPositive[82]");

        dataProviderXML.addClient(client31);
        dataProviderXML.addMaterial(material31);

        List<Material> materials = new ArrayList<>();
        materials.add(material31);

        List<EngineeringSystem> systems = new ArrayList<>();
        systems.add(EngineeringSystem.HEATING);

        dataProviderXML.preparationOfConstructionPlan(apartmentHouse31, client31, materials, systems);

        ApartmentHouse ah = apartmentHouse31;
        ah.setMaterials(materials);
        ah.setOwner(client31);
        ah.setEngineeringSystems(systems);

        assertEquals(ah, dataProviderXML.getBuilding("31", ApartmentHouse.class).get());

    }

    @Test
    public void preparationOfConstructionPlanNegative() throws Exception {
        log.debug("preparationOfConstructionPlanNegative[83]");

        assertThrows(ClassCastException.class,
                () -> dataProviderXML.preparationOfConstructionPlan(new Building(), client, null, null));

    }

    @Test
    public void selectionOfMaterialsPositive() throws Exception {
        log.debug("selectionOfMaterialsPositive[84]");

        dataProviderXML.addMaterial(material32);
        dataProviderXML.addMaterial(material33);

        List<Material> list = new ArrayList<>();
        list.add(material32);
        list.add(material33);

        assertEquals(list, dataProviderXML.selectionOfMaterials("32,33"));
    }

    @Test
    public void selectionOfMaterialsNegative() throws Exception {
        log.debug("selectionOfMaterialsNegative[85]");

        assertThrows(NoSuchElementException.class,
                () -> dataProviderXML.selectionOfMaterials(INCORRECTID));
    }

    @Test
    public void selectionOfEngineeringSystemsPositive() throws Exception {
        log.debug("selectionOfEngineeringSystemsPositive[86]");

        List<EngineeringSystem> list = new ArrayList<>();
        list.add(EngineeringSystem.HEATING);
        list.add(EngineeringSystem.SEWERAGE);

        assertEquals(list, dataProviderXML.selectionOfEngineeringSystems("HEATING,SEWERAGE"));
    }

    @Test
    public void selectionOfEngineeringSystemsNegative() throws Exception {
        log.debug("selectionOfEngineeringSystemsNegative[87]");

        assertThrows(IllegalArgumentException.class,
                () -> dataProviderXML.selectionOfEngineeringSystems(INCORRECTID));
    }

    @Test
    public void preparationForBuildingPositive() throws Exception {
        log.debug("preparationForBuildingPositive[88]");

        Worker w = new Worker("xsaxxxs", "893746273821", "xsaxsa", 32767);
        Client c = new Client("name", "+78372839102", "sss@mail.ru", "7362782349");
        ConstructionEquipment ce = new ConstructionEquipment("axsa", 3333);

        dataProviderXML.addWorker(w);
        dataProviderXML.addClient(c);
        dataProviderXML.addConstructionEquipment(ce);
        apartmentHouse33.setOwner(c);
        dataProviderXML.addBuilding(apartmentHouse33);

        dataProviderXML.preparationForBuilding(apartmentHouse33);

        List<Worker> workers = List.of(w);
        List<ConstructionEquipment> equipments = List.of(ce);

        ApartmentHouse ah = apartmentHouse33;
        ah.setWorkers(workers);
        ah.setConstructionEquipments(equipments);
        ah.setCompletionDate(LocalDate.now().plusMonths(Constants.TIME_IN_MONTH_FOR_BUILD_AN_APARTMENT_HOUSE));
        ah.setOwner(c);

        assertEquals(ah, dataProviderXML.getBuilding("33", ApartmentHouse.class).get());

    }

    @Test
    public void preparationForBuildingNegative() throws Exception {
        log.debug("preparationForBuildingNegative[89]");
        assertThrows(ClassCastException.class, () -> dataProviderXML.preparationForBuilding(new Building()));

    }

    @Test
    public void distributionOfWorkersPositive() throws Exception {

        log.debug("distributionOfWorkersPositive[90]");
        
        dataProviderXML.addWorker(new Worker("cdsbc", "894839283911", "xsx", 333232));
        dataProviderXML.addWorker(new Worker("isisi", "89384728391", "xsxaasaq", 83838));
        dataProviderXML.addWorker(new Worker("ooow", "89382903922", "ppqpq", 26090));
        dataProviderXML.addWorker(new Worker("pwnc", "89384194371", "soppox", 83922));
        dataProviderXML.addWorker(new Worker("oeiwoidj", "84928391031", "xsaxx21", 21000));

        assertEquals(4, dataProviderXML.distributionOfWorkers(new Garage(), Constants.PATH_TO_WORKER_XML_FILE).size());
    }

    @Test
    public void distributionOfWorkersNegative() throws Exception {
        log.debug("distributionOfWorkersNegative[91]");
        assertThrows(IOException.class, () -> dataProviderXML.distributionOfWorkers(new Garage(), INCORRECTPATH));
    }

    @Test
    public void distributionOfConstructionEquipmentPositive() throws Exception {
        log.debug("distributionOfConstructionEquipmentPositive[92]");
        

        dataProviderXML.addConstructionEquipment(new ConstructionEquipment("xsa", 21));
        dataProviderXML.addConstructionEquipment(new ConstructionEquipment("spapxosa", 31));
        dataProviderXML.addConstructionEquipment(new ConstructionEquipment("xwoo", 31));
        dataProviderXML.addConstructionEquipment(new ConstructionEquipment("xkjwoijd", 3131));
        dataProviderXML.addConstructionEquipment(new ConstructionEquipment("ppwpxxxsq", 23));
        dataProviderXML.addConstructionEquipment(new ConstructionEquipment("cndshciuds", 21));

        assertEquals(5, dataProviderXML.distributionOfConstructionEquipment(new Garage(), Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE).size());
    }

    @Test
    public void distributionOfConstructionEquipmentNegative() throws Exception {
        log.debug("distributionOfConstructionEquipmentNegative[93]");
        assertThrows(IOException.class, () -> dataProviderXML.distributionOfConstructionEquipment(new Garage(), INCORRECTPATH));
    }

    @Test
    public void coordinationOfConstructionTermsPositive() throws Exception {
        log.debug("coordinationOfConstructionTermsPositive[94]");
        assertEquals(LocalDate.now().plusMonths(6), dataProviderXML.coordinationOfConstructionTerms(house));
    }

    @Test
    public void coordinationOfConstructionTermsNegative() throws Exception {
        log.debug("coordinationOfConstructionTermsNegative[95]");
        assertThrows(ClassCastException.class, () -> dataProviderXML.coordinationOfConstructionTerms(new Building()));
    }

    @Test
    public void calculationOfTheTotalCostPositive() throws Exception {
        log.debug("calculationOfTheTotalCostPositive[96]");

        dataProviderXML.addMaterial(material34);
        dataProviderXML.addConstructionEquipment(constructionEquipment34);
        dataProviderXML.addWorker(worker34);

        List<Material> materials = List.of(material34);
        List<ConstructionEquipment> equipments = List.of(constructionEquipment34);
        List<Worker> workers = List.of(worker34);

        House house = new House();
        house.setMaterials(materials);
        house.setConstructionEquipments(equipments);
        house.setWorkers(workers);
        house.setNumberOfFloors(2);
        house.setSquare(56);

        assertEquals(506276.496, dataProviderXML.calculationOfTheTotalCost(house));

    }

    @Test
    public void calculationOfTheTotalCostNegative() throws Exception {
        log.debug("calculationOfTheTotalCostNegative[97]");
        assertThrows(ClassCastException.class, () -> dataProviderXML.calculationOfTheTotalCost(new Building()));

    }

    @Test
    public void calculationCostOfMaterialsPositive() throws Exception {
        log.debug("calculationCostOfMaterialsPositive[98]");

        dataProviderXML.addMaterial(material35);

        List<Material> list = List.of(material35);

        House house = new House();
        house.setMaterials(list);

        assertEquals(11100, dataProviderXML.calculationCostOfMaterials(house));

    }

    @Test
    public void calculationCostOfMaterialsNegative() throws Exception {
        log.debug("calculationCostOfMaterialsNegative[99]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataProviderXML.calculationCostOfMaterials(house));

    }

    @Test
    public void calculationCostOfConstructionEquipmentPositive() throws Exception {
        log.debug("calculationCostOfConstructionEquipmentPositive[100]");

        dataProviderXML.addConstructionEquipment(constructionEquipment35);

        List<ConstructionEquipment> list = List.of(constructionEquipment35);

        House house = new House();
        house.setConstructionEquipments(list);

        assertEquals(10000, dataProviderXML.calculationCostOfConstructionEquipment(house, 1));

    }

    @Test
    public void calculationCostOfConstructionEquipmentNegative() throws Exception {
        log.debug("calculationCostOfConstructionEquipmentNegative[101]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataProviderXML.calculationCostOfConstructionEquipment(house, 1));

    }

    @Test
    public void calculationCostOfJobPositive() throws Exception {
        log.debug("calculationCostOfJobPositive[102]");

        dataProviderXML.addWorker(worker35);

        List<Worker> list = List.of(worker35);

        House house = new House();
        house.setWorkers(list);

        assertEquals(10000, dataProviderXML.calculationCostOfJob(house, 1));
    }

    @Test
    public void calculationCostOfJobNegative() throws Exception {
        log.debug("calculationCostOfJobNegative[103]");

        House house = new House();

        assertThrows(NullPointerException.class, () -> dataProviderXML.calculationCostOfJob(house, 1));

    }
    
 

    private static void clearAllFIles() throws IOException {
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_WORKER_XML_FILE));
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_MATERIAL_XML_FILE));
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CONSTRUCTION_EQUIPMENT_XML_FILE));
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_CLIENT_XML_FILE));
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_APARTMENT_HOUSE_XML_FILE));
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_HOUSE_XML_FILE));
        dataProviderXML.clearFile(Constants.PATH_TO_RESOURCES.concat(Constants.PATH_TO_GARAGE_XML_FILE));
    }
    
    private static void addNecessaryRecords(Class clazz) throws IOException {
        log.debug("addNecessaryRecords [1]: class = " + clazz);

        switch (clazz.getSimpleName()) {

            case "Worker" ->
                dataProviderXML.addWorker(worker21);
            case "ConstructionEquipment" ->
                dataProviderXML.addConstructionEquipment(constructionEquipment21);
            case "Material" ->
                dataProviderXML.addMaterial(material21);
            case "Client" ->
                dataProviderXML.addClient(client21);
            default -> {
                dataProviderXML.addWorker(worker21);
                dataProviderXML.addConstructionEquipment(constructionEquipment21);
                dataProviderXML.addMaterial(material21);
                dataProviderXML.addClient(client21);
            }

        }

    }
    
}
