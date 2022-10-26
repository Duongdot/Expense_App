package com.Duong.Expense.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.Duong.Expense.Object.Expense;
import com.Duong.Expense.Object.Trip;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "TripManagement.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COLUMN_ID = "id";
    private static final String TABLE_NAME = "Trip";
    private static final String COLUMN_NAME = "TripName";
    private static final String COLUMN_DESTINATION = "TripDestination";
    private static final String COLUMN_DATE_FROM = "TripDateFrom";
    private static final String COLUMN_DATE_TO = "TripDateTo";
    private static final String COLUMN_RISK = "Risk";
    private static final String COLUMN_DESC = "TripDesc";
    // Expense
    public static final String Expense_ID_COLUMN = "ExpenseId";
    private static final String TABLE_NAME_Expense = "Expense";
    private static final String COLUMN_TYPE = "expenseType";
    public static final String AMOUNT_COLUMN = "Amount";
    public static final String DATE_COLUMN = "Date";
    public static final String COMMENT_COLUMN = "Note";
    public static final String LOCATION_COLUMN = "ExpenseDestination";
//    public static final String IMAGE_COLUMN = "image";
    public static final String TRIP_ID_COLUMN = "trip_Id";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTablesExpense(db);
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropAndRecreate(db);
    }

    private void dropAndRecreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void createTables(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESTINATION + " TEXT, " +
                COLUMN_DATE_FROM + " TEXT, " +
                COLUMN_DATE_TO + " TEXT, " +
                COLUMN_RISK + " INTEGER, " +
                COLUMN_DESC + " TEXT);";
        db.execSQL(query);
    }

    private void createTablesExpense(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + Expense_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AMOUNT_COLUMN + " Float, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_DESTINATION + " TEXT, " +
                DATE_COLUMN + " DATE, " +
                COMMENT_COLUMN + " TEXT, " +
                LOCATION_COLUMN + " TEXT, " +
                COLUMN_DESC + " TEXT," +
                TRIP_ID_COLUMN + " INTEGER, " +
                " FOREIGN KEY ("+TRIP_ID_COLUMN+") REFERENCES "+TABLE_NAME+"("+COLUMN_ID+"));";
        db.execSQL(query);
    }

    public long addTrip(Trip trip) {
        long insertId;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, trip.getName());
        values.put(COLUMN_DESTINATION, trip.getDes());
        values.put(COLUMN_DATE_FROM, trip.getDateFrom());
        values.put(COLUMN_DATE_TO, trip.getDateTo());
        values.put(COLUMN_RISK, trip.getRisk());
        values.put(COLUMN_DESC, trip.getDesc());

        // Inserting Row
        insertId = db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return insertId;
    }

    public long addExpense(Expense expense) {
        long insertId;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, expense.getTypeExpense());
        values.put(AMOUNT_COLUMN, expense.getAmount());
        values.put(DATE_COLUMN, expense.getDate());
        values.put(LOCATION_COLUMN, expense.getNote());
        values.put(COMMENT_COLUMN, expense.getNote());

        // Inserting Row
        insertId = db.insert(TABLE_NAME_Expense, null, values);
        db.close(); // Closing database connection
        return insertId;
    }

    public List<Trip> getAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        final List<Trip> list = new ArrayList<>();
        final Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Trip trip = new Trip();
                    trip.setId(cursor.getInt(0));
                    trip.setName(cursor.getString(1));
                    trip.setDes(cursor.getString(2));
                    trip.setDateFrom(cursor.getString(3));
                    trip.setDateTo(cursor.getString(4));
                    trip.setRisk(cursor.getString(5));
                    trip.setDesc(cursor.getString(6));
                    // Adding object to list
                    list.add(trip);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return list;
    }
    public List<Expense> getAllExpense() {
        final String query = "SELECT * FROM " + TABLE_NAME_Expense;
        SQLiteDatabase db = this.getReadableDatabase();
        final List<Expense> list = new ArrayList<>();
        final Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Expense expense = new Expense();
                    expense.setId(cursor.getInt(0));
                    expense.setTypeExpense(cursor.getString(1));
                    expense.setDate(cursor.getString(2));
                    expense.setNote(cursor.getString(3));
                    expense.setAmount(Float.valueOf(cursor.getString(4)));
                    // Adding object to list
                    list.add(expense);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return list;
    }
    public Float getTotalExpense(String id){
        Float total = 0f;
        String query = "SELECT " + AMOUNT_COLUMN + " FROM " + TABLE_NAME_Expense + " WHERE " + TRIP_ID_COLUMN + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();

        final Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    total += cursor.getFloat(0);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return total;
    }

    public long update(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, trip.getName());
        values.put(COLUMN_DESTINATION, trip.getDes());
        values.put(COLUMN_DATE_FROM, trip.getDateFrom());
        values.put(COLUMN_DATE_TO, trip.getDateTo());
        values.put(COLUMN_RISK, trip.getRisk());
        values.put(COLUMN_DESC, trip.getDesc());

        return db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(trip.getId())});
    }

    public long updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, expense.getTypeExpense());
        values.put(AMOUNT_COLUMN, expense.getAmount());
        values.put(DATE_COLUMN, expense.getDate());
        values.put(LOCATION_COLUMN, expense.getNote());
        values.put(COMMENT_COLUMN, expense.getNote());

        return db.update(TABLE_NAME_Expense, values, "id=?", new String[]{String.valueOf(expense.getId())});
    }

    public long delete(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id=?", new String[]{row_id});
    }

    public long deleteExpense(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_Expense, "id=?", new String[]{row_id});
    }


    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        dropAndRecreate(db);
    }

}
