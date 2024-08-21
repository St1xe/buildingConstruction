package ru.sfedu.buildingconstruction.lab4.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;


@Data
@Embeddable
public class ConstructionEquipment implements Serializable{

    @Column(name = "id")
    private String id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "price")
    private double price;

}
