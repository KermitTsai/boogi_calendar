package com.example.presstest2.event.event_today;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.presstest2.R;

import java.util.List;

public class EventAdapter_today extends RecyclerView.Adapter<EventAdapter_today.EventViewHolder>{
    List<Event_today> mListEvents;
    private ViewBinderHelper viewBinderHelper =new ViewBinderHelper();

    String[] color ={"red","orange","yellow","teal","green","blue","navy","light_purple"};

    public EventAdapter_today(List<Event_today> mListEvents) {
        this.mListEvents = mListEvents;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event_today event = mListEvents.get(position);
        if(event ==null){
            return;
        }

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(event.getTime()));
        holder.tvEventTitle.setText(event.getTitle());
        holder.tvEventTime.setText(event.getTime());
        holder.ivEventKind.setBackgroundTintList(null);


        switch (event.getKind()){
            case "Course":
                holder.ivEventKind.setImageResource(R.drawable.course);
                switch(event.getColor()){
                    case "yellow":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#FFEB3B"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#FFEB3B"));
                        break;
                    case "green":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#4CAF50"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#4CAF50"));
                        break;
                    case "blue":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#03A9F4"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#03A9F4"));
                        break;
                    default:
                        holder.ivEventKind.setColorFilter(Color.parseColor("#53D3C3"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#53D3C3"));
                }
                break;

            case "Schedule":
                holder.ivEventKind.setImageResource(R.drawable.schedule);
                switch(event.getColor()){
                    case "yellow":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#FFEB3B"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#FFEB3B"));
                        break;
                    case "green":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#4CAF50"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#4CAF50"));
                        break;
                    case "blue":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#03A9F4"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#03A9F4"));
                        break;
                    default:
                        holder.ivEventKind.setColorFilter(Color.parseColor("#53D3C3"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#53D3C3"));
                }
                break;
            case "Merge":
                holder.ivEventKind.setImageResource(R.drawable.work);
                switch(event.getColor()){
                    case "yellow":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#FFEB3B"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#FFEB3B"));
                        break;
                    case "green":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#4CAF50"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#4CAF50"));
                        break;
                    case "blue":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#03A9F4"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#03A9F4"));
                        break;
                    default:
                        holder.ivEventKind.setColorFilter(Color.parseColor("#53D3C3"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#53D3C3"));
                }
                break;
            default:
                holder.ivEventKind.setImageResource(R.drawable.ic_baseline_person_24);
                switch(event.getColor()){
                    case "yellow":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#FFEB3B"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#FFEB3B"));
                        break;
                    case "green":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#4CAF50"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#4CAF50"));
                        break;
                    case "blue":
                        holder.ivEventKind.setColorFilter(Color.parseColor("#03A9F4"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#03A9F4"));
                        break;
                    default:
                        holder.ivEventKind.setColorFilter(Color.parseColor("#53D3C3"));
                        holder.vColor.setBackgroundColor(Color.parseColor("#53D3C3"));
                }
        }



//        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListEvents.remove(holder.getAdapterPosition());
//                notifyItemRemoved(holder.getAdapterPosition());
//            }
//        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListEvents.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mListEvents.remove(holder.getAdapterPosition());
//                notifyItemRemoved(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListEvents != null){
            return mListEvents.size();
        }

        return 0;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{

        private SwipeRevealLayout swipeRevealLayout;
//        private LinearLayout layoutDelete;
        private TextView tvEventTitle;
        private TextView tvEventTime;
        private TextView edit;
        private TextView delete;
        private ImageView ivEventKind;

        private View vColor;



        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
//            layoutDelete = itemView.findViewById(R.id.layout_delete);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
            tvEventTitle = itemView.findViewById(R.id.event_Title);
            tvEventTime = itemView.findViewById(R.id.event_Time);
            ivEventKind = itemView.findViewById(R.id.event_kind);
            vColor = itemView.findViewById(R.id.vColor);




        }

    }




}
