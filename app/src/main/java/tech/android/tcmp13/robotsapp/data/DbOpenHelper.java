package tech.android.tcmp13.robotsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static tech.android.tcmp13.robotsapp.data.RobotsContract.DB_NAME;
import static tech.android.tcmp13.robotsapp.data.RobotsContract.DB_VERSION;
import static tech.android.tcmp13.robotsapp.data.RobotsContract.RobotEntry.COLUMNS_BRAND;
import static tech.android.tcmp13.robotsapp.data.RobotsContract.RobotEntry.COLUMNS_NAME;
import static tech.android.tcmp13.robotsapp.data.RobotsContract.RobotEntry.COLUMNS_TYPE;
import static tech.android.tcmp13.robotsapp.data.RobotsContract.RobotEntry.TABLE_NAME;
import static tech.android.tcmp13.robotsapp.data.RobotsContract.RobotEntry._ID;

/**
 * Created by tcmp13-t on 12/4/2016.
 * <p>
 * The first class to interact with the DB directly, must be implemented by android
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called only if the .db file doesn't exist.
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createSql = "CREATE TABLE " + TABLE_NAME + " (" + COLUMNS_NAME + " TEXT," +
                COLUMNS_BRAND + " TEXT, " + COLUMNS_TYPE + " INTEGER," +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";
        sqLiteDatabase.execSQL(createSql);
    }

    /**
     * Called when the DB_VERSION constant value is changed;
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        String deleteSql = "DROP TABLE IF EXIST " + COLUMNS_NAME + ";";
        sqLiteDatabase.execSQL(deleteSql);
        onCreate(sqLiteDatabase);
    }
}
