package com.example.sandy.budgettracker.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.fragments.ExpenseSelectionFragment;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence);

        Toolbar toolbar = (Toolbar) findViewById(R.id.expenseToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Uri currentExpenseUri = intent.getData();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putParcelable("currentExpenseUri", currentExpenseUri);
        ExpenseSelectionFragment expenseSelectionFragment = new ExpenseSelectionFragment();
        expenseSelectionFragment.setArguments(args);
        transaction.replace(R.id.contentExpense, expenseSelectionFragment);
        transaction.commit();


    }

}
