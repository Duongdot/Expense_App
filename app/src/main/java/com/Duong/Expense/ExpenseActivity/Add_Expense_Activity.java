package com.Duong.Expense.ExpenseActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.Duong.Expense.Database.MyDatabaseHelper;
import com.Duong.Expense.Object.Expense;
import com.Duong.Expense.Object.Trip;
import com.Duong.Expense.R;
import com.Duong.Expense.TripActivity.TripActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Add_Expense_Activity extends AppCompatActivity {
    //    TextInputEditText amount, note;
    EditText dateExpense, amount, note, des;

    Trip selectedTrip;

    Button add_button;
    Calendar calendar;

    Spinner spinnerType;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Intent intent = getIntent();
        selectedTrip = (Trip) intent.getSerializableExtra("selectedTrip");
//        Spinner spinner = (Spinner) findViewById(R.id.Expense_spinner);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
        dateExpense = findViewById(R.id.dateFromExpense);
        note = findViewById(R.id.Note);
        amount = findViewById(R.id.Amount);
        des = findViewById(R.id.ExpenseDestination);
        add_button = findViewById(R.id.add_button_expense);
        spinnerType = (Spinner) findViewById(R.id.Expense_spinner);

        final ArrayList<String> arrayType = new ArrayList<String>();
        arrayType.add("Hotel");
        arrayType.add("Food");
        arrayType.add("Transport");
        arrayType.add("Shopping");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(arrayAdapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Add_Expense_Activity.this, arrayType.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        calendar = Calendar.getInstance();

        //Date Picker for EditText Date
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
                dateExpense.setText(sdf.format(calendar.getTime()));
            }
        };
        //setOnClickListener to show date picker
        dateExpense.setOnClickListener(view -> new DatePickerDialog(Add_Expense_Activity.this, datePickerFrom, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());
        add_button = findViewById(R.id.add_button_expense);
        add_button.setOnClickListener(v -> checkCredentials());
//        add_button.setOnClickListener(view -> {
//        MyDatabaseHelper myDB = new MyDatabaseHelper(Add_Expense_Activity.this);
//
//        Expense expense = new Expense();
//        expense.setTypeExpense(spinnerType.getSelectedItem().toString().trim());
//        expense.setAmount(Float.parseFloat(amount.getText().toString().trim()));
//        expense.setDate(date.getText().toString().trim());
//        expense.setDestinationExpense(des.getText().toString().trim());
//        expense.setNote(note.getText().toString().trim());
//        expense.setTripID(selectedTrip.getId());
//
//        long result = myDB.addExpense(expense);
//        if (result == -1) {
//            Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getBaseContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(Add_Expense_Activity.this, ExpenseActivity.class));
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        }
//    });
}

    private void checkCredentials() {
        String type = spinnerType.getSelectedItem().toString().trim();
        String amountInput = amount.getText().toString().trim();
        String DateExpense = dateExpense.getText().toString().trim();

        if (type.isEmpty()) {
            showError(spinnerType);
            spinnerType.requestFocus();
        } else if (amountInput.isEmpty()) {
            showError(amount);
        } else if (DateExpense.isEmpty()) {
            showError(dateExpense);
        } else {
            addExpense();
        }
    }

    private void showError(Spinner spinnerType) {
//        spinnerType.set("This is a required field");
        spinnerType.requestFocus();
    }

    private void addExpense() {
        MyDatabaseHelper myDB = new MyDatabaseHelper(Add_Expense_Activity.this);
        Expense expense = new Expense();
        expense.setAmount(Float.valueOf(amount.getText().toString().trim()));
        expense.setDate(dateExpense.getText().toString().trim());
        expense.setNote(note.getText().toString().trim());
        expense.setDestinationExpense(des.getText().toString().trim());
        expense.setTypeExpense(spinnerType.getSelectedItem().toString().trim());
        expense.setTripID(selectedTrip.getId());

        long result = myDB.addExpense(expense);
        if (result == -1) {
            Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Add_Expense_Activity.this, ExpenseActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
    private void showError(EditText input) {
        input.setError("This is a required field");
        input.requestFocus();
    }
}

