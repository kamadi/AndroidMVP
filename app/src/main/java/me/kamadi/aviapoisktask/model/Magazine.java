package me.kamadi.aviapoisktask.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import me.kamadi.aviapoisktask.BuildConfig;

/**
 * Created by Madiyar on 05.07.2016.
 */

@DatabaseTable
public class Magazine implements Parcelable{
    @DatabaseField(id=true)
    private long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String image;

    public Magazine(){

    }
    protected Magazine(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<Magazine> CREATOR = new Creator<Magazine>() {
        @Override
        public Magazine createFromParcel(Parcel in) {
            return new Magazine(in);
        }

        @Override
        public Magazine[] newArray(int size) {
            return new Magazine[size];
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePath() {
        return BuildConfig.BASE_URL + image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(image);
    }
}
