package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.GeneralViewHolder> {
    private ArrayList<ArrayList<ExpenseData>> expenseDatas;
    private Activity activity;
    private double walletBalance;
    private double totalExpenseAmount;

    public TransactionsAdapter(Activity activity, ArrayList<ArrayList<ExpenseData>> expenseDatas, double walletBalance, double totalExpenseAmount) {
        this.expenseDatas = expenseDatas;
        this.activity = activity;
        this.walletBalance = walletBalance;
        this.totalExpenseAmount = totalExpenseAmount;
    }


    class GeneralViewHolder extends RecyclerView.ViewHolder {
        GeneralViewHolder(View view) {
            super(view);
        }
    }


    class CustomViewHolder extends GeneralViewHolder {
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


    class MonthSummaryCard extends GeneralViewHolder {

        private TextView walletAmountView;
        private TextView totalExpensesPerMonth;

        MonthSummaryCard(View view) {
            super(view);
            this.walletAmountView = (TextView) view.findViewById(R.id.walletAmount);
            this.walletAmountView.setTextColor(ContextCompat.getColor(activity, R.color.green_500));
            this.totalExpensesPerMonth = (TextView) view.findViewById(R.id.totalExpensesPerMonth);
            this.walletAmountView.setTextColor(ContextCompat.getColor(activity, R.color.red_500));
        }

        void setWalletAmountView(String date) {
            walletAmountView.setText(date);
        }

        void setTotalExpensesPerMonth(String amount) {
            totalExpensesPerMonth.setText(amount);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? -1 : position;
    }

    @Override
    public GeneralViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


        GeneralViewHolder holder;
        View view;
        if (viewType == -1) {
            view = LayoutInflater.from(activity)
                    .inflate(R.layout.month_summary_card, viewGroup, false);
            holder = new MonthSummaryCard(view);
        } else {
            view = LayoutInflater.from(activity)
                    .inflate(R.layout.transactions_list, viewGroup, false);
            holder = new CustomViewHolder(view);
        }
        return holder;

    }

    @Override
    public void onBindViewHolder(GeneralViewHolder holder, int position) {
        if (getItemViewType(position) == -1) {
            MonthSummaryCard holder1 = (MonthSummaryCard) holder;
            holder1.setWalletAmountView("$" + Double.valueOf(walletBalance).toString());
            holder1.setTotalExpensesPerMonth("$" + Double.valueOf(totalExpenseAmount).toString());
        } else {
            CustomViewHolder holder1 = (CustomViewHolder) holder;
            double totalAmount = 0;
            holder1.transListView.setLayoutManager(new LinearLayoutManager(activity));
            TransactionListAdapter itemsAdapter = new TransactionListAdapter(activity, expenseDatas.get(position));
            holder1.transListView.setAdapter(itemsAdapter);
            holder1.setTotalExpenseDate(expenseDatas.get(position).get(0).getExpenseDate());

            for (ExpenseData expenseData : expenseDatas.get(position)) {
                totalAmount += expenseData.getExpenseAmount();
            }
            holder1.setTotalExpenseAmount("-$ " + Double.valueOf(totalAmount).toString());
        }


    }

    @Override
    public int getItemCount() {
        return expenseDatas.size();
    }
}
