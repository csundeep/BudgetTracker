package com.example.sandy.budgettracker.activities;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.contracts.BudgetsContract;
import com.example.sandy.budgettracker.util.Session;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddBudgetActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView dateTextView;
    private DatePickerDialog datePickerDialog;
    private String period = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.add_budget);
        }
        this.dateTextView = (TextView) findViewById(R.id.dateStartBudget);
        this.dateTextView.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(new Date()));

        ViewGroup dateViewGroup = (ViewGroup) findViewById(R.id.selectDate);

        if (dateViewGroup != null)
            dateViewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    String dayOfWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

                    datePickerDialog = DatePickerDialog.newInstance(AddBudgetActivity.this, year, month, day);
                    datePickerDialog.setThemeDark(false);
                    datePickerDialog.showYearPickerFirst(false);
                    datePickerDialog.setAccentColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                    datePickerDialog.setTitle(dayOfWeek);
                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                }
            });


        TextView budgetExpensesTextView = (TextView) findViewById(R.id.budgetExpenses);
        EditText budgetNameEditText = (EditText) findViewById(R.id.addbudgetName);
        EditText budgetAmountEditText = (EditText) findViewById(R.id.addbudgetAmount);
        SwitchCompat notificationsSwitch = (SwitchCompat) findViewById(R.id.notificationsSwitch);


        final String name = budgetNameEditText.getText().toString();
        final Double amount = Double.parseDouble(budgetAmountEditText.getText().toString());
        final String expenses = budgetExpensesTextView.getText().toString();
        final String date = this.dateTextView.getText().toString();
        final boolean isNotificationRequired = notificationsSwitch.isChecked();


        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinnerPeriod);
        spinner.setItems("Month", "year", "Half Yearly", "Quarterly");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                period = item;
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        final FloatingActionButton addBudget = (FloatingActionButton) findViewById(R.id.addBudget);
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBudget(name, amount, expenses, date, isNotificationRequired);
            }
        });


    }

    private void insertBudget(String name, double amount, String expenses, String startDate, boolean isNotificationRequired) {
        String endDate;
        Calendar beginCalendar = Calendar.getInstance();
        try {
            beginCalendar.setTime(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (period) {
            case "Month": {
                beginCalendar.add(Calendar.MONTH, 1);
                break;
            }
            case "year": {
                beginCalendar.add(Calendar.MONTH, 12);
                break;
            }
            case "Half Yearly": {
                beginCalendar.add(Calendar.MONTH, 6);
                break;
            }
            case "Quarterly": {
                beginCalendar.add(Calendar.MONTH, 3);
                break;
            }
        }
        endDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(beginCalendar.getTime());

        ContentValues values = new ContentValues();
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME, name);
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME, amount);
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME, startDate);
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME, endDate);
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME, isNotificationRequired);
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_USER_ID, new Session(getBaseContext()).getuserId());
        Uri uri = getContentResolver().insert(BudgetsContract.BudgetsEntry.CONTENT_URI, values);

        int budgetId = Integer.parseInt(uri.getLastPathSegment());
        if (budgetId != 0) {
            if (expenses.equals("All Expenses")) {

            } else {
                
            }
        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date d = cal.getTime();
        dateTextView.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(d));
    }

}
