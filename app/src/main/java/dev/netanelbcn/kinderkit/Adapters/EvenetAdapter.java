package dev.netanelbcn.kinderkit.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import dev.netanelbcn.kinderkit.Controllers.DataManager;
import dev.netanelbcn.kinderkit.Interfaces.DelEventCallback;
import dev.netanelbcn.kinderkit.Models.Kid;
import dev.netanelbcn.kinderkit.Models.KidEvent;
import dev.netanelbcn.kinderkit.R;

public class EvenetAdapter extends RecyclerView.Adapter<EvenetAdapter.EventViewHolder> {

    private Context context;
    private DelEventCallback delEventCallback;
    private ArrayList<KidEvent> events;
    private DataManager dataManager = DataManager.getInstance();
    private int currentKidPosition;

    public EvenetAdapter(Context context, ArrayList<KidEvent> events, int currentKidPosition) {
        this.context = context;
        this.currentKidPosition = currentKidPosition;
        this.events = events;
        setEvents(currentKidPosition);
    }

    public void setEventCallback(DelEventCallback DelEventCallback) {
        this.delEventCallback = DelEventCallback;
    }

    public void setEvents(int currentKidPosition) {
        dataManager.loadAllKids(new DataManager.OnKidsLoadedListener() {
            @Override
            public void onKidsLoaded(ArrayList<Kid> kids) {
                //Log.d("kidEvents47:", events.toString()+", kid:"+ kids.get(currentKidPosition).toString());
                EvenetAdapter.this.events = kids.get(currentKidPosition).getEvents();

            }
            @Override
            public void onFailure(Exception exception) {
                Log.e("errr", exception.getMessage());
            }
        });
    }

    @NonNull
    @Override
    public EvenetAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_kid_card, parent, false);
        return new EvenetAdapter.EventViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        KidEvent event = getItem(position);
        String status;
        if (event.getApproved() == null)
            status = "Pending";
        else if (event.getApproved() == true)
            status = "Approved";
        else
            status = "Denied";


        holder.EV_MTV_event.setText(event.getEventTitle() + " | " + status);
        holder.EV_MTV_eventDate.setText(event.getEDate().toString());
    }


    @Override
    public int getItemCount() {
        return events.size();
    }

    private KidEvent getItem(int position) {
        return events.get(position);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView EV_MTV_event;
        MaterialTextView EV_MTV_eventDate;
        MaterialButton EV_MB_delete;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            EV_MTV_event = itemView.findViewById(R.id.EKC_MTV_event);
            EV_MTV_eventDate = itemView.findViewById(R.id.EKC_MTV_eventDate);
            EV_MB_delete = itemView.findViewById(R.id.EKC_MB_delete);
            EV_MB_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delEventCallback != null) {
                        delEventCallback.deleteClicked(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }
    }


}
