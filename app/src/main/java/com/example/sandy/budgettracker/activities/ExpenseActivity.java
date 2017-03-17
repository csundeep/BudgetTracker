package com.example.sandy.budgettracker.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;
import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.SimpleFragmentPagerAdapter;
import com.example.sandy.budgettracker.fragments.ExpenseFragment;
import com.example.sandy.budgettracker.fragments.ExpenseSelectionFragment;
import com.example.sandy.budgettracker.fragments.TransactionsFragment;

import java.util.ArrayList;
import java.util.List;

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

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentExpense, new ExpenseSelectionFragment());
        transaction.commit();


    }

}
