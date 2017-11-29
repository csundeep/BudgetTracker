package com.example.sandy.budgettracker.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.TransactionsAdapter;
import com.example.sandy.budgettracker.contracts.ExpensesContract;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.util.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


public class BudgetTransactionsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayList<ExpenseData> expenseDatas;
    private BudgetData selectedBudgetData;
    private double totalExpenseCount;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_budget_transactions, container, false);

        ImageButton backIB =  view.findViewById(R.id.backToBudget);
        TextView budgetTitleTV =  view.findViewById(R.id.budgetTransTitle);
        TextView budgetPeriodTV =  view.findViewById(R.id.timeIntervel);

        if (getArguments() != null && getArguments().getSerializable("selectedBudgetData") != null) {
            selectedBudgetData = (BudgetData) getArguments().getSerializable("selectedBudgetData");
            totalExpenseCount = getArguments().getDouble("totalExpenses");
        }
        if (selectedBudgetData != null) {
            if (backIB != null)
                backIB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            budgetTitleTV.setText(selectedBudgetData.getBudgetName());
            budgetPeriodTV.setText(selectedBudgetData.getStartDate() + "-" + selectedBudgetData.getEndDate());
            expenseDatas = new ArrayList<>();
        }
        if (selectedBudgetData != null)
            getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ExpensesContract.ExpenseEntry._Id,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_LATITUDE,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_LONGITUDE,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE};

        String selection = ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_USER_ID + "=?";

        String[] selectionArgs = {
                String.valueOf(new Session(getActivity().getBaseContext()).getuserId())
        };

        return new CursorLoader(this.getActivity(),
                ExpensesContract.ExpenseEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        try {
            int expenseId = cursor.getColumnIndex(ExpensesContract.ExpenseEntry._Id);
            int nameColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME);
            int typeColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE);
            int amountColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT);
            int noteColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES);
            int dateColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE);
            int latitudeColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_LATITUDE);
            int longitudeColumnIndex = cursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_LONGITUDE);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(expenseId);
                String name = cursor.getString(nameColumnIndex);
                String type = cursor.getString(typeColumnIndex);
                double amount = cursor.getDouble(amountColumnIndex);
                String note = cursor.getString(noteColumnIndex);
                String date = cursor.getString(dateColumnIndex);
                double latitude = cursor.getDouble(latitudeColumnIndex);
                double longitude = cursor.getDouble(longitudeColumnIndex);

                Date expenseDate = null, start = null, end = null;
                try {
                    expenseDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date);
                    start = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(selectedBudgetData.getStartDate());
                    end = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(selectedBudgetData.getEndDate());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (expenseDate == null || start == null || end == null)
                    continue;
                if (!(expenseDate.compareTo(start) >= 0 && expenseDate.compareTo(end) <= 0))
                    continue;

                ExpenseData expenseData = new ExpenseData(id, name, type, amount, date, note, latitude, longitude);
                if (!selectedBudgetData.getExpenses().equals("All Expenses")) {
                    if (!selectedBudgetData.getExpenses().contains(name))
                        continue;
                }
                expenseDatas.add(expenseData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        if (expenseDatas != null && expenseDatas.size() != 0) {

            ArrayList<ArrayList<ExpenseData>> exp = new ArrayList<>();

            Map<Date, ArrayList<ExpenseData>> expenses = new HashMap<>();

            try {
                for (ExpenseData expenseData : expenseDatas) {

                    if (expenses.containsKey(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(expenseData.getExpenseDate()))) {
                        expenses.get(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(expenseData.getExpenseDate())).add(expenseData);
                    } else {
                        ArrayList<ExpenseData> datas = new ArrayList<>();
                        datas.add(expenseData);
                        expenses.put(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(expenseData.getExpenseDate()), datas);
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SortedSet<Date> keys = new TreeSet<>(expenses.keySet());
            int x = expenses.size();
            ArrayList<ExpenseData>[] exxx = new ArrayList[expenses.size() + 1];
            exxx[0] = new ArrayList<>();
            for (Date key : keys) {
                ArrayList<ExpenseData> value = expenses.get(key);
                exxx[x] = value;
                x--;

            }
            for (int i = 0; i < exxx.length; i++) {
                exp.add(exxx[i]);
            }
            RecyclerView recyclerView =  view.findViewById(R.id.budgetTransactions);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            TransactionsAdapter itemsAdapter = new TransactionsAdapter(this.getActivity(), exp, selectedBudgetData.getBudgetAmount(), totalExpenseCount);
            recyclerView.setAdapter(itemsAdapter);


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
