package com.kkfc.milkbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FarmerCollection extends AppCompatActivity {


    DatabaseHelper db;
    private Button cancelCollection;
    private Button saveCollection;
    private String farmerName;
    private int farmerId;
    private int transporterId;
    private int containerId = 1;
    private TextView nameFarmer;
    private EditText quantity;
    private EditText comment;
    private RadioButton sniffPass, sniffFail, sniffNa, alcoholPass, alcoholFail, alcoholNa,desnityTwoSeven, densityTwoEight, densityTwoNine, densityThirtyPlus, densityNa ;
    private String sniffTest;
    private String alcoholTest;
    private String densityTest;
    private String dateToday;
    private String timeToday;
    SimpleCursorAdapter containerCursorAdapter;
    private Spinner containerSpinnerView;
    SimpleCursorAdapter transporterCursorAdapter;
    private Spinner transportersSpinnerView;
    int selectedDropdown;

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


        //Container dropdown
        //container size
        //String[] containerAdapterCols=new String[]{db.};
        //int[] containerAdapterRowViews=new int[]{android.R.id.text1};

        //Cursor containerCursor = db.fetchContainers();

        //containerCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, containerCursor, containerAdapterCols, containerAdapterRowViews, 0);
        //containerCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //containerSpinnerView = findViewById(R.id.Spinner1);
        //containerSpinnerView.setAdapter(containerCursorAdapter);
        //containerSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //@Override
            //public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //When the dropdown selection changes, fetch the ID of the selected item
                //Cursor cursor = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
                //cursor.moveToPosition(position);
                //selectedDropdown = cursor.getInt(cursor.getColumnIndex("container_id"));
            //}

            //@Override
            //public void onNothingSelected(AdapterView<?> parent) {

            //}
        //});

        cancelCollection = findViewById(R.id.Button01);
        cancelCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToFarmerSearch();
            }
        });

        saveCollection = findViewById(R.id.Button02);
        saveCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save data into database
                quantity = findViewById(R.id.editText1);
                comment = findViewById(R.id.editText2);
                String quantityLitre = quantity.getText().toString();
                Double quantityL = Double.parseDouble(quantityLitre);
                String wordComment = comment.getText().toString();

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

                //Gathering collection data from sniff test
                desnityTwoSeven = findViewById(R.id.radioButton7);
                densityTwoEight = findViewById(R.id.radioButton8);
                densityTwoNine = findViewById(R.id.radioButton9);
                densityThirtyPlus = findViewById(R.id.radioButton10);
                densityNa = findViewById(R.id.radioButton11);
                if(desnityTwoSeven.isChecked()){
                    densityTest = desnityTwoSeven.getText().toString();
                } else if(densityTwoEight.isChecked()){
                    densityTest=densityTwoEight.getText().toString();
                } else if(densityTwoNine.isChecked()){
                    densityTest=densityTwoNine.getText().toString();
                } else if(densityThirtyPlus.isChecked()){
                    densityTest=densityThirtyPlus.getText().toString();
                } else if(densityNa.isChecked()){
                    densityTest=densityNa.getText().toString();
                }

                dateToday = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault()).format(new Date());
                timeToday = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
                db.insertFarmerCollection(farmerId, transporterId, containerId, quantityL, sniffTest, alcoholTest, densityTest, wordComment, dateToday, timeToday);
                //Toast.makeText(getApplicationContext(),quantityL + " - " + wordComment + " - " +  sniffTest+ " - " +  alcoholTest + " - " +  densityTest + " - " + farmerId + " - " + transporterId , Toast.LENGTH_SHORT).show();
                returnToFarmerSearch();
            }
        });

    }

    private void returnToFarmerSearch() {
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        returnToFarmerSearch();
    }
}
