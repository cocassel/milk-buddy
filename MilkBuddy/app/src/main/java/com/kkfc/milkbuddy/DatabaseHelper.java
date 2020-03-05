package com.kkfc.milkbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dairy.db";

    // Transporter table info
    public static final String TABLE_TRANSPORTER = "transporter_table";
    public static final String TRANSPORTER_ID = "_id";
    public static final String TRANSPORTER_NAME = "name";
    public static final String TRANSPORTER_PHONE_NUMBER = "phone_number";

    private String TABLE_CREATE_TRANSPORTER = "CREATE TABLE " + TABLE_TRANSPORTER + " (" +
            TRANSPORTER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TRANSPORTER_NAME + " text, " +
            TRANSPORTER_PHONE_NUMBER + " text);";

    // Logged-in transporter table info. This could be a regular transporter or a guest transporter
    public static final String TABLE_LOGGED_IN_TRANSPORTER = "logged_in_transporter_table";
    public static final String LOGGED_IN_TRANSPORTER_ID = "_id";
    public static final String LOGGED_IN_TRANSPORTER_NAME = "name";
    public static final String LOGGED_IN_TRANSPORTER_PHONE_NUMBER = "phone_number";

    private String TABLE_CREATE_LOGGED_IN_TRANSPORTER = "CREATE TABLE " + TABLE_LOGGED_IN_TRANSPORTER + " (" +
            LOGGED_IN_TRANSPORTER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            LOGGED_IN_TRANSPORTER_NAME + " text, " +
            LOGGED_IN_TRANSPORTER_PHONE_NUMBER + " text);";


    // Receiver table info
    public static final String TABLE_RECEIVER = "receiver_table";
    public static final String RECEIVER_ID = "_id";
    public static final String RECEIVER_NAME = "name";
    public static final String RECEIVER_PHONE_NUMBER = "phone_number";
    public static final String RECEIVER_USERNAME = "username";
    public static final String RECEIVER_PASSWORD = "password_hash";

    private String TABLE_CREATE_RECEIVER = "CREATE TABLE " + TABLE_RECEIVER + " (" +
            RECEIVER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            RECEIVER_NAME + " text, " +
            RECEIVER_PHONE_NUMBER + " text, " +
            RECEIVER_USERNAME + " text UNIQUE, " +
            RECEIVER_PASSWORD + " text);";


    // Logged-in receiver table info
    public static final String TABLE_LOGGED_IN_RECEIVER = "logged_in_receiver_table";
    public static final String LOGGED_IN_RECEIVER_ID = "_id";
    public static final String LOGGED_IN_RECEIVER_NAME = "name";
    public static final String LOGGED_IN_RECEIVER_PHONE_NUMBER = "phone_number";

    private String TABLE_CREATE_LOGGED_IN_RECEIVER = "CREATE TABLE " + TABLE_LOGGED_IN_RECEIVER + " (" +
            LOGGED_IN_RECEIVER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            LOGGED_IN_RECEIVER_NAME + " text, " +
            LOGGED_IN_RECEIVER_PHONE_NUMBER + " text);";


    // Farmer table info.
    public static final String TABLE_FARMER = "farmer_table";
    public static final String FARMER_ID = "_id";
    public static final String FARMER_ASSIGNED_TRANSPORTER_ID = "assigned_transporter_id";
    public static final String FARMER_NAME = "name";
    public static final String FARMER_PHONE_NUMBER = "phone_number";
    public static final String FARMER_ACTIVE = "active";
    public static final String FARMER_EXPECTED_COLLECTION_TIME = "expected_collection_time";

    private String TABLE_CREATE_FARMER = "CREATE TABLE " + TABLE_FARMER + " (" +
            FARMER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            FARMER_ASSIGNED_TRANSPORTER_ID + " integer REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_ID + ")," +
            FARMER_NAME + " text, " +
            FARMER_PHONE_NUMBER + " text, " +
            FARMER_ACTIVE + " text, " +
            FARMER_EXPECTED_COLLECTION_TIME + " text);";


    // Container table info
    public static final String TABLE_CONTAINER = "container_table";
    public static final String CONTAINER_ID = "_id";
    public static final String CONTAINER_SIZE = "size";
    public static final String CONTAINER_AMOUNT_REMAINING = "amount_remaining";

    private String TABLE_CREATE_CONTAINER = "CREATE TABLE " + TABLE_CONTAINER + " (" +
            CONTAINER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            CONTAINER_SIZE + " numeric," +
            CONTAINER_AMOUNT_REMAINING  + " numeric);";


    // Transporter_data table info
    public static final String TABLE_TRANSPORTER_DATA = "transporter_data_table";
    public static final String TRANSPORTER_DATA_PICK_UP_NUMBER = "pick_up_number";
    public static final String TRANSPORTER_DATA_FARMER_ID = "farmer_id";
    public static final String TRANSPORTER_DATA_TRANSPORTER_ID = "transporter_id";
    public static final String TRANSPORTER_DATA_CONTAINER_ID = "container_id";
    public static final String TRANSPORTER_DATA_QUANTITY_COLLECTED = "quantity_collected";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_SMELL = "smell_test";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_DENSITY = "density_test";
    public static final String TRANSPORTER_DATA_QUALITY_TEST_ALCOHOL = "alcohol_test";
    public static final String TRANSPORTER_DATA_COMMENT = "comment";
    public static final String TRANSPORTER_DATA_CREATE_DATE = "create_date";
    public static final String TRANSPORTER_DATA_CREATE_TIME = "create_time";

    private String TABLE_CREATE_TRANSPORTER_DATA = "CREATE TABLE " + TABLE_TRANSPORTER_DATA+ " (" +
            TRANSPORTER_DATA_PICK_UP_NUMBER + " integer PRIMARY KEY AUTOINCREMENT," +
            TRANSPORTER_DATA_FARMER_ID + " integer REFERENCES " + TABLE_FARMER + " (" + FARMER_ID + ")," +
            TRANSPORTER_DATA_TRANSPORTER_ID + " integer REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_ID + ")," +
            TRANSPORTER_DATA_CONTAINER_ID + " integer REFERENCES " + TABLE_CONTAINER + " (" + CONTAINER_ID + ")," +
            TRANSPORTER_DATA_QUANTITY_COLLECTED + " numeric," +
            TRANSPORTER_DATA_QUALITY_TEST_SMELL  + " text, " +
            TRANSPORTER_DATA_QUALITY_TEST_DENSITY + " text," +
            TRANSPORTER_DATA_QUALITY_TEST_ALCOHOL + " text," +
            TRANSPORTER_DATA_COMMENT + " text, " +
            TRANSPORTER_DATA_CREATE_DATE + " text, " +
            TRANSPORTER_DATA_CREATE_TIME + " text);";


    // Plant_data table info
    public static final String TABLE_PLANT_DATA = "plant_data_table";
    public static final String PLANT_DATA_CONTAINER_ID = "container_id";
    public static final String PLANT_DATA_TRANSPORTER_ID = "transporter_id";
    public static final String PLANT_DATA_RECEIVER_ID = "receiver_id";
    public static final String PLANT_DATA_QUANTITY_COLLECTED = "quantity_collected";
    public static final String PLANT_DATA_QUALITY_TEST_SMELL = "smell_test";
    public static final String PLANT_DATA_QUALITY_TEST_DENSITY = "density_test";
    public static final String PLANT_DATA_QUALITY_TEST_ALCOHOL = "alcohol_test";
    public static final String PLANT_DATA_COMMENT = "comment";
    public static final String PLANT_DATA_CREATE_DATE = "create_date";
    public static final String PLANT_DATA_CREATE_TIME = "create_time";


    private String TABLE_CREATE_PLANT_DATA = "CREATE TABLE " + TABLE_PLANT_DATA+ " (" +
            PLANT_DATA_CONTAINER_ID + " integer PRIMARY KEY REFERENCES " + TABLE_CONTAINER + " (" + CONTAINER_ID + ")," +
            PLANT_DATA_TRANSPORTER_ID + " integer REFERENCES " + TABLE_TRANSPORTER + " (" + TRANSPORTER_ID + ")," +
            PLANT_DATA_RECEIVER_ID + " integer REFERENCES " + TABLE_RECEIVER + " (" + RECEIVER_ID + ")," +
            PLANT_DATA_QUANTITY_COLLECTED + " numeric," +
            PLANT_DATA_QUALITY_TEST_SMELL  + " text, " +
            PLANT_DATA_QUALITY_TEST_DENSITY + " text," +
            PLANT_DATA_QUALITY_TEST_ALCOHOL + " text," +
            PLANT_DATA_COMMENT + " text, " +
            PLANT_DATA_CREATE_DATE + " text, " +
            PLANT_DATA_CREATE_TIME + " text);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_TRANSPORTER);
        sqLiteDatabase.execSQL(TABLE_CREATE_LOGGED_IN_TRANSPORTER);
        sqLiteDatabase.execSQL(TABLE_CREATE_RECEIVER);
        sqLiteDatabase.execSQL(TABLE_CREATE_LOGGED_IN_RECEIVER);
        sqLiteDatabase.execSQL(TABLE_CREATE_FARMER);
        sqLiteDatabase.execSQL(TABLE_CREATE_CONTAINER);
        sqLiteDatabase.execSQL(TABLE_CREATE_TRANSPORTER_DATA);
        sqLiteDatabase.execSQL(TABLE_CREATE_PLANT_DATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase SQLiteDatabase, int i, int i1) {
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSPORTER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGGED_IN_TRANSPORTER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIVER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGGED_IN_RECEIVER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINER);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSPORTER_DATA);
        SQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANT_DATA);

        onCreate(SQLiteDatabase);
    }

    // Fetch transporter table
    public Cursor fetchTransporters() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSPORTER, null);
        return cursor;
    }

    public Cursor fetchLoggedInTransporter() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGGED_IN_TRANSPORTER, null);
        return cursor;
    }

    public Cursor fetchLoggedInReceiver() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGGED_IN_RECEIVER, null);
        return cursor;
    }

    // Fetch entire farmer table
    public Cursor fetchFarmers() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FARMER + " ORDER BY " + FARMER_NAME, null);
        return cursor;
    }

    // Fetch farmer table based on checkboxes, dropdown filter, and search bar
    public Cursor fetchFarmers(Boolean active, Boolean collected, Integer id, String search) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertStatement = "SELECT * FROM " + TABLE_FARMER
                + " WHERE " + FARMER_NAME + " LIKE " + "'%" + search + "%'";

        // if active checkbox is toggled, only select farmers who are active
        if(active) {
            insertStatement += " AND " + FARMER_ACTIVE + "='1'";
        }
        // if collected checkbox is toggled, only select farmers who have not been collected from
        if(collected) {
            insertStatement += " AND " + FARMER_ID + " NOT IN (SELECT " + TRANSPORTER_DATA_FARMER_ID
                    + " FROM " + TABLE_TRANSPORTER_DATA + ")" ;
        }
        // if a transporter is selected from the dropdown, only select farmers who are on that transporter's route
        // id is -1 for the "All Routes" dropdown item. So when id = -1, don't filter by route.
        if(id != -1) {
            insertStatement += " AND " + FARMER_ASSIGNED_TRANSPORTER_ID + "=" + id;

        }
        insertStatement += " ORDER BY " + FARMER_NAME;
        Cursor cursor = db.rawQuery(insertStatement, null);
        return cursor;
    }



    // Fetch transporter data table
    public Cursor fetchTransporterData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSPORTER_DATA, null);
        return cursor;
    }

    // Fetch transporter data table
    public Cursor fetchTransporterDataToExport() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TRANSPORTER_DATA_PICK_UP_NUMBER + ", " + TRANSPORTER_DATA_CONTAINER_ID +
                ", " + TRANSPORTER_DATA_QUANTITY_COLLECTED + ", " + TRANSPORTER_DATA_QUALITY_TEST_SMELL +
                ", " + TRANSPORTER_DATA_QUALITY_TEST_ALCOHOL + ", " + TRANSPORTER_DATA_QUALITY_TEST_DENSITY +
                ", " + TRANSPORTER_DATA_COMMENT + ", " + TRANSPORTER_DATA_CREATE_DATE + ", " +
                TRANSPORTER_DATA_CREATE_TIME + ", " + TRANSPORTER_DATA_FARMER_ID + ", farmer_name, farmer_phone_number, " +
                TRANSPORTER_DATA_TRANSPORTER_ID + ", transporter_name, transporter_phone_number FROM " +
                TABLE_TRANSPORTER_DATA + " AS data INNER JOIN " + "(SELECT " +
                FARMER_ID + " AS farmer_id_2, " + FARMER_NAME + " AS farmer_name, " + FARMER_PHONE_NUMBER +
                " AS farmer_phone_number FROM " + TABLE_FARMER + ") AS farmer ON data." +
                TRANSPORTER_DATA_FARMER_ID + "=farmer.farmer_id_2 INNER JOIN (SELECT " + LOGGED_IN_TRANSPORTER_ID +
                " AS transporter_id_2, " + LOGGED_IN_TRANSPORTER_NAME + " AS transporter_name, " +
                LOGGED_IN_TRANSPORTER_PHONE_NUMBER + " AS transporter_phone_number FROM " +
                TABLE_LOGGED_IN_TRANSPORTER + ") AS transporter ON data." + TRANSPORTER_DATA_TRANSPORTER_ID +
                "=transporter.transporter_id_2";
        Log.i("query", query);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // Fetch transporter data table
    public Cursor fetchPlantData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PLANT_DATA, null);
        return cursor;
    }

    public Cursor fetchContainers() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTAINER, null);
        return cursor;
    }


    // fetch container data in a concatenated form for the container dropdown on the farmer collection page
    public Cursor fetchConcatContainerInfo(){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQLquery = "SELECT " + CONTAINER_ID + ", " + CONTAINER_SIZE + ", " +
                CONTAINER_AMOUNT_REMAINING + ", " + "'Container ' || " +CONTAINER_ID + " || ' (' || " +
                CONTAINER_AMOUNT_REMAINING +" || 'L left) ' AS container_dropdown  FROM " + TABLE_CONTAINER + ";";
        SQLquery += " AND " + CONTAINER_ID + "NOT IN (SELECT " + PLANT_DATA_CONTAINER_ID + " FROM " + TABLE_PLANT_DATA + ")";
        SQLquery += " ORDER BY " + CONTAINER_ID;
        Log.i("query ", SQLquery);
        Cursor cursor = db.rawQuery(SQLquery, null);
        return cursor;

    }

    // fetch container data in a concatenated form for the container view on the receiver homepage
    public Cursor fetchConcatContainerForReceiver(){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQLquery = "SELECT " + CONTAINER_ID + ", " + CONTAINER_SIZE + ", " +
                CONTAINER_AMOUNT_REMAINING + ", " +
                "'Container ' || " + CONTAINER_ID + " || ' (Amount Collected: ' || amount_collected" +
                "|| 'L, Container Size: '|| " + CONTAINER_SIZE + "|| 'L) ' AS container_info  FROM ("
                + "SELECT " + CONTAINER_ID + ", " + CONTAINER_SIZE + ", " + CONTAINER_AMOUNT_REMAINING +
                ", " + CONTAINER_SIZE + "-" + CONTAINER_AMOUNT_REMAINING + " AS amount_collected FROM " + TABLE_CONTAINER + ");";
        Log.i("query ", SQLquery);
        Cursor cursor = db.rawQuery(SQLquery, null);
        return cursor;

    }
    
    // Check receiver credentials
    public Cursor checkReceiverLoginCredentials(String username, String password) {
        String passwordHash = getMd5Hash(password);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RECEIVER + " WHERE " + RECEIVER_USERNAME + "='" +
                username + "' AND " + RECEIVER_PASSWORD + "='" + passwordHash + "'";
        // Get all the matching entries for the entered username and password (there should be 0 or 1)
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public String getMd5Hash(String password) {
        try {
            final String MD5 = "MD5";
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void deleteLoggedInTransporter() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_LOGGED_IN_TRANSPORTER);
    }

    public void deleteLoggedInReceiver() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_LOGGED_IN_RECEIVER);
    }

    public void deleteContainers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_CONTAINER);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_CONTAINER + "'");

    }

    public void deleteTransporterCollectionData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_TRANSPORTER_DATA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_TRANSPORTER_DATA + "'");

    }

    public void deletePlantData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_PLANT_DATA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_PLANT_DATA + "'");

    }

    // Reset the app to its beginning-of-day state. Do not reset transporters, farmers, and receivers
    // because the transporter may want to use the existing data rather than importing from new CSV files
    public void resetTables() {
        deleteLoggedInTransporter();
        deleteLoggedInReceiver();
        deleteContainers();
        deleteTransporterCollectionData();
        deletePlantData();
    }

    // Save logged-in transporter data. This function is used for regular transporters (not guest)
    public void insertLoggedInTransporter(int id, String name, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete existing entry from the table. The table will only be non-empty if the transporter
        // goes back from the container selection page and chooses a different transporter
        db.execSQL("DELETE FROM "+ TABLE_LOGGED_IN_TRANSPORTER);

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGGED_IN_TRANSPORTER_ID, id);
        contentValues.put(LOGGED_IN_TRANSPORTER_NAME, name);
        contentValues.put(LOGGED_IN_TRANSPORTER_PHONE_NUMBER, phoneNumber);
        db.insert(TABLE_LOGGED_IN_TRANSPORTER, null, contentValues);
    }

    // Save logged-in transporter data. Use -1 as the transporter ID
    public void insertLoggedInGuestTransporter(String name, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete existing entry from the table. The table will only be non-empty if the transporter
        // goes back from the container selection page and chooses a different transporter
        db.execSQL("DELETE FROM "+ TABLE_LOGGED_IN_TRANSPORTER);

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGGED_IN_TRANSPORTER_ID, -1);
        contentValues.put(LOGGED_IN_TRANSPORTER_NAME, name);
        contentValues.put(LOGGED_IN_TRANSPORTER_PHONE_NUMBER, phoneNumber);
        db.insert(TABLE_LOGGED_IN_TRANSPORTER, null, contentValues);
    }

    // Save logged-in receiver data.
    public void insertLoggedInReceiver(int id, String name, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete existing entry from the table.
        db.execSQL("DELETE FROM "+ TABLE_LOGGED_IN_RECEIVER);

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGGED_IN_RECEIVER_ID, id);
        contentValues.put(LOGGED_IN_RECEIVER_NAME, name);
        contentValues.put(LOGGED_IN_RECEIVER_PHONE_NUMBER, phoneNumber);
        db.insert(TABLE_LOGGED_IN_RECEIVER, null, contentValues);
    }

    // Save farmer collection data.
    public void insertFarmerCollection(int fId, int tId,int cId, double quantityL, String sniffTest, String alcoholTest, String densityTest, String wordComment, String createDate, String createTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertStatement = "INSERT INTO " + TABLE_TRANSPORTER_DATA + " ( "+
                TRANSPORTER_DATA_FARMER_ID + ", " + TRANSPORTER_DATA_TRANSPORTER_ID + ", " +
                TRANSPORTER_DATA_CONTAINER_ID + ", " + TRANSPORTER_DATA_QUANTITY_COLLECTED + ", " +
                TRANSPORTER_DATA_QUALITY_TEST_SMELL + ", " + TRANSPORTER_DATA_QUALITY_TEST_ALCOHOL + ", " +
                TRANSPORTER_DATA_QUALITY_TEST_DENSITY + ", " + TRANSPORTER_DATA_COMMENT + ", " +
                TRANSPORTER_DATA_CREATE_DATE + ", " + TRANSPORTER_DATA_CREATE_TIME + " ) " +
                "VALUES ('" + fId + "', '" + tId + "', '" + cId + "', '" + quantityL + "', '" +
                sniffTest + "', '" + alcoholTest + "', '" + densityTest + "', '" +
                wordComment.replace("'", "''") + "', '" +
                createDate + "', '" + createTime +"');";
        db.execSQL(insertStatement);
    }
//TODO remove test
    //Save container id in the plant_info table
   /* public void insertContainerID (int cID){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("INSERT INTO "+ TABLE_PLANT_DATA);
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_DATA_CONTAINER_ID, cID);
        db.insert(TABLE_PLANT_DATA, null, contentValues);

    }*/


    //Update conatiner data after collection
    public void updateContainerInfo (int cId, double amountRemaining){
        SQLiteDatabase db = this.getWritableDatabase();
        String insertStatement = "UPDATE " + TABLE_CONTAINER + " SET " + CONTAINER_AMOUNT_REMAINING + " = " +"'" + amountRemaining + "' WHERE " + CONTAINER_ID + " = " +"'" + cId + "';";
        db.execSQL(insertStatement);

    }

    // Insert into container table a number of containers of the specified size
    public void addContainers (int numContainers, int size){
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i<= numContainers-1; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CONTAINER_SIZE, size);
            contentValues.put(CONTAINER_AMOUNT_REMAINING, size);
            db.insert(TABLE_CONTAINER, null, contentValues);
        }

    }

    public void insertTransportersFromCSV(BufferedReader buffer) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            db.execSQL("DELETE FROM "+ TABLE_TRANSPORTER);

            // TODO: Don't hardcode this
            String insertStatementPart1 = "INSERT INTO " + TABLE_TRANSPORTER + " (_id, " +
                    "name, phone_number) values(";
            String insertStatementPart2 = ");";

            // Skip first line of csv which contains labels/headings
            String line = buffer.readLine();

            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(insertStatementPart1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "','");
                // Some names have apostrophes in them so we need to escape them.
                // This is done by using two apostrophes in place of one
                sb.append(str[1].replace("'", "''") + "','");
                sb.append(str[2] + "'");
                sb.append(insertStatementPart2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertReceiversFromCSV(BufferedReader buffer) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            db.execSQL("DELETE FROM "+ TABLE_RECEIVER);

            // TODO: Don't hardcode this
            String insertStatementPart1 = "INSERT INTO " + TABLE_RECEIVER + " (_id, " +
                    "name, phone_number, username, password_hash) values(";
            String insertStatementPart2 = ");";

            // Skip first line of csv which contains labels/headings
            String line = buffer.readLine();

            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(insertStatementPart1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "','");
                // Some names have apostrophes in them so we need to escape them.
                // This is done by using two apostrophes in place of one
                sb.append(str[1].replace("'", "''") + "','");
                sb.append(str[2] + "','");
                sb.append(str[3] + "','");
                sb.append(str[4] + "'");
                sb.append(insertStatementPart2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertFarmersFromCSV(BufferedReader buffer) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            db.execSQL("DELETE FROM "+ TABLE_FARMER);

            // TODO: Don't hardcode this
            String insertStatementPart1 = "INSERT INTO " + TABLE_FARMER + " (_id, " +
                    "assigned_transporter_id, name, phone_number," +
                    "active, expected_collection_time) values(";
            String insertStatementPart2 = ");";

            // Skip first line of csv which contains labels/headings
            String line = buffer.readLine();

            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(insertStatementPart1);
                String[] str = line.split(",");

                sb.append("'" + str[0] + "','");
                sb.append(str[1] + "','");
                // Some names have apostrophes in them so we need to escape them.
                // This is done by using two apostrophes in place of one
                sb.append(str[2].replace("'", "''")  + "','");
                sb.append(str[3] + "','");
                sb.append(str[4] + "','");
                sb.append(str[5] + "'");
                sb.append(insertStatementPart2);
                db.execSQL(sb.toString());
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
