/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class WorkerCsvConverter extends AbstractCsvConverter{
    
    private static Logger log = Logger.getLogger(WorkerCsvConverter.class);

    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        
        if(value == "") {
            log.debug("WorkerCsvConverter [1]: список работников пуст");
            return null;
        }
        
        Worker w = new Worker();
        DataProviderCSV dataProviderCSV = new DataProviderCSV();

        try {
            Optional<Worker> o = dataProviderCSV.getWorker(value);
            w = o.get();
            log.info("WorkerCsvConverter [2]: работник c id = " + value + " = " + w);
        } catch (IOException ex) {
            log.error("WorkerCsvConverter [3]: " + ex.getMessage());
        } catch (NoSuchElementException x) {
            log.error("WorkerCsvConverter [4]: работников с id = " + value + " не найден");
            throw new NoSuchElementException("работник не найден");
        }

        return w;

    }

    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        log.info("WorkerCsvConverter [5]: работник = " + value);
        Worker w = (Worker) value;
        return String.format("%s", w.getId());
    }
}
