package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.data.ExpenseItem;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

import java.util.ArrayList;

public class ExpenseItemAdapter extends RecyclerView.Adapter<ExpenseItemAdapter.CustomViewHolderList> {
    private ArrayList<ExpenseItem> expenseItems;
    private Activity context;
    private ExpenseData selectExpenseData;

    public ExpenseItemAdapter(Activity context, ArrayList<ExpenseItem> expenseItems, ExpenseData selectExpenseData) {
        this.expenseItems = expenseItems;
        this.context = context;
        this.selectExpenseData = selectExpenseData;
    }

    class CustomViewHolderList extends RecyclerView.ViewHolder {
        private ImageView expenseImage;
        private TextView expenseName;

        CustomViewHolderList(View view) {
            super(view);

            this.expenseImage =  view.findViewById(R.id.expense_image);
            this.expenseName =  view.findViewById(R.id.expense_name);

        }
    }

    @Override
    public ExpenseItemAdapter.CustomViewHolderList onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.expense_list, null);
        return new ExpenseItemAdapter.CustomViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ExpenseItemAdapter.CustomViewHolderList holder, int position) {


        ExpenseItem expenseItem = expenseItems.get(position);

        holder.expenseName.setText(expenseItem.getName());
        if (selectExpenseData != null) {
            if (expenseItems.get(position).getName().equals(selectExpenseData.getExpenseName())) {
                holder.expenseName.setAlpha(.5f);
                holder.expenseImage.setAlpha(.5f);
            }
        }


        if (expenseItem.hasImage()) {
            holder.expenseImage.setImageResource(ImageAndColorUtil.getImageContentId(expenseItem.getName()));
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
