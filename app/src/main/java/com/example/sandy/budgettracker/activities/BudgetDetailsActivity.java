package com.example.sandy.budgettracker.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.contracts.BudgetsContract;
import com.example.sandy.budgettracker.contracts.ExpensesContract;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.util.CustomProgress;
import com.example.sandy.budgettracker.util.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BudgetDetailsActivity extends AppCompatActivity {
    private BudgetData selectedBudgetData;
    private double totalExpenseCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_details);

        Intent intent = getIntent();
        Uri currentBudgetUri = intent.getData();

        ImageButton backIB = (ImageButton) findViewById(R.id.backToBudgetList);
        TextView budgetTitleTV = (TextView) findViewById(R.id.budgetTitle);
        TextView budgetPeriodTV = (TextView) findViewById(R.id.budgetPeriod);
        ImageButton transListIB = (ImageButton) findViewById(R.id.listBudgetTransactions);

        TextView line1TV = (TextView) findViewById(R.id.line1);
        TextView averageAmountTV = (TextView) findViewById(R.id.averageAmount);
        TextView line3TV = (TextView) findViewById(R.id.line3);
        CustomProgress customProgressShowProgress = (CustomProgress) findViewById(R.id.totalBudgetProgress);
        TextView startDateTV = (TextView) findViewById(R.id.budgetDetailStartDate);
        TextView endDateTV = (TextView) findViewById(R.id.budgetDetailEndDate);

        TextView spentTV = (TextView) findViewById(R.id.spent);
        TextView totalTV = (TextView) findViewById(R.id.totalBudget);

        if (currentBudgetUri != null) {
            String[] projection = {
                    BudgetsContract.BudgetsEntry._ID,
                    BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME,
                    BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT,
                    BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES,
                    BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE,
                    BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE,
                    BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS};
            Cursor cursor = getContentResolver().query(currentBudgetUri, projection, null, null, null);

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
                    this.selectedBudgetData = new BudgetData(id, budgetName, budgetAmount, expenses, startDate, endDate, notification);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        if (selectedBudgetData != null) {
            totalExpenseCount = calculateTotalExpenses(selectedBudgetData.getExpenses(), selectedBudgetData.getStartDate(), selectedBudgetData.getEndDate());

            if (backIB != null)
                backIB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            budgetTitleTV.setText(selectedBudgetData.getBudgetName());

            budgetPeriodTV.setText("Month");

            if (transListIB != null)
                transListIB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                    }
                });

            float per = (float) totalExpenseCount / (float) selectedBudgetData.getBudgetAmount();

            long diff = 31;
            try {
                diff = new Date().getTime() - new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(selectedBudgetData.getStartDate()).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }


            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            double averageAmount = (selectedBudgetData.getBudgetAmount() - totalExpenseCount) / (31 - days);

            if (per * 100 < 100) {
                line1TV.setText("Keep spending. You cab spend");
                averageAmountTV.setVisibility(View.VISIBLE);
                averageAmountTV.setText("$ " + String.format(Locale.US, "%1$,.2f", averageAmount));
                line3TV.setVisibility(View.VISIBLE);
                line3TV.setText("each rest day.");
            } else {
                line1TV.setText("Your budget has been exhausted");
                averageAmountTV.setVisibility(View.GONE);
                line3TV.setVisibility(View.GONE);
            }
            customProgressShowProgress.setMaximumPercentage(per);
            if (per * 100 > 80) {
                customProgressShowProgress.setProgressColor(ContextCompat.getColor(this, R.color.red_500));
                customProgressShowProgress.setProgressBackgroundColor(ContextCompat.getColor(this, R.color.red_200));
            } else if (per * 100 > 40) {
                customProgressShowProgress.setProgressColor(ContextCompat.getColor(this, R.color.Gold));
                customProgressShowProgress.setProgressBackgroundColor(ContextCompat.getColor(this, R.color.Yellow));
            } else {
                customProgressShowProgress.setProgressColor(ContextCompat.getColor(this, R.color.green_500));
                customProgressShowProgress.setProgressBackgroundColor(ContextCompat.getColor(this, R.color.green_200));
            }
            customProgressShowProgress.setShowingPercentage(true);
            startDateTV.setText(selectedBudgetData.getStartDate());
            endDateTV.setText(selectedBudgetData.getEndDate());

            spentTV.setText("$ " + totalExpenseCount);
            totalTV.setText("$ " + selectedBudgetData.getBudgetAmount());


        }


    }

    private double calculateTotalExpenses(String expenses, String startDate, String endDate) {
        double totalExpenses = 0;
        String[] projection = {
                ExpensesContract.ExpenseEntry._Id,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE};

        String selection = ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_USER_ID + "=? ";


        String[] selectionArgs = {
                String.valueOf(new Session(getBaseContext()).getuserId()),
        };

        Cursor expensesCursor = getContentResolver().query(ExpensesContract.ExpenseEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        if (expensesCursor != null) {
            try {
                int expenseNameIndex = expensesCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME);
                int expenseTypeIndex = expensesCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE);
                int expenseAmountIndex = expensesCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT);
                int dateIndex = expensesCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE);
                while (expensesCursor.moveToNext()) {
                    String expenseName = expensesCursor.getString(expenseNameIndex);
                    String expenseType = expensesCursor.getString(expenseTypeIndex);
                    double expenseAmount = expensesCursor.getDouble(expenseAmountIndex);
                    String date = expensesCursor.getString(dateIndex);
                    Date expenseDate = null, start = null, end = null;
                    try {
                        expenseDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date);
                        start = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(startDate);
                        end = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(endDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (expenseDate == null || start == null || end == null)
                        continue;
                    if (!(expenseDate.compareTo(start) >= 0 && expenseDate.compareTo(end) <= 0))
                        continue;

                    if (!expenses.equals("All Expenses")) {
                        if (!expenses.contains(expenseName))
                            continue;
                    }
                    if (expenseType.equals("Expense"))
                        totalExpenses += expenseAmount;
                    else
                        totalExpenses -= expenseAmount;

                }
            } finally {
                expensesCursor.close();
            }
        }

        if (totalExpenses < 0)
            return 0;
        else
            return totalExpenses;
    }
}
