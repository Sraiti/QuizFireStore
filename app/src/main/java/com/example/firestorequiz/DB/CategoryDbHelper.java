package com.example.firestorequiz.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.firestorequiz.Contracts.CategoryContract.CategoryTable;
import com.example.firestorequiz.Contracts.StageContract.StageTable;
import com.example.firestorequiz.Model.Category;
import com.example.firestorequiz.Model.Stage;

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
        String QueryCategory = "Create TABLE " + CategoryTable.TableName + "( " +
                CategoryTable.CategoryID + " INTEGER PRIMARY KEY, " +
                CategoryTable.CategoryName + " TEXT, " +
                CategoryTable.CategoryImage + " TEXT, " +
                CategoryTable.CategoryPoints + " INTEGER " +
                ")";

        String QueryStage = "Create TABLE " + StageTable.TableName + "( " +
                StageTable.ID + " INTEGER PRIMARY KEY, " +
                StageTable.CategoryID + " INTEGER , " +
                StageTable.StageID + " INTEGER , " +
                StageTable.SatgePoints + " INTEGER, " +
                StageTable.IsOpen + " INTEGER " +
                ")";
        db.execSQL(QueryCategory);
        db.execSQL(QueryStage);

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
        String QuerygetPoints = "SELECT  " + CategoryTable.CategoryPoints +
                " FROM " + CategoryTable.TableName +
                " Where " + CategoryTable.CategoryID + "=" + Category.getCategoryId() + ";";

        Cursor c = db.rawQuery(QuerygetPoints, null);

        if (c.moveToFirst()) {
            UpdateCategoryPoints(Category.getCategoryId(), Point);
        } else if (!c.moveToFirst()) {
            InsertCategoryPoints(Category);
        }
        c.close();

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

    public int getsatgeState(int StageId,int CategoryId) {

        db = getReadableDatabase();
        int State = 0;
        Cursor c = db.rawQuery("SELECT  " + StageTable.IsOpen +
                " FROM " + StageTable.TableName +
                " Where " + StageTable.CategoryID + "=" + CategoryId + " and " +
                StageTable.StageID + "=" + StageId, null);

        if (c.moveToFirst()) {
            State = c.getInt(c.getColumnIndex(StageTable.IsOpen));
            c.close();
            return State;
        }

        return State;

    }

    public void AddStage(Stage stage) {

        db = getReadableDatabase();
        String QuerygetStatue = "SELECT  " + StageTable.IsOpen +
                " FROM " + StageTable.TableName +
                " Where " + StageTable.CategoryID + "=" + stage.getCategoryID() + " and "+
                StageTable.StageID+"=" +stage.getStageId() +";";
        Cursor c = db.rawQuery(QuerygetStatue, null);

        if (c.moveToFirst()) {
            UpdateStageStatue(stage);
        } else if (!c.moveToFirst()) {
            InsertStageStatue(stage);
        }
        c.close();

    }

    private void InsertStageStatue(Stage stage) {

        String InertQuery =
                "INSERT INTO " + StageTable.TableName + "(" +
                        StageTable.CategoryID + "," +
                        StageTable.StageID + "," +
                        StageTable.SatgePoints + "," +
                        StageTable.IsOpen + ")" +
                        " VALUES(" + stage.getCategoryID() + ",'" +
                        stage.getStageId() + "','" +
                        stage.getPoints() + "'," +
                        1 + ")";

        db.execSQL(InertQuery);

    }

    private void UpdateStageStatue(Stage stage) {
        String UpdateQuery =
                "UPDATE " + StageTable.TableName + " SET " +
                        StageTable.SatgePoints + " = " +
                        StageTable.SatgePoints +
                        "+" + stage.getPoints() +
                        "\n WHERE  " + StageTable.CategoryID + "=" + stage.getCategoryID() +
                          " and "+StageTable.StageID +"="+stage.getStageId() +";";

        db.execSQL(UpdateQuery);
        
    }

}
