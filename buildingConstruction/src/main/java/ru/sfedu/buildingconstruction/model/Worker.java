package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maksim
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Worker {

    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private String phoneNumber;
    @CsvBindByPosition(position = 3)
    private String jobTitle;
    @CsvBindByPosition(position = 4)
    private double salary;

    public Worker() {
    }

    public Worker(String name, String phoneNumber, String jobTitle, double salary) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    public String getId() {
        return id;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.phoneNumber);
        hash = 11 * hash + Objects.hashCode(this.jobTitle);
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.salary) ^ (Double.doubleToLongBits(this.salary) >>> 32));
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
        final Worker other = (Worker) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.phoneNumber, other.phoneNumber);
    }

 

    @Override
    public String toString() {
        return "Worker{" + "name=" + name + ", phoneNumber=" + phoneNumber + ", jobTitle=" + jobTitle + ", salary=" + salary + '}';
    }


}
