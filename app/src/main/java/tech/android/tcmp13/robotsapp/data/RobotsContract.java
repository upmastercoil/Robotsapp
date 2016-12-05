package tech.android.tcmp13.robotsapp.data;

import android.provider.BaseColumns;

/**
 * Created by tcmp13-t on 12/4/2016.
 */
public interface RobotsContract {

    int DB_VERSION = 1;
    String DB_NAME = "Robots.db";

    interface RobotEntry extends BaseColumns {

        String TABLE_NAME = "robots";
        String COLUMNS_NAME = "name";
        String COLUMNS_BRAND = "brand";
        String COLUMNS_TYPE = "type";
    }
}
