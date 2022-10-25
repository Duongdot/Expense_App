package com.Duong.Expense.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Duong.Expense.Object.Expense;
import com.Duong.Expense.R;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final List<ExpenseAdapter> expenses;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    ExpenseAdapter(Activity activity, Context context, List<Expense> expense) {
        this.activity = activity;
        this.context = context;
        this.expenses = expense;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_expense, parent, false);
        return new ExpenseAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {
        Expense expense = expenses.get(position);

        String types = expense.getTypeExpense();
        Float money = expense.getAmount();
        String date = expense.getDate();
//        String currency = expense.getCurrency();

        holder.type.setText(types);
//        holder.currency.setText(money + " " + currency.substring(currency.length() - 3)); // amount and currency
        holder.expenseDate.setText(date);

        holder.deleteExpense.setOnClickListener(v -> deleteExpense(expense, expense.getId()));
//        holder.updateExpense.setOnClickListener(v -> {
//            Intent intent = new Intent(context, UpdateExpenseActivity.class);
//            intent.putExtra("selectedExpense", expense);
//            activity.startActivityForResult(intent, 1);
//        });

    }

    private void deleteExpense(Expense expense, Integer id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete " + expense.getTypeExpense() + "?");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(context);
            long result = myDB.deleteExpense(String.valueOf(id));
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Delete Successfully!", Toast.LENGTH_SHORT).show();
                activity.finish();
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                context.startActivity(activity.getIntent());
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            // do nothing
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView updateExpense, deleteExpense;
        TextView type, expenseDate, currency;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.expenseType);

            expenseDate = itemView.findViewById(R.id.expenseDate);
            currency = itemView.findViewById(R.id.amount);
            updateExpense = itemView.findViewById(R.id.updateExpense);
            deleteExpense = itemView.findViewById(R.id.deleteExpense);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            mainLayout.setAnimation(translate_anim);
        }

    }
}