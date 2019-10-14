package com.example.firestorequiz.Contracts;

import android.provider.BaseColumns;

public final class CategoryContract {

    private CategoryContract() {
    }

    public static class CategoryTable implements BaseColumns {


        public static final String TableName = "Categories";

        public static final String CategoryName = "CategoryName";
        public static final String CategoryID = "CategoryID";
        public static final String CategoryPoints = "CategoryPoints";
        public static final String CategoryImage = "CategoryImage";
    }


}
