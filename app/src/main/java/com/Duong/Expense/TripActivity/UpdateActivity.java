//package com.Duong.Expense.TripActivity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.Duong.Expense.Database.MyDatabaseHelper;
//import com.Duong.Expense.Object.Trip;
//import com.Duong.Expense.R;
//
//import java.util.Calendar;
//
//
//public class UpdateActivity extends AppCompatActivity {
//
//    ImageView btnBack;
//    TextView actionBarText;
//    Calendar calendar;
//    EditText tripName, tripDestination, dateFrom, dateTo, description;
//    Trip selectedTrip;
//    RadioGroup radioGroup;
//    RadioButton rdYes, rdNo, selectedRadioButton;
//    Button btnSave;
//    Button update_button, delete_button;
//
//    Trip selectedTrip;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update);
//
//        title_input = findViewById(R.id.title_input2);
//        author_input = findViewById(R.id.author_input2);
//        pages_input = findViewById(R.id.pages_input2);
//        update_button = findViewById(R.id.update_button);
//        delete_button = findViewById(R.id.delete_button);
//
//        getAndDisplayInfo();
//
//        update_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
//
//                selectedTrip.setTitle(title_input.getText().toString().trim());
//                selectedTrip.setAuthor(author_input.getText().toString().trim());
//                selectedTrip.setPages(Integer.valueOf(pages_input.getText().toString().trim()));
//                long result = myDB.update(selectedTrip);
//            }
//        });
//        delete_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteAction();
//            }
//        });
//
//    }
//
//    void getAndDisplayInfo() {
//        Intent intent = getIntent();
//        selectedTrip = (Trip) intent.getSerializableExtra("selectedTrip");
//
//        //display in textview
//        title_input.setText(selectedTrip.getTitle());
//        author_input.setText(selectedTrip.getAuthor());
//        pages_input.setText(String.valueOf(selectedTrip.getPages()));
//
//        //display in action bar
//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setTitle(selectedTrip.getTitle());
//        }
//    }
//
//    void deleteAction() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Delete " + selectedTrip.getTitle() + " ?");
//        builder.setMessage("Are you sure you want to delete " + selectedTrip.getTitle() + " ?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
//                long result = myDB.delete(String.valueOf(selectedTrip.getId()));
//                if (result == -1) {
//                    Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getBaseContext(), "Delete Successfully!", Toast.LENGTH_SHORT).show();
//                }
//                onBackPressed();
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        builder.create().show();
//    }
//}
