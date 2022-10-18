package com.Duong.Expense.TripActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.Duong.Expense.Database.MyDatabaseHelper;
import com.Duong.Expense.Object.Trip;
import com.Duong.Expense.R;

import java.util.Calendar;
import java.util.Locale;


public class AddActivity extends AppCompatActivity {

    EditText Trip_input, Destination_input, DateFrom_input, DateTo_input, desc_input;
    Button add_button;
    Calendar calendar;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    String risk;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Trip_input = findViewById(R.id.TripName);
        Destination_input = findViewById(R.id.TripDestination);
        desc_input = findViewById(R.id.TripDescription);
        DateFrom_input = findViewById(R.id.dateFrom);
        DateTo_input = findViewById(R.id.dateEnd);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);

            Trip trip = new Trip();
            trip.setName(Trip_input.getText().toString().trim());
            trip.setDes(Destination_input.getText().toString().trim());
            trip.setDesc(Destination_input.getText().toString().trim());
            trip.setDateFrom(DateFrom_input.getText().toString().trim());
            trip.setDateTo(DateTo_input.getText().toString().trim());

            radioGroup = findViewById(R.id.radioGroup);
            selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            risk = selectedRadioButton.getText().toString();

            trip.setRisk(risk);


            long result = myDB.add(trip);
            if (result == -1) {
                Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddActivity.this, TripActivity.class);
                startActivity(intent);
            }
        });

        calendar = Calendar.getInstance();

        //Date Picker for EditText Date From
        DatePickerDialog.OnDateSetListener datePickerFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendar();
            }

            private void updateCalendar() {
                String format = "dd MMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                DateFrom_input.setText(sdf.format(calendar.getTime()));
            }
        };

        //Date Picker for EditText Date To
        DatePickerDialog.OnDateSetListener datePickerTo =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateCalendar();
            }
            private void updateCalendar() {
                String format = "dd MMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                DateTo_input.setText(sdf.format(calendar.getTime()));
            }
        };

        //setOnClickListener to show date picker
        DateFrom_input.setOnClickListener(view -> new DatePickerDialog(AddActivity.this, datePickerFrom, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());
        DateTo_input.setOnClickListener(view -> new DatePickerDialog(AddActivity.this, datePickerTo, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());

    }
}
