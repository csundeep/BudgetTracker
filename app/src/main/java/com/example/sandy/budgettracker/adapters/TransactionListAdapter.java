package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.util.ArrayList;

import static com.example.sandy.budgettracker.fragments.ExpenseFragment.expenseItem;

public class TransactionListAdapter extends ArrayAdapter<ExpenseData> {

    public TransactionListAdapter(Activity context, ArrayList<ExpenseData> expenseDatas) {
        super(context, 0, expenseDatas);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.trans_list, parent, false);
        }
        ExpenseData expenseData = getItem(position);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.expense_image);
        imageView.setImageResource(R.mipmap.ic_car);
        imageView.setVisibility(View.VISIBLE);


//        View textContainer=listItemView.findViewById(R.id.text_container);
//        int color= ContextCompat.getColor(getContext(), colorResourceId);
//        textContainer.setBackgroundColor(color);


        TextView expenseName = (TextView) listItemView.findViewById(R.id.expense_name);
        expenseName.setText(expenseData.getExpenseName());
//        expenseName.setBackgroundColor(expenseItem.getColorContentId());


        TextView expenseAmount = (TextView) listItemView.findViewById(R.id.expense_amount);
        expenseAmount.setText(new Double(expenseData.getExpenseAmount()).toString());


        return listItemView;
    }

}
