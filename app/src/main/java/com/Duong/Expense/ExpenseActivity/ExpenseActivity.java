package com.Duong.Expense.ExpenseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Duong.Expense.Adapter.ExpenseAdapter;
import com.Duong.Expense.Database.MyDatabaseHelper;
import com.Duong.Expense.Object.Expense;
import com.Duong.Expense.Object.Trip;
import com.Duong.Expense.R;
import com.Duong.Expense.TripActivity.AddActivity;
import com.Duong.Expense.TripActivity.TripActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {
    TextView tripName, destination, dateFrom, dateTo, tripRisk, empty, total, desc;
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


        tripName = findViewById(R.id.tripName);
        destination = findViewById(R.id.destination);
        dateFrom = findViewById(R.id.dateFrom);
        dateTo = findViewById(R.id.dateTo);
        tripRisk = findViewById(R.id.Risk);
        desc = findViewById(R.id.Description);

        total = findViewById(R.id.Total_expense);
        empty = findViewById(R.id.no_data_Expense);
        emptyImage = findViewById(R.id.empty_imageview_Expense);

        recyclerView = findViewById(R.id.recyclerViewExpense);

        myDB = new MyDatabaseHelper(ExpenseActivity.this);
        expenses = new ArrayList<>();

        displayOrNot();

        expenseAdapter = new ExpenseAdapter(ExpenseActivity.this,this, expenses);
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

    private void getDetails() {

        tripName.setText(selectedTrip.getName());
        destination.setText(selectedTrip.getDes());
        dateFrom.setText(selectedTrip.getDateFrom());
        dateTo.setText(selectedTrip.getDateTo());
        tripRisk.setText(selectedTrip.getRisk());
        desc.setText(selectedTrip.getDesc());

        Float totalExpenses = myDB.getTotalExpense(String.valueOf(selectedTrip.getId()));

        total.setText(totalExpenses + " " + "USD");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void displayOrNot(){
        expenses = myDB.getAllExpense(selectedTrip.getId());
        if(expenses.size() == 0){
            emptyImage.setVisibility(View.VISIBLE);
            empty.setVisibility(View.VISIBLE);
        }else{
            emptyImage.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
