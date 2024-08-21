package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;
import java.util.UUID;


public class Material {

    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private double price;
    @CsvBindByPosition(position = 3)
    private double quantityInStock;

    public Material() {
    }

    public Material(String name, double price, double quantityInStock) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public double getQuantityInStock() {
        return quantityInStock;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantityInStock(double quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
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
        final Material other = (Material) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Material{" + "name=" + name + ", price=" + price + ", quantityInStock=" + quantityInStock + '}';
    }

}
