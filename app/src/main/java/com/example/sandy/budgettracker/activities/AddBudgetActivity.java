package com.example.sandy.budgettracker.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.fragments.BudgetCreationFragment;

public class AddBudgetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        Intent intent = getIntent();
        Uri currentBudgetUri = intent.getData();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (currentBudgetUri == null)
                getSupportActionBar().setTitle(R.string.add_budget_title);
            else
                getSupportActionBar().setTitle(R.string.edit_budget_title);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new BudgetCreationFragment();
        Bundle args = new Bundle();
        args.putParcelable("currentBudgetUri", currentBudgetUri);
        fragment.setArguments(args);
        transaction.replace(R.id.contentBudget, fragment);
        transaction.commit();


    }


}
