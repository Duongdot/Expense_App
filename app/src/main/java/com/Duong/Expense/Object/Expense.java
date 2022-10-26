package com.Duong.Expense.Object;

import java.io.Serializable;

public class Expense implements Serializable {
    private int id;
    private String TypeExpense;
    private String DestinationExpense;
    private String Date;
    private Float Amount;
    private String Note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeExpense() {
        return TypeExpense;
    }

    public void setTypeExpense(String type) {
        this.TypeExpense = type;
    }
    public String getDestinationExpense() {
        return DestinationExpense;
    }

    public void setDestinationExpense(String des) {
        this.DestinationExpense = des;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        this.Amount = amount;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        this.Note = note;
    }


    public Expense(int id, String type, String des, String date, String note, float amount) {
        this.id = id;
        this.TypeExpense = type;
        this.DestinationExpense = des;
        this.Date = date;
        this.Note = note;
        this.Amount = amount;
    }

    public Expense(String type, String des, String date, String note, float amount) {
        this.TypeExpense = type;
        this.DestinationExpense = des;
        this.Date = date;
        this.Note = note;
        this.Amount = amount;
    }

    public Expense() {

    }


    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + TypeExpense + '\'' +
                ", des='" + DestinationExpense + '\'' +
                ", dateFrom='" + Date + '\'' +
                ", dateTo='" + Note + '\'' +
                ", risk='" + Amount + '\'' +
                '}';
    }
}