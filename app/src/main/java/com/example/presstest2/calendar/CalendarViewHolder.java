package com.example.presstest2.calendar;



import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.presstest2.R;

import java.time.LocalDate;
import java.util.ArrayList;


public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView cellTextView;
    public final TextView dayOfMonth;
    public static View indicator;
    public static LinearLayout linearLayout;



    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        cellTextView = itemView.findViewById(R.id.cellDayText);
        linearLayout = itemView.findViewById(R.id.linearLayout);





        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days=days;
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }

}
