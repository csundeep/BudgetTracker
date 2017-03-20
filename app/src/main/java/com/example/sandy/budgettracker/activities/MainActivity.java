package com.example.sandy.budgettracker.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.fragments.BudgetFragment;
import com.example.sandy.budgettracker.fragments.OverviewFragment;
import com.example.sandy.budgettracker.fragments.TransactionsFragment;
import com.example.sandy.budgettracker.fragments.WalletFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new TransactionsFragment());
        transaction.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.action_transactions:
                    selectedFragment = new TransactionsFragment();
                    break;
                case R.id.action_overview:
                    selectedFragment = new OverviewFragment();
                    break;
                case R.id.action_budget:
                    selectedFragment = new BudgetFragment();
                    break;
                case R.id.action_wallet:
                    selectedFragment = new WalletFragment();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        }

    };


}
