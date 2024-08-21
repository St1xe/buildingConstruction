package ru.sfedu.buildingconstruction.lab4.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;


@Data
@Entity
@Table(name = "lab4_building")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class_type")
public class Building implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "square")
    private double square;

    @Column(name = "number_of_floors")
    private int numberOfFloors;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Embedded
    private Client owner;

    @ElementCollection
    @CollectionTable(name = "lab4_material", joinColumns = @JoinColumn(name = "building_id"))
    @OrderColumn(name = "materials_order")
    private List<Material> materials;

    @ElementCollection(targetClass = EngineeringSystem.class)
    @CollectionTable(name = "lab4_engineering_system", joinColumns = @JoinColumn(name = "building_id"))
    @Column(name = "engineering_system")
    private Set<EngineeringSystem> engineerinSystems;

    @ElementCollection
    @CollectionTable(name = "lab4_equipment", joinColumns = @JoinColumn(name = "building_id"))
    @MapKeyColumn(name = "id")
    @Column(name = "count")
    private Map<ConstructionEquipment, Integer> constructionEqipMap;

    @ElementCollection
    @CollectionTable(name = "lab4_worker", joinColumns = @JoinColumn(name = "building_id"))
    @OrderColumn(name = "workers_order")
    private List<Worker> worker;

}
