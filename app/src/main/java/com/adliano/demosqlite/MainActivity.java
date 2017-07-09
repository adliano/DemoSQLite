package com.adliano.demosqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity
{
    TextView textView;
    AppCompatEditText edCity, edState;
    SQLiteDatabase database;
    String stateName = null;
    String capitalName = null;

    /****************** onCreate() *********************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*********** text inputs *********//
        edCity = (AppCompatEditText)findViewById(R.id.ed_city_id);
        edState = (AppCompatEditText)findViewById(R.id.ed_state_id);
        // ********** text output ************ //
        textView=(TextView)findViewById(R.id.tv);

        createSQLiDatabase();
    }

    // Method to create the SQL database and create the table
    private void createSQLiDatabase()
    {
        /*================= SQLite ==================*/
        //*** 1) create the database ******/
        database = this.openOrCreateDatabase(SQLiteProperties.DATABASE_NAME,SQLiteDatabase.CREATE_IF_NECESSARY,null);
        database.setVersion(SQLiteProperties.DATABASE_VERSION);

        //*** 2) create table(s) ********/
        database.execSQL(SQLiteProperties.StatesTablesProperties.QUERY_CREATE_STATES);

        //*** 3) populate table(s)*******/
        // See addSQLiteData() Method
    }

    // OnClick event for the Add Button , thi method will add data into SQLite database
    public void addSQLiteData(View view)
    {

        if(!String.valueOf(edCity.getText()).isEmpty() && !String.valueOf(edState.getText()).isEmpty())
        {
            //*** 3) populate table(s)*******/
            // there is 2 ways to populate the table
            // A) Using void execSQL (String sql) method
            //database.execSQL("INSERT INTO "+ SQLiteProperties.StatesTablesProperties.TABLE_NAME
            //        + "("+ SQLiteProperties.StatesTablesProperties.COLUMN_STATES+", "
            //        + SQLiteProperties.StatesTablesProperties.COLUMN_CITIES+")"
            //        + " VALUES ('california', 'sacramento');");

            // B) Using android.content.ContentValues (Better Approach)
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQLiteProperties.StatesTablesProperties.COLUMN_STATES,String.valueOf(edState.getText()));
            contentValues.put(SQLiteProperties.StatesTablesProperties.COLUMN_CITIES,String.valueOf(edCity.getText()));
            long newRowsId = database.insert(SQLiteProperties.StatesTablesProperties.TABLE_NAME,null,contentValues);
            // show newRowsId just for debug
            Toast.makeText(getBaseContext(),String.valueOf(newRowsId),Toast.LENGTH_SHORT).show();

            // **** 4) checking if tables is populated ********* //
            Cursor c = database.rawQuery("select * from " + SQLiteProperties.StatesTablesProperties.TABLE_NAME, null);

            int indexOfStates = c.getColumnIndex(SQLiteProperties.StatesTablesProperties.COLUMN_STATES);
            int indexOfCapitals = c.getColumnIndex(SQLiteProperties.StatesTablesProperties.COLUMN_CITIES);

            String temp = "";

            for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
            {
                stateName = c.getString(indexOfStates);      // 0 means the first field
                capitalName = c.getString(indexOfCapitals);  // 1 means the 2nd field

                temp += stateName +" - "+ capitalName+"\n";
            }
            c.close();

            // *** display in a TextView ****** //
            textView.setText(temp);
        }
        else Toast.makeText(getBaseContext(), R.string.check_fields,Toast.LENGTH_LONG).show();
    }
    // OnClick event to clear data, this method will remove data from SQLite database and remove text from TextView
    public void clearData(View view)
    {
        database.execSQL(SQLiteProperties.StatesTablesProperties.QUERY_DROP_STATES);
        textView.setText(null);
        // re create a empty SQL database
        createSQLiDatabase();
    }
}
//******* END *********//