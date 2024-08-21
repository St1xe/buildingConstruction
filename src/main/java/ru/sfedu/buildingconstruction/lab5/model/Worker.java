package ru.sfedu.buildingconstruction.lab5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;


@Data
@Entity
@Table(name = "lab5_worker")
public class Worker implements Serializable{

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "salary")
    private double salary;

}
