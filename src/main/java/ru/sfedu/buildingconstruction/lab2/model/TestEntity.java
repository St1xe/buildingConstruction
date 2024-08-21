package ru.sfedu.buildingconstruction.lab2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;


@Data
@Entity
@Table(name = "test")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name= "name")
    private String name;
    
    @Column(name= "description")
    private String description;
    
    @Column(name = "date_created")
    private LocalDate dateCreated;
    
    @Column(name= "checks")
    private boolean check;
    
    @Embedded
    User user;

}
