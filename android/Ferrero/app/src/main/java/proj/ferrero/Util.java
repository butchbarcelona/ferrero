package proj.ferrero;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tonnyquintos on 1/16/16.
 */
public class Util {

    public static String epochToStringFormat(long epochSeconds, String formatString){

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        sdf.setTimeZone(tz);

        String localTime = sdf.format(new Date(epochSeconds )); // I assume your timestamp is in seconds and you're converting to milliseconds?

        return localTime;
    }
    public static String getDiffOfTime(long start, long end){

        Date dStart = new Date(start);
        Date dEnd = new Date(end);

        //milliseconds
        long different = start - end;//end - start; //endDate.getTime() - startDate.getTime();

/*        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);*/
        return getStringDuration(different);
    }


    public static String getStringDuration(long different){

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedHours+" hrs "+elapsedMinutes+" mins";
    }
    public static void showDialog(Context ctx, String message, String okButton, String cancelButton
      , DialogInterface.OnClickListener positiveListener
      , DialogInterface.OnClickListener negativeListener){
        new AlertDialog.Builder(ctx)
          .setTitle(MainNavActivity.TAG)
          .setMessage(message)
          .setPositiveButton(okButton, positiveListener)
          .setNegativeButton(cancelButton, negativeListener)
          .setIcon(android.R.drawable.ic_dialog_alert)
          .show();
    }

    public static void showDialog(Context ctx, String message, String okButton
      , DialogInterface.OnClickListener positiveListener ){
        new AlertDialog.Builder(ctx)
          .setTitle(MainNavActivity.TAG)
          .setMessage(message)
          .setPositiveButton(okButton, positiveListener)
          .setIcon(android.R.drawable.ic_dialog_alert)
          .show();
    }

}
