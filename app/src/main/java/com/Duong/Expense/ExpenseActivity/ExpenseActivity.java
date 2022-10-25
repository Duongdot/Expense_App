package com.Duong.Expense.ExpenseActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Duong.Expense.Adapter.ExpenseAdapter;
import com.Duong.Expense.Database.MyDatabaseHelper;
import com.Duong.Expense.Object.Expense;
import com.Duong.Expense.Object.Trip;
import com.Duong.Expense.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpenseActivity extends AppCompatActivity {
    TextView tripName, destination, dateFrom, dateTo, tripRisk, currency, empty, total;
    Trip selectedTrip;

    FloatingActionButton btnAdd;
    ImageView emptyImage;

    List<Expense> expenses;
    ExpenseAdapter expenseAdapter;
    RecyclerView recyclerView;

    MyDatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Expense List");

        Intent intent = getIntent();
        selectedTrip = (Trip) intent.getSerializableExtra("selectedTrip");

//        tripID = findViewById(R.id.tripID);
        tripName = findViewById(R.id.tripName);
        destination = findViewById(R.id.destination);
        dateFrom = findViewById(R.id.dateFrom);
        dateTo = findViewById(R.id.dateTo);
        tripRisk = findViewById(R.id.Risk);
        currency = findViewById(R.id.amount);
        total = findViewById(R.id.Total_expense);
        empty = findViewById(R.id.no_data_Expense);
        emptyImage = findViewById(R.id.empty_imageview_Expense);

        recyclerView = findViewById(R.id.recyclerView);

        myDB = new MyDatabaseHelper(this);
        expenses = new ArrayList<>();

        displayOrNot();

        expenseAdapter = new ExpenseAdapter(ExpenseActivity.this, this, expenses);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpenseActivity.this));

        btnAdd = findViewById(R.id.Floating_Add_button_Expense);
        btnAdd.setOnClickListener(view -> {
            Intent intents = new Intent(ExpenseActivity.this, Add_Expense_Activity.class);
            intents.putExtra("selectedTrip", selectedTrip);
            startActivity(intents);
        });
        getDetails();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    private void getDetails() {
//        tripID.setText(String.valueOf(selectedTrip.getId()));

        tripName.setText(selectedTrip.getName());
        destination.setText(selectedTrip.getDes());
        dateFrom.setText(selectedTrip.getDateFrom());
        dateTo.setText(selectedTrip.getDateTo());
        tripRisk.setText(selectedTrip.getRisk());
//        currency.setText(selectedTrip.getCurrency());

        Float totalExpenses = myDB.getTotalExpense(String.valueOf(selectedTrip.getId()));

//        total.setText(totalExpenses + " " + (selectedTrip.getCurrency()).substring((selectedTrip.getCurrency()).length() - 3));
    }

    void displayOrNot(){
        expenses = myDB.getAll();
        if(expenses.size() == 0){
            empty_imageview_Expense.setVisibility(View.VISIBLE);
            no_data_Expense.setVisibility(View.VISIBLE);
        }else{
            empty_imageview_Expense.setVisibility(View.VISIBLE);
            no_data_Expense.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
