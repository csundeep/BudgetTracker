package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.CustomViewHolder> {
    private ArrayList<ArrayList<ExpenseData>> expenseDatas;
    private Activity activity;

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView transListView;
        private TextView totalExpenseDateView;
        private TextView totalExpenseAmountView;

        CustomViewHolder(View view) {
            super(view);
            this.transListView = (RecyclerView) view.findViewById(R.id.recyclerviewList);
            this.totalExpenseDateView = (TextView) view.findViewById(R.id.totalExpenseDate);
            this.totalExpenseAmountView = (TextView) view.findViewById(R.id.totalExpenseAmount);
        }

        void setTotalExpenseDate(String date) {
            totalExpenseDateView.setText(date);
        }

        void setTotalExpenseAmount(String amount) {
            totalExpenseAmountView.setText(amount);
        }
    }

    public TransactionsAdapter(Activity activity, ArrayList<ArrayList<ExpenseData>> expenseDatas) {
        this.expenseDatas = expenseDatas;
        this.activity = activity;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transactions_list, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        double totalAmount = 0;
        holder.transListView.setLayoutManager(new LinearLayoutManager(activity));
        TransactionListAdapter itemsAdapter = new TransactionListAdapter(activity, expenseDatas.get(position));
        holder.transListView.setAdapter(itemsAdapter);
        holder.setTotalExpenseDate(expenseDatas.get(position).get(0).getExpenseDate());

        for (ExpenseData expenseData : expenseDatas.get(position)) {
            totalAmount += expenseData.getExpenseAmount();
        }
        holder.setTotalExpenseAmount("-$ " + Double.valueOf(totalAmount).toString());

    }

    @Override
    public int getItemCount() {
        return expenseDatas.size();
    }
}
