package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.CustomViewHolder> {
    private ArrayList<ArrayList<ExpenseData>> expenseDatas;
    private Activity activity;

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected RecyclerView transListView;

        public CustomViewHolder(View view) {
            super(view);
            this.transListView = (RecyclerView) view.findViewById(R.id.recyclerviewList);
        }
    }

    public TransactionsAdapter(Activity activity, ArrayList<ArrayList<ExpenseData>> expenseDatas) {
        this.expenseDatas = expenseDatas;
        this.activity = activity;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transactions_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.transListView.setLayoutManager(new LinearLayoutManager(activity));
        TransactionListAdapter itemsAdapter = new TransactionListAdapter(activity, expenseDatas.get(position));
        holder.transListView.setAdapter(itemsAdapter);

    }

    @Override
    public int getItemCount() {
        return expenseDatas.size();
    }
}