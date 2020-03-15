package com.kkfc.milkbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ReceiverCollection extends AppCompatActivity {

    DatabaseHelper db;
    AlertDialog.Builder builder;
    private Button cancelCollection;
    private Button saveCollection;
    private TextView transporterTextView;
    private TextView containerTextView;
    private TextView containerSizeTextView;
    private EditText quantityFull;
    private EditText quantityEmpty;
    private EditText comment;
    private double containerQuantity;
    private int transporterId;
    private int containerId;
    private String containerSize;
    private int receiverId;
    private RadioButton sniffPass, sniffFail, sniffNa, alcoholPass, alcoholFail, alcoholNa,densityTwoSeven, densityTwoEight, densityTwoNine, densityThirty, densityThirtyOnePlus, densityFail, densityNa ;
    private String sniffTest;
    private String alcoholTest;
    private String densityTest;
    private String dateToday;
    private String timeToday;
    private String wordComment;
    private Double quantityL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_collection);

        db = new DatabaseHelper(this);
        //Fetch the loggedInTransporter to show on the UI
        Cursor cursor = db.fetchLoggedInTransporter();
        //only one row in the table so use first row
        cursor.moveToFirst();
        transporterId = cursor.getInt(cursor.getColumnIndex(db.TRANSPORTER_ID));
        String loggedInTransporter = cursor.getString(cursor.getColumnIndex(db.TRANSPORTER_NAME));
        transporterTextView = findViewById(R.id.textView1);
        transporterTextView.setText("Transporter Name: " + loggedInTransporter );

        //Fetch the loggedInReceiver to show on the UI
        Cursor c = db.fetchLoggedInReceiver();
        //only one row in the table so use the first row
        c.moveToFirst();
        receiverId = c.getInt(c.getColumnIndex(db.RECEIVER_ID));


        Intent intent = getIntent();
        // Get the container ID, which was passed from the receiver home page
        containerId = intent.getIntExtra("containerId", 1);
        containerTextView = findViewById(R.id.textView2);
        containerTextView.setText("Container: " + containerId);

        // Set the container size
        containerSize = intent.getStringExtra("containerSize");
        containerSizeTextView = findViewById(R.id.textView6);
        containerSizeTextView.setText("Container Weight: ("+containerSize+" L)");

        // Save Collection Process
        saveCollection =findViewById(R.id.Button02);
        builder = new AlertDialog.Builder(this);
        saveCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityFull = findViewById(R.id.editText1);
                quantityEmpty = findViewById(R.id.editText2);
                comment = findViewById(R.id.editText3);
                String quantityFullLitre = quantityFull.getText().toString();
                String quantityEmptyLitre = quantityEmpty.getText().toString();
                wordComment = comment.getText().toString();

                //Gathering collection data from sniff test
                sniffPass = findViewById(R.id.radioButton1);
                sniffFail = findViewById(R.id.radioButton2);
                sniffNa = findViewById(R.id.radioButton3);
                if (sniffPass.isChecked()) {
                    sniffTest = sniffPass.getText().toString();
                } else if (sniffFail.isChecked()) {
                    sniffTest = sniffFail.getText().toString();
                } else if (sniffNa.isChecked()) {
                    sniffTest = sniffNa.getText().toString();
                }

                //Gathering collection data from alcohol test
                alcoholPass = findViewById(R.id.radioButton4);
                alcoholFail = findViewById(R.id.radioButton5);
                alcoholNa = findViewById(R.id.radioButton6);
                if (alcoholPass.isChecked()) {
                    alcoholTest = alcoholPass.getText().toString();
                } else if (alcoholFail.isChecked()) {
                    alcoholTest = alcoholFail.getText().toString();
                } else if (alcoholNa.isChecked()) {
                    alcoholTest = alcoholNa.getText().toString();
                }

                //Gathering collection data from density test
                densityTwoSeven = findViewById(R.id.radioButton7);
                densityTwoEight = findViewById(R.id.radioButton8);
                densityTwoNine = findViewById(R.id.radioButton9);
                densityThirty = findViewById(R.id.radioButton10);
                densityThirtyOnePlus = findViewById(R.id.radioButton11);
                densityFail = findViewById(R.id.radioButton12);
                densityNa = findViewById(R.id.radioButton13);
                if (densityTwoSeven.isChecked()) {
                    densityTest = densityTwoSeven.getText().toString();
                } else if (densityTwoEight.isChecked()) {
                    densityTest = densityTwoEight.getText().toString();
                } else if (densityTwoNine.isChecked()) {
                    densityTest = densityTwoNine.getText().toString();
                } else if (densityThirty.isChecked()) {
                    densityTest = densityThirty.getText().toString();
                } else if (densityThirtyOnePlus.isChecked()) {
                    densityTest = densityThirtyOnePlus.getText().toString();
                } else if(densityFail.isChecked()){
                    densityTest=densityFail.getText().toString();
                } else if (densityNa.isChecked()) {
                    densityTest = densityNa.getText().toString();
                }

                dateToday = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault()).format(new Date());
                timeToday = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

                if (quantityFullLitre.length() == 0) {
                    quantityFull.setError("Please record the weight of the full container");
                }
                if (quantityEmptyLitre.length() == 0) {
                    quantityEmpty.setError("Please record the weight of the empty container");
                }
                if(quantityFullLitre.length() != 0 && quantityEmptyLitre.length() != 0){
                    final Double quantityFullL = Double.parseDouble(quantityFullLitre);
                    final Double quantityEmptyL = Double.parseDouble(quantityEmptyLitre);
                    quantityL = quantityFullL - quantityEmptyL;
                    containerQuantity = Integer.parseInt(containerSize);

                    // Check container quantity to make sure container quantity is a positive number
                    if (quantityEmptyL > quantityFullL) {
                        quantityFull.setError("Weight of full container needs to be greater than weight of empty container");
                        quantityEmpty.setError("Weight of full container needs to be greater than weight of empty container");
                    } else {
                        if (quantityL > containerQuantity && (alcoholTest.equals("Fail") || sniffTest.equals("Fail") || densityTest.equals("Fail"))) {
                            overCapacityAndFailedTests();
                        } else if (quantityL > containerQuantity) {
                            overCapacity();
                        } else if (alcoholTest.equals("Fail") || sniffTest.equals("Fail") || densityTest.equals("Fail")) {
                            failedTest();
                        } else {
                            noFailedTestsAndUnderCapacity();
                        }
                    }
                }
            }
        });


        // Cancel Collection Process
        cancelCollection = findViewById(R.id.Button01);
        builder = new AlertDialog.Builder(this);
        cancelCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clicking the cancel button should do the same thing as clicking the back button
                onBackPressed();
            }
        });

    }

    private void overCapacityAndFailedTests() {
        builder.setMessage("You have failed quality test(s) and the quantity entered is greater than the container capacity. Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (alcoholTest.equals("Fail") || sniffTest.equals("Fail") || densityTest.equals("Fail") ) {
                            failedTest();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Collection Aborted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Milk Buddy");
        alert.show();
    }

    // Check recorded container quantity is not greater than actual container quantity
    private void overCapacity() {
        builder.setMessage("The quantity entered is greater than the container capacity. Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (alcoholTest.equals("Fail") || sniffTest.equals("Fail") || densityTest.equals("Fail") ) {
                            failedTest();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Collection Aborted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Milk Buddy");
        alert.show();
    }

    private void failedTest() {
        builder.setMessage("You have failed quality test(s). Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.insertReceiverCollection(containerId, transporterId, receiverId, quantityL, sniffTest, alcoholTest, densityTest, wordComment, dateToday, timeToday);
                        Toast.makeText(getApplicationContext(), "Receiver Collection Information Saved",
                                Toast.LENGTH_SHORT).show();
                        returnToReceiverHome();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Saving Aborted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Milk Buddy");
        alert.show();
    }

    private void noFailedTestsAndUnderCapacity() {
        builder.setMessage("Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.insertReceiverCollection(containerId, transporterId, receiverId, quantityL, sniffTest, alcoholTest, densityTest, wordComment, dateToday, timeToday);
                        Toast.makeText(getApplicationContext(), "Receiver Collection Information Saved",
                                Toast.LENGTH_SHORT).show();
                        returnToReceiverHome();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Collection Aborted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Milk Buddy");
        alert.show();
    }

    private void returnToReceiverHome() {
        Intent intent = new Intent(this, ReceiverHome.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        builder.setMessage("All unsaved data will be lost. Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Collection Cancelled",
                                Toast.LENGTH_SHORT).show();
                        returnToReceiverHome();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"Cancel Aborted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Milk Buddy");
        alert.show();
    }
}
