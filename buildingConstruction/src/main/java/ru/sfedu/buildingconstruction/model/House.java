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
public class House extends Building {

    @CsvBindByPosition(position = 9)
    private int numberOfRooms;

    public House() {
    }

    public House(String id, int numberOfRooms, double square, int nummberOfFloors, LocalDate completionDate, Client owner, List<Material> materials, List<EngineeringSystem> engineeringSystems, List<ConstructionEquipment> constructionEquipments, List<Worker> workers) {
        super(id, square, nummberOfFloors, completionDate, owner, materials, engineeringSystems, constructionEquipments, workers);
        this.numberOfRooms = numberOfRooms;
    }

    public House(Building building, int numberOfRooms) {
        super(building.getId(),
                building.getSquare(),
                building.getNumberOfFloors(),
                building.getCompletionDate(),
                building.getOwner(),
                building.getMaterials(),
                building.getEngineeringSystems(),
                building.getConstructionEquipments(),
                building.getWorkers());
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @Override
    public String toString() {
        return "House{" + "numberOfRooms=" + numberOfRooms + '}' + super.toString();
    }

}
