package yours.auction.mobile.ani.net.ta.auctionyours.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import yours.auction.mobile.ani.net.ta.auctionyours.util.DatabaseSetupConstants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Utility;

import static yours.auction.mobile.ani.net.ta.auctionyours.database.DatabaseContract.AuctionItemTable.ITEM_CATEGORY;
import static yours.auction.mobile.ani.net.ta.auctionyours.database.DatabaseContract.AuctionItemTable.ITEM_DESCRIPTION;
import static yours.auction.mobile.ani.net.ta.auctionyours.database.DatabaseContract.AuctionItemTable.ITEM_NAME;
import static yours.auction.mobile.ani.net.ta.auctionyours.util.Constants.BID_EXPIRY_DURATION;

/**
 * Created by taru on 5/13/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Database Version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Common constants for use in queries
     */
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA = ", ";

    /**
     * Create table queries
     */
    private static final String CREATE_USER_INFO_TABLE = "CREATE TABLE "
            + DatabaseContract.UserInfoTable.TABLE_NAME + " ("
            + DatabaseContract.UserInfoTable.USER_ID + TEXT_TYPE + " PRIMARY KEY NOT NULL" + COMMA
            + DatabaseContract.UserInfoTable.USER_NAME + TEXT_TYPE + " NOT NULL" + COMMA
            + DatabaseContract.UserInfoTable.USER_KEY + TEXT_TYPE + " NOT NULL" + " )";

    private static final String CREATE_AUCTION_ITEM_TABLE = "CREATE TABLE "
            + DatabaseContract.AuctionItemTable.TABLE_NAME + " ("
            + DatabaseContract.AuctionItemTable.ITEM_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA
            + ITEM_NAME + TEXT_TYPE + " NOT NULL" + COMMA
            + ITEM_CATEGORY + TEXT_TYPE + COMMA
            + ITEM_DESCRIPTION + TEXT_TYPE + COMMA
            + DatabaseContract.AuctionItemTable.ITEM_IMAGE_URI + TEXT_TYPE + COMMA
            + DatabaseContract.AuctionItemTable.ITEM_AUCTION_START_DATE + TEXT_TYPE + " NOT NULL" + COMMA
            + DatabaseContract.AuctionItemTable.ITEM_AUCTION_EXPIRY_DATE + TEXT_TYPE + " NOT NULL" + COMMA
            + DatabaseContract.AuctionItemTable.ITEM_MIN_BID_VALUE + INTEGER_TYPE + " NOT NULL" + COMMA
            + DatabaseContract.AuctionItemTable.ITEM_OWNER_ID + TEXT_TYPE + " NOT NULL" + " )";

    public static final String CREATE_BID_TABLE = "CREATE TABLE "
            + DatabaseContract.BidInfoTable.TABLE_NAME + " ("
            + DatabaseContract.BidInfoTable.USER_ID + TEXT_TYPE + " NOT NULL" + COMMA
            + DatabaseContract.BidInfoTable.ITEM_ID + INTEGER_TYPE + " NOT NULL" + COMMA
            + DatabaseContract.BidInfoTable.BID_VALUE + INTEGER_TYPE + " NOT NULL" + COMMA
            + " PRIMARY KEY(" + DatabaseContract.BidInfoTable.USER_ID + COMMA + DatabaseContract.BidInfoTable.ITEM_ID + ")" + " )";

    /**
     * Delete tables queries
     */
    public static final String DROP_USER_INFO_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.UserInfoTable.TABLE_NAME;

    public static final String DROP_AUCTION_ITEM_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.AuctionItemTable.TABLE_NAME;

    public static final String DROP_BID_TABLE = "DROP TABLE IF EXISTS " + DatabaseContract.BidInfoTable.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_INFO_TABLE);
        db.execSQL(CREATE_AUCTION_ITEM_TABLE);
        db.execSQL(CREATE_BID_TABLE);
        insertusers(db);
        insertitems(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_BID_TABLE);
        db.execSQL(DROP_AUCTION_ITEM_TABLE);
        db.execSQL(DROP_USER_INFO_TABLE);
        onCreate(db);
    }

    private void insertusers(SQLiteDatabase db) {
        for (int i = 0; i < 50; i++) {
            String query = "INSERT INTO user_info_table VALUES ('" + DatabaseSetupConstants.userName[i].split(" ")[0].toLowerCase() + "', '" + DatabaseSetupConstants.userName[i] + "' ,'" + DatabaseSetupConstants.USER_PASSWORD + "');";
            db.execSQL(query);
        }
    }

    private void insertitems(SQLiteDatabase db) {
        String s = "INSERT INTO auction_item_table (name , category , decsription , image_uri , start_date , expiry_date, min_bid_value, item_owner) VALUES(";
        long time = System.currentTimeMillis();
        String dateCurrent = new SimpleDateFormat("MM-dd-yyyy").format(new Date(time));
        String dateExpiry = new SimpleDateFormat("MM-dd-yyyy").format(new Date(time + BID_EXPIRY_DURATION));
        for (int i = 0; i < 50; i++) {
            int minBid = Utility.generateRandomNumber(5, (int) 500);
            System.out.println(minBid);
            String query = s + "'" + DatabaseSetupConstants.ITEM_NAME[i] + "' ,'" + DatabaseSetupConstants.ITEM_CATEGORY[i] + "', \"" + DatabaseSetupConstants.ITEM_DESCRIPTION[i]
                    + "\", '', '" + dateCurrent + "', '" + dateExpiry + "', " + minBid + ", '" + DatabaseSetupConstants.OWNER_ID[i] + "');";
            db.execSQL(query);
        }
    }
}
