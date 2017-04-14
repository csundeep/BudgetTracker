package com.example.sandy.budgettracker.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.fragments.ExpenseDetailFragment;
import com.example.sandy.budgettracker.fragments.ExpenseSelectionFragment;
import com.example.sandy.budgettracker.helper.ExpensesContract;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence);

        Toolbar toolbar = (Toolbar) findViewById(R.id.expenseToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ExpenseData selectedExpenceData = null;

        Intent intent = getIntent();
        if (intent.getData() != null) {
            Uri currentExpenseUri = intent.getData();


            String[] projection = {
                    ExpensesContract.ExpenseEntry._Id,
                    ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME,
                    ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE,
                    ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT,
                    ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES,
                    ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE};

            Cursor expenseCursor = this.getContentResolver().query(currentExpenseUri, projection, null, null, null);
            if (expenseCursor != null) {
                try {
                    int expenseIdIndex = expenseCursor.getColumnIndex(ExpensesContract.ExpenseEntry._Id);
                    int expenseNameIndex = expenseCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME);
                    int expenseTypeIndex = expenseCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE);
                    int expenseAmountIndex = expenseCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT);
                    int expenseNoteIndex = expenseCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES);
                    int expenseDateIden = expenseCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE);

                    while (expenseCursor.moveToNext()) {
                        int id = expenseCursor.getInt(expenseIdIndex);
                        String name = expenseCursor.getString(expenseNameIndex);
                        String type = expenseCursor.getString(expenseTypeIndex);
                        double amount = expenseCursor.getDouble(expenseAmountIndex);
                        String note = expenseCursor.getString(expenseNoteIndex);
                        String date = expenseCursor.getString(expenseDateIden);
                        selectedExpenceData = new ExpenseData(id, name, type, amount, date, note);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    expenseCursor.close();
                }
            }
        }


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("selectedExpenseData", selectedExpenceData);

        if (selectedExpenceData == null) {
            ExpenseSelectionFragment expenseSelectionFragment = new ExpenseSelectionFragment();
            expenseSelectionFragment.setArguments(args);
            transaction.replace(R.id.contentExpense, expenseSelectionFragment);
            transaction.commit();
        } else {

            ExpenseDetailFragment expenseDetailFragment = new ExpenseDetailFragment();
            expenseDetailFragment.setArguments(args);
            transaction.replace(R.id.contentExpense, expenseDetailFragment);
            transaction.commit();
        }

    }

}
