package ru.sfedu.buildingconstruction.lab3_2.model;

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
@Table(name = "lab3_2_house")
public class House extends Building{

    @Column(name = "number_of_rooms")
    private int numberOfrooms;
}