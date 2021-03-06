package com.example.sandy.budgettracker.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.AddBudgetActivity;
import com.example.sandy.budgettracker.adapters.BudgetsAdapter;
import com.example.sandy.budgettracker.adapters.RecyclerItemClickListener;
import com.example.sandy.budgettracker.contracts.BudgetsContract;
import com.example.sandy.budgettracker.contracts.ExpensesContract;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.util.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class BudgetFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayList<BudgetData> budgetDatas = null;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, container, false);


        Toolbar toolbar =  view.findViewById(R.id.budgetToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.budgets);


        recyclerView =  view.findViewById(R.id.budgets);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == budgetDatas.size() - 1) {
                            openExpenseActivity();
                        }
                    }
                })
        );


        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    private void openExpenseActivity() {
        Intent intent = new Intent(this.getActivity(), AddBudgetActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                BudgetsContract.BudgetsEntry._Id,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS};

        String selection = BudgetsContract.BudgetsEntry.COLUMN_BUDGET_USER_ID + "=?";

        String[] selectionArgs = {
                String.valueOf(new Session(getActivity().getBaseContext()).getuserId())
        };

        return new CursorLoader(this.getActivity(),
                BudgetsContract.BudgetsEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        budgetDatas = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            int budgetId = cursor.getColumnIndex(BudgetsContract.BudgetsEntry._Id);
            int nameColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME);
            int amountColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT);
            int expensesColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES);
            int startDateColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE);
            int endDateColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE);
            int notificationColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(budgetId);
                String name = cursor.getString(nameColumnIndex);
                double amount = cursor.getDouble(amountColumnIndex);
                String expenses = cursor.getString(expensesColumnIndex);
                String startDate = cursor.getString(startDateColumnIndex);
                String endDate = cursor.getString(endDateColumnIndex);
                int isNotify = cursor.getInt(notificationColumnIndex);
                BudgetData budgetData = new BudgetData(id, name, amount, expenses, startDate, endDate, isNotify);
                double totalExpense = calculateTotalExpenses(expenses, startDate, endDate);
                budgetData.setTotalExpenses(totalExpense);
                budgetDatas.add(budgetData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BudgetsAdapter itemsAdapter = new BudgetsAdapter(this.getActivity(), budgetDatas);

        recyclerView.setAdapter(itemsAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
                String.valueOf(new Session(getActivity().getBaseContext()).getuserId()),
        };

        Cursor expensesCursor = getActivity().getContentResolver().query(ExpensesContract.ExpenseEntry.CONTENT_URI, projection, selection, selectionArgs, null);
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
