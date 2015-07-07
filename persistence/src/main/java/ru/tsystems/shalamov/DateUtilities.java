package ru.tsystems.shalamov;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by viacheslav on 04.07.2015.
 */
public class DateUtilities {

    private DateUtilities() {
    }

    public static float diffInHours(Date startDate, Date endDate) {
        long resultMills = endDate.getTime() - startDate.getTime();
        float resultHours = (float) resultMills / 1000 / 60 / 60;
        return resultHours;
    }


    public static Date getFirstDayOfMonthDate(Date date) {

        Calendar calendar = getFirstDayOfMonthCalendar(date);
        Date result = calendar.getTime();
        calendar.setTime(new Date());
        return result;
    }


    public static Date getFirstDayOfNextMonth(Date date) {
        Calendar calendar = getFirstDayOfMonthCalendar(date);
        calendar.add(Calendar.MONTH, 1);
        Date result = calendar.getTime();
        calendar.setTime(new Date());
        return result;
    }


    private static Calendar getFirstDayOfMonthCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }
}
