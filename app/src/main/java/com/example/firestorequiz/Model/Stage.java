package com.example.firestorequiz.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.firestorequiz.Constant.FinalValues;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Stage {

    boolean isOpen;
    private int StageId;
    private String StageText;
    private String Image;

    public Stage() {
    }

    public Stage(int stageId, String stageText, String image, boolean isOpen) {
        StageId = stageId;
        StageText = stageText;
        Image = image;
        this.isOpen = isOpen;
    }

    public static ArrayList<Stage> CreateStages(Context context, int CategoryID) {
        ArrayList<Stage> Stages = new ArrayList<>();

        SharedPreferences prefs;
        prefs = context.getSharedPreferences("MyPref", MODE_PRIVATE);

        int points = prefs.getInt("Points" + CategoryID, 0);


        Stages.add(new Stage(1, RomanNumerals(1), "lock", true));


        if (points >= FinalValues.Stage2) {
            Stages.add(new Stage(2, RomanNumerals(2), "lock", true));
        } else {
            Stages.add(new Stage(2, RomanNumerals(2), "lock", false));
        }
        if (points >= FinalValues.Stage3) {
            Stages.add(new Stage(3, RomanNumerals(3), "lock", true));
        } else {
            Stages.add(new Stage(3, RomanNumerals(3), "lock", false));
        }
        if (points >= FinalValues.Stage4) {
            Stages.add(new Stage(4, RomanNumerals(4), "lock", true));
        } else {
            Stages.add(new Stage(4, RomanNumerals(4), "lock", false));
        }
        if (points >= FinalValues.Stage5) {
            Stages.add(new Stage(5, RomanNumerals(5), "lock", true));
        } else {
            Stages.add(new Stage(5, RomanNumerals(5), "lock", false));
        }
        if (points >= FinalValues.Stage6) {
            Stages.add(new Stage(6, RomanNumerals(6), "lock", true));
        } else {
            Stages.add(new Stage(6, RomanNumerals(6), "lock", false));
        }
        if (points >= FinalValues.Stage7) {
            Stages.add(new Stage(7, RomanNumerals(7), "lock", true));
        } else {
            Stages.add(new Stage(7, RomanNumerals(7), "lock", false));
        }
        if (points >= FinalValues.Stage8) {
            Stages.add(new Stage(8, RomanNumerals(8), "lock", true));
        } else {
            Stages.add(new Stage(8, RomanNumerals(8), "lock", false));
        }
        if (points >= FinalValues.Stage9) {
            Stages.add(new Stage(9, RomanNumerals(9), "lock", true));
        } else {
            Stages.add(new Stage(9, RomanNumerals(9), "lock", false));
        }
        if (points >= FinalValues.Stage10) {
            Stages.add(new Stage(10, RomanNumerals(10), "lock", true));
        } else {
            Stages.add(new Stage(10, RomanNumerals(10), "lock", false));
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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
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
