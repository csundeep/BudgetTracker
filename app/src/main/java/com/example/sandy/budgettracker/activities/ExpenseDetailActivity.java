package com.example.sandy.budgettracker.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseItem;
import com.example.sandy.budgettracker.helper.ExpensesContract;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpenseDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private DatePickerDialog datePickerDialog;
    private TextView dateTextView;
    ImageView appBarDetailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        ExpenseItem expenseItem=(ExpenseItem) getIntent().getSerializableExtra("selectedExpenseItem");
        Log.v("@@@@@@@@@@@@@@@@@@@@@ "," "+getIntent().getSerializableExtra("selectedExpenseItem"));
        double amount = intent.getDoubleExtra("amount", 0);

        appBarDetailImageView = (ImageView)findViewById(R.id.appBarExpenseDetailImage);
//        appBarDetailImageView.setImageResource(intent.getIntExtra("imageContentId",0));
        EditText amountEditText = (EditText) findViewById(R.id.amountFinal);
        amountEditText.setText(new Double(amount).toString());
        this.dateTextView = (TextView) findViewById(R.id.date);
        this.dateTextView.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(new Date()));

        ViewGroup dateViewGroup = (ViewGroup) findViewById(R.id.calender);
        if (dateViewGroup != null)
            dateViewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    String dayOfWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

                    datePickerDialog = DatePickerDialog.newInstance(ExpenseDetailActivity.this, year, month, day);
                    datePickerDialog.setThemeDark(false);
                    datePickerDialog.showYearPickerFirst(false);
                    datePickerDialog.setAccentColor(Color.parseColor("#009688"));
                    datePickerDialog.setTitle(dayOfWeek);
                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                }
            });
        ImageButton b = (ImageButton) findViewById(R.id.submitExpense);
        if (b != null)
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView amountTextView = (TextView) findViewById(R.id.amountFinal);
                    double amount = Double.parseDouble(amountTextView.getText().toString());
                    EditText notesEditText = (EditText) findViewById(R.id.comments);
                    String notes = notesEditText.getText().toString();
                    TextView calenderTextView = (TextView) findViewById(R.id.date);
                    String date = calenderTextView.getText().toString();
                    storeExpense(v, getIntent().getStringExtra("expenseItem"), amount, notes, date);
                }
            });

    }

    public void storeExpense(@SuppressWarnings("unused") View view, String expenseItem, double amount, String notes, String date) {
        ContentValues values = new ContentValues();
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME, expenseItem);
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT, amount);
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES, notes);
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE, date);

        Uri uri = getContentResolver().insert(ExpensesContract.ExpenseEntry.CONTENT_URI, values);

        if (uri == null) {
            Toast.makeText(this, "Unable to add expense",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Expense added successful",
                    Toast.LENGTH_SHORT).show();
        }
        finish();
        Intent homepage = new Intent(this, MainActivity.class);
        startActivity(homepage);
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
