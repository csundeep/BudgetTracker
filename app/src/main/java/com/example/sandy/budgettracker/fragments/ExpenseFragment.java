package com.example.sandy.budgettracker.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.ExpenseItemAdapter;
import com.example.sandy.budgettracker.adapters.RecyclerItemClickListener;
import com.example.sandy.budgettracker.contracts.ItemsContract;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.data.ExpenseItem;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

import java.util.ArrayList;


public class ExpenseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private int selectedPosition = -1;
    ExpenseItemAdapter itemsAdapter;
    private ArrayList<ExpenseItem> items;
    ImageView appBarImageView;
    public static ExpenseData selectedExpenseData;
    private ImageButton b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        if (getArguments().getSerializable("selectedExpenseData") != null) {
            selectedExpenseData = (ExpenseData) getArguments().getSerializable("selectedExpenseData");
        }

        if (ExpenseFragment.selectedExpenseData == null)
            selectedExpenseData = new ExpenseData();

//
//        final EditText editText = (EditText) getActivity().findViewById(R.id.amount);
//        editText.addTextChangedListener(new TextWatcher() {
//            int len = 0;
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String str = editText.getText().toString();
//                if (str.length() == 1 && len < str.length()) {//len check for backspace
//                    editText.setText("-" + str);
//                    editText.setSelection(str.length() + 1);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//                String str = editText.getText().toString();
//                len = str.length();
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//
//        });
        appBarImageView =  getActivity().findViewById(R.id.appBarExpenseImage);
        recyclerView =  view.findViewById(R.id.expense);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getContext(), new RecyclerItemClickListener.OnItemClickListener()

                {
                    @Override
                    public void onItemClick(View view, int position) {

                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);

                        if (selectedPosition != -1) {
                            recyclerView.getLayoutManager().findViewByPosition(selectedPosition).setAlpha(1f);
                        }

                        viewItem.setAlpha(.5f);
                        TextView textView =  viewItem.findViewById(R.id.expense_name);
                        String expenseItem = textView.getText().toString();
                        selectedPosition = position;

                        for (ExpenseItem item : items) {
                            if (item.getName().equals(expenseItem)) {
                                if (ExpenseFragment.selectedExpenseData == null)
                                    ExpenseFragment.selectedExpenseData = new ExpenseData();
                                ExpenseFragment.selectedExpenseData.setExpenseName(item.getName());
                                ExpenseFragment.selectedExpenseData.setExpenseType(item.getType());
                                appBarImageView.setImageResource(ImageAndColorUtil.getWhiteImageContentId(item.getName()));
                                Toolbar toolbar =  getActivity().findViewById(R.id.expenseToolbar);
                                toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), item.getColorContentId()));
                                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
                                LinearLayout linearLayout =  getActivity().findViewById(R.id.expenseInfoLayout);
                                linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), item.getColorContentId()));
                                TabLayout tabLayout =  getActivity().findViewById(R.id.tabs1);
                                tabLayout.setBackgroundColor(ContextCompat.getColor(getContext(), item.getColorContentId()));
                            }
                        }
                    }
                })
        );


        b =  getActivity().findViewById(R.id.addExpense);
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                TextView amountTextView =  getActivity().findViewById(R.id.amount);
                double amount = 0;
                if (amountTextView.getText() != null)
                    try {
                        amount = Double.parseDouble(amountTextView.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                openExpenseDetailActivity(v, amount);
            }
        });


        ImageButton b1 = getActivity().findViewById(R.id.appBarExpenseImage);
        if (b1 != null)
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    private void openExpenseDetailActivity(@SuppressWarnings("unused") View view, double amount) {

        if (selectedExpenseData.getExpenseName() == null) {
            Toast.makeText(getActivity(), "You have to select an expense item", Toast.LENGTH_LONG).show();
        } else if (amount == 0) {
            Toast.makeText(getActivity(), "Amount should not be zero", Toast.LENGTH_LONG).show();
        } else {
            selectedExpenseData.setExpenseAmount(amount);
            b.setImageResource(R.drawable.ic_action_done);
            Bundle args = new Bundle();
            args.putSerializable("selectedExpenseData", selectedExpenseData);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
            ExpenseDetailFragment expenseDetailFragment = new ExpenseDetailFragment();
            expenseDetailFragment.setArguments(args);
            transaction.replace(R.id.contentExpense, expenseDetailFragment);
            transaction.commit();
            selectedExpenseData = null;
        }


    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                ItemsContract.ItemsEntry._Id,
                ItemsContract.ItemsEntry.COLUMN_ITEM_NAME,
                ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE,
                ItemsContract.ItemsEntry.COLUMN_ITEM_LOGO,
                ItemsContract.ItemsEntry.COLUMN_ITEM_COLOR,
                ItemsContract.ItemsEntry.COLUMN_ITEM_CREATED_DATE};

        String selection = ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE + "=?";

        String[] selectionArgs = {
                getArguments().getString("type")
        };

        return new CursorLoader(this.getActivity(),
                ItemsContract.ItemsEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        items = new ArrayList<>();

        try {
            int nameColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM_NAME);
            int typeColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE);
            int logoColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM_LOGO);
            int colorColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM_COLOR);
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumnIndex);
                String type = cursor.getString(typeColumnIndex);
                int ImageContentId = cursor.getInt(logoColumnIndex);
                int colorContentId = cursor.getInt(colorColumnIndex);
                ExpenseItem expenseItem = new ExpenseItem(name, type, ImageContentId, colorContentId);
                items.add(expenseItem);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        final GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 4);
        recyclerView.setLayoutManager(layoutManager);
        itemsAdapter = new ExpenseItemAdapter(this.getActivity(), items, selectedExpenseData);
        recyclerView.setAdapter(itemsAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
