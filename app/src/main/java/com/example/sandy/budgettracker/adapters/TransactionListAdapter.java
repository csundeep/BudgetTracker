package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.data.ExpenseItem;

import java.util.ArrayList;

public class TransactionListAdapter extends ArrayAdapter<ExpenseData> {

    public TransactionListAdapter(Activity context, ArrayList<ExpenseData> words) {
        super(context, 0, words);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.transactions_list, parent, false);
        }
        ExpenseData expenseItem = getItem(position);

        //        ImageView imageView = (ImageView) listItemView.findViewById(R.id.expense_image);
//        if (expenseItem.hasImage()) {
//            imageView.setImageResource(currentWord.getImageResourceId());
//            imageView.setVisibility(View.VISIBLE);
//        }
//        else
//            imageView.setVisibility(View.GONE);

//        View textContainer=listItemView.findViewById(R.id.text_container);
//        int color= ContextCompat.getColor(getContext(), colorResourceId);
//        textContainer.setBackgroundColor(color);


        TextView expenseName = (TextView) listItemView.findViewById(R.id.expense_name);
        expenseName.setText(expenseItem.getExpenseName());
//        expenseName.setBackgroundColor(expenseItem.getColorContentId());


        TextView expenseAmount = (TextView) listItemView.findViewById(R.id.expense_amount);
        expenseAmount.setText(new Double(expenseItem.getExpenseAmount()).toString());



        return listItemView;
    }

}
