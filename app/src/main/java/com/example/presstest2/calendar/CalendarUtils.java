package com.example.presstest2.calendar;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils
{

    //the time of user's calendar display   (e.g. at  10月 2021 =>  equals selectedDate)
    public static LocalDate selectedDate;


    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date)
    {
        //------To store how many days in every month------
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        //to set day on calendars
        //Variable 1.dayOfWeek = the first day of the month start (e.g. Septempber 2021 's dayOfWeek is 3)
        for(int i=1;i<=42;i++){
            if(dayOfWeek==7)//To solve the problem that first week would be empty and the first day of month would start from second week
            {
                if(i<=daysInMonth)//To set days
                    daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i ));
            }
            else if//To set null lattice
            (i<=dayOfWeek || i >daysInMonth + dayOfWeek){
                daysInMonthArray.add(null);
            }
            else//To set days
                {
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }
    //------To store how many days in every month------

    //Convert the date type into String from LocalDate
    public static String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    //WeekView --unused code
    public static ArrayList<LocalDate>  daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days  = new ArrayList<>();
        LocalDate current  =sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while(current.isBefore(endDate)){

            days.add(current);
            current=current.plusDays(1);
        }

        return days;
    }

    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while(current.isAfter(oneWeekAgo)){
            if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current =current.minusDays(1);

        }
        return null;
    }
    //the code again but type String to set the ring which highlights current day or day you selected
    public static ArrayList<String> daysInMonthArray2(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();//當月之中有幾天 //yearMonth ->取得年月日

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int startDayOfFirstWeek = firstOfMonth.getDayOfWeek().getValue();

        //自己寫的迴圈
        // 1.大if:若startDayOfFirstWeek==7，避免第一個禮拜為空白，故額外處理填入日期 2.else if:除了startDayOfFirstWeek==7，填入空白日 3.else 填入日期
        for(int i=1;i<=42;i++){
            //if條件敘述為 日期開始前與後要填入空白 else則填入開始日期的數字
            if(startDayOfFirstWeek==7){
                if(i<=daysInMonth)
                    daysInMonthArray.add(String.valueOf(i));
            }
            else if(i<=startDayOfFirstWeek || i >daysInMonth + startDayOfFirstWeek){
                daysInMonthArray.add(" ");//original code
            }
            else {
                daysInMonthArray.add(String.valueOf(i-startDayOfFirstWeek));
            }
        }
        return daysInMonthArray;

    }

}