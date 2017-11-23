package com.atilano.sqliteactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;
import android.widget.Toast;

/**
 * Created by Jeanina on 23 Nov 2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public DBHelper(Context context){
        super(context, "grades.sqlite", null, 1);
        this.context = context;
        //SQLiteDatabase database = this.getWritableDatabase();
        Toast.makeText(context, "Constructor executed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE grades (_ID INTEGER PRIMARY KEY AUTOINCREMENT, FNAME TEXT, LNAME TEXT, GRADE TEXT)";
        db.execSQL(query);
        Toast.makeText(context, "Table created successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il){
        db.execSQL("DROP TABLE IF EXISTS grades");
        onCreate(db);
    }

    public boolean insertData(String fname, String lname, String grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("grade", grade);
        long result = db.insert("grades", null, cv);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(String id, String fname, String lname, String grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("grade", grade);

        db.update("grades", cv, "_id=?", new String[]{ id });
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM grades", null);
    }

   public Integer deleteData(String id){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.delete("grades", "_id=?", new String[]{id});
   }
}
