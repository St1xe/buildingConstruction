package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;

import java.time.LocalDate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Building {

    @CsvBindByPosition(position = 0)
    private String id;

    @CsvBindByPosition(position = 1)
    private double square;

    @CsvBindByPosition(position = 2)
    private int numberOfFloors;

    @XmlJavaTypeAdapter(value = LocalDateConverter.class, type = LocalDate.class)
    @CsvBindByPosition(position = 3)
    @CsvDate(value = "yyyyMMdd")
    private LocalDate completionDate;

    @XmlJavaTypeAdapter(ClientXmlConverter.class)
    @CsvCustomBindByPosition(converter = ClientCsvConverter.class, position = 4)
    private Client owner;

    @XmlJavaTypeAdapter(ListMaterialsXMLConverter.class)
    @CsvBindAndSplitByPosition(position = 5, elementType = Material.class, splitOn = "/", writeDelimiter = "/", converter = MaterialCsvConverter.class)
    private List<Material> materials;

    @XmlList
    @CsvBindAndSplitByPosition(position = 6, elementType = EngineeringSystem.class)
    private List<EngineeringSystem> engineeringSystems;

    @XmlJavaTypeAdapter (ListConstructionEquipmentXMLConverter.class)
    @CsvBindAndSplitByPosition(position = 7, elementType = ConstructionEquipment.class, splitOn = "/", writeDelimiter = "/", converter = ConstructionEquipmentCsvConverter.class)
    private List<ConstructionEquipment> constructionEquipments;

    @XmlJavaTypeAdapter (ListWorkerXMLConverter.class)
    @CsvBindAndSplitByPosition(position = 8, elementType = Worker.class, splitOn = "/", writeDelimiter = "/", converter = WorkerCsvConverter.class)
    private List<Worker> workers;

    public Building() {
    }

    public Building(String id, double square, int nummberOfFloors, LocalDate completionDate, Client owner, List<Material> materials, List<EngineeringSystem> engineeringSystems, List<ConstructionEquipment> constructionEquipments, List<Worker> workers) {

        if (id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }

        this.square = square;
        this.numberOfFloors = nummberOfFloors;
        this.completionDate = completionDate;
        this.owner = owner;
        this.materials = materials;
        this.engineeringSystems = engineeringSystems;
        this.constructionEquipments = constructionEquipments;
        this.workers = workers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<EngineeringSystem> getEngineeringSystems() {
        return engineeringSystems;
    }

    public void setEngineeringSystems(List<EngineeringSystem> engineeringSystems) {
        this.engineeringSystems = engineeringSystems;
    }

    public List<ConstructionEquipment> getConstructionEquipments() {
        return constructionEquipments;
    }

    public void setConstructionEquipments(List<ConstructionEquipment> constructionEquipments) {
        this.constructionEquipments = constructionEquipments;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.square) ^ (Double.doubleToLongBits(this.square) >>> 32));
        hash = 79 * hash + this.numberOfFloors;
        hash = 79 * hash + Objects.hashCode(this.owner);
        hash = 79 * hash + Objects.hashCode(this.materials);
        hash = 79 * hash + Objects.hashCode(this.engineeringSystems);
        hash = 79 * hash + Objects.hashCode(this.constructionEquipments);
        hash = 79 * hash + Objects.hashCode(this.workers);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Building other = (Building) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Building{"
                + " square=" + square
                + ", nummberOfFloors=" + numberOfFloors
                + ", completionDate=" + completionDate
                + ", owner= {" + owner + "}"
                + ", materials=" + materials
                + ", engineeringSystems=" + engineeringSystems
                + ", constructionEquipments=" + constructionEquipments
                + ", workers=" + workers + '}';
    }

    private Building(Builder builder) {
        if (builder.id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = builder.id;
        }

        this.square = builder.square;
        this.numberOfFloors = builder.nummberOfFloors;
        this.completionDate = builder.completionDate;
        this.owner = builder.owner;
        this.materials = builder.materials;
        this.engineeringSystems = builder.engineeringSystems;
        this.constructionEquipments = builder.constructionEquipments;
        this.workers = builder.workers;
    }

    public static class Builder {

        private String id;
        private double square;
        private int nummberOfFloors;
        private LocalDate completionDate;
        private Client owner;
        private List<Material> materials;
        private List<EngineeringSystem> engineeringSystems;
        private List<ConstructionEquipment> constructionEquipments;
        private List<Worker> workers;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setSquare(double square) {
            this.square = square;
            return this;
        }

        public Builder setNummberOfFloors(int nummberOfFloors) {
            this.nummberOfFloors = nummberOfFloors;
            return this;
        }

        public Builder setCompletionDate(LocalDate completionDate) {
            this.completionDate = completionDate;
            return this;
        }

        public Builder setOwner(Client owner) {
            this.owner = owner;
            return this;
        }

        public Builder setMaterials(List<Material> materials) {
            this.materials = materials;
            return this;
        }

        public Builder setEngineeringSystems(List<EngineeringSystem> engineeringSystems) {
            this.engineeringSystems = engineeringSystems;
            return this;
        }

        public Builder setConstructionEquipments(List<ConstructionEquipment> constructionEquipments) {
            this.constructionEquipments = constructionEquipments;
            return this;
        }

        public Builder setWorkers(List<Worker> workers) {
            this.workers = workers;
            return this;
        }

        public Building build() {
            return new Building(this);
        }

    }

}
