package com.kkfc.milkbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dairy.db";

    // User table info. A user consists of anyone who actually uses the mobile app
    public static final String TABLE_USER = "user_table";
    public static final String USER_ID = "user_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_TYPE = "user_type";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_GENDER = "gender";

    private String TABLE_CREATE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            USER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            USER_USERNAME + " text UNIQUE," +
            USER_PASSWORD + " text," +
            USER_TYPE + " text, " +
            USER_FIRST_NAME + " text, " +
            USER_LAST_NAME + " text, " +
            USER_PHONE_NUMBER + " text, " +
            USER_GENDER + " text);";


    // Farmer table info. Farmers do not use the mobile app and are NOT in the users table above
    public static final String TABLE_FARMER = "farmer_table";
    public static final String FARMER_ID = "farmer_id";
    public static final String FARMER_ROUTE_ID = "route_id";
    public static final String FARMER_FIRST_NAME = "first_name";
    public static final String FARMER_LAST_NAME = "last_name";
    public static final String FARMER_PHONE_NUMBER = "phone_number";
    public static final String FARMER_GENDER = "gender";
    public static final String FARMER_EXPECTED_COLLECTION_TIME = "collection_time";

    private String TABLE_CREATE_FARMER = "CREATE TABLE " + TABLE_FARMER + " (" +
            FARMER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            FARMER_ROUTE_ID + " integer REFERENCES " + TABLE_ROUTE + " (" + ROUTE_ID + ")," +
            FARMER_FIRST_NAME + " text, " +
            FARMER_LAST_NAME + " text, " +
            FARMER_PHONE_NUMBER + " text, " +
            FARMER_GENDER + " text, " +
            FARMER_EXPECTED_COLLECTION_TIME + " text);";


    // Route table info
    public static final String TABLE_ROUTE = "route_table";
    public static final String ROUTE_ID = "route_id";
    public static final String ROUTE_PRIMARY_TRANSPORTER_ID = "primary_transporter_id";

    private String TABLE_CREATE_ROUTE = "CREATE TABLE " + TABLE_ROUTE + " (" +
            ROUTE_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            ROUTE_PRIMARY_TRANSPORTER_ID + " integer REFERENCES " + TABLE_USER + " (" + USER_ID + "));";


    // Container table info
    public static final String TABLE_CONTAINER = "container_table";
    public static final String CONTAINER_ID = "container_id";
    public static final String CONTAINER_TRANSPORTER_ID = "transporter_id";
    public static final String CONTAINER_SIZE = "size";
    public static final String CONTAINER_AMOUNT_REMAINING = "amount_remaining";

    private String TABLE_CREATE_CONTAINER = "CREATE TABLE " + TABLE_CONTAINER + " (" +
            // TODO add autoincrement to container ID
            CONTAINER_ID + " integer," +
            CONTAINER_TRANSPORTER_ID + " integer REFERENCES " + TABLE_USER + " (" + USER_ID + ")," +
            CONTAINER_SIZE + " numeric," +
            CONTAINER_AMOUNT_REMAINING  + " numeric," +
            "PRIMARY KEY (" + CONTAINER_ID + ", " + CONTAINER_TRANSPORTER_ID + "));";


    // Transporter_data table info
    public static final String TABLE_TRANSPORTER_DATA = "transporter_data_table";
    public static final String TRANSPORTER_DATA_CREATE_DATE = "create_date";
    public static final String TRANSPORTER_DATA_TRANSPORTER_ID = "transporter_id";
    public static final String TRANSPORTER_DATA_FARMER_ID = "farmer_id";
    public static final String TRANSPORTER_DATA_CONTAINER_ID = "container_id";
    public static final String TRANSPORTER_DATA_QUANTITY_COLLECTED = "quantity_collected";
    public static final String TRANSPORTER_DATA_UPDATE_DATE = "update_date";
    public static final String TRANSPORTER_DATA_COMMENT = "comment";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_SMELL = "smell_test";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_DENSITY = "density_test";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_ALCOHOL = "alcohol_test";

    private String TABLE_CREATE_TRANSPORTER_DATA = "CREATE TABLE " + TABLE_TRANSPORTER_DATA+ " (" +
            TRANSPORTER_DATA_CREATE_DATE + " text, " +
            TRANSPORTER_DATA_FARMER_ID + " integer REFERENCES " + TABLE_FARMER + " (" + FARMER_ID + ")," +
            TRANSPORTER_DATA_TRANSPORTER_ID + " integer REFERENCES " + TABLE_USER + " (" + USER_ID + ")," +
            TRANSPORTER_DATA_CONTAINER_ID + " integer REFERENCES " + TABLE_CONTAINER + " (" + CONTAINER_ID + ")," +
            TRANSPORTER_DATA_QUANTITY_COLLECTED + " numeric," +
            TRANSPORTER_DATA_UPDATE_DATE + " text, " +
            TRANSPORTER_DATA_COMMENT + " text, " +
            TRANSPORTER_DATA_QUALITY_TEST_SMELL  + " boolean, " +
            TRANSPORTER_DATA_QUALITY_TEST_DENSITY + " numeric," +
            TRANSPORTER_DATA_QUALITY_TEST_ALCOHOL + " numeric," +
            "PRIMARY KEY (" + TRANSPORTER_DATA_CREATE_DATE + ", " + TRANSPORTER_DATA_FARMER_ID + "));";


    // Plant_data table info
    public static final String TABLE_PLANT_DATA = "plant_data_table";
    public static final String PLANT_DATA_CONTAINER_ID = "container_id";
    public static final String PLANT_DATA_CREATE_DATE = "create_date";
    public static final String PLANT_DATA_UPDATE_DATE = "update_date";
    public static final String PLANT_DATA_QUANTITY_COLLECTED = "quantity_collected";
    public static final String PLANT_DATA_TRANSPORTER_ID = "transporter_id";
    public static final String PLANT_DATA_QUALITY_TEST_SMELL = "smell_test";
    public static final String PLANT_DATA_QUALITY_TEST_DENSITY = "density_test";
    public static final String PLANT_DATA_QUALITY_TEST_ALCOHOL = "alcohol_test";
    public static final String PLANT_DATA_COMMENT = "comment";

    private String TABLE_CREATE_PLANT_DATA = "CREATE TABLE " + TABLE_PLANT_DATA+ " (" +
            PLANT_DATA_CONTAINER_ID + " integer REFERENCES " + TABLE_CONTAINER + " (" + CONTAINER_ID + ")," +
            PLANT_DATA_CREATE_DATE + " text, " +
            PLANT_DATA_UPDATE_DATE + " text, " +
            PLANT_DATA_QUANTITY_COLLECTED + " numeric," +
            PLANT_DATA_TRANSPORTER_ID + " integer REFERENCES " + TABLE_USER + " (" + USER_ID + ")," +
            PLANT_DATA_QUALITY_TEST_SMELL  + " boolean, " +
            PLANT_DATA_QUALITY_TEST_DENSITY + " numeric," +
            PLANT_DATA_QUALITY_TEST_ALCOHOL + " numeric," +
            PLANT_DATA_COMMENT + " text," +
            "PRIMARY KEY (" + PLANT_DATA_CONTAINER_ID + ", " + PLANT_DATA_CREATE_DATE + "));";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Log.i("state", TABLE_CREATE_USER);
        sqLiteDatabase.execSQL(TABLE_CREATE_USER);
        sqLiteDatabase.execSQL(TABLE_CREATE_FARMER);
        sqLiteDatabase.execSQL(TABLE_CREATE_ROUTE);
        sqLiteDatabase.execSQL(TABLE_CREATE_CONTAINER);
        sqLiteDatabase.execSQL(TABLE_CREATE_TRANSPORTER_DATA);
        sqLiteDatabase.execSQL(TABLE_CREATE_PLANT_DATA);

        // TODO get rid of this. For now, use this to insert dummy data

        String dummy_data_1 = "INSERT INTO " + TABLE_USER + "(" +
                USER_USERNAME + "," + USER_PASSWORD + "," + USER_TYPE + "," + USER_FIRST_NAME + ","
                + USER_LAST_NAME + "," + USER_PHONE_NUMBER + "," + USER_GENDER + ")" +
                " VALUES('aa','b','c','Celeste','e','f','g')";

        String dummy_data_2 = "INSERT INTO " + TABLE_USER + "(" +
                USER_USERNAME + "," + USER_PASSWORD + "," + USER_TYPE + "," + USER_FIRST_NAME + ","
                + USER_LAST_NAME + "," + USER_PHONE_NUMBER + "," + USER_GENDER + ")" +
                " VALUES('ab','b','c','Malvika','e','f','g')";

        String dummy_data_3 = "INSERT INTO " + TABLE_USER + "(" +
                USER_USERNAME + "," + USER_PASSWORD + "," + USER_TYPE + "," + USER_FIRST_NAME + ","
                + USER_LAST_NAME + "," + USER_PHONE_NUMBER + "," + USER_GENDER + ")" +
                " VALUES('ac','b','c','Priyanka','e','f','g')";

        String dummy_data_4 = "INSERT INTO " + TABLE_USER + "(" +
                USER_USERNAME + "," + USER_PASSWORD + "," + USER_TYPE + "," + USER_FIRST_NAME + ","
                + USER_LAST_NAME + "," + USER_PHONE_NUMBER + "," + USER_GENDER + ")" +
                " VALUES('ad','b','c','Ahmad','e','f','g')";


        sqLiteDatabase.execSQL(dummy_data_1);
        sqLiteDatabase.execSQL(dummy_data_2);
        sqLiteDatabase.execSQL(dummy_data_3);
        sqLiteDatabase.execSQL(dummy_data_4);


    }

    @Override
    public void onUpgrade(SQLiteDatabase SQLiteDatabase, int i, int i1) {
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSPORTER_DATA);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANT_DATA);

        onCreate(SQLiteDatabase);
    }

    // Fetch data
    public Cursor fetchUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        return cursor;
    }
}
