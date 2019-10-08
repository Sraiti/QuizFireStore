package com.example.firestorequiz.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Stage {

    private int StageId;
    private String StageText;
    private String Image;
    boolean isOpen;

    public Stage() {
    }

    public Stage(int stageId, String stageText, String image, boolean isOpen) {
        StageId = stageId;
        StageText = stageText;
        Image = image;
        this.isOpen = isOpen;
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

    public static ArrayList<Stage> CreatStages(Context context) {
        ArrayList<Stage> Stages = new ArrayList<>();
//        int imageResID = context.getResources().getIdentifier("lock", "drawable",
//                context.getPackageName());
//        ImageView image=new ImageView(context);
//        image.setImageResource(imageResID);
        for (int i = 1; i <= 10; i++) {
            if (i==1){
                Stages.add(new Stage(i,RomanNumerals(i), "lock",true));
            }else{
                Stages.add(new Stage(i,RomanNumerals(i), "lock",false));
            }
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
}
