package ru.sfedu.buildingconstruction.lab5.model;

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
@DiscriminatorValue("House")
public class House extends Building{

    @Column(name = "number_of_rooms")
    private int numberOfRooms;
    
}
