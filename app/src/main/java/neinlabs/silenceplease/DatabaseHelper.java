package neinlabs.silenceplease;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DatabaseHelper extends Activity {

    LinearLayout Linear;
    SQLiteDatabase mydb;
    private static String DBNAME = "PERSONS.db";    // THIS IS THE SQLITE DATABASE FILE NAME.
    private static String TABLE = "MY_TABLE";       // THIS IS THE TABLE NAME
    static String k = "MAX";
    static String l = "lel";
    static String n = null;
    public void setk(String kai){
        k = kai;
    }
    public void setl(String lay){
        l = lay;
    }
    public void setn(String name){
        n = name;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Linear  = (LinearLayout)findViewById(R.id.linear);
        createTable();
        insertIntoTable(n,k,l);
        Toast.makeText(getApplicationContext(), "Showing table values after updation.", Toast.LENGTH_SHORT).show();
        showTableValues();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            Toast.makeText(getApplicationContext(),"Deleting all locations",Toast.LENGTH_SHORT).show();
            dropTable();
            Linear.removeAllViews();
            showTableValues();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // THIS FUNCTION SETS COLOR AND PADDING FOR THE TEXTVIEWS
    public void setColor(TextView t){
        t.setTextColor(Color.BLACK);
        t.setPadding(20, 5, 0, 5);
        t.setTextSize(1, 15);
    }

    // CREATE TABLE IF NOT EXISTS
    public void createTable(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, NAME TEXT,LATITUDE TEXT,LONGITUDE TEXT);");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION INSERTS DATA TO THE DATABASE
    public void insertIntoTable(String name,String lat,String lon){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("INSERT INTO " + TABLE + "(NAME,LATITUDE,LONGITUDE) VALUES('"+ name +"','"+lat+"','"+lon+"');");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION SHOWS DATA FROM THE DATABASE
    public void showTableValues(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
            System.out.println("COUNT : " + allrows.getCount());
            Integer cindex = allrows.getColumnIndex("NAME");
            Integer cindex1 = allrows.getColumnIndex("LATITUDE");
            Integer cindex2 = allrows.getColumnIndex("LONGITUDE");

            TextView t = new TextView(this);
            t.setText("========================================");
            //Linear.removeAllViews();
            Linear.addView(t);

            if(allrows.moveToFirst()){
                do{
                    LinearLayout id_row   = new LinearLayout(this);
                    LinearLayout name_row = new LinearLayout(this);
                    LinearLayout lat_row= new LinearLayout(this);
                    LinearLayout lon_row= new LinearLayout(this);

                    final TextView id_  = new TextView(this);
                    final TextView name_ = new TextView(this);
                    final TextView lat_ = new TextView(this);
                    final TextView lon_ = new TextView(this);
                    final TextView   sep  = new TextView(this);

                    String ID = allrows.getString(0);
                    String NAME= allrows.getString(1);
                    String LATITUDE= allrows.getString(2);
                    String LONGITUDE= allrows.getString(3);

                    id_.setTextColor(Color.RED);
                    id_.setPadding(20, 5, 0, 5);
                    name_.setTextColor(Color.RED);
                    name_.setPadding(20, 5, 0, 5);
                    lat_.setTextColor(Color.RED);
                    lat_.setPadding(20, 5, 0, 5);
                    lon_.setTextColor(Color.RED);
                    lon_.setPadding(20, 5, 0, 5);

                    System.out.println("NAME " + allrows.getString(cindex) + " LATITUDE : "+ allrows.getString(cindex1)+ " LONGITUDE : "+ allrows.getString(cindex2));
                    System.out.println("ID : "+ ID  + " || NAME " + NAME + "|| LATITUDE : "+ LATITUDE+"||LONGITUDE: "+LONGITUDE);

                    id_.setText("ID : " + ID);
                    id_row.addView(id_);
                    Linear.addView(id_row);
                    name_.setText("NAME : "+NAME);
                    name_row.addView(name_);
                    Linear.addView(name_row);
                    lat_.setText("LATITUDE : " + LATITUDE);
                    lat_row.addView(lat_);
                    Linear.addView(lat_row);
                    lon_.setText("LONGITUDE : " +LONGITUDE);
                    lon_row.addView(lon_);
                    Linear.addView(lon_row);
                    sep.setText("---------------------------------------------------------------");
                    Linear.addView(sep);
                }
                while(allrows.moveToNext());
            }
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error encountered.", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION UPDATES THE DATABASE ACCORDING TO THE CONDITION
    public void updateTable(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("UPDATE " + TABLE + " SET NAME = '"+k+"' WHERE PLACE = 'JAPAN'");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error encountered", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION DELETES VALUES FROM THE DATABASE ACCORDING TO THE CONDITION
    public void deleteValues(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("DELETE FROM " + TABLE + " WHERE PLACE = 'USA'");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error encountered while deleting.", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNTION DROPS A TABLE
    public void dropTable(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("DROP TABLE " + TABLE);
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error encountered while dropping.", Toast.LENGTH_LONG);
        }
    }
}