package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author maksim
 */
public class Client {


    @CsvBindByPosition(position = 0)
    private String id;
    
    @CsvBindByPosition(position = 1)
    private String name;
    
    @CsvBindByPosition(position = 2)
    private String phoneNumber;
    
    @CsvBindByPosition(position = 3)
    private String email;
    
    @CsvBindByPosition(position = 4)
    private String passport;

    public Client() {
    }

    public Client(String name, String phoneNumber, String email, String passport) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passport = passport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.phoneNumber);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.passport);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;

        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.passport, other.passport);
    }

    @Override
    public String toString() {
        return "name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + ", passport=" + passport;
    }

    

}
