package com.example.sandy.budgettracker.util;

import android.util.Log;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseItem;


public final class ImageAndColorUtil {

    public static int getImageContentId(String expenseName) {
        if (expenseName.equalsIgnoreCase("Car")) {
            return R.drawable.ic_sports_car;
        } else if (expenseName.equalsIgnoreCase("Travel")) {
            return R.drawable.ic_aeroplane;
        } else if (expenseName.equalsIgnoreCase("Food")) {
            return R.drawable.ic_cutlery;
        } else if (expenseName.equalsIgnoreCase("Family")) {
            return R.drawable.ic_user;
        } else if (expenseName.equalsIgnoreCase("Bills")) {
            return R.drawable.ic_payment_method;
        } else if (expenseName.equalsIgnoreCase("Entertainment")) {
            return R.drawable.ic_video_camera;
        } else if (expenseName.equalsIgnoreCase("Home")) {
            return R.drawable.ic_house;
        } else if (expenseName.equalsIgnoreCase("Utilities")) {
            return R.drawable.ic_light_bulb;
        } else if (expenseName.equalsIgnoreCase("Shopping")) {
            return R.drawable.ic_shopping_cart;
        } else if (expenseName.equalsIgnoreCase("Hotel")) {
            return R.drawable.ic_rural_hotel_of_three_stars;
        } else if (expenseName.equalsIgnoreCase("HealthCare")) {
            return R.drawable.ic_first_aid_kit;
        } else if (expenseName.equalsIgnoreCase("HealthCare")) {
            return R.drawable.ic_first_aid_kit;
        } else if (expenseName.equalsIgnoreCase("Other")) {
            return R.drawable.ic_paper_plane;
        } else if (expenseName.equalsIgnoreCase("Clothing")) {
            return R.drawable.ic_shirt;
        } else if (expenseName.equalsIgnoreCase("Transport")) {
            return R.drawable.ic_van;
        } else if (expenseName.equalsIgnoreCase("Groceries")) {
            return R.drawable.ic_groceries;
        } else if (expenseName.equalsIgnoreCase("Drinks")) {
            return R.drawable.ic_cocktail_glass;
        } else if (expenseName.equalsIgnoreCase("Hobbies")) {
            return R.drawable.ic_soccer_ball_variant;
        } else if (expenseName.equalsIgnoreCase("Pets")) {
            return R.drawable.ic_animal_prints;
        } else if (expenseName.equalsIgnoreCase("Education")) {
            return R.drawable.ic_atomic;
        } else if (expenseName.equalsIgnoreCase("Cinema")) {
            return R.drawable.ic_film;
        } else if (expenseName.equalsIgnoreCase("Love")) {
            return R.drawable.ic_like;
        } else if (expenseName.equalsIgnoreCase("Kids")) {
            return R.drawable.ic_windmill;
        } else if (expenseName.equalsIgnoreCase("Rent")) {
            return R.drawable.ic_rent;
        } else if (expenseName.equalsIgnoreCase("iTunes")) {
            return R.drawable.ic_itunes_logo_of_amusical_note_inside_a_circle;
        } else if (expenseName.equalsIgnoreCase("Savings")) {
            return R.drawable.ic_piggy_bank;
        } else if (expenseName.equalsIgnoreCase("Gifts")) {
            return R.drawable.ic_gift;
        } else if (expenseName.equalsIgnoreCase("Salary")) {
            return R.drawable.ic_incomes;
        } else if (expenseName.trim().equalsIgnoreCase("Business")) {
            return R.drawable.ic_briefcase;
        } else if (expenseName.equalsIgnoreCase("Loan")) {
            return R.drawable.ic_contract;
        } else if (expenseName.equalsIgnoreCase("Extra Income")) {
            return R.drawable.ic_salary;
        } else
            return 0;

    }

    public static int getWhiteImageContentId(String expenseName) {
        if (expenseName.equalsIgnoreCase("Car")) {
            return R.drawable.ic_sports_car_w;
        } else if (expenseName.equalsIgnoreCase("Travel")) {
            return R.drawable.ic_aeroplane_w;
        } else if (expenseName.equalsIgnoreCase("Food")) {
            return R.drawable.ic_cutlery_w;
        } else if (expenseName.equalsIgnoreCase("Family")) {
            return R.drawable.ic_user_w;
        } else if (expenseName.equalsIgnoreCase("Bills")) {
            return R.drawable.ic_payment_method_w;
        } else if (expenseName.equalsIgnoreCase("Entertainment")) {
            return R.drawable.ic_video_camera_w;
        } else if (expenseName.equalsIgnoreCase("Home")) {
            return R.drawable.ic_house_w;
        } else if (expenseName.equalsIgnoreCase("Utilities")) {
            return R.drawable.ic_light_bulb_w;
        } else if (expenseName.equalsIgnoreCase("Shopping")) {
            return R.drawable.ic_shopping_cart_w;
        } else if (expenseName.equalsIgnoreCase("Hotel")) {
            return R.drawable.ic_rural_hotel_of_three_stars_w;
        } else if (expenseName.equalsIgnoreCase("HealthCare")) {
            return R.drawable.ic_first_aid_kit_w;
        } else if (expenseName.equalsIgnoreCase("HealthCare")) {
            return R.drawable.ic_first_aid_kit_w;
        } else if (expenseName.equalsIgnoreCase("Other")) {
            return R.drawable.ic_paper_plane_w;
        } else if (expenseName.equalsIgnoreCase("Clothing")) {
            return R.drawable.ic_shirt_w;
        } else if (expenseName.equalsIgnoreCase("Transport")) {
            return R.drawable.ic_van_w;
        } else if (expenseName.equalsIgnoreCase("Groceries")) {
            return R.drawable.ic_groceries_w;
        } else if (expenseName.equalsIgnoreCase("Drinks")) {
            return R.drawable.ic_cocktail_glass_w;
        } else if (expenseName.equalsIgnoreCase("Hobbies")) {
            return R.drawable.ic_soccer_ball_variant_w;
        } else if (expenseName.equalsIgnoreCase("Pets")) {
            return R.drawable.ic_animal_prints_w;
        } else if (expenseName.equalsIgnoreCase("Education")) {
            return R.drawable.ic_atomic_w;
        } else if (expenseName.equalsIgnoreCase("Cinema")) {
            return R.drawable.ic_film_w;
        } else if (expenseName.equalsIgnoreCase("Love")) {
            return R.drawable.ic_like_w;
        } else if (expenseName.equalsIgnoreCase("Kids")) {
            return R.drawable.ic_windmill_w;
        } else if (expenseName.equalsIgnoreCase("Rent")) {
            return R.drawable.ic_rent_w;
        } else if (expenseName.equalsIgnoreCase("iTunes")) {
            return R.drawable.ic_itunes_logo_of_amusical_note_inside_a_circle_w;
        } else if (expenseName.equalsIgnoreCase("Savings")) {
            return R.drawable.ic_piggy_bank_w;
        } else if (expenseName.equalsIgnoreCase("Gifts")) {
            return R.drawable.ic_gift_w;
        } else if (expenseName.equalsIgnoreCase("Salary")) {
            return R.drawable.ic_incomes_w;
        } else if (expenseName.trim().equalsIgnoreCase("Business")) {
            return R.drawable.ic_briefcase_w;
        } else if (expenseName.equalsIgnoreCase("Loan")) {
            return R.drawable.ic_contract_w;
        } else if (expenseName.equalsIgnoreCase("Extra Income")) {
            return R.drawable.ic_salary_w;
        } else {
            return 0;
        }


    }
}
