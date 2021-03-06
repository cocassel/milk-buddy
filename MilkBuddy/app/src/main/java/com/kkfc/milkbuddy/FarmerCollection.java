package com.kkfc.milkbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FarmerCollection extends AppCompatActivity {


    DatabaseHelper db;
    AlertDialog.Builder builder;
    private Button cancelCollection;
    private Button saveCollection;
    private String farmerName;
    private int farmerId;
    private int transporterId;
    private int containerId;
    private double quantityLeftContainer;
    private TextView nameFarmer;
    private EditText quantity;
    private EditText comment;
    private RadioButton sniffPass, sniffFail, sniffNa, alcoholPass, alcoholFail, alcoholNa,densityTwoSeven, densityTwoEight, densityTwoNine, densityThirty, densityThirtyOnePlus, densityFail, densityNa ;
    private String sniffTest;
    private String alcoholTest;
    private String densityTest;
    private String dateToday;
    private String timeToday;
    SimpleCursorAdapter containerCursorAdapter;
    private Spinner containerSpinnerView;
    private RadioGroup radioSniffTestGroup;
    private RadioGroup radioAlcoholTestGroup;
    private RadioGroup radioDensityTestGroup;
    SharedPreferences states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_collection);

        db = new DatabaseHelper(this);
        Cursor cursor = db.fetchLoggedInTransporter();
        //only one row in the table so use first row
        cursor.moveToFirst();
        transporterId = cursor.getInt(cursor.getColumnIndex(db.TRANSPORTER_ID));

        Intent intent = getIntent();
        // Get the farmer ID, which was passed from the farmer search activity page
        farmerId = intent.getIntExtra("farmerId", 1);
        farmerName = intent.getStringExtra("farmerName");
        nameFarmer = findViewById(R.id.textView1);
        nameFarmer.setText("Farmer Name: " + farmerName);


        String [] containerAdapterCols = new String[]{"container_dropdown"};
        int[] containerAdapterRowViews=new int[]{android.R.id.text1};

        Cursor containerCursor = db.fetchConcatContainerInfo();

        containerCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, containerCursor, containerAdapterCols, containerAdapterRowViews, 0);
        containerCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        containerSpinnerView = findViewById(R.id.Spinner1);
        containerSpinnerView.setAdapter(containerCursorAdapter);

        // Retrieve saved state (current selection) of container dropdown
        states = getSharedPreferences("states", Context.MODE_PRIVATE);
        // Get prior dropdown selection (if applicable)
        int selectedDropdownPosition = states.getInt("selectedDropdownPosition2", 0);
        //Set dropdown selection to prior selection
        containerSpinnerView.setSelection(selectedDropdownPosition);

        containerSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
                c.moveToPosition(position);
                containerId = c.getInt(c.getColumnIndex(db.CONTAINER_ID));
                quantityLeftContainer=c.getDouble(c.getColumnIndex((db.CONTAINER_AMOUNT_REMAINING)));
                saveState();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Greying out container dropdown when sniff, alcohol, or density tests fail
        radioSniffTestGroup = findViewById(R.id.radioGroup1);
        radioSniffTestGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               enableOrDisableContainerDropdown();
           }
        });

        // Greying out container dropdown when sniff, alcohol, or density tests fail
        radioAlcoholTestGroup = findViewById(R.id.radioGroup2);
        radioAlcoholTestGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                enableOrDisableContainerDropdown();
            }
        });

        // Greying out container dropdown when sniff, alcohol, or density tests fail
        radioDensityTestGroup = findViewById(R.id.radioGroup3);
        radioDensityTestGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                enableOrDisableContainerDropdown();
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

        // Save Collection Process
        saveCollection = findViewById(R.id.Button02);
        builder = new AlertDialog.Builder(this);
        saveCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = findViewById(R.id.editText1);
                comment = findViewById(R.id.editText2);
                String quantityLitre = quantity.getText().toString();
                final String wordComment = comment.getText().toString();

                //Gathering collection data from sniff test
                sniffPass = findViewById(R.id.radioButton1);
                sniffFail = findViewById(R.id.radioButton2);
                sniffNa = findViewById(R.id.radioButton3);
                if(sniffPass.isChecked()){
                    sniffTest = sniffPass.getText().toString();
                } else if(sniffFail.isChecked()){
                    sniffTest=sniffFail.getText().toString();
                } else if(sniffNa.isChecked()){
                    sniffTest=sniffNa.getText().toString();
                }

                //Gathering collection data from alcohol test
                alcoholPass = findViewById(R.id.radioButton4);
                alcoholFail = findViewById(R.id.radioButton5);
                alcoholNa = findViewById(R.id.radioButton6);
                if(alcoholPass.isChecked()){
                    alcoholTest = alcoholPass.getText().toString();
                } else if(alcoholFail.isChecked()){
                    alcoholTest=alcoholFail.getText().toString();
                } else if(alcoholNa.isChecked()){
                    alcoholTest=alcoholNa.getText().toString();
                }

                //Gathering collection data from density test
                densityTwoSeven = findViewById(R.id.radioButton7);
                densityTwoEight = findViewById(R.id.radioButton8);
                densityTwoNine = findViewById(R.id.radioButton9);
                densityThirty = findViewById(R.id.radioButton10);
                densityThirtyOnePlus = findViewById(R.id.radioButton11);
                densityFail = findViewById(R.id.radioButton12);
                densityNa = findViewById(R.id.radioButton13);
                if(densityTwoSeven.isChecked()){
                    densityTest = densityTwoSeven.getText().toString();
                } else if(densityTwoEight.isChecked()){
                    densityTest=densityTwoEight.getText().toString();
                } else if(densityTwoNine.isChecked()){
                    densityTest=densityTwoNine.getText().toString();
                } else if(densityThirty.isChecked()){
                    densityTest=densityThirty.getText().toString();
                } else if(densityThirtyOnePlus.isChecked()){
                    densityTest=densityThirtyOnePlus.getText().toString();
                } else if(densityFail.isChecked()){
                    densityTest=densityFail.getText().toString();
                } else if(densityNa.isChecked()){
                    densityTest=densityNa.getText().toString();
                }


                dateToday = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault()).format(new Date());
                timeToday = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
                if(quantityLitre.length()==0){
                    quantity.setError("Please collect milk quantity, otherwise click 'Cancel'");
                } else {
                    final Double quantityL = Double.parseDouble(quantityLitre);
                    if(alcoholTest.equals("Fail") || sniffTest.equals("Fail") || densityTest.equals("Fail") ){
                        // Recorded milk collection with failed tests
                        containerId = -1;
                        builder.setMessage("Are you sure you want to save farmer collection with failed test?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.insertFarmerCollection(farmerId, transporterId, containerId, quantityL, sniffTest, alcoholTest, densityTest, wordComment, dateToday, timeToday);
                                        Toast.makeText(getApplicationContext(), "Collection Information Saved",
                                                Toast.LENGTH_SHORT).show();
                                        returnToFarmerSearch();
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
                    } else {
                        if (quantityL > quantityLeftContainer) {
                            // Warning for going over capacity
                            builder.setMessage("Are you sure you want to proceed with current container? According to our records, the quantity of milk being collected is greater than remaining capacity")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            quantityLeftContainer = quantityLeftContainer - quantityL;
                                            db.insertFarmerCollection(farmerId, transporterId, containerId, quantityL, sniffTest, alcoholTest, densityTest, wordComment, dateToday, timeToday);
                                            db.updateContainerInfo(containerId, quantityLeftContainer);
                                            Toast.makeText(getApplicationContext(), "Collection Information Saved",
                                                    Toast.LENGTH_SHORT).show();
                                            returnToFarmerSearch();
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
                        } else {
                            // Check if users is satisfied with correct entry
                            builder.setMessage("Are you sure you want to save farmer collection?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            quantityLeftContainer = quantityLeftContainer - quantityL;
                                            db.insertFarmerCollection(farmerId, transporterId, containerId, quantityL, sniffTest, alcoholTest, densityTest, wordComment, dateToday, timeToday);
                                            db.updateContainerInfo(containerId, quantityLeftContainer);
                                            Toast.makeText(getApplicationContext(), "Collection Information Saved",
                                                    Toast.LENGTH_SHORT).show();
                                            returnToFarmerSearch();


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
                    }
                }
            }
        });

    }

    // The container dropdown should keep its selection
    private void saveState() {
        states = getSharedPreferences("states", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = states.edit();
        editor.putInt("selectedDropdownPosition2", containerSpinnerView.getSelectedItemPosition());
        editor.commit();
    }

    // Grey out container dropdown when there are any failing tests (since the milk will be rejected,
    // it won't be added to any container
    private void enableOrDisableContainerDropdown() {
        //Gathering collection data from sniff and alcohol tests
        RadioButton sniffTestFail = findViewById(R.id.radioButton2);
        RadioButton alcoholTestFail = findViewById(R.id.radioButton5);
        RadioButton densityTestFail = findViewById(R.id.radioButton12);

        if(sniffTestFail.isChecked() || alcoholTestFail.isChecked() || densityTestFail.isChecked()){
            containerSpinnerView.setEnabled(false);
            containerSpinnerView.setClickable(false);
        } else {
            containerSpinnerView.setEnabled(true);
            containerSpinnerView.setClickable(true);
        }
    }

    private void returnToFarmerSearch() {
        Intent intent = new Intent(this, FarmerSearch.class);
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
                        returnToFarmerSearch();


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
