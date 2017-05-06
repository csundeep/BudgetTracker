package com.example.sandy.budgettracker.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.fragments.BudgetCreationFragment;

public class AddBudgetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        Intent intent = getIntent();
        Uri currentBudgetUri = intent.getData();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new BudgetCreationFragment();

        if (currentBudgetUri != null) {
            Bundle args = new Bundle();
            args.putString("currentBudgetUri", currentBudgetUri.toString());
            fragment.setArguments(args);
        }
        transaction.replace(R.id.contentBudget, fragment);
        transaction.commit();


    }


}
