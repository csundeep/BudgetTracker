package com.example.sandy.budgettracker.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.SimpleFragmentPagerAdapter;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.util.ArrayList;
import java.util.List;

public class ExpenseSelectionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_selection, container, false);

        ViewPager viewPager =  view.findViewById(R.id.expense_view_pager);
        List<String> tabs = new ArrayList<>();
        tabs.add("EXPENSES");
        tabs.add("INCOME");

        ExpenseData selectedExpenseData = null;
        if (getArguments().getSerializable("selectedExpenseData") != null)
            selectedExpenseData = (ExpenseData) getArguments().getSerializable("selectedExpenseData");


        ImageButton b =  getActivity().findViewById(R.id.addExpense);
        b.setImageResource(R.drawable.ic_action_forward);

        Fragment f1 = new ExpenseFragment();
        Bundle b1 = new Bundle();
        b1.putString("type", "Expense");
        b1.putSerializable("selectedExpenseData", selectedExpenseData);
        f1.setArguments(b1);

        Fragment f2 = new ExpenseFragment();
        Bundle b2 = new Bundle();
        b2.putString("type", "Income");
        b2.putSerializable("selectedExpenseData", selectedExpenseData);
        f2.setArguments(b2);


        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(f1);
        fragments.add(f2);


        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getChildFragmentManager(), fragments, tabs);
        if (viewPager != null)
            viewPager.setAdapter(adapter);
        TabLayout tabLayout =  getActivity().findViewById(R.id.expense_tabs);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getContext(), R.color.grey_200),
                ContextCompat.getColor(getContext(), R.color.white)
        );

        EditText amountET = getActivity().findViewById(R.id.amount);
        amountET.setSelection(amountET.getText().length());
        amountET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                EditText et=(EditText) view;
                et.setSelection(et.getText().length());
            }
        });

        // Attach the view pager to the tab strip
        if (viewPager != null) {
            tabLayout.setupWithViewPager(viewPager);


        }
        return view;
    }



}
