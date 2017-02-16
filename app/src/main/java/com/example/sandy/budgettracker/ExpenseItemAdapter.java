package com.example.sandy.budgettracker;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sandy on 15-02-2017.
 */
public class ExpenseItemAdapter extends ArrayAdapter<ExpenseItem> {

    public ExpenseItemAdapter(Activity context, ArrayList<ExpenseItem> words) {
        super(context, 0, words);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.expense_list, parent, false);
        }


        ExpenseItem expenseItem = getItem(position);


        TextView expenseName = (TextView) listItemView.findViewById(R.id.expense_name);
        expenseName.setText(expenseItem.getName());
        expenseName.setBackgroundColor(expenseItem.getColorContentId());


        ImageView imageView = (ImageView) listItemView.findViewById(R.id.expense_image);
//        if (expenseItem.hasImage()) {
//            imageView.setImageResource(currentWord.getImageResourceId());
//            imageView.setVisibility(View.VISIBLE);
//        }
//        else
//            imageView.setVisibility(View.GONE);

//        View textContainer=listItemView.findViewById(R.id.text_container);
//        int color= ContextCompat.getColor(getContext(), colorResourceId);
//        textContainer.setBackgroundColor(color);

        return listItemView;
    }

}
