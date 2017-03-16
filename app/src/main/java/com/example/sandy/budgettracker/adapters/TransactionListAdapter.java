package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

import java.util.ArrayList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.CustomViewHolderList> {
    private ArrayList<ExpenseData> expenseDatas;
    private Activity activity;

    class CustomViewHolderList extends RecyclerView.ViewHolder {
        protected ImageView expenseImage;
        protected TextView expenseName;
        protected TextView expenseAmount;

        public CustomViewHolderList(View view) {
            super(view);

            this.expenseImage = (ImageView) view.findViewById(R.id.expense_image);
            this.expenseName = (TextView) view.findViewById(R.id.expense_name);
            this.expenseAmount = (TextView) view.findViewById(R.id.expense_amount);

        }
    }

    public TransactionListAdapter(Activity activity, ArrayList<ExpenseData> expenseDatas) {
        this.expenseDatas = expenseDatas;
        this.activity = activity;
    }

    @Override
    public TransactionListAdapter.CustomViewHolderList onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trans_list, null);
        TransactionListAdapter.CustomViewHolderList viewHolder = new TransactionListAdapter.CustomViewHolderList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransactionListAdapter.CustomViewHolderList holder, int position) {


        holder.expenseImage.setImageResource(ImageAndColorUtil.getWhiteImageContentId(expenseDatas.get(position).getExpenseName()));
        holder.expenseImage.setColorFilter(ContextCompat.getColor(activity, ImageAndColorUtil.getColorContentId(expenseDatas.get(position).getExpenseName())));
        holder.expenseName.setText(expenseDatas.get(position).getExpenseName());

        holder.expenseAmount.setText("- $ "+new Double(expenseDatas.get(position).getExpenseAmount()).toString());

    }

    @Override
    public int getItemCount() {
        return expenseDatas.size();
    }
}
