/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.utils;

import java.util.Calendar;

/**
 *
 * @author Owner
 */
public class TimeHelper 
{
    public static String createTimestampStr(Calendar para_calendar)
    {
        //"dd/MM/yyyy   HH:mm:ss"
        
        String retTStamp = "";
        retTStamp += (para_calendar.get(Calendar.DAY_OF_MONTH));
        retTStamp += ("/" + (para_calendar.get(Calendar.MONTH)+1));
        retTStamp += ("/" + para_calendar.get(Calendar.YEAR));
        retTStamp += ("   " + para_calendar.get(Calendar.HOUR_OF_DAY));
        retTStamp += (":" + para_calendar.get(Calendar.MINUTE));
        retTStamp += (":" + para_calendar.get(Calendar.SECOND));
        
        return retTStamp;
    }
    
    public static String calculateDuration(Calendar para_c1, Calendar para_c2)
    {
        long seconds = (para_c2.getTimeInMillis() - para_c1.getTimeInMillis()) / 1000;
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
    }
}
