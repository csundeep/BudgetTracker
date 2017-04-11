package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseItem;

import java.util.ArrayList;

public class ExpenseItemAdapter extends RecyclerView.Adapter<ExpenseItemAdapter.CustomViewHolderList> {
    private ArrayList<ExpenseItem> expenseItems;
    private Activity context;
    private ExpenseItem selectExpenseItem;

    public ExpenseItemAdapter(Activity context, ArrayList<ExpenseItem> expenseItems, ExpenseItem selectExpenseItem) {
        this.expenseItems = expenseItems;
        this.context = context;
        this.selectExpenseItem = selectExpenseItem;
    }

    class CustomViewHolderList extends RecyclerView.ViewHolder {
        protected ImageView expenseImage;
        protected TextView expenseName;

        public CustomViewHolderList(View view) {
            super(view);

            this.expenseImage = (ImageView) view.findViewById(R.id.expense_image);
            this.expenseName = (TextView) view.findViewById(R.id.expense_name);

        }
    }

    @Override
    public ExpenseItemAdapter.CustomViewHolderList onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expense_list, null);
        return new ExpenseItemAdapter.CustomViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ExpenseItemAdapter.CustomViewHolderList holder, int position) {


        ExpenseItem expenseItem = expenseItems.get(position);

        holder.expenseName.setText(expenseItem.getName());
        if (selectExpenseItem != null) {
            if (expenseItems.get(position).getName().equals(selectExpenseItem.getName())) {
                holder.expenseName.setAlpha(.5f);
                holder.expenseImage.setAlpha(.5f);
            }
        }


        if (expenseItem.hasImage()) {
            holder.expenseImage.setImageResource(expenseItem.getImageContentId());
            holder.expenseImage.setVisibility(View.VISIBLE);
        } else {
            holder.expenseImage.setImageResource(R.mipmap.ic_launcher);
            holder.expenseImage.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return expenseItems.size();
    }


}
