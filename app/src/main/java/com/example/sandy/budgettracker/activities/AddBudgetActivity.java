package com.example.sandy.budgettracker.activities;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.contracts.BudgetsContract;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.util.Session;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddBudgetActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor> {
    private TextView dateTextView;
    private DatePickerDialog datePickerDialog;
    private String period = "Month";
    private BudgetData budgetData;
    private Uri currentBudgetUri;

    private TextView budgetExpensesTextView;
    private EditText budgetNameEditText;
    private EditText budgetAmountEditText;
    private SwitchCompat notificationsSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        Intent intent = getIntent();
        currentBudgetUri = intent.getData();

        if (currentBudgetUri != null)
            getLoaderManager().initLoader(0, null, this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (currentBudgetUri == null)
                getSupportActionBar().setTitle(R.string.add_budget_title);
            else
                getSupportActionBar().setTitle(R.string.edit_budget_title);
        }


        ViewGroup dateViewGroup = (ViewGroup) findViewById(R.id.selectDate);
        this.budgetExpensesTextView = (TextView) findViewById(R.id.budgetExpenses);
        this.budgetNameEditText = (EditText) findViewById(R.id.addbudgetName);
        this.budgetAmountEditText = (EditText) findViewById(R.id.addbudgetAmount);
        this.notificationsSwitch = (SwitchCompat) findViewById(R.id.notificationsSwitch);
        this.dateTextView = (TextView) findViewById(R.id.dateStartBudget);
        this.dateTextView.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(new Date()));

        if (budgetData == null)
            budgetData = new BudgetData();

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

                String expenses = budgetExpensesTextView.getText().toString();
                String startDate = dateTextView.getText().toString();
                boolean isNotificationRequired = notificationsSwitch.isChecked();
                String name = budgetNameEditText.getText().toString();
                double amount = 0;
                if (!budgetAmountEditText.getText().toString().equals(""))
                    amount = Double.parseDouble(budgetAmountEditText.getText().toString());
                String endDate = getEndDate(startDate);


                budgetData.setBudgetName(name);
                budgetData.setBudgetAmount(amount);
                budgetData.setExpenses(expenses);
                budgetData.setStartDate(startDate);
                budgetData.setEndDate(endDate);
                budgetData.setNotify((isNotificationRequired) ? 1 : 0);

                if (budgetData.getId() == 0) {
                    insertBudget(budgetData);
                } else {
                    updateBudget(budgetData);
                }
            }
        });


    }

    private void insertBudget(BudgetData budgetData) {

        if (budgetData.getBudgetName() == null || budgetData.getBudgetName().equals("")) {
            Toast.makeText(this, "You have to select an expense item", Toast.LENGTH_LONG).show();
        } else if (budgetData.getBudgetAmount() == 0) {
            Toast.makeText(this, "Amount should not be zero", Toast.LENGTH_LONG).show();
        }

        ContentValues values = new ContentValues();
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME, budgetData.getBudgetName());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT, budgetData.getBudgetAmount());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES, budgetData.getExpenses());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE, budgetData.getStartDate());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE, budgetData.getEndDate());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS, budgetData.getNotify());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_USER_ID, new Session(getBaseContext()).getuserId());
        Uri uri = getContentResolver().insert(BudgetsContract.BudgetsEntry.CONTENT_URI, values);

        if (uri == null) {
            Toast.makeText(this, R.string.editor_save_budget_failed,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.editor_save_budget_successful,
                    Toast.LENGTH_SHORT).show();
        }
        finish();
        Intent homepage = new Intent(this, MainActivity.class);
        startActivity(homepage);

    }

    public void updateBudget(BudgetData budgetData) {
        Uri currentExpenseURI = ContentUris.withAppendedId(BudgetsContract.BudgetsEntry.CONTENT_URI, budgetData.getId());


        ContentValues values = new ContentValues();
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME, budgetData.getBudgetName());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT, budgetData.getBudgetAmount());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE, budgetData.getStartDate());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE, budgetData.getEndDate());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES, budgetData.getExpenses());
        values.put(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS, budgetData.getNotify());
        int rowsAffected = this.getContentResolver().update(currentExpenseURI, values, null, null);

        if (rowsAffected == 0) {
            Toast.makeText(this, getString(R.string.editor_update_budget_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.editor_update_budget_successful),
                    Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
//        getFragmentManager().popBackStack();
//        Intent homepage = new Intent(this, MainActivity.class);
//        homepage.putExtra("frag", "budget");
//        startActivity(homepage);
    }

    private String getEndDate(String startDate) {
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
        return endDate;
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                BudgetsContract.BudgetsEntry._ID,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this, currentBudgetUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        try {

            int idColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry._Id);
            int budgetNameColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME);
            int budgetAmountColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT);
            int expensesColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES);
            int startDateColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE);
            int endDateColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE);
            int notificationsColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String budgetName = cursor.getString(budgetNameColumnIndex);
                double budgetAmount = cursor.getDouble(budgetAmountColumnIndex);
                String expenses = cursor.getString(expensesColumnIndex);
                String startDate = cursor.getString(startDateColumnIndex);
                String endDate = cursor.getString(endDateColumnIndex);
                int notification = cursor.getInt(notificationsColumnIndex);
                this.budgetData = new BudgetData(id, budgetName, budgetAmount, expenses, startDate, endDate, notification);
            }

            this.budgetNameEditText.setText(budgetData.getBudgetName());
            this.budgetAmountEditText.setText(String.valueOf(budgetData.getBudgetAmount()));
            this.budgetExpensesTextView.setText(budgetData.getExpenses());
            this.dateTextView.setText(budgetData.getStartDate());
            this.notificationsSwitch.setChecked(budgetData.getNotify() != 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
