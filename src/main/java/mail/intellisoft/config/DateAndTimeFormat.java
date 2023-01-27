package mail.intellisoft.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateAndTimeFormat {

    public String returnTimeStamp(){
        DateTimeFormatter dateFormatter  = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime localTime = LocalDateTime.now();
        return dateFormatter.format(localTime);
    }
    public String returnTimeStampShort(){
        DateTimeFormatter dateFormatter  = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime localTime = LocalDateTime.now();
        return dateFormatter.format(localTime);
    }
    public String returnBoricaFormat(String googleFormat) throws ParseException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date setDayOfOrigin = dateFormatter.parse(googleFormat);
        dateFormatter.applyPattern("dd.MM.yyyy");
        return dateFormatter.format(setDayOfOrigin);
    }
    public String returnDaysDelay(Long delayDays,String plusOrMinus,String timeFormat){

        DateTimeFormatter dateFormatter  = DateTimeFormatter.ofPattern(timeFormat);

        LocalDateTime localTime;
        if(plusOrMinus.equals("plus")) localTime = LocalDateTime.now().plusDays(delayDays);
        else localTime = LocalDateTime.now().minusDays(delayDays);

        return dateFormatter.format(localTime);
    }
    public String returnTimeAndDateNow(){
        DateTimeFormatter dateFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localTime = LocalDateTime.now();
        return dateFormatter.format(localTime);

    }

}
