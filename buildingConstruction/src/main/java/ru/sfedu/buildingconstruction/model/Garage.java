/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.CsvBindByPosition;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import ru.sfedu.buildingconstruction.util.EntityConfugurationUtil;

/**
 *
 * @author maksim
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Garage extends Building {

    @CsvBindByPosition(position = 9)
    private int numberOfCars;

    public Garage() {
    }

    public Garage(String id, int numberOfCars, double square, int nummberOfFloors, LocalDate completionDate, Client owner, List<Material> materials, List<EngineeringSystem> engineeringSystems, List<ConstructionEquipment> constructionEquipments, List<Worker> workers) {
        super(id, square, nummberOfFloors, completionDate, owner, materials, engineeringSystems, constructionEquipments, workers);
        this.numberOfCars = numberOfCars;
    }

    public Garage(Building building, int numberOfCars) {
        super(building.getId(),
                building.getSquare(),
                building.getNumberOfFloors(),
                building.getCompletionDate(),
                building.getOwner(),
                building.getMaterials(),
                building.getEngineeringSystems(),
                building.getConstructionEquipments(),
                building.getWorkers());
        this.numberOfCars = numberOfCars;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }


    @Override
    public String toString() {
        return "Garage{" + "numberOfCars=" + numberOfCars + '}' + super.toString();
    }

}
