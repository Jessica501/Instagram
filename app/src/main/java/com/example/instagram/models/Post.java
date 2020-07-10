package com.example.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKES_COUNT = "likesCount";
    public static final String KEY_LIKES = "likedBy";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public int getLikesCount() {
//        return getJSONArray(KEY_LIKES).length();
        return getInt(KEY_LIKES_COUNT);
    }

    public void incrementLikesCount() {
        put(KEY_LIKES_COUNT, getLikesCount()+1);
        saveInBackground();
    }

    public void decrementLikesCount() {
        put(KEY_LIKES_COUNT, getLikesCount()-1);
        saveInBackground();
    }
}
