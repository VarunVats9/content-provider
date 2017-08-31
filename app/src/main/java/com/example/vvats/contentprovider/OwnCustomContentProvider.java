package com.example.vvats.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class OwnCustomContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.varun.own.contentprovider";
    private static final String DATABASE = "College";
    private static final String TABLE_NAME = "student";
    private static final String COL_STUDENT_NAME = "student_name";
    private static final String COL_COLLEGE_NAME = "college_name";
    private static final int VERSION = 1;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/emp");
    public static final String COL_PRIMARY_KEY = "_id";
    public static final String BLANK_SPACES = "       ";

    /*static final int EMP = 1;
    static final int EMP_ID = 2;

    static final UriMatcher uri = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uri.addURI(AUTHORITY, "emp", EMP);
        uri.addURI(AUTHORITY, "emp/#", EMP_ID);
    }*/

    private SQLiteDatabase customDb;

    public OwnCustomContentProvider() {
    }

    private class OwnCustomDatabase extends SQLiteOpenHelper {

        Context context;

        private OwnCustomDatabase(Context context) {
            super(context, DATABASE, null, VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"("+COL_PRIMARY_KEY+" integer PRIMARY KEY autoincrement, "+COL_STUDENT_NAME+" TEXT, "+COL_COLLEGE_NAME+" TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+";");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = customDb.insert(TABLE_NAME, null, values);
        if (row > 0) {
            uri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        final OwnCustomDatabase ownCustomDatabase = new OwnCustomDatabase(getContext());
        customDb = ownCustomDatabase.getWritableDatabase();
        if (customDb != null) {
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(TABLE_NAME);

        Cursor cursor = sqLiteQueryBuilder.query(customDb, null, null, null, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
