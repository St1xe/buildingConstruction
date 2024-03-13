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
public class ListWorkerXMLConverter extends XmlAdapter<String, List<Worker>> {

    private static Logger log = Logger.getLogger(ListWorkerXMLConverter.class);

    @Override
    public List<Worker> unmarshal(String value) throws Exception {

        if (value == "") {
            log.debug("ListWorkerXMLConverter [1]: список работников пуст");
            return null;
        }

        DataProviderXML dataProviderXML = new DataProviderXML();

        String[] str = value.split("/");

        List<Worker> list = new ArrayList<>();

        Arrays.stream(str).forEach(el -> {
            try {
                list.add(dataProviderXML.getWorker(el).get());
            } catch (IOException ex) {
                log.error("ListWorkerXMLConverter [2]: " + ex.getMessage());
            } catch (NoSuchElementException x) {
                log.info("ListWorkerXMLConverter [3]: работник с id = " + el + " не найден");
                throw new NoSuchElementException("работник не найден");
            }
        });

        return list;
    }

    @Override
    public String marshal(List<Worker> list) throws Exception {

        String str = list.stream().map(el -> el.getId().concat("/")).toList().stream().reduce("", (accumulator, el) -> accumulator += el);

        return str;

    }
}
