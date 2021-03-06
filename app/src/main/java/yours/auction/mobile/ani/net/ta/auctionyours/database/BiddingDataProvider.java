package yours.auction.mobile.ani.net.ta.auctionyours.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import yours.auction.mobile.ani.net.ta.auctionyours.util.Utility;

/**
 * Created by taru on 5/13/2017.
 */

public class BiddingDataProvider extends ContentProvider {

    private String authority;

    private UriMatcher uriMatcher;

    private static final int BID_TABLE = 0;
    private static final int BID_TABLE_TABLE_ROW = 1;

    private SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        authority = Utility.getAuthotity(getContext().getApplicationInfo().packageName, DatabaseContract.BidInfoTable.TABLE_NAME);
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(authority, DatabaseContract.BidInfoTable.TABLE_NAME, BID_TABLE);
        uriMatcher.addURI(authority, DatabaseContract.BidInfoTable.TABLE_NAME + "/#", BID_TABLE_TABLE_ROW);

        sqLiteDatabase = new DatabaseHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case BID_TABLE:
                return sqLiteDatabase.query(DatabaseContract.BidInfoTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case BID_TABLE_TABLE_ROW:
                selection = "rowid LIKE " + uri.getLastPathSegment();
                return sqLiteDatabase.query(DatabaseContract.BidInfoTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = sqLiteDatabase.insertOrThrow(DatabaseContract.BidInfoTable.TABLE_NAME, null, values);
        return (id == -1) ? null : Uri.parse(uri + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated = sqLiteDatabase.update(DatabaseContract.BidInfoTable.TABLE_NAME, values, selection, selectionArgs);
        return updated;
    }
}
