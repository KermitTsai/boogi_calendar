package com.example.presstest2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

//parentView
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Activity activity;
    ArrayList<ParentItem> parentItemArrayList;
    ArrayList<ChildItem> childItemArrayList;

    public MyAdapter(Activity activity, ArrayList<ParentItem> parentItemArrayList, ArrayList<ChildItem> childItemArrayList) {
        this.activity = activity;
        this.parentItemArrayList = parentItemArrayList;
        this.childItemArrayList = childItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ParentItem parentItem = parentItemArrayList.get(position);

        holder.TodayWhendayTextView.setText(parentItem.TodayWhendayTextView);
        holder.TodayDateTextView.setText(parentItem.TodayDateTextView);

        ChildAdapter childAdapter = new ChildAdapter(childItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.nested_rv.setLayoutManager(linearLayoutManager);
        holder.nested_rv.setAdapter(childAdapter);




    }

    @Override
    public int getItemCount() {
        return parentItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView TodayWhendayTextView, TodayDateTextView;
        RecyclerView nested_rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //------today,tomorrow and the day after tomorrow
            TodayWhendayTextView = itemView.findViewById(R.id.TodayWhendayTextView);
            TodayDateTextView = itemView.findViewById(R.id.TodayDateTextView);
            //------today,tomorrow and the day after tomorrow

            //the view where at the today,tomorrow and the day after tomorrow place
            nested_rv = itemView.findViewById(R.id.nested_rv);

        }
    }



}
