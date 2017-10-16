package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails;

import org.greenrobot.eventbus.Subscribe;

import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.BidInfoDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventAuctionItems;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventBidInfo;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;

/**
 * Created by taru on 5/17/2017.
 */

public class AuctionItemDetailsModel implements AuctionItemDetailsMvp.Model {

    BidInfoDataManager bidInfoDataManager;
    AuctionItemsDataManager auctionItemsDataManager;
    AuctionItemDetailsMvp.Presenter presenter;


    public AuctionItemDetailsModel(BidInfoDataManager bidInfoDataManager, AuctionItemsDataManager auctionItemsDataManager) {
        this.bidInfoDataManager = bidInfoDataManager;
        this.auctionItemsDataManager = auctionItemsDataManager;
    }

    @Override
    public void setPresenter(AuctionItemDetailsMvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setup() {
        GlobalEventBus.getsBus().register(this);
    }

    @Override
    public void cleanup() {
        GlobalEventBus.getsBus().unregister(this);
    }

    @Override
    public void initiateBidRequest(String userId, long itemId, int userBid) {
        bidInfoDataManager.saveOrUpdateBid(userId, itemId, userBid);
        auctionItemsDataManager.updateItem(itemId, userBid);
    }

    @Override
    @Subscribe
    public void getEvent(EventBidInfo.EventResponseCode event) {
        presenter.onBidResult(event.responseCode);
    }

    @Override
    @Subscribe
    public void getEvent(EventAuctionItems.EventAuctionItemUpdate event) {
        presenter.onMinimumBidUpdateResult(event.responseCode);
    }
}
