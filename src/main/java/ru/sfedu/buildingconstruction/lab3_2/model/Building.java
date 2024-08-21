package ru.sfedu.buildingconstruction.lab3_2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;


@Data
@Entity
@Table(name = "lab3_2_building")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Building implements Serializable{

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
}
