package com.example.sandy.budgettracker.fragments;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.SimpleFragmentPagerAdapter;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.data.ExpenseItem;
import com.example.sandy.budgettracker.helper.ExpensesContract;
import com.example.sandy.budgettracker.helper.Session;
import com.example.sandy.budgettracker.helper.UserContract;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

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

        ExpenseData selectedExpenseData = null;
        if (getArguments().getSerializable("selectedExpenseData") != null)
            selectedExpenseData = (ExpenseData) getArguments().getSerializable("selectedExpenseData");


        ImageButton b = (ImageButton) getActivity().findViewById(R.id.addExpense);
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
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs1);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getContext(), R.color.grey_200),
                ContextCompat.getColor(getContext(), R.color.white)
        );

        // Attach the view pager to the tab strip
        if (viewPager != null) {
            tabLayout.setupWithViewPager(viewPager);

//            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                public void onPageScrollStateChanged(int state) {
//                }
//
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//
//                }
//
//                public void onPageSelected(int position) {
//                    double d = 0d;
//                    EditText editText = (EditText) getActivity().findViewById(R.id.amount);
//                    if (!editText.getText().toString().equals(""))
//                        d = Double.parseDouble(editText.getText().toString());
//                    if (position == 0) {
//                        if (d > 0) {
//                            d = d * -1;
//                        }
//                    } else {
//                        if (d < 0) {
//                            d = d * -1;
//                        }
//                    }
//                    editText.setText(Double.valueOf(d).toString());
//                }
//            });
        }
        return view;
    }

}
