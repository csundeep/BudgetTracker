package com.example.sandy.budgettracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ExpenseFragment extends Fragment {

    private ArrayList<ExpenseItem> items;
    private int selectedPosition = -1;
    private GridView gridView;
    ExpenseItemAdapter itemsAdapter;
    String expenseItem = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments().get("type") == "Expense") {
            items = new ArrayList<ExpenseItem>();
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
            items = new ArrayList<ExpenseItem>();
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
                    // TextView textView = (TextView) gridView.getChildAt(selectedPosition).findViewById(R.id.expense_name);
                    gridView.getChildAt(selectedPosition).setAlpha(1f);
                }
                gridView.getChildAt(position).setAlpha(.5f);
                TextView textView = (TextView) gridView.getChildAt(position).findViewById(R.id.expense_name);
                expenseItem = textView.getText().toString();
                selectedPosition = position;
            }
        });


        Button b = (Button) getActivity().findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Enter expense details", Toast.LENGTH_SHORT).show();
                TextView amountTextView = (TextView) getActivity().findViewById(R.id.amount);
                double amount = Double.parseDouble(amountTextView.getText().toString());
                openExpenseDetailActivity(v, expenseItem, amount);
            }
        });
        return view;
    }

    private void openExpenseDetailActivity(View view, String expenseItem, double amount) {
        Intent intent = new Intent(getActivity(), ExpenseDetailActivity.class);
        intent.putExtra("amount", amount);
        intent.putExtra("expenseItem", expenseItem);
        startActivity(intent);
    }


}
