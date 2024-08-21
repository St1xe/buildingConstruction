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
@Table(name = "lab3_4_apartment_house")
public class ApartmentHouse extends Building {

    @Column(name = "number_of_apartments")
    private int numberOfApartments;
}
