package com.atilano.sqliteactivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase dbase;
    EditText etFName, etLName, etFGrade, etStudID;
    Button btnSaveRecord, btnSaveUpdate, btnAdd, btnEdit, btnDelete, btnFirst, btnPrevious, btnNext, btnLast;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);
        dbase = helper.getWritableDatabase();
        res = helper.getAllData();

        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
        etFGrade = (EditText) findViewById(R.id.etFGrade);
        etStudID = (EditText) findViewById(R.id.etStudID);
        btnSaveRecord = (Button) findViewById(R.id.btnSaveRecord);
        btnSaveUpdate = (Button) findViewById(R.id.btnSaveUpdate);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnFirst = (Button) findViewById(R.id.btnFirst);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnLast = (Button) findViewById(R.id.btnLast);
    }

    public void saveRecord(View view){
        String fname, lname, grade;
        fname = etFName.getText().toString();
        lname = etLName.getText().toString();
        grade = etFGrade.getText().toString();
        boolean isInserted = helper.insertData(fname, lname, grade);

        if (isInserted == true) {
            Toast.makeText(this, "Data inserted.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data insertion failed.", Toast.LENGTH_SHORT).show();
        }

        res.close();
        res = helper.getAllData();

    }

    public void saveUpdate(View view){
        String id, fname, lname, grade;
        id = etStudID.getText().toString();
        fname = etFName.getText().toString();
        lname = etLName.getText().toString();
        grade = etFGrade.getText().toString();

        helper.updateData(id,fname,lname,grade);
        res.close();
        res = helper.getAllData();
        res.move(Integer.parseInt(id));
    }

    public void add(View view){

    }

    public void delete(View view){
        String id = etStudID.getText().toString();
        Integer deleted = helper.deleteData(id);

        if(deleted > 0){
            Toast.makeText(this, "ID No. " + id + "is deleted", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Deletion failed", Toast.LENGTH_SHORT).show();
        }

        res.close();
        res = helper.getAllData();
        res.moveToLast();
        displayRecord();
    }

    private void displayRecord(){
        etStudID.setText(res.getString(0));
        etFName.setText(res.getString(1));
        etLName.setText(res.getString(2));
        etFGrade.setText(res.getString(3));
    }

    public void first(View view){
        if (res.getCount() > 0){
            res.moveToFirst();
            Toast.makeText(this, "First Record", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show();
        }
    }

    public void previous(View view){
        if (res.getCount() > 0){
            res.moveToPrevious();
        }
    }

    public void next(View view){
        if (res.getCount() > 0){
            res.moveToNext();
        }
    }

    public void last(View view){
        if (res.getCount() > 0){
            res.moveToLast();
            Toast.makeText(this, "Last Record", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show();
        }
    }

}
