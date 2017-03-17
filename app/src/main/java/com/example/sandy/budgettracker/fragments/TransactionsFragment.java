package com.example.sandy.budgettracker.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.ExpenseActivity;
import com.example.sandy.budgettracker.adapters.SimpleFragmentPagerAdapter;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.helper.ExpensesContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class TransactionsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transactions, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.transactionsToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.transactions);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Add your expence", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    openExpenseActivity(view);
                }
            });
        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    private void openExpenseActivity(@SuppressWarnings("unused") View view) {
        Intent intent = new Intent(this.getActivity(), ExpenseActivity.class);
        startActivity(intent);
    }

    private static Date min(Date a, Date b) {
        return a == null ? b : (b == null ? a : (a.before(b) ? a : b));
    }

    private static Date max(Date a, Date b) {
        return a == null ? b : (b == null ? a : (a.after(b) ? a : b));
    }

    private boolean containsDate(Map<Date, ArrayList<ExpenseData>> expenses, Date date) {
        boolean isThere = false;
        for (Map.Entry<Date, ArrayList<ExpenseData>> entry : expenses.entrySet()) {
            if (entry.getKey().getYear() == date.getYear() && entry.getKey().getMonth() == date.getMonth()) {
                isThere = true;
                break;
            }
        }

        return isThere;
    }

    private ArrayList<ExpenseData> getExistentKey(Map<Date, ArrayList<ExpenseData>> expenses, Date date) {
        ArrayList<ExpenseData> result = null;
        for (Map.Entry<Date, ArrayList<ExpenseData>> entry : expenses.entrySet()) {
            if (entry.getKey().getYear() == date.getYear() && entry.getKey().getMonth() == date.getMonth()) {
                result = entry.getValue();
                break;
            }
        }

        return result;
    }

    private void displayDatabaseInfo(Cursor cursor) {
        Map<Date, ArrayList<ExpenseData>> expenses = new HashMap<>();
        List<Date> dates = new ArrayList<>();
        List<String> tabs = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();

        try {
            int nameColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME);
            int amountColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT);
            int noteColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES);
            int dateColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE);
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumnIndex);
                double amount = cursor.getDouble(amountColumnIndex);
                String note = cursor.getString(noteColumnIndex);
                String date = cursor.getString(dateColumnIndex);
                ExpenseData expenseData = new ExpenseData(name, amount, date, note);
                dates.add(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date));
                if (containsDate(expenses, new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date))) {
                    getExistentKey(expenses, new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date)).add(expenseData);
                } else {
                    ArrayList<ExpenseData> datas = new ArrayList<>();
                    datas.add(expenseData);
                    expenses.put(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date), datas);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }


        if (dates.size() != 0) {
            Date minDate = dates.get(0);
            Date maxDate = dates.get(0);
            for (Date date : dates) {
                minDate = min(minDate, date);
                maxDate = max(maxDate, date);
            }
            Calendar beginCalendar = Calendar.getInstance();
            Calendar finishCalendar = Calendar.getInstance();
            beginCalendar.setTime(minDate);
            finishCalendar.setTime(maxDate);
            boolean flag = true;


            while (beginCalendar.before(finishCalendar) || flag) {
                String date = new SimpleDateFormat("MMM-yyyy", Locale.US).format(beginCalendar.getTime()).toUpperCase();
                if (date.equalsIgnoreCase(new SimpleDateFormat("MMM-yyyy", Locale.US).format(finishCalendar.getTime()).toUpperCase()))
                    flag = false;
                tabs.add(new SimpleDateFormat("MMMM yyyy", Locale.US).format(beginCalendar.getTime()).toUpperCase());
                Fragment monthFragment = new MonthFragment();
                Bundle monthBundle = new Bundle();
                for (Map.Entry<Date, ArrayList<ExpenseData>> entry : expenses.entrySet()) {
                    if (date.equalsIgnoreCase(new SimpleDateFormat("MMM-yyyy", Locale.US).format(entry.getKey()))) {
//                        Log.v("@@@@@@@@@@@@@@@ ", date + " " + entry.getKey());
//                        for (ExpenseData expenseData : entry.getValue()) {
//                            Log.v("    !!!!!!!!!!!", expenseData.toString());
//                        }
                        monthBundle.putParcelableArrayList("expensedatas", entry.getValue());
                        break;
                    }

                }
                monthFragment.setArguments(monthBundle);
                fragments.add(monthFragment);
                beginCalendar.add(Calendar.MONTH, 1);
            }
        }


        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this.getChildFragmentManager(), fragments, tabs);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        // Set the adapter onto the view pager
        if (viewPager != null) {
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(viewPager.getAdapter().getCount());

        }

        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        if (tabsStrip != null && viewPager != null)
            tabsStrip.setViewPager(viewPager);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ExpensesContract.ExpenseEntry._Id,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE};
        return new CursorLoader(this.getActivity(), ExpensesContract.ExpenseEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {


        displayDatabaseInfo(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
