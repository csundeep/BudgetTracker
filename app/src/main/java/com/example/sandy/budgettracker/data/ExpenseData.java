package com.example.sandy.budgettracker.data;

/**
 * Created by sandy on 04-03-2017.
 */
public class ExpenseData {

    private String expenseName;
    private double expenseAmount;
    private String note;
    private String expenseDate;

    public ExpenseData(String expenseName,double expenseAmount, String expenseDate, String note) {
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.note = note;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
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
                "expenseName='" + expenseName + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", note='" + note + '\'' +
                ", expenseDate='" + expenseDate + '\'' +
                '}';
    }
}
