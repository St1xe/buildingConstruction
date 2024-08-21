package ru.sfedu.buildingconstruction.lab4.model;


import jakarta.persistence.Entity;

@Entity
public enum EngineeringSystem {
    SEWERAGE, ELECTRICITY, WATER_SUPPLY, HEATING
}
