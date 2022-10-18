package com.Duong.Expense.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.Duong.Expense.Database.MyDatabaseHelper;
import com.Duong.Expense.Object.Trip;
import com.Duong.Expense.R;
import com.Duong.Expense.TripActivity.TripActivity;
import com.Duong.Expense.TripActivity.UpdateActivity;
//import com.Duong.Expense.TripActivity.UpdateActivity;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final List<Trip> trips;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

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
    // adapter get list trip
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
        holder.TripId.setText(String.valueOf(id));
        holder.tripDestination.setText(des);
        holder.tripDate.setText(dateFrom.concat(" - " + dateTo));
        holder.deleteTrip.setOnClickListener(view -> deleteTrip(trip.getId(), trip.getName()));
        // select trip
//        holder.editTrip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //passing parameter values
//                Intent intent = new Intent(context, UpdateActivity.class);
//                intent.putExtra("selectedTrip", trip);
//                activity.startActivityForResult(intent, 1);
//            }
//        });
        holder.editTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("selectedTrip", UpdateActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    private void deleteTrip(int id, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete " + name);
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                long result = myDB.delete(String.valueOf(id));
                if (result == -1) {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Delete Successfully!", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, TripActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView editTrip, deleteTrip;
        TextView tripName, tripDestination, tripDate, TripId ;
        LinearLayout mainLayout;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TripId = itemView.findViewById(R.id.TripID);
            tripName = itemView.findViewById(R.id.tripName);
            tripDestination = itemView.findViewById(R.id.TripDestination);
            tripDate = itemView.findViewById(R.id.date);
            deleteTrip = itemView.findViewById(R.id.imageViewDelete);
            editTrip = itemView.findViewById(R.id.imageViewEdit);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
