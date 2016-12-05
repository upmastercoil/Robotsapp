package tech.android.tcmp13.robotsapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import tech.android.tcmp13.robotsapp.Robot;
import tech.android.tcmp13.robotsapp.RobotType;
import tech.android.tcmp13.robotsapp.data.RobotsContract.RobotEntry;

import static tech.android.tcmp13.robotsapp.data.RobotsContract.RobotEntry.*;

// TODO: 12/4/2016 Calls to getReadableDataBase and getWritableDataBase MUST be performed on a background thread

/**
 * Decorates the DbOpenHelper with a set of convenient methods to avoid SQL!@#!@#!@
 * <p>
 * Created by tcmp13-t on 12/4/2016.
 */
public class DbHandler {

    private DbOpenHelper dbOpenHelper;

    public DbHandler(Context context) {

        dbOpenHelper = new DbOpenHelper(context);
    }

    public void insert(Robot robot) {

        //Get The DB
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return;

        //Special type of KV object, perfect for working with DB.
        ContentValues values = generateContentValues(robot);

        //Perform the insertion
        long id = -1;
        try {
            id = db.insert(TABLE_NAME, null, values);
        } finally {
            //Must be performed
            db.close();
        }
        if (id != -1)
            robot.setId(id);
    }

    public void update(Robot robot) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return;

        ContentValues values = generateContentValues(robot);
        try {
            db.update(TABLE_NAME, values, _ID + "=?", new String[]{String.valueOf(robot.getId())});
        } finally {
            db.close();
        }
    }

    public void delete(int id) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return;

        try {
            db.delete(TABLE_NAME, _ID + "=?", new String[]{String.valueOf(id)});
        } finally {
            db.close();
        }
    }

    public Robot query(String name) {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        if (db == null)
            return null;

        //The cursor holds all the results fetched from the db
        //If there are several names fetch the first one by its ID
        Cursor cursor = db.query(TABLE_NAME, null, COLUMNS_NAME + "=?", new String[]{name}, null, null, "DESC");

        Robot robot = null;
        try {
            if (cursor.moveToFirst()) {
                //If there are results, fetch the data and create the robot!
                long id = cursor.getLong(cursor.getColumnIndex(_ID));
                String brand = cursor.getString(cursor.getColumnIndex(COLUMNS_BRAND));
                int typeString = cursor.getInt(cursor.getColumnIndex(COLUMNS_TYPE));
                RobotType type = RobotType.fromRawValue(typeString);
                robot = new Robot(id, name, brand, type);
            }
        } finally {
            cursor.close();
        }

        return robot;
    }

    public Cursor queryAll() {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        if (db == null)
            return null;
        Cursor cursor = null;
        cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMNS_NAME + " DESC");
        return cursor;
    }

    public void clearAll() {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return;

        try {
            db.delete(TABLE_NAME, null, null);
        } finally {
            db.close();
        }
    }

    @NonNull
    private ContentValues generateContentValues(Robot robot) {
        ContentValues values = new ContentValues();
        values.put(COLUMNS_NAME, robot.getName());
        values.put(COLUMNS_BRAND, robot.getBrand());
        values.put(COLUMNS_TYPE, robot.getType().getRawValue());
        return values;
    }
}
