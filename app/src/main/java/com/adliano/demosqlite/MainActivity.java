package com.adliano.demosqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;


public class MainActivity extends Activity
{
    TextView textView;
    SQLiteDatabase database;
//    String tableName = "myTableTest";
//    String queryCreateTable = "create table if not exists "+tableName+
//                              "(_id integer primary key autoincrement,"+
//                                "states text not null,capitals text not null);";

    String test = SQLiteProperties.StatesTablesProperties.TABLE_NAME;

    String gettingState = null;
    String gettingCapital = null;

    /****************** onCreate() *********************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        /*================= SQLite ==================*/

        //*** 1) create the database ******/
        database = this.openOrCreateDatabase(SQLiteProperties.DATABASE_NAME,SQLiteDatabase.CREATE_IF_NECESSARY,null);
//        database=this.openOrCreateDatabase(databaseName,SQLiteDatabase.CREATE_IF_NECESSARY,null);
       // database.setLockingEnabled(true);
        database.setVersion(SQLiteProperties.DATABASE_VERSION);

        //*** 2) create table(s) ********/
        database.execSQL(SQLiteProperties.StatesTablesProperties.QUERY_CREATE_STATES);
//        database.execSQL(queryCreateTable);

        //*** 3) populate table(s)*******/
        database.execSQL("INSERT INTO "+ SQLiteProperties.StatesTablesProperties.TABLE_NAME
                       + "(states, capitals)"
                       + " VALUES ('california', 'sacramento');");

      //  database.execSQL("INSERT INTO "+tableName
      //          + "(states, capitals)"
      //          + " VALUES ('alabama', 'montgomery');");

///*
        ContentValues contentValues = new ContentValues();

        contentValues.put("states","Alabama");
        contentValues.put("capitals","Montgomery");
        contentValues.put("states","Alaska");
        contentValues.put("capitals","Juneau");
        long newRowsId = database.insert(SQLiteProperties.StatesTablesProperties.TABLE_NAME,null,contentValues);
//*/
        /**** 4) checking if tables is populated *********/
        Cursor c = database.rawQuery("select * from " + SQLiteProperties.StatesTablesProperties.TABLE_NAME, null);

        int indexOfStates = c.getColumnIndex("states");
        int indexOfCapitals = c.getColumnIndex("capitals");

       // c.moveToFirst(); //moving cursor to first line

        //while(c.moveToNext())
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            gettingState = c.getString(indexOfStates);      // 0 means the first field
            gettingCapital = c.getString(indexOfCapitals);  // 1 means the 2nd field
        }
        c.close();

        /*** display in a TextView ******/
        textView=(TextView)findViewById(R.id.tv);
        textView.setText(""+indexOfStates+" | "+ gettingState +"\n"
                           +indexOfCapitals+" | "+ gettingCapital);
    }
}
/******* END *********/