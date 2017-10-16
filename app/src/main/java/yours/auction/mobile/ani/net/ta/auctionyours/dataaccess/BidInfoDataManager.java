package yours.auction.mobile.ani.net.ta.auctionyours.dataaccess;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import yours.auction.mobile.ani.net.ta.auctionyours.beans.BidInfo;
import yours.auction.mobile.ani.net.ta.auctionyours.database.DatabaseContract;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.Event;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventBidInfo;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Utility;

/**
 * Created by taru on 5/14/2017.
 */

public class BidInfoDataManager {

    private Context context;
    private String authority;
    private Uri uriPath;

    public BidInfoDataManager(Context context) {
        this.context = context;
        authority = Utility.getAuthotity(context.getApplicationInfo().packageName, DatabaseContract.BidInfoTable.TABLE_NAME);
        uriPath = Uri.parse("content://" + authority + "/" + DatabaseContract.BidInfoTable.TABLE_NAME);
    }

    private void saveBid(BidInfo bidInfo) throws SQLException {
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.BidInfoTable.USER_ID, bidInfo.userId);
        values.put(DatabaseContract.BidInfoTable.ITEM_ID, bidInfo.itemId);
        values.put(DatabaseContract.BidInfoTable.BID_VALUE, bidInfo.bidValue);
        cr.insert(uriPath, values);
    }

    private void updateBid(BidInfo bidInfo) throws SQLException {
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.BidInfoTable.USER_ID, bidInfo.userId);
        values.put(DatabaseContract.BidInfoTable.ITEM_ID, bidInfo.itemId);
        values.put(DatabaseContract.BidInfoTable.BID_VALUE, bidInfo.bidValue);
        String whereClause = DatabaseContract.BidInfoTable.USER_ID + "=? AND " + DatabaseContract.BidInfoTable.ITEM_ID + "=?";
        String[] selectionArgs = {bidInfo.userId, bidInfo.itemId + ""};
        cr.update(uriPath, values, whereClause, selectionArgs);
    }

    public List<BidInfo> getAllBids() {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uriPath, null, null, null, null);
        List<BidInfo> bidInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String userId = cursor.getString(cursor.getColumnIndex(DatabaseContract.BidInfoTable.USER_ID));
            long itemId = cursor.getLong(cursor.getColumnIndex(DatabaseContract.BidInfoTable.ITEM_ID));
            int bidValue = cursor.getInt(cursor.getColumnIndex(DatabaseContract.BidInfoTable.BID_VALUE));
            BidInfo bidInfo = new BidInfo(userId, itemId, bidValue);
            bidInfoList.add(bidInfo);
        }
        cursor.close();
        return bidInfoList;
    }

    public List<BidInfo> getBidsByUser(String userId) {
        ContentResolver cr = context.getContentResolver();
        String selection = DatabaseContract.UserInfoTable.USER_ID + "=?";
        String[] selectionArgs = {userId};
        List<BidInfo> bidInfoList = new ArrayList<>();
        Cursor cursor = cr.query(uriPath, null, selection, selectionArgs, null);
        BidInfo bidInfo = null;
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndex(DatabaseContract.BidInfoTable.ITEM_ID));
            int bidValue = cursor.getInt(cursor.getColumnIndex(DatabaseContract.BidInfoTable.BID_VALUE));
            bidInfo = new BidInfo(userId, itemId, bidValue);
            cursor.close();
            bidInfoList.add(bidInfo);
        }
        return bidInfoList;
    }

    public void saveOrUpdateBid(final String userId, final long itemId, final int bidValue, final Event event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    saveBid(new BidInfo(userId, itemId, bidValue));
                    event.responseCode = Constants.CODE_SUCCESS_GENERIC;
                    GlobalEventBus.getsBus().post(event);
                } catch (SQLiteConstraintException e) {
                    updateBid(new BidInfo(userId, itemId, bidValue));
                    event.responseCode = Constants.CODE_SUCCESS_GENERIC;
                    GlobalEventBus.getsBus().post(event);
                    e.printStackTrace();
                } catch (SQLException e) {
                    event.responseCode = Constants.CODE_FAILURE_GENERIC;
                    GlobalEventBus.getsBus().post(event);
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void saveOrUpdateBid(final String userId, final long itemId, final int bidValue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBidInfo.EventResponseCode event = new EventBidInfo.EventResponseCode();
                try {
                    saveBid(new BidInfo(userId, itemId, bidValue));
                    event.responseCode = Constants.CODE_SUCCESS_GENERIC;
                    GlobalEventBus.getsBus().post(event);
                } catch (SQLiteConstraintException e) {
                    updateBid(new BidInfo(userId, itemId, bidValue));
                    event.responseCode = Constants.CODE_SUCCESS_GENERIC;
                    GlobalEventBus.getsBus().post(event);
                    e.printStackTrace();
                } catch (SQLException e) {
                    event.responseCode = Constants.CODE_FAILURE_GENERIC;
                    GlobalEventBus.getsBus().post(event);
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
