package com.example.firestorequiz.Contracts;

import android.provider.BaseColumns;

public final class StageContract   {

    public StageContract() {
    }

    public static class StageTable implements BaseColumns {



        public static final String TableName = "Satge";

        public static final String ID = "ID";

        public static final String StageID = "StageID";
        public static final String CategoryID = "CategoryID";
        public static final String SatgePoints = "StagePoints";
        public static final String IsOpen = "IsOpen";
    }


}
