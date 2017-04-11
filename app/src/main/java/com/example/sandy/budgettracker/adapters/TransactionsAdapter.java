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
    private double walletBalance;
    private double totalExpenseAmount;


    class CustomViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView transListView;
        private TextView totalExpenseDateView;
        private TextView totalExpenseAmountView;
        private TextView walletAmountView;
        private TextView totalExpensesPerMonth;


        CustomViewHolder(View view) {
            super(view);

            this.transListView = (RecyclerView) view.findViewById(R.id.recyclerviewList);
            this.totalExpenseDateView = (TextView) view.findViewById(R.id.totalExpenseDate);
            this.totalExpenseAmountView = (TextView) view.findViewById(R.id.totalExpenseAmount);
            this.walletAmountView = (TextView) view.findViewById(R.id.walletAmount);
            this.totalExpensesPerMonth = (TextView) view.findViewById(R.id.totalExpensesPerMonth);
        }

        void setTotalExpenseDate(String date) {
            totalExpenseDateView.setText(date);
        }

        void setTotalExpenseAmount(String amount) {
            totalExpenseAmountView.setText(amount);
        }

        void setWalletAmountView(String date) {
            walletAmountView.setText(date);
        }

        void setTotalExpensesPerMonth(String amount) {
            totalExpensesPerMonth.setText(amount);
        }
    }

    public TransactionsAdapter(Activity activity, ArrayList<ArrayList<ExpenseData>> expenseDatas, double walletBalance, double totalExpenseAmount) {
        this.expenseDatas = expenseDatas;
        this.activity = activity;
        this.walletBalance = walletBalance;
        this.totalExpenseAmount = totalExpenseAmount;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transactions_list, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (position == 0) {
            holder.setWalletAmountView(Double.valueOf(walletBalance).toString());
            holder.setTotalExpensesPerMonth(Double.valueOf(totalExpenseAmount).toString());
        } else {
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


    }

    @Override
    public int getItemCount() {
        return expenseDatas.size();
    }
}
