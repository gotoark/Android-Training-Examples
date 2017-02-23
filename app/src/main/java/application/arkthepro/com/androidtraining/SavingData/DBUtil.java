package application.arkthepro.com.androidtraining.SavingData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAJESH KUMAR ARUMUGAM on 20-02-2017.
 */
public class DBUtil extends SQLiteOpenHelper {

    //DB version
    private static final int DB_VERSION = 1;

    //DB Name
    public static final String DB_NAME = "Users2";

    //Table Name
    public static final String TABLE_NAME = "users_table";

    //Table Columns Names

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_NUMBER = "phone_number";
    public static final String KEY_IMAGE = "profilepic";

    public DBUtil(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create TABLE
        Log.d("DB RESULT", "---------------------------------" + "Oncreate DBUTIL ");
      //  String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_NUMBER + " VARCHAR" + ")";
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_NUMBER + " VARCHAR," +KEY_IMAGE+" BLOB NOT NULL)";
        Log.d("DB RESULT", "---------------------------------" + CREATE_TABLE);
        try {
            db.execSQL(CREATE_TABLE);
            Log.d("DB RESULT", "---------------------------------" + "Table Created Successfully");
        } catch (SQLException e) {
            Log.d("DB RESULT", "---------------------------------" + "Err in Creation of Table");
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Old Table
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        try {
            db.execSQL(DROP_TABLE);
            Log.d("DB RESULT", "---------------------------------" + "Table DELETED Successfully");
        } catch (SQLException e) {
            Log.d("DB RESULT", "---------------------------------" + "ERROR in  DELETING Table");
            e.printStackTrace();
        }
        //Create Table Again
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    /*

    CRUD OPERATIONS

    C  --createUser()
    R  --readUser()
    U  --updateUser()
    D  --deleteUser()
 */

    public void createUser(UserDetails user) {
        SQLiteDatabase db = this.getReadableDatabase();
        //    onCreate(db);
        Log.d("DB RESULT", "---------------------------------DB PATH" + db.getPath());
        Log.d("DB RESULT", "---------------------------------" + "Trying to Enter DB");
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_NUMBER, user.getphoneNumber());
        values.put(KEY_IMAGE, user.getprofilepic());
        db.insert(TABLE_NAME, null, values);
        Log.d("DB RESULT", "---------------------------------" + "User Inserted Success Fully");
        db.close();
    }

    public UserDetails readUser(String phone_number) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{KEY_ID, KEY_NAME, KEY_NUMBER,KEY_IMAGE},
                KEY_NUMBER + "=?", new String[]{String.valueOf(phone_number)},
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        UserDetails userDetails = new UserDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),cursor.getBlob(3));
        cursor.close();
        db.close();
        return userDetails;
    }

    public List<UserDetails> getAllUsers() {
        List<UserDetails> userDetailsList = new ArrayList<UserDetails>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//Looping through all rows and add them in List
        if (cursor.moveToFirst()) {
            do {
                UserDetails userDetails = new UserDetails();
                userDetails.setId(Integer.parseInt(cursor.getString(0)));
                userDetails.setName(cursor.getString(1));
                userDetails.setPhoneNumber(cursor.getString(2));
                userDetails.setProfilepic(cursor.getBlob(3));
                //Addding contact to list
                userDetailsList.add(userDetails);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return userDetailsList;
    }

    public int getUserCount() {

        String countQuery = "SELECT * FROM " + DB_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        return cursor.getCount();
    }

    public int updateUser(String name, String number,byte[] profilepic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_NUMBER, number);
        values.put(KEY_IMAGE, profilepic);
        Log.d("DB RESULT", "---------------------------------" + "Table Values Updated");
        return db.update(TABLE_NAME, values, KEY_NUMBER + " =?", new String[]{String.valueOf(number)});
    }

    public int deleteUser(String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DB RESULT", "---------------------------------" + "Table Row Deleted with Number" + number);
        return db.delete(TABLE_NAME, KEY_NUMBER + " =?", new String[]{String.valueOf(number)});
    }

    public void emptyTable(Context c) {
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_QUERY = "DELETE FROM " + TABLE_NAME;
        db.execSQL(DELETE_QUERY);
        Toast.makeText(c, "Table values Deleted Successfully", Toast.LENGTH_LONG).show();
        Log.d("DB RESULT", "---------------------------------" + "Table Values Deleted");
    }

    public boolean isExists(String Column, String data) {
        Boolean result;
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "SELECT " + Column + " FROM " + TABLE_NAME + " WHERE " + Column + '='+"'"+data+"'";
        Log.d("DB RESULT", "---------------------------------" + "Executed Query : " + QUERY);
        Log.d("DB RESULT", "---------------------------------" + "RESULTS OBTAINED with Rows Count" + db.rawQuery(QUERY, null).getCount());
        Log.d("DB RESULT", "---------------------------------" + "RESULTS OBTAINED with Rows Count" + db.rawQuery(QUERY,null).getCount());
        if (db.rawQuery(QUERY, null).getCount() > 0) {

            result = true;
        } else {
            result = false;
        }
        return result;
    }


}
