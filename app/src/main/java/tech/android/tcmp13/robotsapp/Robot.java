package tech.android.tcmp13.robotsapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tcmp13-t on 12/4/2016.
 */
public class Robot implements Parcelable {

    private long id;
    private String name;
    private String brand;
    private RobotType type;

    public Robot(String name, String brand, RobotType type) {

        this(-1, name, brand, type);
    }

    public Robot(long id, String name, String brand, RobotType type) {

        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
    }

    protected Robot(Parcel in) {

        id = in.readLong();
        name = in.readString();
        brand = in.readString();
        //Since RobotType is enum and enum cannot implement or extends interfaces/classes,
        // the parcel operation can't save the object.
        //HOWEVER! since enum represents Keys and Values we can save the VALUE here and recreate
        // the type from the value. (PAY ATTENTION TO THE ORDER)
        type = RobotType.fromRawValue(in.readInt());
    }

    public static final Creator<Robot> CREATOR = new Creator<Robot>() {
        @Override
        public Robot createFromParcel(Parcel in) {
            return new Robot(in);
        }

        @Override
        public Robot[] newArray(int size) {
            return new Robot[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public RobotType getType() {
        return type;
    }

    public void setType(RobotType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Robot robot = (Robot) o;

        return id == robot.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(brand);
        //Since RobotType is enum and enum cannot implement or extends interfaces/classes,
        // the parcel operation can't save the object.
        //HOWEVER! since enum represents Keys and Values we can save the VALUE here and recreate
        // the type from the value.
        parcel.writeInt(type.getRawValue());
    }
}
