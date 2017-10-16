package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd;

import org.greenrobot.eventbus.Subscribe;

import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventAuctionItems;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;

/**
 * Created by taru on 5/17/2017.
 */

public class AuctionItemAddModel implements AuctionItemAddMvp.Model {

    AuctionItemsDataManager auctionItemsDataManager;
    AuctionItemAddMvp.Presenter presenter;


    public AuctionItemAddModel(AuctionItemsDataManager bidInfoManager) {
        this.auctionItemsDataManager = bidInfoManager;
    }

    @Override
    public void setPresenter(AuctionItemAddMvp.Presenter presenter) {
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
    @Subscribe
    public void getEvent(EventAuctionItems.EventAuctionItemAdd event) {
        presenter.onAuctionItemSaveResult(event.responseCode);
    }

    @Override
    public void initiateAddItemRequest(String itemName, String itemCategory, String itemDescription, String dateCur, String dateExp, int minBid, String ownerId) {
        auctionItemsDataManager.saveItem(itemName, itemCategory, itemDescription, null, dateCur, dateExp, minBid, ownerId);
    }
}
