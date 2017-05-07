package com.example.sandy.budgettracker.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.contracts.BudgetsContract;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.util.ArrayList;


public class BudgetTransactionsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private Uri currentBudgetUri;
    private ArrayList<ExpenseData> expenseDatas;
    private BudgetData selectedBudgetData;
    private double totalExpenseCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_transactions, container, false);

        ImageButton backIB = (ImageButton) view.findViewById(R.id.backToBudget);
        TextView budgetTitleTV = (TextView) view.findViewById(R.id.budgetTransTitle);
        TextView budgetPeriodTV = (TextView) view.findViewById(R.id.timeIntervel);

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

        }
        return view;
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
        return new CursorLoader(getActivity(), currentBudgetUri, projection, null, null, null);
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
                this.selectedBudgetData = new BudgetData(id, budgetName, budgetAmount, expenses, startDate, endDate, notification);
            }


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
