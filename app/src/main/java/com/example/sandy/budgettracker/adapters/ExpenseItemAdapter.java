package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseItem;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

import java.util.ArrayList;

public class ExpenseItemAdapter extends RecyclerView.Adapter<ExpenseItemAdapter.CustomViewHolderList> {
    private ArrayList<ExpenseItem> expenseItems;
    private Activity context;

    public ExpenseItemAdapter(Activity context, ArrayList<ExpenseItem> expenseItems) {
        this.expenseItems = expenseItems;
        this.context = context;
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
        ExpenseItemAdapter.CustomViewHolderList viewHolder = new ExpenseItemAdapter.CustomViewHolderList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ExpenseItemAdapter.CustomViewHolderList holder, int position) {


        ExpenseItem expenseItem = expenseItems.get(position);

        holder.expenseName.setText(expenseItem.getName());
//        expenseName.setBackgroundColor(expenseItem.getColorContentId());


        if (expenseItem.hasImage()) {
            holder.expenseImage.setImageResource(expenseItem.getImageContentId());
            holder.expenseImage.setVisibility(View.VISIBLE);
        } else {
            holder.expenseImage.setImageResource(R.mipmap.ic_launcher);
            holder.expenseImage.setVisibility(View.VISIBLE);
        }
//        else
//            imageView.setVisibility(View.GONE);

//        View textContainer=listItemView.findViewById(R.id.text_container);
//        int color= ContextCompat.getColor(getContext(), colorResourceId);
//        textContainer.setBackgroundColor(color);


//        holder.expenseImage.setImageResource(ImageAndColorUtil.getImageContentId(expenseDatas.get(position).getExpenseName()));
//        holder.expenseName.setText(expenseDatas.get(position).getExpenseName());
//        holder.expenseAmount.setText(new Double(expenseDatas.get(position).getExpenseAmount()).toString());

    }

    @Override
    public int getItemCount() {
        return expenseItems.size();
    }



}
