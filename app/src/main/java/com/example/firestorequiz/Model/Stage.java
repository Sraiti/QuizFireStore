package com.example.firestorequiz.Model;

import android.content.Context;

import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.DB.CategoryDbHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Stage {

    int isOpen;
    private int StageId;
    private String StageText;
    private String Image;
    private int Points;
    private int CategoryID;

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public Stage() {
    }

    public Stage(int stageId, String stageText, String image, int isOpen) {
        StageId = stageId;
        StageText = stageText;
        Image = image;
        this.isOpen = isOpen;
    }
    public Stage(int stageId, int CategoryID,int Points,int isOpen) {
        StageId = stageId;
        this.CategoryID=CategoryID;
        this.Points=Points;
        this.isOpen = isOpen;
    }
    public static ArrayList<Stage> CreateStages(Context context, int CategoryID) {


        ArrayList<Stage> Stages = new ArrayList<>();

        CategoryDbHelper categoryDbHelper = new CategoryDbHelper(context);

        Stages.add(new Stage(1, RomanNumerals(1), "lock", 1));

        int stage2state =categoryDbHelper.getsatgeState(2,CategoryID);
        int stage3state =categoryDbHelper.getsatgeState(3,CategoryID);
        int stage4state =categoryDbHelper.getsatgeState(4,CategoryID);
        int stage5state =categoryDbHelper.getsatgeState(5,CategoryID);
        int stage6state =categoryDbHelper.getsatgeState(6,CategoryID);
        int stage7state =categoryDbHelper.getsatgeState(7,CategoryID);
        int stage8state =categoryDbHelper.getsatgeState(8,CategoryID);
        int stage9state =categoryDbHelper.getsatgeState(9,CategoryID);
        int stage10state =categoryDbHelper.getsatgeState(10,CategoryID);

        if (stage2state==1) {
            Stages.add(new Stage(2, RomanNumerals(2), "lock", 1));
        } else {
            Stages.add(new Stage(2, RomanNumerals(2), "lock", 0));
        }
        if (stage3state==1) {
            Stages.add(new Stage(3, RomanNumerals(3), "lock", 1));
        } else {
            Stages.add(new Stage(3, RomanNumerals(3), "lock", 0));
        }
        if (stage4state==1) {
            Stages.add(new Stage(4, RomanNumerals(4), "lock", 1));
        } else {
            Stages.add(new Stage(4, RomanNumerals(4), "lock", 0));
        }
        if (stage5state==1) {
            Stages.add(new Stage(5, RomanNumerals(5), "lock", 1));
        } else {
            Stages.add(new Stage(5, RomanNumerals(5), "lock", 0));
        }
        if (stage6state==1) {
            Stages.add(new Stage(6, RomanNumerals(6), "lock", 1));
        } else {
            Stages.add(new Stage(6, RomanNumerals(6), "lock", 0));
        }
        if (stage7state==1) {
            Stages.add(new Stage(7, RomanNumerals(7), "lock", 1));
        } else {
            Stages.add(new Stage(7, RomanNumerals(7), "lock", 0));
        }
        if (stage8state==1) {
            Stages.add(new Stage(8, RomanNumerals(8), "lock", 1));
        } else {
            Stages.add(new Stage(8, RomanNumerals(8), "lock", 0));
        }
        if (stage9state==1) {
            Stages.add(new Stage(9, RomanNumerals(9), "lock", 1));
        } else {
            Stages.add(new Stage(9, RomanNumerals(9), "lock", 0));
        }
        if (stage10state==1) {
            Stages.add(new Stage(10, RomanNumerals(10), "lock", 1));
        } else {
            Stages.add(new Stage(10, RomanNumerals(10), "lock", 0));
        }

        return Stages;
    }

    ///Roman Numbers
    public static String RomanNumerals(int Int) {
        LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
        roman_numerals.put("M", 1000);
        roman_numerals.put("CM", 900);
        roman_numerals.put("D", 500);
        roman_numerals.put("CD", 400);
        roman_numerals.put("C", 100);
        roman_numerals.put("XC", 90);
        roman_numerals.put("L", 50);
        roman_numerals.put("XL", 40);
        roman_numerals.put("X", 10);
        roman_numerals.put("IX", 9);
        roman_numerals.put("V", 5);
        roman_numerals.put("IV", 4);
        roman_numerals.put("I", 1);
        String res = "";
        for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
            int matches = Int / entry.getValue();
            res += repeat(entry.getKey(), matches);
            Int = Int % entry.getValue();
        }
        return res;
    }

    public static String repeat(String s, int n) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public int isOpen() {
        return isOpen;
    }

    public void setOpen(int open) {
        isOpen = open;
    }

    public int getStageId() {
        return StageId;
    }

    public void setStageId(int stageId) {
        StageId = stageId;
    }

    public String getStageText() {
        return StageText;
    }

    public void setStageText(String stageText) {
        StageText = stageText;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
