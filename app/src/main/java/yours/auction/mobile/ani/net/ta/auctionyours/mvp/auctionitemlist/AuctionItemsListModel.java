package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist;

import org.greenrobot.eventbus.Subscribe;

import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventAuctionItems;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;

/**
 * Created by taru on 5/15/2017.
 */

public class AuctionItemsListModel implements AuctionItemsListMvp.Model {
    AuctionItemsDataManager auctionItemsDataManager;
    private AuctionItemsListMvp.Presenter presenter;

    public AuctionItemsListModel(AuctionItemsDataManager auctionItemsDataManager) {
        this.auctionItemsDataManager = auctionItemsDataManager;
    }

    @Override
    public void setPresenter(AuctionItemsListMvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestAuctionItemsData(int page) {
        EventAuctionItems.EventResponseItemsList event = new EventAuctionItems.EventResponseItemsList();
        auctionItemsDataManager.requestItemsPaged(page, event);
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
    public void getEvent(EventAuctionItems.EventResponseItemsList itemsList) {
        presenter.onAuctionItemsListResult(itemsList.getList());
    }
}
