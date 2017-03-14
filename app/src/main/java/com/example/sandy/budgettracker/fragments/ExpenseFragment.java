package com.example.sandy.budgettracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.ExpenseDetailActivity;
import com.example.sandy.budgettracker.adapters.ExpenseItemAdapter;
import com.example.sandy.budgettracker.data.ExpenseItem;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

import java.util.ArrayList;


public class ExpenseFragment extends Fragment {

    private int selectedPosition = -1;
    private GridView gridView;
    ExpenseItemAdapter itemsAdapter;
    private ArrayList<ExpenseItem> items;
    ImageView appBarImageView;
    public static  ExpenseItem selectedExpenceItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        items = new ArrayList<>();
        if (getArguments().get("type") == "Expense") {
            items.add(new ExpenseItem("Car", R.drawable.ic_sports_car, R.color.RebeccaPurple));
            items.add(new ExpenseItem("Travel", R.drawable.ic_aeroplane, R.color.PaleVioletRed));
            items.add(new ExpenseItem("Food", R.drawable.ic_cutlery, R.color.Brown));
            items.add(new ExpenseItem("Family", R.drawable.ic_user, R.color.DarkRed));
            items.add(new ExpenseItem("Bills", R.drawable.ic_payment_method, R.color.DarkGreen));
            items.add(new ExpenseItem("Entertainment", R.drawable.ic_video_camera, R.color.Yellow));
            items.add(new ExpenseItem("Home", R.drawable.ic_house, R.color.Tomato));
            items.add(new ExpenseItem("Utilities", R.drawable.ic_light_bulb, R.color.DarkGray));
            items.add(new ExpenseItem("Shopping", R.drawable.ic_shopping_cart, R.color.LightGreen));
            items.add(new ExpenseItem("Hotel", R.drawable.ic_rural_hotel_of_three_stars,R.color.LightBlue));
            items.add(new ExpenseItem("Health Care", R.drawable.ic_first_aid_kit, R.color.IndianRed));
            items.add(new ExpenseItem("Other", R.drawable.ic_paper_plane, R.color.MediumPurple));
            items.add(new ExpenseItem("Clothing", R.drawable.ic_shirt, R.color.YellowGreen));
            items.add(new ExpenseItem("Transport", R.drawable.ic_van, R.color.Lime));
            items.add(new ExpenseItem("Groceries", R.drawable.ic_groceries, R.color.SandyBrown));
            items.add(new ExpenseItem("Drinks", R.drawable.ic_cocktail_glass, R.color.RosyBrown));
            items.add(new ExpenseItem("Hobbies", R.drawable.ic_soccer_ball_variant, R.color.PaleTurquoise));
            items.add(new ExpenseItem("Pets", R.drawable.ic_animal_prints, R.color.Peru));
            items.add(new ExpenseItem("Education", R.drawable.ic_atomic, R.color.RoyalBlue));
            items.add(new ExpenseItem("Cinema", R.drawable.ic_film, R.color.PowderBlue));
            items.add(new ExpenseItem("Love", R.drawable.ic_like, R.color.DeepPink));
            items.add(new ExpenseItem("Kids", R.drawable.ic_windmill, R.color.Chocolate));
            items.add(new ExpenseItem("Rent", R.drawable.ic_rent, R.color.LightSlateGray));
            items.add(new ExpenseItem("iTunes", R.drawable.ic_itunes_logo_of_amusical_note_inside_a_circle, R.color.MediumSeaGreen));
            items.add(new ExpenseItem("Savings", R.drawable.ic_piggy_bank, R.color.Marroon));
            items.add(new ExpenseItem("Gifts", R.drawable.ic_gift, R.color.OrangeRed));



        } else {
            items.add(new ExpenseItem("Salary", R.drawable.ic_incomes, R.color.Indigo));
            items.add(new ExpenseItem("Business ", R.drawable.ic_briefcase, R.color.Gold));
            items.add(new ExpenseItem("Gifts",  R.drawable.ic_gift, R.color.OrangeRed));
            items.add(new ExpenseItem("Loan", R.drawable.ic_contract, R.color.Kakhi));
            items.add(new ExpenseItem("Extra Income", R.drawable.ic_salary, R.color.DarkSlateBlue));

        }
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        appBarImageView = (ImageView) getActivity().findViewById(R.id.appBarExpenseImage);
        gridView = (GridView) view.findViewById(R.id.expense);

        itemsAdapter = new ExpenseItemAdapter(this.getActivity(), items);
        gridView.setAdapter(itemsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Log.v("@@@@@@@@@@@@ ",position+" ");
                if (selectedPosition != -1) {
                    gridView.getChildAt(selectedPosition).setAlpha(1f);
                }


                gridView.getChildAt(position).setAlpha(.5f);
                TextView textView = (TextView) gridView.getChildAt(position).findViewById(R.id.expense_name);
                String expenseItem = textView.getText().toString();
                selectedPosition = position;

                for (ExpenseItem item : items) {
                    if (item.getName().equals(expenseItem)) {
                        ExpenseFragment.selectedExpenceItem=item;
                        appBarImageView.setImageResource(ImageAndColorUtil.getWhiteImageContentId(item.getName()));

                    }
                }

            }
        });


        ImageButton b = (ImageButton) getActivity().findViewById(R.id.addExpense);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView amountTextView = (TextView) getActivity().findViewById(R.id.amount);
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
        return view;
    }

    private void openExpenseDetailActivity(@SuppressWarnings("unused") View view, double amount) {
        Intent intent = new Intent(getActivity(), ExpenseDetailActivity.class);
        if (amount == 0) {
            Toast.makeText(getActivity(), "Amount should not be zero", Toast.LENGTH_LONG).show();
        } else if (selectedExpenceItem == null) {
            Toast.makeText(getActivity(), "You have to select an expense item", Toast.LENGTH_LONG).show();
        } else {
            intent.putExtra("amount", amount);
            intent.putExtra("selectedExpenseItem",selectedExpenceItem);
            selectedExpenceItem = null;
            startActivity(intent);
        }


    }


}
