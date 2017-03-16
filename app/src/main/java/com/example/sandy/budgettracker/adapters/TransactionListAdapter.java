package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
        private ImageView expenseImage;
        private TextView expenseName;
        private TextView expenseAmount;

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

        Drawable background = holder.expenseImage.getBackground();

        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(activity.getResources().getColor(ImageAndColorUtil.getColorContentId(expenseDatas.get(position).getExpenseName())));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(activity.getResources().getColor(ImageAndColorUtil.getColorContentId(expenseDatas.get(position).getExpenseName())));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(activity.getResources().getColor(ImageAndColorUtil.getColorContentId(expenseDatas.get(position).getExpenseName())));
        }

        holder.expenseName.setText(expenseDatas.get(position).getExpenseName());

        holder.expenseAmount.setText("- $ " + new Double(expenseDatas.get(position).getExpenseAmount()).toString());

    }

    @Override
    public int getItemCount() {
        return expenseDatas.size();
    }
}
