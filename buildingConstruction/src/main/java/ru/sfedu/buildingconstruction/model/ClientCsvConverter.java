package ru.sfedu.buildingconstruction.model;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.log4j.Logger;

import ru.sfedu.buildingconstruction.api.DataProviderCSV;

/**
 *
 * @author maksim
 */
public class ClientCsvConverter extends AbstractBeanField<String, Client> {

    private static Logger log = Logger.getLogger(ClientCsvConverter.class);

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if (value == "") {
            log.debug("ClientCsvConverter [1]: владелец не задан");
            return null;
        }

        Client c = new Client();
        DataProviderCSV dataProviderCSV = new DataProviderCSV();

        try {
            Optional<Client> o = dataProviderCSV.getClient(value);
            c = o.get();
            log.info("ClientCsvConverter [2]: владелец c id = " + value + " = " + c);
        } catch (IOException ex) {
            log.error("ClientCsvConverter [3]: " + ex.getMessage());
        } catch (NoSuchElementException x) {
            log.error("ClientCsvConverter [4]: владелец с id = " + value + " не найден");
            throw new NoSuchElementException("владелец не найден");
        }

        return c;
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        log.info("ClientCsvConverter [5]: владелец = " + value);
        Client c = (Client) value;
        return String.format("%s", c.getId());
    }

}
