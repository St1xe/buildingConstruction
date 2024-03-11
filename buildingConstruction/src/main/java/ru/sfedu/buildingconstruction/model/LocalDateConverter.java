/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.sfedu.buildingconstruction.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author maksim
 */
public class LocalDateConverter extends XmlAdapter<String, LocalDate>{

    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    @Override
    public LocalDate unmarshal(String vt) throws Exception {
        return LocalDate.parse(vt, f);
    }

    @Override
    public String marshal(LocalDate bt) throws Exception {
        return bt.format(f);
    }


  
    
}
