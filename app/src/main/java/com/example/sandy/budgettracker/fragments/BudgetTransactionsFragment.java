package com.example.sandy.budgettracker.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.util.ArrayList;


public class BudgetTransactionsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
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

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
