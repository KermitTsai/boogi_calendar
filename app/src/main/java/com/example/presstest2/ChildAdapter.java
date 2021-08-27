package com.example.presstest2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//eventView
public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    ArrayList<ChildItem> childItemArrayList;
    public ChildAdapter(ArrayList<ChildItem> childItemArrayList) {
        this.childItemArrayList = childItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //setText what text and kind in first view
        ChildItem childItem = childItemArrayList.get(position);
        holder.TitleTextView.setText(childItem.Title);
        holder.TimeTextView.setText(childItem.Time);
        holder.kindOfEventGV.setImageResource(childItem.kindOfEventG);

    }

    @Override
    public int getItemCount() {
        return childItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TitleTextView, TimeTextView;
        ImageView kindOfEventGV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TitleTextView = itemView.findViewById(R.id.TitleTextView);
            TimeTextView = itemView.findViewById(R.id.TimeTextView);
            kindOfEventGV = itemView.findViewById(R.id.kindOfEventGV);

        }
    }



}
