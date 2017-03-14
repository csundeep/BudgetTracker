package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandy on 12-03-2017.
 */

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.CustomViewHolder> {
    private ArrayList<ExpenseData> expenseDatas;
    private Activity activity;

    class CustomViewHolder extends RecyclerView.ViewHolder {
       // protected ListView transListView;
        protected ImageView expenseImage;
        protected TextView expenseName;
        protected TextView expenseAmount;

        public CustomViewHolder(View view) {
            super(view);
            //this.transListView = (ListView) view.findViewById(R.id.trans);

            this.expenseImage=(ImageView)view.findViewById(R.id.expense_image);
            this.expenseName=(TextView)view.findViewById(R.id.expense_name);
            this.expenseAmount=(TextView)view.findViewById(R.id.expense_amount);

        }
    }

    public TransactionsAdapter(Activity activity, ArrayList<ExpenseData> expenseDatas) {
        this.expenseDatas = expenseDatas;
        this.activity=activity;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transactions_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

//        TransactionListAdapter  itemsAdapter = new TransactionListAdapter(activity, expenseDatas);
//        holder.transListView.setAdapter(itemsAdapter);

        holder.expenseImage.setImageResource(ImageAndColorUtil.getImageContentId(expenseDatas.get(position).getExpenseName()));
        holder.expenseName.setText(expenseDatas.get(position).getExpenseName());
        holder.expenseAmount.setText(new Double(expenseDatas.get(position).getExpenseAmount()).toString());

    }

    @Override
    public int getItemCount() {
        return expenseDatas.size();
    }
}
