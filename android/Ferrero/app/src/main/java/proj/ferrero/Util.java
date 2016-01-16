package proj.ferrero;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tonnyquintos on 1/16/16.
 */
public class Util {

    public static String epochToStringFormat(long epochSeconds, String formatString){
       /* String dateSt = 1386580621268;
        Log.i("*****", "date st is = " + dateSt);
        long unixSeconds = Long.parseLong(dateSt);*//*
        Log.i("*******", "unix seconds is = "+unixSeconds);
        Date date = new Date(unixSeconds
        ); // *1000 is to convert minutes to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"); // the format of your date
        String formattedDate = sdf.format(date);
        //System.out.println(formattedDate);*/
        Date updatedate = new Date(epochSeconds * 1000);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(updatedate);
    }
}
