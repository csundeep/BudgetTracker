package com.example.sandy.budgettracker.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.SimpleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpenseSelectionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_selection, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager1);
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
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getChildFragmentManager(), fragments, tabs);
        if (viewPager != null)
            viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs1);
        
        // Attach the view pager to the tab strip
        if (tabLayout != null && viewPager != null)
            tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
