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

import yours.auction.mobile.ani.net.ta.auctionyours.beans.UserInfo;
import yours.auction.mobile.ani.net.ta.auctionyours.database.DatabaseContract;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventBiddingHelperBot;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventUserLogin;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventUserRegister;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.ListEvent;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Utility;

/**
 * Created by taru on 5/14/2017.
 */

public class UserInfoDataManager {

    private Context context;
    private String authority;
    private Uri uriPath;

    public UserInfoDataManager(Context context) {
        this.context = context;
        authority = Utility.getAuthotity(context.getApplicationInfo().packageName, DatabaseContract.UserInfoTable.TABLE_NAME);
        uriPath = Uri.parse("content://" + authority + "/" + DatabaseContract.UserInfoTable.TABLE_NAME);
    }

    private void saveUser(UserInfo userInfo) throws SQLException {
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserInfoTable.USER_ID, userInfo.userId.toLowerCase());
        values.put(DatabaseContract.UserInfoTable.USER_NAME, userInfo.userName);
        values.put(DatabaseContract.UserInfoTable.USER_KEY, userInfo.userKey);
        cr.insert(uriPath, values);
    }

    private List<UserInfo> requestUsersPaged(String sortOrder) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uriPath, null, null, null, sortOrder);
        List<UserInfo> userInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(DatabaseContract.UserInfoTable.USER_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.UserInfoTable.USER_NAME));
            String key = cursor.getString(cursor.getColumnIndex(DatabaseContract.UserInfoTable.USER_KEY));
            UserInfo userInfo = new UserInfo(id, name, key);
            userInfoList.add(userInfo);
        }
        cursor.close();
        return userInfoList;
    }

    public void requestUsersPaged(final int page, final ListEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int pageSize = 1;
                String sortOrder = pageSize * page + ", " + pageSize;
                List<UserInfo> userInfoList = requestUsersPaged(sortOrder);
                event.setList(userInfoList);
                GlobalEventBus.getsBus().post(event);
            }
        }).start();
    }

    public UserInfo getUser(String id) {
        ContentResolver cr = context.getContentResolver();
        String selection = DatabaseContract.UserInfoTable.USER_ID + "=?";
        String[] selectionArgs = {id.toLowerCase()};
        Cursor cursor = cr.query(uriPath, null, selection, selectionArgs, null);
        UserInfo userInfo = null;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.UserInfoTable.USER_NAME));
            String key = cursor.getString(cursor.getColumnIndex(DatabaseContract.UserInfoTable.USER_KEY));
            userInfo = new UserInfo(id.toLowerCase(), name, key);
            cursor.close();
        }
        return userInfo;
    }

    public void validateUserCredentials(final String id, final String key) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = getUser(id);
                if (userInfo == null || Utility.equalsIgnoreCase(userInfo.userKey, key) == false) {
                    GlobalEventBus.getsBus().post(new EventUserLogin(Constants.CODE_FAILURE_INVALID_CREDENTIALS));
                    return;
                }
                GlobalEventBus.getsBus().post(new EventUserLogin(Constants.CODE_SUCCESS_LOGIN));
            }
        });
        th.start();
    }

    public void saveUserIfNotExists(String id, String name, String pass) {
        try {
            saveUser(new UserInfo(id, name, pass));
            GlobalEventBus.getsBus().post(new EventUserRegister(Constants.CODE_SUCCESS_REGISTER));
        } catch (SQLiteConstraintException e) {
            GlobalEventBus.getsBus().post(new EventUserRegister(Constants.CODE_FAILURE_CONSTRAINTS));
            e.printStackTrace();
        } catch (SQLException e) {
            GlobalEventBus.getsBus().post(new EventUserRegister(Constants.CODE_FAILURE_GENERIC));
            e.printStackTrace();
        }
    }

    public void getUsersCount() {
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
                GlobalEventBus.getsBus().post(new EventBiddingHelperBot.EventUserInfoCount(result));
            }
        }).start();
    }
}
