package com.example.presstest2;



import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    FloatingActionButton addImage;
    LinearLayout layout;
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private String pageMonth;
    private String pageYear;
    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if(days.size()>15)//MonthView
            layoutParams.height = (int) (parent.getHeight() * 0.1277777);
        else//WeekView
            layoutParams.height = (int) parent.getHeight();
        return new CalendarViewHolder( view, onItemListener,days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {

//        movingHighlighter
        final LocalDate date = days.get(position);
        if(date==null)
            holder.dayOfMonth.setText("");
        else{
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate)){
                //holder.parentView.setBackgroundColor(Color.LTGRAY);灰色
//                holder.parentView.setBackgroundResource(R.drawable.indicator2);
//                holder.cellTextView.setTextColor(Color.rgb(255,255,255));
            }
        }

//        currentDayHighlighter
        String location = CalendarUtils.daysInMonthArray2(CalendarUtils.selectedDate).get(position);
        LocalDate nowDate = LocalDate.now();

        DateTimeFormatter nowDay = DateTimeFormatter.ofPattern("dd ");
        String currentDay = nowDate.format(nowDay);
        DateTimeFormatter nowMonth = DateTimeFormatter.ofPattern("MM");
        String currentMonth = nowDate.format(nowMonth);
        DateTimeFormatter nowYear = DateTimeFormatter.ofPattern("yyyy");
        String currentYear = nowDate.format(nowYear);

        DateTimeFormatter floatingMonth = DateTimeFormatter.ofPattern("MM");
        pageMonth =CalendarUtils.selectedDate.format(floatingMonth);
        DateTimeFormatter floatingYear = DateTimeFormatter.ofPattern("yyyy");
        pageYear =CalendarUtils.selectedDate.format(floatingYear);


        if (pageYear.equals(currentYear)&&pageMonth.equals(currentMonth))
            if (location.equals(currentDay)){
                holder.parentView.setBackgroundResource(R.drawable.indicator1);
                holder.cellTextView.setTextColor(Color.rgb(255,255,255));
            }

    }



    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position,LocalDate date);
    }







}
