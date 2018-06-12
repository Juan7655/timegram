package com.example.juandavid.timegram.database;

import android.provider.BaseColumns;

/**
 * Created by juandavid on 11/06/18.
 */

public final class ReaderContract {
    private ReaderContract(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "EVENTS";
        public static final String COLUMN_DATE = "DATE";
        public static final String COLUMN_OBJECTIVE = "OBJECTIVE";
        public static final String COLUMN_REAL = "REALTIME";
        public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
        public static final String COLUMN_CATEGORY = "CATEGORY";

        public static final String[] ALL_COLUMNS = {COLUMN_DATE,
                COLUMN_OBJECTIVE,
                COLUMN_REAL,
                COLUMN_DESCRIPTION,
                COLUMN_CATEGORY};
    }
}
