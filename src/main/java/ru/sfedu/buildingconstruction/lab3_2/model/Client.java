package ru.sfedu.buildingconstruction.lab3_2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class Client {

    @Column(name = "user_id")
    private String id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_phone")
    private String phoneNumber;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_passport")
    private String passport;

}
