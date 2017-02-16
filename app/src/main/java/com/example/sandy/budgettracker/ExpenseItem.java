package com.example.sandy.budgettracker;

/**
 * Created by sandy on 15-02-2017.
 */
public class ExpenseItem {

    private String name;
    private int imageContentId;
    private int colorContentId;

    public ExpenseItem(String name, int imageContentId, int colorContentId) {
        this.name = name;
        this.imageContentId = imageContentId;
        this.colorContentId = colorContentId;
    }

    public String getName() {
        return name;
    }

    public int getImageContentId() {
        return imageContentId;
    }

    public int getColorContentId() {
        return colorContentId;
    }
}
