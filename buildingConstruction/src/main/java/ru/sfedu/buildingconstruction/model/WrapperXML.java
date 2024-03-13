package ru.sfedu.buildingconstruction.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author maksim
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Worker.class, ConstructionEquipment.class, Client.class, Material.class, Garage.class, ApartmentHouse.class, House.class})
public class WrapperXML<T> {

    @XmlElement (name = "wrapper")
    public List<T> list = new ArrayList<>();
}
