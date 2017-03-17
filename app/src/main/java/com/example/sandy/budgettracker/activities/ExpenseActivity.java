package com.example.sandy.budgettracker.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;
import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.SimpleFragmentPagerAdapter;
import com.example.sandy.budgettracker.fragments.ExpenseFragment;

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager1);
        List<String> tabs = new ArrayList<>();
        tabs.add("EXPENSES");
        tabs.add("INCOME");


        Fragment f1 = new ExpenseFragment();
        Bundle b1 = new Bundle();
        b1.putString("type", "Expense");
        f1.setArguments(b1);

        Fragment f2 = new ExpenseFragment();
        Bundle b2 = new Bundle();
        b2.putString("type", "Income");
        f2.setArguments(b2);


        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(f1);
        fragments.add(f2);


        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, tabs);
        if (viewPager != null)
            viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs1);
        // Attach the view pager to the tab strip
        if (tabsStrip != null && viewPager != null)
            tabsStrip.setViewPager(viewPager);

    }

}
