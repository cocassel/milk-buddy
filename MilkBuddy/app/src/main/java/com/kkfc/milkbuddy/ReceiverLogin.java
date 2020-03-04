package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReceiverLogin extends AppCompatActivity {

    DatabaseHelper db;
    private Button loginReceiver;
    private Button cancelButton;
    private EditText usernameField;
    private EditText passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_login);

        db = new DatabaseHelper(this);



        cancelButton = findViewById(R.id.Button01);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pressing the cancel button does the same thing as pressing the back button
                onBackPressed();
            }
        });

        loginReceiver = findViewById(R.id.Button02);
        loginReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO show popup if empty fields and disallow moving forward

                // Get text from username and password fields
                usernameField = findViewById(R.id.editText1);
                passwordField = findViewById(R.id.editText2);
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                Cursor authenticatedReceivers = db.checkReceiverLoginCredentials(username, password);
                // If there is at least one matching entry in the db, user is authenticated
                if(authenticatedReceivers.getCount() >= 1) {
                    authenticatedReceivers.moveToFirst();
                    goToReceiverHomepage();
                    // TODO add to logged in receiver table
                    int loggedInId = authenticatedReceivers.getInt(authenticatedReceivers.getColumnIndex(db.RECEIVER_ID));
                    String loggedInName = authenticatedReceivers.getString(authenticatedReceivers.getColumnIndex(db.RECEIVER_NAME));
                    String loggedInPhoneNumber = authenticatedReceivers.getString(authenticatedReceivers.getColumnIndex(db.RECEIVER_PHONE_NUMBER));

                    db.insertLoggedInReceiver(loggedInId, loggedInName, loggedInPhoneNumber);
                } else {

                }


            }
        });
    }

    private void goToReceiverHomepage() {
        Intent intent = new Intent(this, ReceiverHome.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // We allow the user to go back to farmer search/collection as long as no receiver is
        // logged-in yet. Once a receiver logs in, farmer search/collection will no longer be accessible
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }
}
