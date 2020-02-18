package com.kkfc.milkbuddy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ImportFarmers extends AppCompatActivity {

    DatabaseHelper db;
    private Button importButton;
    private Button keepExistingButton;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_farmers);
        db = new DatabaseHelper(this);

        importButton = findViewById(R.id.button1);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();

            }
        });

        keepExistingButton = findViewById(R.id.button2);
        keepExistingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueWithExistingFarmerData();
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
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    db.insertFarmersFromCSV(reader);
                    inputStream.close();
                    Toast. makeText(getApplicationContext(),"Farmers have been imported!",Toast. LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast. makeText(getApplicationContext(),"Error importing farmers!",Toast. LENGTH_LONG).show();
                    Intent intent = new Intent(this, ImportFarmers.class);
                    startActivity(intent);
                }
                Intent intent = new Intent(this, TransporterLogin.class);
                startActivity(intent);
            } else {
                // If back button is pressed (no file is chosen), go back to same page
                Intent intent = new Intent(this, ImportFarmers.class);
                startActivity(intent);
            }
        }
    }

    private void continueWithExistingFarmerData() {
        Intent intent = new Intent(this, TransporterLogin.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // TODO
    }
}
