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
    String stateName = null;
    String capitalName = null;

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
        database.setVersion(SQLiteProperties.DATABASE_VERSION);

        //*** 2) create table(s) ********/
        database.execSQL(SQLiteProperties.StatesTablesProperties.QUERY_CREATE_STATES);

        //*** 3) populate table(s)*******/
        // there is 2 ways to populate the table
        // A) Using void execSQL (String sql) method
        database.execSQL("INSERT INTO "+ SQLiteProperties.StatesTablesProperties.TABLE_NAME
                + "("+ SQLiteProperties.StatesTablesProperties.COLUMN_STATES+", "
                + SQLiteProperties.StatesTablesProperties.COLUMN_CAPITALS+")"
                + " VALUES ('california', 'sacramento');");

        // B) Using android.content.ContentValues (Better Approach)
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteProperties.StatesTablesProperties.COLUMN_STATES,"Alaska");
        contentValues.put(SQLiteProperties.StatesTablesProperties.COLUMN_CAPITALS,"Juneau");
        long newRowsId = database.insert(SQLiteProperties.StatesTablesProperties.TABLE_NAME,null,contentValues);

        /**** 4) checking if tables is populated *********/
        Cursor c = database.rawQuery("select * from " + SQLiteProperties.StatesTablesProperties.TABLE_NAME, null);

        int indexOfStates = c.getColumnIndex(SQLiteProperties.StatesTablesProperties.COLUMN_STATES);
        int indexOfCapitals = c.getColumnIndex(SQLiteProperties.StatesTablesProperties.COLUMN_CAPITALS);

        String temp = "";

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            stateName = c.getString(indexOfStates);      // 0 means the first field
            capitalName = c.getString(indexOfCapitals);  // 1 means the 2nd field

            temp += stateName +" - "+ capitalName+"\n";
        }
        c.close();

        /*** display in a TextView ******/

        textView=(TextView)findViewById(R.id.tv);
        textView.setText(temp);
    }
}
/******* END *********/