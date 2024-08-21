package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.api.DataProviderCSV;


public class ConstructionEquipmentCsvConverter extends AbstractCsvConverter{
    
    private static Logger log = Logger.getLogger(ConstructionEquipmentCsvConverter.class);

    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        
        if(value == "") {
            log.debug("ConstructionEquipmentCsvConverter [1]: список строительного оборудования пуст");
            return null;
        }
        
        ConstructionEquipment ce = new ConstructionEquipment();
        DataProviderCSV dataProviderCSV = new DataProviderCSV();

        try {
            Optional<ConstructionEquipment> o = dataProviderCSV.getConstructionEquipment(value);
            ce = o.get();
            log.info("ConstructionEquipmentCsvConverter [2]: строительное оборудование c id = " + value + " = " + ce);
        } catch (IOException ex) {
            log.error("ConstructionEquipmentCsvConverter [3]: " + ex.getMessage());
        } catch (NoSuchElementException x) {
            log.error("ConstructionEquipmentCsvConverter [4]: строительное оборудование с id = " + value + " не найдено");
            throw new NoSuchElementException("строительное оборудование не найдено");
        }

        return ce;

    }

    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        log.info("ConstructionEquipmentCsvConverter [5]: строительное оборудование = " + value);
        ConstructionEquipment ce = (ConstructionEquipment) value;
        return String.format("%s", ce.getId());
    }
}
