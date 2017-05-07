package com.example.sandy.budgettracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.fragments.BudgetTransactionsFragment;

public class BudgetTransactionsActivity extends AppCompatActivity {

    private BudgetData selectedBudgetData;
    private double totalExpenseCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_transactions);

        Intent intent = getIntent();
        if (getIntent() != null) {
            selectedBudgetData = (BudgetData) intent.getExtras().get("selectedBudgetData");
            totalExpenseCount = (double) intent.getExtras().get("totalExpenses");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Fragment fragment = new BudgetTransactionsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putSerializable("selectedBudgetData", selectedBudgetData);
        args.putDouble("totalExpenses", totalExpenseCount);
        fragment.setArguments(args);

        transaction.replace(R.id.contentBudgetTransactions, fragment);
        transaction.commit();
    }
}
