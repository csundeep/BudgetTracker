package com.example.sandy.budgettracker.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.SimpleFragmentPagerAdapter;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.fragments.MonthFragment;
import com.example.sandy.budgettracker.helper.ExpensesContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab!=null)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add your expence", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                openExpenseActivity(view);
            }
        });

        getLoaderManager().initLoader(0, null, this);


    }

    private void openExpenseActivity(@SuppressWarnings("unused") View view) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public static Date min(Date a, Date b) {
        return a == null ? b : (b == null ? a : (a.before(b) ? a : b));
    }

    public static Date max(Date a, Date b) {
        return a == null ? b : (b == null ? a : (a.after(b) ? a : b));
    }


    private void displayDatabaseInfo(Cursor cursor) {
        Map<Date, List<ExpenseData>> expenses = new HashMap<>();
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
                new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date);
                dates.add(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date));
                if (expenses.containsKey(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date))) {
                    expenses.get(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date)).add(expenseData);
                } else {
                    List<ExpenseData> datas = new ArrayList<>();
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
                String date = new SimpleDateFormat("MMM-yyyy",Locale.US).format(beginCalendar.getTime()).toUpperCase();
                if (date.equalsIgnoreCase(new SimpleDateFormat("MMM-yyyy",Locale.US).format(finishCalendar.getTime()).toUpperCase()))
                    flag = false;
                tabs.add(date);
                Fragment monthFragment = new MonthFragment();
                String fragmentData = "";
                for (Map.Entry<Date, List<ExpenseData>> entry : expenses.entrySet()) {
                    if (date.equalsIgnoreCase(new SimpleDateFormat("MMM-yyyy",Locale.US).format(entry.getKey()))) {
                        for (ExpenseData data : entry.getValue()) {
                            fragmentData = fragmentData + "\n \n" + data.toString();
                        }
                    }
                }
                Bundle monthBundle = new Bundle();
                monthBundle.putString("Text", fragmentData);
                monthFragment.setArguments(monthBundle);
                fragments.add(monthFragment);
                beginCalendar.add(Calendar.MONTH, 1);
            }
        }


        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // Set the adapter onto the view pager
        if (viewPager != null)
            viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
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
        return new CursorLoader(this, ExpensesContract.ExpenseEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {


        displayDatabaseInfo(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
