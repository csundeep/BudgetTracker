package com.example.sandy.budgettracker.activities;

import android.content.Intent;
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
import com.example.sandy.budgettracker.fragments.ProfileFragment;
import com.example.sandy.budgettracker.fragments.TransactionsFragment;
import com.example.sandy.budgettracker.util.BottomNavigationViewHelper;
import com.example.sandy.budgettracker.util.Session;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment = new TransactionsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Session session = new Session(getBaseContext());
//        Log.v("@@@@@@@@@@@@@", "  " + session.getuserId());
//        session.logout();
//        Log.v("@@@@@@@@@@@@@", "  " + session.getuserId());
        if (session.getuserId() == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_transactions:
                    fragment = new TransactionsFragment();
                    break;
                case R.id.action_overview:
                    fragment = new OverviewFragment();
                    break;
                case R.id.action_budget:
                    fragment = new BudgetFragment();
                    break;
                case R.id.action_wallet:
                    fragment = new ProfileFragment();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
            return true;
        }

    };


}
