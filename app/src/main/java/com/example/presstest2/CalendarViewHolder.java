package com.example.presstest2;


import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView cellTextView;
    public final TextView dayOfMonth;
    //dot
//    public static FloatingActionButton addImage;
//    public static LinearLayout layout;

    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        cellTextView = itemView.findViewById(R.id.cellDayText);
        //dot
//        addImage = itemView.findViewById(R.id.addImage);
//        layout = itemView.findViewById(R.id.layout);
        //
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days=days;
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
    //dot
    public static void addView(ImageView imageView, int width, int height){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
        layoutParams.setMargins(0,10,0,10);
        imageView.setLayoutParams(layoutParams);
//        layout.addView(imageView);
    }
    public void colorRandom(ImageView imageView){
        Random random = new Random();
        int color = Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
        imageView.setBackgroundColor(color);
    }
}
