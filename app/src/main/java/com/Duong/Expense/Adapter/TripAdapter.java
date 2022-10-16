package com.Duong.Expense.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.Duong.Expense.Object.Trip;
import com.Duong.Expense.R;
//import com.Duong.Expense.TripActivity.UpdateActivity;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final List<Trip> trips;

    public TripAdapter(Activity activity, Context context, List<Trip> trips) {
        this.activity = activity;
        this.context = context;
        this.trips = trips;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_trip, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    // adapter get list book
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Trip trip = trips.get(position);
        int id = trip.getId();
        String name = trip.getName();
        String des = trip.getDes();
        String dateFrom = trip.getDateFrom();
        String dateTo = trip.getDateTo();
        String risk = trip.getRisk();
        String desc = trip.getDesc();

        // set value to form
        holder.tripName.setText(name);
        holder.tripDestination.setText(des);
        holder.tripDate.setText(dateFrom.concat(" - " + dateTo));
//        holder.trip_date_txt.setText(date);
//        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //passing parameter values
//                Intent intent = new Intent(context, UpdateActivity.class);
//                intent.putExtra("selectedTrip", trip);
//                activity.startActivityForResult(intent, 1);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

//        ImageView editTrip;
        TextView tripName, tripDestination, tripDate;
        LinearLayout mainLayout;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tripName = itemView.findViewById(R.id.tripName);
            tripDestination = itemView.findViewById(R.id.destination);
            tripDate = itemView.findViewById(R.id.date);
//            editTrip = itemView.findViewById(R.id.editTrip);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
