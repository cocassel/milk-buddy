package com.kkfc.milkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GuestTransporter extends AppCompatActivity {

    DatabaseHelper db;
    private Button cancelGuestTransporter;
    private Button loginGuestTransporter;
    private EditText guestTransporterName;
    private EditText guestTransporterPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_transporter);

        db = new DatabaseHelper(this);

        cancelGuestTransporter = findViewById(R.id.Button01);
        cancelGuestTransporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel button should do the same thing as back button
                onBackPressed();
            }
        });

        loginGuestTransporter = findViewById(R.id.Button02);
        loginGuestTransporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save guest transporter info to database
                guestTransporterName = findViewById(R.id.editText1);
                guestTransporterPhoneNumber = findViewById(R.id.editText2);

                if(guestTransporterName.getText().toString().length()==0 ){
                    guestTransporterName.setError("Please input a name");
                } else if (guestTransporterPhoneNumber.getText().toString().length()==0){
                    guestTransporterPhoneNumber.setError("Please input a phone number");
                }else {
                    db.insertLoggedInGuestTransporter(
                            guestTransporterName.getText().toString(),
                            guestTransporterPhoneNumber.getText().toString()
                    );
                    goToTransporterHomepage();
                }
            }
        });


    }

    private void goToTransporterHomepage() {
        Intent intent = new Intent(this, TransporterContainerSelection.class);
        startActivity(intent);
    }

    private void goBackTransporterLogin() {
        Intent intent = new Intent(this, TransporterLogin.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goBackTransporterLogin();
    }

}
