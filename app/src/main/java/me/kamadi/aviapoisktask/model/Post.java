package me.kamadi.aviapoisktask.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import me.kamadi.aviapoisktask.BuildConfig;

/**
 * Created by Madiyar on 05.07.2016.
 */

public class Post implements Parcelable{

    @DatabaseField(id=true)
    private long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String image;

    @SerializedName("descr")
    @DatabaseField
    private String description;

    @DatabaseField
    private String tags;

    @DatabaseField
    private String author;

    @DatabaseField
    private String text;

    @DatabaseField(foreign = true, foreignColumnName = "id")
    private Magazine magazine;

    public Post(){

    }

    protected Post(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image = in.readString();
        description = in.readString();
        tags = in.readString();
        author = in.readString();
        text = in.readString();
        magazine = in.readParcelable(Magazine.class.getClassLoader());
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    public String getImagePath() {
        return BuildConfig.BASE_URL + this.image;
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
        dest.writeString(description);
        dest.writeString(tags);
        dest.writeString(author);
        dest.writeString(text);
        dest.writeParcelable(magazine, flags);
    }
}
