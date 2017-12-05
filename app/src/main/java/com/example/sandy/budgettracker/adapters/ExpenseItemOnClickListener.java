package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.ExpenseActivity;
import com.example.sandy.budgettracker.contracts.ExpensesContract;

public class ExpenseItemOnClickListener implements View.OnClickListener {
    private Activity activity;

    ExpenseItemOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        TextView textView =  v.findViewById(R.id.trans_expense_name);
        int id = (Integer) textView.getTag();
        Intent intent = new Intent(activity, ExpenseActivity.class);
        Uri currentExpenseUri = ContentUris.withAppendedId(ExpensesContract.ExpenseEntry.CONTENT_URI, id);

        intent.setData(currentExpenseUri);

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
