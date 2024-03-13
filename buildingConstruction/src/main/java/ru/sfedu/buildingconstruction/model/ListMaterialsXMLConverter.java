package ru.sfedu.buildingconstruction.model;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.api.DataProviderXML;

/**
 *
 * @author maksim
 */
public class ListMaterialsXMLConverter extends XmlAdapter<String, List<Material>> {

    private static Logger log = Logger.getLogger(ListMaterialsXMLConverter.class);

    @Override
    public List<Material> unmarshal(String value) throws Exception {

        if (value == "") {
            log.debug("ListMaterialsXMLConverter [1]: список материалов пуст");
            return null;
        }

        DataProviderXML dataProviderXML = new DataProviderXML();

        String[] str = value.split("/");

        List<Material> list = new ArrayList<>();

        Arrays.stream(str).forEach(el -> {
            try {
                list.add(dataProviderXML.getMaterial(el).get());
            } catch (IOException ex) {
                log.error("ListMaterialsXMLConverter [2]: " + ex.getMessage());
            } catch (NoSuchElementException x) {
                log.info("ListMaterialsXMLConverter [3]: материал с id = " + el + " не найден");
                throw new NoSuchElementException("материал не найден");
            }
        });

        return list;
    }

    @Override
    public String marshal(List<Material> list) throws Exception {

        String str = list.stream().map(el -> el.getId().concat("/")).toList().stream().reduce("", (accumulator, el) -> accumulator += el);

        return str;

    }

}
