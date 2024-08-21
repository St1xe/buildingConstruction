package ru.sfedu.buildingconstruction.model;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.api.DataProviderXML;


public class ListConstructionEquipmentXMLConverter extends XmlAdapter<String, List<ConstructionEquipment>> {

    private static Logger log = Logger.getLogger(ListConstructionEquipmentXMLConverter.class);

    @Override
    public List<ConstructionEquipment> unmarshal(String value) throws Exception {

        if (value == "") {
            log.debug("ListConstructionEquipmentXMLConverter [1]: список строительного оборудования пуст");
            return null;
        }

        DataProviderXML dataProviderXML = new DataProviderXML();

        String[] str = value.split("/");

        List<ConstructionEquipment> list = new ArrayList<>();

        Arrays.stream(str).forEach(el -> {
            try {
                list.add(dataProviderXML.getConstructionEquipment(el).get());
            } catch (IOException ex) {
                log.error("ListConstructionEquipmentXMLConverter [3]: " + ex.getMessage());
            } catch (NoSuchElementException x) {
                log.info("ListConstructionEquipmentXMLConverter [4]: строительное оборудование с id = " + el + " не найдено");
                throw new NoSuchElementException("строительное оборудование не найдено");
            }
        });

        return list;
    }

    @Override
    public String marshal(List<ConstructionEquipment> list) throws Exception {

        String str = list.stream().map(el -> el.getId().concat("/")).toList().stream().reduce("", (accumulator, el) -> accumulator += el);

        return str;

    }
}
