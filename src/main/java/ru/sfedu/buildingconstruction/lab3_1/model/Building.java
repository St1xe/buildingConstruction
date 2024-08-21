package ru.sfedu.buildingconstruction.lab3_1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;


@Data
@MappedSuperclass
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
