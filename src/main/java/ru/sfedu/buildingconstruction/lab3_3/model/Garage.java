package ru.sfedu.buildingconstruction.lab3_3.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("Garage")
public class Garage extends Building {

    @Column(name = "number_of_cars")
    private int numberOfCars;

}
