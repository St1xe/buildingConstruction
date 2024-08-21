package ru.sfedu.buildingconstruction.lab2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class User {

    @Column(name = "user_name")
    private String name;
    
    @Column(name = "user_surname")
    private String surname;
    
    @Column(name = "user_phone")
    private String phone;
    
    
}
