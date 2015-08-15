package ru.tsystems.shalamov.services;

import ru.tsystems.shalamov.entities.ShiftEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by viacheslav on 04.07.2015.
 */
public final class DateUtilities {

    private DateUtilities() {
    }

    public static final float MAX_HOURS = 176;

    public static float getWorkingHours(List<ShiftEntity> shifts)
    {
        float acc = 0;
        for(ShiftEntity s : shifts) {
            acc += diffInHours(s.getShiftBegin(), s.getShiftEnd());
        }

        return acc;
    }

    public static float diffInHours(Date startDate, Date endDate) {
        long resultMills = endDate.getTime() - startDate.getTime();
        return(float) resultMills / 1000 / 60 / 60;
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
