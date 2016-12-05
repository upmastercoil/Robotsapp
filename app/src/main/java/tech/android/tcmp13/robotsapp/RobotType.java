package tech.android.tcmp13.robotsapp;

import android.os.Parcelable;

/**
 * Created by tcmp13-t on 12/4/2016.
 */
public enum RobotType {
    TERMINATOR(0),
    CLEANING(1),
    TRANSFORMER(2),
    DECEPTICONS(3),
    BENDING(4);

    private int rawValue;

    RobotType(int rawValue) {
        this.rawValue = rawValue;
    }

    public int getRawValue() {

        return rawValue;
    }

    public static RobotType fromRawValue(int rawValue) {

        for (RobotType type : values())
            if (type.getRawValue() == rawValue)
                return type;
        return null;
    }
}
