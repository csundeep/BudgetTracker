package com.example.sandy.budgettracker;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    private ArrayList<ExpenseItem> items;
    private int selectedPosition=-1;
    private GridView gridView;
    ExpenseItemAdapter itemsAdapter;

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
        gridView.setBackgroundColor(0x0000ff);
        itemsAdapter = new ExpenseItemAdapter(this, items);
        gridView.setAdapter(itemsAdapter);
        gridView.setAlpha(0.5f);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                if(selectedPosition!=-1)
                {
                   // TextView textView = (TextView) gridView.getChildAt(selectedPosition).findViewById(R.id.expense_name);
                    gridView.getChildAt(selectedPosition).setAlpha(.5f);
                }
                gridView.getChildAt(position).setAlpha(1f);
//                TextView textView = (TextView) gridView.getChildAt(position).findViewById(R.id.expense_name);
//                int color = ContextCompat.getColor(itemsAdapter.getContext(), R.color.colorPrimary);
//                textView.setBackgroundColor(color);
                selectedPosition=position;
            }
        });


    }
}
