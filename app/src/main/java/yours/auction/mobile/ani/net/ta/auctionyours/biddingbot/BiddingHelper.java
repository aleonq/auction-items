package yours.auction.mobile.ani.net.ta.auctionyours.biddingbot;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import yours.auction.mobile.ani.net.ta.auctionyours.beans.AuctionItem;
import yours.auction.mobile.ani.net.ta.auctionyours.beans.UserInfo;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.BidInfoDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.UserInfoDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventBiddingHelperBot;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Utility;

/**
 * Created by taru on 5/16/2017.
 */

public class BiddingHelper {

    private static final String TAG = BiddingHelper.class.getSimpleName();

    private BidInfoDataManager bidInfoDataManager;
    private Context context;

    private volatile boolean isAvailableUserInfo;
    private volatile boolean isAvailableAuctionItem;

    private long usersCount;
    private long itemsCount;
    private String userId;
    private long itemId;
    private int currentBid;
    private Handler handler;
    private Runnable runnable;

    public BiddingHelper(Context context) {
        bidInfoDataManager = new BidInfoDataManager(context);
        this.context = context;
        setup();
    }

    public void startBidding() {
        initiateDataRequest();
    }

    private void initiateDataRequest() {
        isAvailableUserInfo = false;
        isAvailableAuctionItem = false;
        AuctionItemsDataManager auctionItemsDataManager = new AuctionItemsDataManager(context);
        auctionItemsDataManager.getItemsCount();

        UserInfoDataManager userInfoDataManager = new UserInfoDataManager(context);
        userInfoDataManager.getUsersCount();
    }

    @Subscribe
    public void onEvent(EventBiddingHelperBot.EventUserInfoCount event) {
        usersCount = event.count;
        Log.d(TAG, "get users count : " + event.count);
        requestRandomUserInfo(event.count);
    }

    private void requestRandomUserInfo(long count) {
        int randUserRow = Utility.generateRandomNumber(0, (int) count);
        EventBiddingHelperBot.EventUserInfo usersEvent = new EventBiddingHelperBot.EventUserInfo<>();
        UserInfoDataManager userInfoDataManager = new UserInfoDataManager(context);
        userInfoDataManager.requestUsersPaged(randUserRow, usersEvent);
    }

    @Subscribe
    public void onEvent(EventBiddingHelperBot.EventAuctionItemCount event) {
        itemsCount = event.count;
        Log.d(TAG, "get items count : " + event.count);
        requestRandomAuctionItem(event.count);
    }

    private void requestRandomAuctionItem(long count) {
        int randItemRow = Utility.generateRandomNumber(0, (int) count);
        EventBiddingHelperBot.EventAuctionItems itemsEvent = new EventBiddingHelperBot.EventAuctionItems();
        AuctionItemsDataManager auctionItemsDataManager = new AuctionItemsDataManager(context);
        auctionItemsDataManager.requestItemsPaged(randItemRow, 1, itemsEvent);
    }

    @Subscribe
    public void onEvent(EventBiddingHelperBot.EventAuctionItems event) {
        List<AuctionItem> auctionItemList = event.getList();
        if (auctionItemList.isEmpty() == false) {
            itemId = auctionItemList.get(0).itemId;
            currentBid = auctionItemList.get(0).itemMinimumBidValue;
            isAvailableAuctionItem = true;
        }
        bidOnItemRandom(userId, itemId, currentBid);
    }

    @Subscribe
    public void onEvent(EventBiddingHelperBot.EventUserInfo eventUserInfo) {
        List<UserInfo> userInfoList = eventUserInfo.getList();
        if (userInfoList.isEmpty() == false) {
            userId = new String(userInfoList.get(0).userId);
            isAvailableUserInfo = true;
        }
        bidOnItemRandom(new String(userId), itemId, currentBid);
    }

    public void bidOnItemRandom(final String userId, final long itemId, final int currentBidValue) {
        if (isDataAvailable()) {
            int maxBid = getMaxBid(currentBidValue);
            int randomBidValue = Utility.generateRandomNumber(currentBidValue, maxBid + 1);
            bidInfoDataManager.saveOrUpdateBid(userId, itemId, randomBidValue, new EventBiddingHelperBot.EventBidUpdateResponseCode());
        }
    }

    private int getMaxBid(int minBid) {
        int maxBid = minBid + minBid / 10;
        maxBid = maxBid == 0 ? 1 : maxBid;
        return maxBid;
    }

    private boolean isDataAvailable() {
        return isAvailableUserInfo && isAvailableAuctionItem;
    }

    @Subscribe
    public void onEvent(final EventBiddingHelperBot.EventBidUpdateResponseCode event) {
        Log.d(TAG, "onEvent: creating another bid - bidder the bot");
        runnable = new Runnable() {
            @Override
            public void run() {
                if (event.responseCode == Constants.CODE_SUCCESS_GENERIC) {
                    isAvailableUserInfo = false;
                    isAvailableAuctionItem = false;
                    requestRandomAuctionItem(itemsCount);
                    requestRandomUserInfo(usersCount);
                }
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    public void setup() {
        GlobalEventBus.getsBus().register(this);
        handler = new Handler();
    }

    public void cleanup() {
        GlobalEventBus.getsBus().unregister(this);
        handler.removeCallbacks(runnable);
    }

}
