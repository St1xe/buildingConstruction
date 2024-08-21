package ru.sfedu.buildingconstruction.lab3_1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = "lab3_1_apartment_house")
public class ApartmentHouse extends Building{

    @Column(name = "number_of_apartments")
    private int numberOfApartments;
    
}
