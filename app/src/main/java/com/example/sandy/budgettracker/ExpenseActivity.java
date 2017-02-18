package com.example.sandy.budgettracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    private ArrayList<ExpenseItem> items;
    private int selectedPosition = -1;
    private GridView gridView;
    ExpenseItemAdapter itemsAdapter;
    String expenseItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence);
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


        gridView = (GridView) findViewById(R.id.expense);

        itemsAdapter = new ExpenseItemAdapter(this, items);
        gridView.setAdapter(itemsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                if (selectedPosition != -1) {
                    // TextView textView = (TextView) gridView.getChildAt(selectedPosition).findViewById(R.id.expense_name);
                    gridView.getChildAt(selectedPosition).setAlpha(1f);
                }
                gridView.getChildAt(position).setAlpha(.5f);
//                TextView textView = (TextView) gridView.getChildAt(position).findViewById(R.id.expense_name);
//                int color = ContextCompat.getColor(itemsAdapter.getContext(), R.color.colorPrimary);
//                textView.setBackgroundColor(color);
                TextView textView = (TextView) gridView.getChildAt(position).findViewById(R.id.expense_name);
                expenseItem = textView.getText().toString();
                selectedPosition = position;
            }
        });


        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Enter expense details", Toast.LENGTH_SHORT).show();
                TextView amountTextView = (TextView) findViewById(R.id.amount);
                double amount = Double.parseDouble(amountTextView.getText().toString());
                openExpenseDetailActivity(v, expenseItem, amount);
            }
        });


    }

    private void openExpenseDetailActivity(View view, String expenseItem, double amount) {
        Intent intent = new Intent(this, ExpenseDetailActivity.class);
        intent.putExtra("amount", amount);
        intent.putExtra("expenseItem", expenseItem);
        startActivity(intent);
    }
}
