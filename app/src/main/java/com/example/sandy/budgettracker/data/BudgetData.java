package com.example.sandy.budgettracker.data;

public class BudgetData {
    private int id;
    private String budgetName;
    private double budgetAmount;
    private String expenses;
    private String startDate;
    private String endDate;
    private int notify;
    private double totalExpenses;

    public BudgetData() {

    }

    public BudgetData(int id, String budgetName, double budgetAmount, String expenses, String startDate, String endDate, int notify) {
        this.id = id;
        this.budgetName = budgetName;
        this.budgetAmount = budgetAmount;
        this.expenses = expenses;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notify = notify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    @Override
    public String toString() {
        return "BudgetData{" +
                "id=" + id +
                ", budgetName='" + budgetName + '\'' +
                ", budgetAmount=" + budgetAmount +
                ", expenses='" + expenses + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", notify=" + notify +
                ", totalExpenses=" + totalExpenses +
                '}';
    }
}
