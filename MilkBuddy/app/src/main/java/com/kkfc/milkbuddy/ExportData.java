package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class ExportData extends AppCompatActivity {

    DatabaseHelper db;
    private static final int WRITE_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);

        db = new DatabaseHelper(this);

        // Create new document of type csv
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setType("text/csv")
                .putExtra(Intent.EXTRA_TITLE, "Transporter Milk Log " + date);
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WRITE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

                    Cursor cursor = db.fetchTransporterData();
                    if(cursor.getCount() == 0) {
                        Toast.makeText(this, "No data to export", Toast.LENGTH_LONG).show();
                    } else {
                        while(cursor.moveToNext()) {
                            // 1 is the column for first name
                             String blah = cursor.getString(1) + " " + cursor.getString(2);
                        }
                    }

                    // TODO

                    writer.write("DO I WORK?, YES");
                    
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast. makeText(getApplicationContext(),"Error exporting transporters!",Toast. LENGTH_LONG).show();
                }
            } else {
                Log.i("Export transpo. failed", data.toString());
            }
        }
    }

    @Override
    public void onBackPressed() {
        // TODO
    }

}
