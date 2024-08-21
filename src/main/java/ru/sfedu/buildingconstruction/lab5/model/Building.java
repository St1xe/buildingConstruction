package ru.sfedu.buildingconstruction.lab5.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name = "lab5_building")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class_type")
@EqualsAndHashCode()
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner_id")
    private Client owner;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "lab5_building_material",
            joinColumns = @JoinColumn(name = "building_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    @OrderColumn(name = "materials_order")
    private List<Material> materials;

    @ElementCollection(targetClass = EngineeringSystem.class)
    @CollectionTable(name = "lab5_engineering_system", joinColumns = @JoinColumn(name = "building_id"))
    @Column(name = "engineering_system")
    private Set<EngineeringSystem> engineerinSystems;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "building_id")
    @OrderColumn(name = "equipment_order")
    private List<ConstructionEquipment> constructionEqipment;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "building_id")
    @OrderColumn(name = "workers_order")
    private List<Worker> worker;
    
    
    
}
