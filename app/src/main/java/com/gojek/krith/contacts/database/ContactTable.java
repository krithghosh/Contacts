package com.gojek.krith.contacts.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.gojek.krith.contacts.models.Contact;

import rx.functions.Func1;

/**
 * Created by krith on 01/02/17.
 */

public class ContactTable {
    public static final String TABLE = "contact";
    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PROFILE_PIC = "profile_pic";
    public static final String FAVORITE = "favorite";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String URL = "url";
    public static final String QUERY_ALL_CONTACTS = "SELECT " + ID + ", "
            + FIRST_NAME + ", " + LAST_NAME + ", " + EMAIL + ", " + PHONE_NUMBER + ", "
            + PROFILE_PIC + ", " + FAVORITE + ", " + CREATED_AT + ", " + UPDATED_AT + ", "
            + URL + " FROM " + TABLE + " ORDER BY " + FIRST_NAME + " ASC, " + LAST_NAME + " ASC";
    public static final String QUERY_CONTACT = "SELECT " + ID + ", "
            + FIRST_NAME + ", " + LAST_NAME + ", " + EMAIL + ", " + PHONE_NUMBER + ", "
            + PROFILE_PIC + ", " + FAVORITE + ", " + CREATED_AT + ", " + UPDATED_AT + ", "
            + URL + " FROM " + TABLE + " WHERE " + ID + "=?";

    public static final String CREATE_TABLE = ""
            + "CREATE TABLE " + TABLE + "("
            + ID + " INTEGER NOT NULL PRIMARY KEY,"
            + FIRST_NAME + " TEXT NOT NULL,"
            + LAST_NAME + " TEXT NOT NULL,"
            + EMAIL + " TEXT,"
            + PHONE_NUMBER + " TEXT,"
            + PROFILE_PIC + " TEXT,"
            + URL + " TEXT,"
            + CREATED_AT + " TEXT,"
            + UPDATED_AT + " TEXT,"
            + FAVORITE + " BOOLEAN DEFAULT FALSE "
            + ")";

    public static final class Builder {

        private final ContentValues contentValues = new ContentValues();

        public Builder setId(int id) {
            contentValues.put(ID, id);
            return this;
        }

        public Builder setFirstName(String value) {
            contentValues.put(FIRST_NAME, value);
            return this;
        }

        public Builder setLastName(String value) {
            contentValues.put(LAST_NAME, value);
            return this;
        }

        public Builder setEmail(String value) {
            contentValues.put(EMAIL, value);
            return this;
        }

        public Builder setPhoneNumber(String value) {
            contentValues.put(PHONE_NUMBER, value);
            return this;
        }

        public Builder setProfilePic(String value) {
            contentValues.put(PROFILE_PIC, value);
            return this;
        }

        public Builder setFavorite(boolean value) {
            contentValues.put(FAVORITE, value);
            return this;
        }

        public Builder setCreatedAt(String value) {
            contentValues.put(CREATED_AT, value);
            return this;
        }

        public Builder setUpdatedAt(String value) {
            contentValues.put(UPDATED_AT, value);
            return this;
        }

        public Builder setUrl(String value) {
            contentValues.put(URL, value);
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }

    public static Func1<Cursor, Contact> MAPPER = new Func1<Cursor, Contact>() {

        @Override
        public Contact call(Cursor cursor) {

            Contact contact = new Contact();
            contact.setId(Db.getInt(cursor, ID));
            contact.setFirstName(Db.getString(cursor, FIRST_NAME));
            contact.setLastName(Db.getString(cursor, LAST_NAME));
            contact.setEmail(Db.getString(cursor, EMAIL));
            contact.setPhoneNumber(Db.getString(cursor, PHONE_NUMBER));
            contact.setFavorite(Db.getBoolean(cursor, FAVORITE));
            contact.setUrl(Db.getString(cursor, URL));
            contact.setProfilePic(Db.getString(cursor, PROFILE_PIC));
            contact.setCreatedAt(Db.getString(cursor, CREATED_AT));
            contact.setUpdatedAt(Db.getString(cursor, UPDATED_AT));
            return contact;
        }
    };
}
