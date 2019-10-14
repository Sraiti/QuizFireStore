package com.example.firestorequiz.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.firestorequiz.Contracts.CategoryContract.CategoryTable;
import com.example.firestorequiz.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDbHelper extends SQLiteOpenHelper {


    private static String DATABASE_NAME = "CategoryPoints.db";

    private static int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public CategoryDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String Query = "Create TABLE " + CategoryTable.TableName + "( " +
                CategoryTable.CategoryID + " INTEGER PRIMARY KEY, " +
                CategoryTable.CategoryName + " TEXT, " +
                CategoryTable.CategoryImage + " TEXT, " +
                CategoryTable.CategoryPoints + " INTEGER " +
                ")";
        db.execSQL(Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void UpdateCategoryPoints(int CategoryID, int Points) {

        String UpdateQuery =
                "UPDATE " + CategoryTable.TableName + " SET " +
                        CategoryTable.CategoryPoints + " = " + Points +
                        "\nWHERE  " + CategoryTable.CategoryID + "=" + CategoryID + ";";

        db.execSQL(UpdateQuery);


    }

    public void InsertCategoryPoints(Category Cat) {

        String InertQuery =
                "INSERT INTO " + CategoryTable.TableName + "(" +
                        CategoryTable.CategoryID + "," +
                        CategoryTable.CategoryName + "," +
                        CategoryTable.CategoryImage + "," +
                        CategoryTable.CategoryPoints + ")" +
                        "VALUES(" + Cat.getCategoryId() + ",'" +
                        Cat.getCategoryName() + "','" +
                        Cat.getCategoryImage() + "'," +
                        0 + ")";

        db.execSQL(InertQuery);


    }


    public int getPoints(int CategoryID) {
        db = getReadableDatabase();
        int points = 0;
        String QuerygetPoints = "SELECT " + CategoryTable.CategoryPoints +
                " FROM " + CategoryTable.TableName +
                " Where " + CategoryTable.CategoryID + "=" + CategoryID + ";";
        Cursor c = db.rawQuery(QuerygetPoints, null);

        if (c.moveToFirst()) {
            points = c.getInt(c.getColumnIndex(CategoryTable.CategoryPoints));
            c.close();
            return points;
        }

        return points;
    }


    public void AddPoints(Category Category, int Point) {

        db = getReadableDatabase();
        int points = 0;
        String QuerygetPoints = "SELECT " + CategoryTable.CategoryPoints +
                " FROM " + CategoryTable.TableName +
                " Where " + CategoryTable.CategoryID + "=" + Category.getCategoryId() + ";";

        Cursor c = db.rawQuery(QuerygetPoints, null);

        if (c.moveToFirst()) {
            points = c.getInt(c.getColumnIndex(CategoryTable.CategoryPoints));
            c.close();
            UpdateCategoryPoints(Category.getCategoryId(), Point);
        } else if (!c.moveToFirst()) {
            InsertCategoryPoints(Category);
        }
    }

    public List<Category> GetAllCategories() {
        List<Category> categoryList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoryTable.TableName, null);

        if (c.moveToFirst()) {
            do {
                Category cat = new Category();


                cat.setCategoryId(c.getInt(c.getColumnIndex(CategoryTable.CategoryID)));
                cat.setCategoryImage(c.getString(c.getColumnIndex(CategoryTable.CategoryImage)));
                cat.setCategoryName(c.getString(c.getColumnIndex(CategoryTable.CategoryName)));
                cat.setCategoryPoints(c.getInt(c.getColumnIndex(CategoryTable.CategoryPoints)));

                categoryList.add(cat);


            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }


}
