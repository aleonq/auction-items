package yours.auction.mobile.ani.net.ta.auctionyours.dataaccess;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import yours.auction.mobile.ani.net.ta.auctionyours.beans.AuctionItem;
import yours.auction.mobile.ani.net.ta.auctionyours.beans.UserInfo;
import yours.auction.mobile.ani.net.ta.auctionyours.database.DatabaseContract;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventAuctionItems;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventBiddingHelperBot;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.ListEvent;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Utility;

/**
 * Created by taru on 5/14/2017.
 */

public class AuctionItemsDataManager {

    private Context context;
    private String authority;
    private Uri uriPath;

    public AuctionItemsDataManager(Context context) {
        this.context = context;
        authority = Utility.getAuthotity(context.getApplicationInfo().packageName, DatabaseContract.AuctionItemTable.TABLE_NAME);
        uriPath = Uri.parse("content://" + authority + "/" + DatabaseContract.AuctionItemTable.TABLE_NAME);
    }

    /**
     * @param auctionItem
     * @return Returns true if the operation was successful
     */
    private boolean saveItem(AuctionItem auctionItem) {
        try {
            ContentResolver cr = context.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.AuctionItemTable.ITEM_NAME, auctionItem.itemName);
            values.put(DatabaseContract.AuctionItemTable.ITEM_CATEGORY, auctionItem.itemCategory);
            values.put(DatabaseContract.AuctionItemTable.ITEM_DESCRIPTION, auctionItem.itemDescription);
            values.put(DatabaseContract.AuctionItemTable.ITEM_IMAGE_URI, auctionItem.itemImageUri);
            values.put(DatabaseContract.AuctionItemTable.ITEM_AUCTION_START_DATE, auctionItem.itemStartDate);
            values.put(DatabaseContract.AuctionItemTable.ITEM_AUCTION_EXPIRY_DATE, auctionItem.itemExpiryDate);
            values.put(DatabaseContract.AuctionItemTable.ITEM_MIN_BID_VALUE, auctionItem.itemMinimumBidValue);
            values.put(DatabaseContract.AuctionItemTable.ITEM_OWNER_ID, auctionItem.ownerId);
            return cr.insert(uriPath, values) != null;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param auctionItem
     * @return Returns true if the operation was successful
     */
    private boolean updateItem(AuctionItem auctionItem) {
        ContentResolver cr = context.getContentResolver();
        String[] selectionArgs = {"" + auctionItem.itemId};
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AuctionItemTable.ITEM_ID, auctionItem.itemId);
        values.put(DatabaseContract.AuctionItemTable.ITEM_NAME, auctionItem.itemName);
        values.put(DatabaseContract.AuctionItemTable.ITEM_CATEGORY, auctionItem.itemCategory);
        values.put(DatabaseContract.AuctionItemTable.ITEM_DESCRIPTION, auctionItem.itemDescription);
        values.put(DatabaseContract.AuctionItemTable.ITEM_IMAGE_URI, auctionItem.itemImageUri);
        values.put(DatabaseContract.AuctionItemTable.ITEM_AUCTION_START_DATE, auctionItem.itemStartDate);
        values.put(DatabaseContract.AuctionItemTable.ITEM_AUCTION_EXPIRY_DATE, auctionItem.itemExpiryDate);
        values.put(DatabaseContract.AuctionItemTable.ITEM_MIN_BID_VALUE, auctionItem.itemMinimumBidValue);
        values.put(DatabaseContract.AuctionItemTable.ITEM_OWNER_ID, auctionItem.ownerId);
        return cr.update(uriPath, values, DatabaseContract.AuctionItemTable.ITEM_ID + "=?", selectionArgs) > 0;
    }

    private List<AuctionItem> requestItemsPaged(String sortOrder) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uriPath, null, null, null, sortOrder);
        List<AuctionItem> itemsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_NAME));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_DESCRIPTION));
            String category = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_CATEGORY));
            String startdate = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_AUCTION_START_DATE));
            String enddate = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_AUCTION_EXPIRY_DATE));
            String imageurl = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_IMAGE_URI));
            int minBid = cursor.getInt(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_MIN_BID_VALUE));
            String ownerId = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_OWNER_ID));
            AuctionItem item = new AuctionItem(id, name, category, description, imageurl, startdate, enddate, minBid, ownerId);
            itemsList.add(item);
        }
        cursor.close();
        return itemsList;
    }

    public void requestItemsPaged(int page, ListEvent event) {
        requestItemsPaged(page, Constants.AUCTION_ITEM_LIST_PAGE_SIZE, event);
    }

    public void requestItemsPaged(final int page, final int pageSize, final ListEvent event) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String sortOrder = pageSize * page + ", " + pageSize;
                List<AuctionItem> itemsList = requestItemsPaged(sortOrder);
                event.setList(itemsList);
                GlobalEventBus.getsBus().post(event);
            }
        }, 500);
    }

    public void getItemsCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver cr = context.getContentResolver();
                Cursor cursor = cr.query(uriPath, new String[]{"count(*)"},
                        null, null, null);
                int result;
                if (cursor.getCount() == 0) {
                    cursor.close();
                    result = 0;
                } else {
                    cursor.moveToFirst();
                    result = cursor.getInt(0);
                    cursor.close();
                }
                GlobalEventBus.getsBus().post(new EventBiddingHelperBot.EventAuctionItemCount(result));

            }
        }).start();
    }

    public AuctionItem getItemById(int id) {
        ContentResolver cr = context.getContentResolver();
        String selection = DatabaseContract.UserInfoTable.USER_ID + "=?";
        String[] selectionArgs = {id + ""};
        Cursor cursor = cr.query(uriPath, null, selection, selectionArgs, null);
        AuctionItem item = null;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_NAME));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_DESCRIPTION));
            String category = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_CATEGORY));
            String startdate = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_AUCTION_START_DATE));
            String enddate = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_AUCTION_EXPIRY_DATE));
            String imageurl = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_IMAGE_URI));
            String owner_id = cursor.getString(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_OWNER_ID));
            int minBid = cursor.getInt(cursor.getColumnIndex(DatabaseContract.AuctionItemTable.ITEM_MIN_BID_VALUE));
            item = new AuctionItem(id, name, category, description, imageurl, startdate, enddate, minBid, owner_id);
            cursor.close();
        }
        return item;
    }

    public void saveItem(String name, String cat, String desc, String imageUri, String startDate, String endDate, int minBid, String ownerId) {
        boolean isSuccessful = saveItem(new AuctionItem(name, cat, desc, imageUri, startDate, endDate, minBid, ownerId));
        if (isSuccessful) {
            EventAuctionItems.EventAuctionItemAdd event = new EventAuctionItems.EventAuctionItemAdd();
            event.responseCode = Constants.CODE_SUCCESS_GENERIC;
            GlobalEventBus.getsBus().post(event);
        } else {
            EventAuctionItems.EventAuctionItemAdd event = new EventAuctionItems.EventAuctionItemAdd();
            event.responseCode = Constants.CODE_FAILURE_GENERIC;
            GlobalEventBus.getsBus().post(event);
        }
    }

    public void updateItem(int id, String name, String cat, String desc, String imageUri, String startDate, String endDate, int minBid, String ownerId) {
        boolean isSuccessful = updateItem(new AuctionItem(id, name, cat, desc, imageUri, startDate, endDate, minBid, ownerId));
        if (isSuccessful) {
            EventAuctionItems.EventAuctionItemAdd event = new EventAuctionItems.EventAuctionItemAdd();
            event.responseCode = Constants.CODE_SUCCESS_GENERIC;
            GlobalEventBus.getsBus().post(event);
        } else {
            EventAuctionItems.EventAuctionItemAdd event = new EventAuctionItems.EventAuctionItemAdd();
            event.responseCode = Constants.CODE_FAILURE_GENERIC;
            GlobalEventBus.getsBus().post(event);
        }
    }

    /**
     * @param itemId
     * @param minBid
     * @return Returns true if the operation was successful
     */
    public void updateItem(long itemId, int minBid) {
        ContentResolver cr = context.getContentResolver();
        String[] selectionArgs = {"" + itemId};
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AuctionItemTable.ITEM_MIN_BID_VALUE, minBid);
        boolean isSuccessful = cr.update(uriPath, values, DatabaseContract.AuctionItemTable.ITEM_ID + "=?", selectionArgs) > 0;
        if (isSuccessful) {
            EventAuctionItems.EventAuctionItemUpdate event = new EventAuctionItems.EventAuctionItemUpdate();
            event.responseCode = Constants.CODE_SUCCESS_GENERIC;
            GlobalEventBus.getsBus().post(event);
        } else {
            EventAuctionItems.EventAuctionItemUpdate event = new EventAuctionItems.EventAuctionItemUpdate();
            event.responseCode = Constants.CODE_FAILURE_GENERIC;
            GlobalEventBus.getsBus().post(event);
        }
    }
}