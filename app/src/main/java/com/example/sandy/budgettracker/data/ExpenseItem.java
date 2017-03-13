package com.example.sandy.budgettracker.data;


import java.io.Serializable;

public class ExpenseItem implements Serializable{

    private String name;
    private int imageContentId=-1;
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

    public boolean hasImage() {
        return imageContentId != -1;
    }
}
