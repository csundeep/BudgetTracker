package com.example.sandy.budgettracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.ExpenseDetailActivity;
import com.example.sandy.budgettracker.adapters.ExpenseItemAdapter;
import com.example.sandy.budgettracker.data.ExpenseItem;

import java.util.ArrayList;


public class ExpenseFragment extends Fragment {

    private int selectedPosition = -1;
    private GridView gridView;
    ExpenseItemAdapter itemsAdapter;
    public static String expenseItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<ExpenseItem> items = new ArrayList<>();
        if (getArguments().get("type") == "Expense") {
            items.add(new ExpenseItem("CAR", 0, 0));
            items.add(new ExpenseItem("RENT", 0, 0));
            items.add(new ExpenseItem("FOOD", 0, 0));
            items.add(new ExpenseItem("GROCERIES", 0, 0));
            items.add(new ExpenseItem("DRINKS", 0, 0));
            items.add(new ExpenseItem("BILL", 0, 0));
            items.add(new ExpenseItem("DRINKS", 0, 0));
            items.add(new ExpenseItem("CLOTHES", 0, 0));
            items.add(new ExpenseItem("HOBBIES", 0, 0));
            items.add(new ExpenseItem("TRANSPORTATION", 0, 0));
            items.add(new ExpenseItem("PETS", 0, 0));
            items.add(new ExpenseItem("SHOPPING", 0, 0));
            items.add(new ExpenseItem("HEALTHCARE", 0, 0));
            items.add(new ExpenseItem("UTILITIES", 0, 0));
            items.add(new ExpenseItem("HOME", 0, 0));
            items.add(new ExpenseItem("ACCOMMODATION", 0, 0));
        } else {
            items.add(new ExpenseItem("EXTRA INCOME", 0, 0));
            items.add(new ExpenseItem("GIFTS", 0, 0));
            items.add(new ExpenseItem("SALARY", 0, 0));
            items.add(new ExpenseItem("LOAN", 0, 0));
            items.add(new ExpenseItem("BUSINESS", 0, 0));

        }
        View view = inflater.inflate(R.layout.fragment_expense, container, false);


        gridView = (GridView) view.findViewById(R.id.expense);

        itemsAdapter = new ExpenseItemAdapter(this.getActivity(), items);
        gridView.setAdapter(itemsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                if (selectedPosition != -1) {
                    gridView.getChildAt(selectedPosition).setAlpha(1f);
                }
                gridView.getChildAt(position).setAlpha(.5f);
                TextView textView = (TextView) gridView.getChildAt(position).findViewById(R.id.expense_name);
                expenseItem = textView.getText().toString();
                selectedPosition = position;
            }
        });


        ImageButton b = (ImageButton) getActivity().findViewById(R.id.addExpense);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView amountTextView = (TextView) getActivity().findViewById(R.id.amount);
                double amount = 0;
                if (amountTextView.getText()!=null)
                    try {
                        amount = Double.parseDouble(amountTextView.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                openExpenseDetailActivity(v, expenseItem, amount);
            }
        });
        return view;
    }

    private void openExpenseDetailActivity(@SuppressWarnings("unused") View view, String expenseItem, double amount) {
        Intent intent = new Intent(getActivity(), ExpenseDetailActivity.class);
        if (amount == 0) {
            Toast.makeText(getActivity(), "Amount should not be zero", Toast.LENGTH_LONG).show();
        } else if (expenseItem == null) {
            Toast.makeText(getActivity(), "You have to select an expense item", Toast.LENGTH_LONG).show();
        } else {
            intent.putExtra("amount", amount);
            intent.putExtra("expenseItem", expenseItem);
            expenseItem = null;
            startActivity(intent);
        }


    }


}
