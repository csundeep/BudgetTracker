package com.example.sandy.budgettracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ExpenseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        Intent intent = getIntent();
        double amount = intent.getDoubleExtra("amount", 0);
        String expenseItem = intent.getStringExtra("expenseItem");

    }
}
