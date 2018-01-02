package com.android.cy.pullrecyclerview.view.linescroll;

/**
 * Created by zhxh on 2018/1/2.
 */

public class DateTime {

    private static final long YEAR_NUM = 10000000000L;

    private static final long MONTH_NUM = 100000000L;

    private static final long DAY_NUM = 1000000L;

    private static final long HOUR_NUM = 10000L;

    private static final long MIN_NUM = 100L;

    /**年*/
    private int year;
    /**月*/
    private int month;
    /**日*/
    private int day;
    /**时*/
    private int hour;
    /**分*/
    private int minute;

    public static DateTime getInstance(String dateTime) {

        return new DateTime(dateTime);

    }

    private DateTime(String dateTime) {

        parse(dateTime);

    }

    public int getYear() {

        return year;

    }

    public int getMonth() {

        return month;

    }

    public String getMonthWith2Numbers() {

        return month>=10 ? month+"":"0"+month;

    }

    public int getDay() {
        return day;
    }

    public String getDayWith2Numbers() {

        return day>=10 ? day+"":"0"+day;

    }

    public int getHour() {

        return hour;

    }

    public int getMinute() {

        return minute;

    }



    private void parse(String dateTime){

        long time = -1;

        try {

            time = Long.parseLong(dateTime);

        }catch(NumberFormatException e){
//			System.out.println("dateTime "+dateTime);
        }

        if (time > 0) {

            this.year   = (int) (time / YEAR_NUM);
            this.month  = (int) ((time % YEAR_NUM) / MONTH_NUM);
            this.day    = (int) ((time % MONTH_NUM) / DAY_NUM);
            this.hour   = (int) ((time % DAY_NUM) / HOUR_NUM);
            this.minute = (int) ((time % HOUR_NUM) / MIN_NUM);

        }

    }


}
