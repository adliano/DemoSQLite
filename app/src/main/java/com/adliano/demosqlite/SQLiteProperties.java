package com.adliano.demosqlite;

import android.provider.BaseColumns;

/**
 * Created by adliano on 7/8/17.
 *
 *
 * In this approach we can create an inner class holding properties for each table
 *
 */

public final class SQLiteProperties
{
    // ****** Private Constructor ******* //
    private SQLiteProperties() {/*private to void instantiate by mistake*/ }

    public static final String DATABASE_NAME = "us_states.db";
    public static final int DATABASE_VERSION = 1;


    // Inner class to hold properties from States table
    // By implementing the android.provider.BaseColumns interface, your inner class can inherit a primary key
    // field called _ID that some Android classes such as cursor adaptors will expect it to have.
    // It's not required, but this can help your database work harmoniously with the Android framework.
    public static class StatesTablesProperties implements BaseColumns
    {
        public static final String TABLE_NAME = "table_states";
        public static final String COLUMN_STATES = "states";
        public static final String COLUMN_CAPITALS = "capitals";
        // query to create the States table
        public static final String QUERY_CREATE_STATES = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                "("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_STATES+ " TEXT NOT NULL," +
                COLUMN_CAPITALS+" TEXT NOT NULL)";
    }

}
