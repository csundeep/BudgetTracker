package com.example.sandy.budgettracker.data;

import java.util.Date;


public class BudgetData {

    private String budgetName;
    private double budgetAmount;
    private Date startDate;
    private Date endDate;

    public BudgetData() {

    }

    public BudgetData(String budgetName, double budgetAmount, Date startDate, Date endDate) {
        super();
        this.budgetName = budgetName;
        this.budgetAmount = budgetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
