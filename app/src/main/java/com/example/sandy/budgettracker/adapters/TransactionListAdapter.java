package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
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

class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.CustomViewHolderList> {
    private ArrayList<ExpenseData> expenseDatas;
    private Activity activity;

    class CustomViewHolderList extends RecyclerView.ViewHolder {
        private ImageView expenseImage;
        private TextView expenseName;
        private TextView expenseAmount;

        CustomViewHolderList(View view) {
            super(view);

            view.setOnClickListener(new ExpenseItemOnClickListener(activity));
            this.expenseImage = (ImageView) view.findViewById(R.id.trans_expense_image);
            this.expenseName = (TextView) view.findViewById(R.id.trans_expense_name);
            this.expenseAmount = (TextView) view.findViewById(R.id.trans_expense_amount);

        }
    }

    TransactionListAdapter(Activity activity, ArrayList<ExpenseData> expenseDatas) {
        this.expenseDatas = expenseDatas;
        this.activity = activity;
    }

    @Override
    public TransactionListAdapter.CustomViewHolderList onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trans_list, viewGroup, false);
        return new TransactionListAdapter.CustomViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(TransactionListAdapter.CustomViewHolderList holder, int position) {


        holder.expenseImage.setImageResource(ImageAndColorUtil.getWhiteImageContentId(expenseDatas.get(position).getExpenseName()));

        Drawable background = holder.expenseImage.getBackground();

        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(ContextCompat.getColor(activity, ImageAndColorUtil.getColorContentId(expenseDatas.get(position).getExpenseName())));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(ContextCompat.getColor(activity, ImageAndColorUtil.getColorContentId(expenseDatas.get(position).getExpenseName())));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(ContextCompat.getColor(activity, ImageAndColorUtil.getColorContentId(expenseDatas.get(position).getExpenseName())));
        }
        Log.v("@@@@@@@@!!!!!!! ", expenseDatas.get(position).getLatitude() + " " + expenseDatas.get(position).getLongitude());
        holder.expenseName.setText(expenseDatas.get(position).getExpenseName());
        holder.expenseName.setTag(expenseDatas.get(position).getId());
        if (expenseDatas.get(position).getExpenseType().equals("Expense")) {
            holder.expenseAmount.setText("- $ " + Double.valueOf(expenseDatas.get(position).getExpenseAmount()).toString());
            holder.expenseAmount.setTextColor(ContextCompat.getColor(activity, R.color.red_500));
        } else {
            holder.expenseAmount.setText("$ " + Double.valueOf(expenseDatas.get(position).getExpenseAmount()).toString());
            holder.expenseAmount.setTextColor(ContextCompat.getColor(activity, R.color.green_500));
        }
    }

    @Override
    public int getItemCount() {
        return expenseDatas.size();
    }
}
