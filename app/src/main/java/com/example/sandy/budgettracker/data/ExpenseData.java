package com.example.sandy.budgettracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class ExpenseData implements Parcelable, Serializable {

    private int id;
    private String expenseName;
    private String expenseType;
    private double expenseAmount;
    private String note;
    private String expenseDate;

    public ExpenseData() {

    }

    public ExpenseData(int id, String expenseName, String expenseType, double expenseAmount, String expenseDate, String note) {
        this.id = id;
        this.expenseName = expenseName;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.note = note;
    }

    public ExpenseData(Parcel in) {
        super();
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Override
    public String toString() {
        return "ExpenseData{" +
                "id=" + id +
                ", expenseName='" + expenseName + '\'' +
                ", expenseType='" + expenseType + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", note='" + note + '\'' +
                ", expenseDate='" + expenseDate + '\'' +
                '}';
    }

    public static final Parcelable.Creator<ExpenseData> CREATOR = new Parcelable.Creator<ExpenseData>() {
        public ExpenseData createFromParcel(Parcel in) {
            return new ExpenseData(in);
        }

        public ExpenseData[] newArray(int size) {

            return new ExpenseData[size];
        }

    };

    public void readFromParcel(Parcel in) {

        expenseName = in.readString();
        expenseAmount = in.readDouble();
        note = in.readString();
        expenseDate = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(expenseName);
        parcel.writeDouble(expenseAmount);
        parcel.writeString(note);
        parcel.writeString(expenseDate);
    }
}
