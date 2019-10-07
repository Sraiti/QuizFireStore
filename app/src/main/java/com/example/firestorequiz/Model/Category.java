package com.example.firestorequiz.Model;

import java.util.ArrayList;

public class Category {



    private int CategoryId;
    private String CategoryName ;
    private String CategoryImage;

    public Category() {
    }

    public Category(int categoryId, String categoryName, String categoryImage) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        CategoryImage = categoryImage;
    }

    public Category(Category toObject) {

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
    public static ArrayList<Category> createCategoryList(int numProducts) {

        ArrayList<Category> Categories = new ArrayList<Category>();

        for (int i = 1; i <= numProducts; i++) {
            Categories.add(new Category(i, "Name" + i,"product_image"));
        }

        return Categories;
    }
}
