package com.example.vvats.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText student, college;
    ContentValues contentValues = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student = (EditText) findViewById(R.id.student);
        college = (EditText) findViewById(R.id.college);
    }

    public void doSave(View view) {
        contentValues.put("student_name", student.getText().toString());
        contentValues.put("college_name", college.getText().toString());

        Uri uri = getContentResolver().insert(OwnCustomContentProvider.CONTENT_URI, contentValues);
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void doLoad(View view) {
        Cursor cursor = getContentResolver().query(OwnCustomContentProvider.CONTENT_URI, null, null, null, OwnCustomContentProvider.COL_PRIMARY_KEY);
        StringBuilder stringBuilder = new StringBuilder();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String student = cursor.getString(1);
            String college = cursor.getString(2);
            stringBuilder.append(id + OwnCustomContentProvider.BLANK_SPACES + student + OwnCustomContentProvider.BLANK_SPACES + college + "\n");
        }
        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
    }
}
