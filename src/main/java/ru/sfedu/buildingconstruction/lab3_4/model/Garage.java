package ru.sfedu.buildingconstruction.lab3_4.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "lab3_4_garage")
public class Garage extends Building {

    @Column(name = "number_of_cars")
    private int numberOfCars;

}
