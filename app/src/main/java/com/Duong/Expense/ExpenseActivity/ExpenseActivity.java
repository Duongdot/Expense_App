package com.Duong.Expense.ExpenseActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.Duong.Expense.Object.Trip;
import com.Duong.Expense.R;

public class ExpenseActivity extends AppCompatActivity {
    TextView tripName, destination, dateFrom, dateTo, tripRisk;
    Trip selectedTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Intent intent = getIntent();
        selectedTrip = (Trip) intent.getSerializableExtra("selectedTrip");

        tripName = findViewById(R.id.tripName);
        destination = findViewById(R.id.destination);
        dateFrom = findViewById(R.id.dateFrom);
        dateTo = findViewById(R.id.dateTo);
//        tripRisk = findViewById(R.id.tripRisk);

        getDetails();
    }

    private void getDetails() {
        tripName.setText(selectedTrip.getName());
        destination.setText(selectedTrip.getDes());
        dateFrom.setText(selectedTrip.getDateFrom());
        dateTo.setText(selectedTrip.getDateTo());
//        tripRisk.setText(selectedTrip.getRisk());

    }
}