package tools;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateTimeHandler {

    public static String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a");  
        Date date = new Date(System.currentTimeMillis());  
        return formatter.format(date);
    }

    public static String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = new Date(System.currentTimeMillis());  
        return formatter.format(date);
    }


    public static String getCurrentTimeStamp(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm a");  
        Date date = new Date(System.currentTimeMillis());  
        return formatter.format(date);
    }
    
}
