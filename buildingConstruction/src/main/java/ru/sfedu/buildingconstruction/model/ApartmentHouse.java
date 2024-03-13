package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.CsvBindByPosition;

import java.time.LocalDate;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maksim
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentHouse extends Building {

    @CsvBindByPosition(position = 9)
    private int numberOfApartments;

    public ApartmentHouse() {
    }

    public ApartmentHouse(String id, int numberOfApartments, double square, int numberOfFloors, LocalDate completionDate, Client owner, List<Material> materials, List<EngineeringSystem> engineeringSystems, List<ConstructionEquipment> constructionEquipments, List<Worker> workers) {
        super(id, square, numberOfFloors, completionDate, owner, materials, engineeringSystems, constructionEquipments, workers);
        this.numberOfApartments = numberOfApartments;
    }

    public ApartmentHouse(Building building, int numberOfApartments) {
        super(building.getId(),
                building.getSquare(),
                building.getNumberOfFloors(),
                building.getCompletionDate(),
                building.getOwner(),
                building.getMaterials(),
                building.getEngineeringSystems(),
                building.getConstructionEquipments(),
                building.getWorkers());
        this.numberOfApartments = numberOfApartments;
    }

    public int getNumberOfApartments() {
        return numberOfApartments;
    }

    public void setNumberOfApartments(int numberOfApartments) {
        this.numberOfApartments = numberOfApartments;
    }


    @Override
    public String toString() {
        return "AprtmentHouse{" + "numberOfApartments=" + numberOfApartments + "} " + super.toString();
    }

}
