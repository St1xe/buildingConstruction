///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
// */
//package ru.sfedu.buildingconstruction.model;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.apache.log4j.Logger;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import ru.sfedu.buildingconstruction.BuildingConstructionClient;
//
///**
// *
// * @author maksim
// */
//public class BuildingTest {
//
//    private static Logger log = Logger.getLogger(BuildingConstructionClient.class);
//
//    double square = 20;
//    int numberOfFloors = 2;
//    Date completionDate = new Date(1234567890L);
//    Client owner = new Client("Maxin", "78787318", "xsds@mail.ru", "1234 567890");
//    List<Material> materials = new ArrayList<>();
//    List<EngineeringSystem> engineeringSystems = new ArrayList<>();
//    House house = new House(square, numberOfFloors, completionDate, owner, materials, engineeringSystems);
//
//    public BuildingTest() {
//        materials.add(new Material("bricks", 150));
//
//        engineeringSystems.add(EngineeringSystem.HEATING);
//    }
//
//    @Test
//    public void testGetSquare() {
//        log.debug("testGetSquare");
//
//        assertEquals(house.getSquare(), square);
//    }
//
//    @Test
//    public void testSetSquare() {
//
//        log.debug("testSetSquare");
//        house.setSquare(35.5);
//        assertEquals(house.getSquare(), 35.5);
//    }
//
//    @Test
//    public void testGetNummberOfFloors() {
//        log.debug("testGetNummberOfFloors");
//        assertEquals(house.getNummberOfFloors(), numberOfFloors);
//    }
//
//    @Test
//    public void testGetCompletionDate() {
//        log.debug("testGetCompletionDate");
//        assertEquals(house.getCompletionDate(), completionDate);
//    }
//
//    @Test
//    public void testGetOwner() {
//        log.debug("testGetOwner");
//        assertEquals(house.getOwner(), owner);
//    }
//
//    @Test
//    public void testGetMaterials() {
//        log.debug("testGetMaterials");
//        assertEquals(house.getMaterials(), materials);
//    }
//
//  
//    @Test
//    public void testGetEngineeringSystems() {
//        log.debug("testGetEngineeringSystems");
//        assertEquals(house.getEngineeringSystems(), engineeringSystems);
//    }
//
//}
