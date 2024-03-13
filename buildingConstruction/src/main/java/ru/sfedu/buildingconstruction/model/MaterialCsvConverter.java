package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.api.DataProviderCSV;

/**
 *
 * @author maksim
 */
public class MaterialCsvConverter extends AbstractCsvConverter {

    private static Logger log = Logger.getLogger(MaterialCsvConverter.class);

    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        
        if(value == "") {
            log.debug("MaterialConverter [1]: список материалов пуст");
            return null;
        }
        
        Material m = new Material();
        DataProviderCSV dataProviderCSV = new DataProviderCSV();

        try {
            Optional<Material> o = dataProviderCSV.getMaterial(value);
            m = o.get();
            log.info("MaterialConverter [2]: материал c id = " + value + " = " + m);
        } catch (IOException ex) {
            log.error("MaterialConverter [3]: " + ex.getMessage());
        } catch (NoSuchElementException x) {
            log.info("MaterialConverter [4]: материал с id = " + value + " не найден");
            throw new NoSuchElementException("материал не найден");
        }

        return m;

    }

    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        log.debug("MaterialCsvConverter [5]: матерал = " + value);
        Material m = (Material) value;
        return String.format("%s", m.getId());
    }

}
