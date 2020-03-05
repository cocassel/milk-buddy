package com.kkfc.milkbuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReceiverLogin extends AppCompatActivity {

    DatabaseHelper db;
    private Button forgotPasswordButton;
    private Button loginReceiver;
    private Button cancelButton;
    private EditText usernameField;
    private EditText passwordField;
    AlertDialog.Builder builder;
    private Button resetButton;
    private Button dropOffToReceiverButton;

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
                // Get text from username and password fields
                usernameField = findViewById(R.id.editText1);
                passwordField = findViewById(R.id.editText2);
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                Cursor authenticatedReceivers = db.checkReceiverLoginCredentials(username, password);
                // If there is at least one matching entry in the db, user is authenticated
                if(authenticatedReceivers.getCount() >= 1) {
                    goToReceiverHomepage();
                    authenticatedReceivers.moveToFirst();
                    int loggedInId = authenticatedReceivers.getInt(authenticatedReceivers.getColumnIndex(db.RECEIVER_ID));
                    String loggedInName = authenticatedReceivers.getString(authenticatedReceivers.getColumnIndex(db.RECEIVER_NAME));
                    String loggedInPhoneNumber = authenticatedReceivers.getString(authenticatedReceivers.getColumnIndex(db.RECEIVER_PHONE_NUMBER));

                    db.insertLoggedInReceiver(loggedInId, loggedInName, loggedInPhoneNumber);
                } else {
                    invalidCredentialsPopup();
                }
            }
        });

        // Forgot password popup
        forgotPasswordButton = findViewById(R.id.Button03);
        builder = new AlertDialog.Builder(this);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Please talk to the admin to reset your password.")
                        .setCancelable(false)
                        .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'Okay' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Milk Buddy");
                alert.show();
            }

        });
    }

    private void invalidCredentialsPopup() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enter valid log-in credentials")
                .setCancelable(false)
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Milk Buddy");
        alert.show();
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
