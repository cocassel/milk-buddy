package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ImportTransporters extends AppCompatActivity {

    DatabaseHelper db;
    private Button importButton;
    private Button keepExistingButton;
    private int STORAGE_PERMISSION_CODE = 200;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_transporters);
        db = new DatabaseHelper(this);

        importButton = findViewById(R.id.button1);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ImportTransporters.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                } else {
                    ActivityCompat.requestPermissions(ImportTransporters.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                }
                performFileSearch();

            }
        });

        keepExistingButton = findViewById(R.id.button2);
        keepExistingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueWithExistingTransporterData();
            }
        });

    }

    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO add error handling

        if (requestCode == READ_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // User picks the file
                Uri uri = data.getData();
                // TODO test to make sure they chose the right csv and the data is formatted right
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    db.insertTransportersFromCSV(reader);
                    inputStream.close();
                    Toast. makeText(getApplicationContext(),"Transporters have been imported!",Toast. LENGTH_LONG).show();
                    Intent intent = new Intent(this, ImportReceivers.class);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast. makeText(getApplicationContext(),"Error importing transporters!",Toast. LENGTH_LONG).show();
                    // TODO does this work? (reloading the same activity)
                    Intent intent = new Intent(this, ImportTransporters.class);
                    startActivity(intent);
                }

            } else {
                Log.i("Import transpo. failed", data.toString());
            }
        }
    }

    private void continueWithExistingTransporterData() {
        // TODO move export functionality somewhere else
        //Comment the following line out to test export
        Intent intent = new Intent(this, ImportReceivers.class);
        // Uncomment the following line to test export
        //Intent intent = new Intent(this, ExportData.class);
        startActivity(intent);
    }

}
