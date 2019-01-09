package com.osapi.osserverapi.utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utilities {

    /**
     * @return Return the zoned current date
     */
    public static String getCurrentDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        return LocalDate.now().format(formatter);
    }

    /**
     * @return Return the current time
     */
    public static String getCurrentTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.now().format(formatter);
    }
}
