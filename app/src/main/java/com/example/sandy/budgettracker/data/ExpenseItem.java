package com.example.sandy.budgettracker.data;


import java.io.Serializable;

public class ExpenseItem implements Serializable {

    private String name;
    private String type;
    private int imageContentId = -1;
    private int colorContentId;

    public ExpenseItem(String name, String type, int imageContentId, int colorContentId) {
        this.name = name;
        this.type = type;
        this.imageContentId = imageContentId;
        this.colorContentId = colorContentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "ExpenseItem{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", imageContentId=" + imageContentId +
                ", colorContentId=" + colorContentId +
                '}';
    }
}
