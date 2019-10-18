package com.example.firestorequiz.Model;

public class Category {


    private int CategoryId;
    private String CategoryName;
    private String CategoryImage;


    private int CategoryPoints;


    public Category() {
    }

    public Category(int categoryId, String categoryName, String categoryImage) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        CategoryImage = categoryImage;
    }

    public Category(int categoryId, String categoryName, String categoryImage, int CategoryPoints) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        CategoryImage = categoryImage;
        this.CategoryPoints = CategoryPoints;
    }


    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        CategoryImage = categoryImage;
    }

    public int getCategoryPoints() {
        return CategoryPoints;
    }

    public void setCategoryPoints(int categoryPoints) {
        CategoryPoints = categoryPoints;
    }


}
