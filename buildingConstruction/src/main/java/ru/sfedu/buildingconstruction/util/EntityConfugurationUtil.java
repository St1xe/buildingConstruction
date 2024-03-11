/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.sfedu.buildingconstruction.util;

import org.apache.log4j.Logger;
import ru.sfedu.buildingconstruction.Constants;

/**
 *
 * @author maksim
 */
public class EntityConfugurationUtil {

    private static Logger log = Logger.getLogger(EntityConfugurationUtil.class);

    public static boolean phoneNumberIsCorrect(String str) {

        if (!str.matches(Constants.REGEX_TO_CHECK_PHONE_NUMBER)) {

            log.error("Неправильно указан номер");
            return false;
        }
        return true;

    }

    public static boolean emailIsCorrect(String str) {

        if (!str.matches(Constants.REGEX_TO_CHECK_EMAIL)) {

            log.error("Неправильно указан email");
            return false;
        }
        return true;

    }

    public static boolean passportIsCorrect(String str) {

        if (!str.matches(Constants.REGEX_TO_CHECK_PASSPORT)) {

            log.error("Неправильно указан паспорт");
            return false;
        }
        return true;

    }

    public static boolean numberIsPositive(double number) {

        if (number < 0) {

            log.error("Недопустимое число");
            return false;
        }
        return true;
    }

    public static boolean numberIsPositive(int number) {

        if (number < 0) {

            log.error("Недопустимое число");
            return false;
        }
        return true;
    }

}
