package com.kkfc.milkbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dairy.db";

    // User table info. A user consists of anyone who actually uses the mobile app
    public static final String TABLE_TRANSPORTER = "transporter_table";
    public static final String TRANSPORTER_ID = "transporter_id";
    public static final String TRANSPORTER_FIRST_NAME = "first_name";
    public static final String TRANSPORTER_LAST_NAME = "last_name";
    public static final String TRANSPORTER_PHONE_NUMBER = "phone_number";

    private String TABLE_CREATE_TRANSPORTER = "CREATE TABLE " + TABLE_TRANSPORTER + " (" +
            TRANSPORTER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TRANSPORTER_FIRST_NAME + " text, " +
            TRANSPORTER_LAST_NAME + " text, " +
            TRANSPORTER_PHONE_NUMBER + " text);";


    // User table info. A user consists of anyone who actually uses the mobile app
    public static final String TABLE_RECEIVER = "receiver_table";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String RECEIVER_FIRST_NAME = "first_name";
    public static final String RECEIVER_LAST_NAME = "last_name";
    public static final String RECEIVER_PHONE_NUMBER = "phone_number";

    private String TABLE_CREATE_RECEIVER = "CREATE TABLE " + TABLE_RECEIVER + " (" +
            RECEIVER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            RECEIVER_FIRST_NAME + " text, " +
            RECEIVER_LAST_NAME + " text, " +
            RECEIVER_PHONE_NUMBER + " text);";


    // Farmer table info. Farmers do not use the mobile app and are NOT in the users table above
    public static final String TABLE_FARMER = "farmer_table";
    public static final String FARMER_ID = "farmer_id";
    public static final String FARMER_ASSIGNED_TRANSPORTER_ID = "assigned_transporter_id";
    public static final String FARMER_FIRST_NAME = "first_name";
    public static final String FARMER_LAST_NAME = "last_name";
    public static final String FARMER_PHONE_NUMBER = "phone_number";
    public static final String FARMER_ACTIVE = "active";
    public static final String FARMER_EXPECTED_COLLECTION_TIME = "expected_collection_time";

    private String TABLE_CREATE_FARMER = "CREATE TABLE " + TABLE_FARMER + " (" +
            FARMER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            FARMER_ASSIGNED_TRANSPORTER_ID + " integer REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_ID + ")," +
            FARMER_FIRST_NAME + " text, " +
            FARMER_LAST_NAME + " text, " +
            FARMER_PHONE_NUMBER + " text, " +
            FARMER_ACTIVE + " text, " +
            FARMER_EXPECTED_COLLECTION_TIME + " text);";


    // Container table info
    public static final String TABLE_CONTAINER = "container_table";
    public static final String CONTAINER_ID = "container_id";
    public static final String CONTAINER_SIZE = "size";
    public static final String CONTAINER_AMOUNT_REMAINING = "amount_remaining";

    private String TABLE_CREATE_CONTAINER = "CREATE TABLE " + TABLE_CONTAINER + " (" +
            CONTAINER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            CONTAINER_SIZE + " numeric," +
            CONTAINER_AMOUNT_REMAINING  + " numeric);";


    // Transporter_data table info
    public static final String TABLE_TRANSPORTER_DATA = "transporter_data_table";
    public static final String TRANSPORTER_DATA_FARMER_ID = "farmer_id";
    public static final String TRANSPORTER_DATA_FARMER_FIRST_NAME = "farmer_first_name";
    public static final String TRANSPORTER_DATA_FARMER_LAST_NAME = "farmer_last_name";
    public static final String TRANSPORTER_DATA_FARMER_PHONE_NUMBER = "farmer_phone_number";
    public static final String TRANSPORTER_DATA_TRANSPORTER_ID = "transporter_id";
    public static final String TRANSPORTER_DATA_TRANSPORTER_FIRST_NAME = "transporter_first_name";
    public static final String TRANSPORTER_DATA_TRANSPORTER_LAST_NAME = "transporter_last_name";
    public static final String TRANSPORTER_DATA_TRANSPORTER_PHONE_NUMBER = "transporter_phone_number";
    public static final String TRANSPORTER_DATA_CONTAINER_ID = "container_id";
    public static final String TRANSPORTER_DATA_QUANTITY_COLLECTED = "quantity_collected";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_SMELL = "smell_test";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_DENSITY = "density_test";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_ALCOHOL = "alcohol_test";
    public static final String TRANSPORTER_DATA_COMMENT = "comment";
    public static final String TRANSPORTER_DATA_CREATE_DATE = "create_date";

    // TODO: Take away foreign keys?
    private String TABLE_CREATE_TRANSPORTER_DATA = "CREATE TABLE " + TABLE_TRANSPORTER_DATA+ " (" +
            TRANSPORTER_DATA_FARMER_ID + " integer PRIMARY KEY REFERENCES " + TABLE_FARMER + " (" + FARMER_ID + ")," +
            TRANSPORTER_DATA_FARMER_FIRST_NAME + " text REFERENCES " + TABLE_FARMER + " (" + FARMER_FIRST_NAME + ")," +
            TRANSPORTER_DATA_FARMER_LAST_NAME + " text REFERENCES " + TABLE_FARMER + " (" + FARMER_LAST_NAME + ")," +
            TRANSPORTER_DATA_FARMER_PHONE_NUMBER + " text REFERENCES " + TABLE_FARMER + " (" + FARMER_PHONE_NUMBER + ")," +
            TRANSPORTER_DATA_TRANSPORTER_ID + " integer REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_ID + ")," +
            TRANSPORTER_DATA_TRANSPORTER_FIRST_NAME + " text REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_FIRST_NAME + ")," +
            TRANSPORTER_DATA_TRANSPORTER_LAST_NAME + " text REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_LAST_NAME + ")," +
            TRANSPORTER_DATA_TRANSPORTER_PHONE_NUMBER + " text REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_PHONE_NUMBER + ")," +
            TRANSPORTER_DATA_CONTAINER_ID + " integer REFERENCES " + TABLE_CONTAINER + " (" + CONTAINER_ID + ")," +
            TRANSPORTER_DATA_QUANTITY_COLLECTED + " numeric," +
            TRANSPORTER_DATA_QUALITY_TEST_SMELL  + " boolean, " +
            TRANSPORTER_DATA_QUALITY_TEST_DENSITY + " numeric," +
            TRANSPORTER_DATA_QUALITY_TEST_ALCOHOL + " numeric," +
            TRANSPORTER_DATA_COMMENT + " text, " +
            TRANSPORTER_DATA_CREATE_DATE + " text);";


    // Plant_data table info
    public static final String TABLE_PLANT_DATA = "plant_data_table";
    public static final String PLANT_DATA_CONTAINER_ID = "container_id";
    public static final String PLANT_DATA_TRANSPORTER_ID = "transporter_id";
    public static final String PLANT_DATA_TRANSPORTER_FIRST_NAME = "transporter_first_name";
    public static final String PLANT_DATA_TRANSPORTER_LAST_NAME = "transporter_last_name";
    public static final String PLANT_DATA_TRANSPORTER_PHONE_NUMBER = "transporter_phone_number";
    public static final String PLANT_DATA_RECEIVER_ID = "receiver_id";
    public static final String PLANT_DATA_RECEIVER_FIRST_NAME = "receiver_first_name";
    public static final String PLANT_DATA_RECEIVER_LAST_NAME = "receiver_last_name";
    public static final String PLANT_DATA_RECEIVER_PHONE_NUMBER = "receiver_phone_number";
    public static final String PLANT_DATA_QUANTITY_COLLECTED = "quantity_collected";
    public static final String PLANT_DATA_QUALITY_TEST_SMELL = "smell_test";
    public static final String PLANT_DATA_QUALITY_TEST_DENSITY = "density_test";
    public static final String PLANT_DATA_QUALITY_TEST_ALCOHOL = "alcohol_test";
    public static final String PLANT_DATA_COMMENT = "comment";
    public static final String PLANT_DATA_CREATE_DATE = "create_date";

    // TODO: Take away foreign keys?
    private String TABLE_CREATE_PLANT_DATA = "CREATE TABLE " + TABLE_PLANT_DATA+ " (" +
            PLANT_DATA_CONTAINER_ID + " integer PRIMARY KEY REFERENCES " + TABLE_CONTAINER + " (" + CONTAINER_ID + ")," +
            PLANT_DATA_TRANSPORTER_ID + " integer REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_ID + ")," +
            PLANT_DATA_TRANSPORTER_FIRST_NAME + " text REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_FIRST_NAME + ")," +
            PLANT_DATA_TRANSPORTER_LAST_NAME + " text REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_LAST_NAME + ")," +
            PLANT_DATA_TRANSPORTER_PHONE_NUMBER + " text REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_PHONE_NUMBER + ")," +
            PLANT_DATA_RECEIVER_ID + " integer REFERENCES " + TABLE_RECEIVER + " (" + RECEIVER_ID + ")," +
            PLANT_DATA_RECEIVER_FIRST_NAME + " text REFERENCES " + TABLE_RECEIVER + " (" + RECEIVER_FIRST_NAME + ")," +
            PLANT_DATA_RECEIVER_LAST_NAME + " text REFERENCES " + TABLE_RECEIVER + " (" + RECEIVER_LAST_NAME + ")," +
            PLANT_DATA_RECEIVER_PHONE_NUMBER + " text REFERENCES " + TABLE_RECEIVER + " (" + RECEIVER_PHONE_NUMBER + ")," +
            PLANT_DATA_QUANTITY_COLLECTED + " numeric," +
            PLANT_DATA_QUALITY_TEST_SMELL  + " boolean, " +
            PLANT_DATA_QUALITY_TEST_DENSITY + " numeric," +
            PLANT_DATA_QUALITY_TEST_ALCOHOL + " numeric," +
            PLANT_DATA_COMMENT + " text, " +
            PLANT_DATA_CREATE_DATE + " text);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Log.i("state", TABLE_CREATE_USER);
        sqLiteDatabase.execSQL(TABLE_CREATE_TRANSPORTER);
        sqLiteDatabase.execSQL(TABLE_CREATE_RECEIVER);
        sqLiteDatabase.execSQL(TABLE_CREATE_FARMER);
        sqLiteDatabase.execSQL(TABLE_CREATE_CONTAINER);
        sqLiteDatabase.execSQL(TABLE_CREATE_TRANSPORTER_DATA);
        sqLiteDatabase.execSQL(TABLE_CREATE_PLANT_DATA);

        // TODO get rid of this. For now, use this to insert dummy data

        String dummy_data_insert_into_transporter = "INSERT INTO " + TABLE_TRANSPORTER + "(" +
                TRANSPORTER_FIRST_NAME + "," + TRANSPORTER_LAST_NAME + "," + TRANSPORTER_PHONE_NUMBER + ")";

        String dummy_data_1 = dummy_data_insert_into_transporter +
                " VALUES('Celeste','Cassel de Camps','000-000-0000')";

        String dummy_data_2 = dummy_data_insert_into_transporter +
                " VALUES('Malvika','Balaraj','111-111-1111')";

        String dummy_data_3 = dummy_data_insert_into_transporter +
                " VALUES('Priyanka','Brodie','222-222-2222')";

        String dummy_data_4 = dummy_data_insert_into_transporter +
                " VALUES('Ahmad','Dakhqan','333-333-3333')";

        String dummy_data_5 = dummy_data_insert_into_transporter +
                " VALUES('Ada','Hurst','444-444-4444')";

        sqLiteDatabase.execSQL(dummy_data_1);
        sqLiteDatabase.execSQL(dummy_data_2);
        sqLiteDatabase.execSQL(dummy_data_3);
        sqLiteDatabase.execSQL(dummy_data_4);
        sqLiteDatabase.execSQL(dummy_data_5);

    }

    @Override
    public void onUpgrade(SQLiteDatabase SQLiteDatabase, int i, int i1) {
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSPORTER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIVER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSPORTER_DATA);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANT_DATA);

        onCreate(SQLiteDatabase);
    }

    // Fetch data
    public Cursor fetchTransporters() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSPORTER, null);
        return cursor;
    }
}
