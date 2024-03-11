/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.sfedu.buildingconstruction.model;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.log4j.Logger;
import ru.sfedu.buildingconstruction.api.DataProviderCSV;
import ru.sfedu.buildingconstruction.api.DataProviderXML;

/**
 *
 * @author maksim
 */
public class ClientXmlConverter extends XmlAdapter<String, Client>{
    
    private static Logger log = Logger.getLogger(ClientXmlConverter.class);

    
    
    @Override
    public Client unmarshal(String value) throws Exception {
        
        if (value == "") {
            log.debug("ClientXmlConverter [1]: владелец не задан");
            return null;
        }

        Client c = new Client();
        DataProviderXML dataProviderXML = new DataProviderXML();

        try {
            Optional<Client> o = dataProviderXML.getClient(value);
            c = o.get();
            log.info("ClientXmlConverter [2]: владелец c id = " + value + " = " + c);
        } catch (IOException ex) {
            log.error("ClientXmlConverter [3]: " + ex.getMessage());
        } catch (NoSuchElementException x) {
            log.error("ClientXmlConverter [4]: владелец с id = " + value + " не найден");
            throw new NoSuchElementException("владелец не найден");
        }
        
        return c;
        
    }

    @Override
    public String marshal(Client c) throws Exception {
        
        log.info("ClientCsvConverter [5]: владелец = " + c);
        return String.format("%s", c.getId());
        
    }
    
}
