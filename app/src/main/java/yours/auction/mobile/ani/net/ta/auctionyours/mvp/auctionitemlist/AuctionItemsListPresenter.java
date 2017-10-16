package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist;

import java.util.List;

import yours.auction.mobile.ani.net.ta.auctionyours.beans.AuctionItem;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;

/**
 * Created by taru on 5/15/2017.
 */

public class AuctionItemsListPresenter implements AuctionItemsListMvp.Presenter {
    AuctionItemsListMvp.View view;
    AuctionItemsListMvp.Model model;

    public AuctionItemsListPresenter(AuctionItemsListMvp.Model model) {
        this.model = model;
        model.setPresenter(this);
    }

    @Override
    public void setView(AuctionItemsListMvp.View view) {
        this.view = view;
    }

    @Override
    public void requestAuctionItemsList(int index) {
        model.requestAuctionItemsData(index);
        view.onRequestStart(Constants.CODE_WAIT_GENERIC);
    }

    @Override
    public void onAuctionItemsListResult(List<AuctionItem> itemsList) {
        view.displayAuctionItems(itemsList);
    }

    @Override
    public void viewOnCreate() {
        model.setup();
    }

    @Override
    public void viewOnDestroy() {
        model.cleanup();
    }
}
