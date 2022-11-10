package com.example.docrecord;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    EditText name, branch, mobno;
    Button insert, find, view_all, delete, update;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        branch = findViewById(R.id.branch);
        mobno = findViewById(R.id.mobno);
        insert =findViewById(R.id.insertbtn);
        find = findViewById(R.id.findbtn);
        view_all = findViewById(R.id.viewallbtn);
        delete = findViewById(R.id.deletebtn);
        update = findViewById(R.id.updatebtn);

        db = openOrCreateDatabase("Doctor", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS docRecord(branch VARCHAR,name VARCHAR,mobno INTEGER);");


        insert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (branch.getText().toString().trim().length() == 0 ||
                        name.getText().toString().trim().length() == 0 ||
                        mobno.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO docRecord VALUES('" + branch.getText() + "','" + name.getText() +
                        "','" + mobno.getText() + "');");
                showMessage("Success", "Record added successfully");
                clearText();
            }
        });
        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (branch.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter branch");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM docRecord WHERE branch='" + branch.getText() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("DELETE FROM docRecord WHERE branch='" + branch.getText() + "'");
                    showMessage("Success", "Record Deleted");
                } else {
                    showMessage("Error", "Invalid Branch");
                }
                clearText();
            }
        });
        update.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (branch.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Branch");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM docRecord WHERE branch='" + branch.getText() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("UPDATE docRecord SET name='" + name.getText() + "',mobno='" + mobno.getText() +
                            "' WHERE branch='" + branch.getText() + "'");
                    showMessage("Success", "Record Modified");
                } else {
                    showMessage("Error", "Invalid Branch");
                }
                clearText();
            }
        });
        find.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (branch.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Branch");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM docRecord WHERE branch='" + branch.getText() + "'", null);
                if (c.moveToFirst()) {
                    name.setText(c.getString(1));
                    mobno.setText(c.getString(2));
                } else {
                    showMessage("Error", "Invalid Branch");
                    clearText();
                }
            }
        });
        view_all.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Cursor c = db.rawQuery("SELECT * FROM docRecord", null);
                if (c.getCount() == 0) {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("Branch: " + c.getString(0) + "\n");
                    buffer.append("Name: " + c.getString(1) + "\n");
                    buffer.append("MobNo.: " + c.getString(2) + "\n\n");
                }
                showMessage("Doctor Details", buffer.toString());
            }
        });

    }

    public void showMessage(String title, String message) {
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        branch.setText("");
        name.setText("");
        mobno.setText("");
        branch.requestFocus();
    }

}
