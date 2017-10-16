package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist;


import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import yours.auction.mobile.ani.net.ta.auctionyours.beans.AuctionItem;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventAuctionItems;

/**
 * Created by taru on 5/15/2017.
 */

public class AuctionItemsListMvp {
    public interface Model {

        void setPresenter(Presenter presenter);

        void requestAuctionItemsData(int index);

        void setup();

        void cleanup();

        @Subscribe
        void getEvent(EventAuctionItems.EventResponseItemsList itemsList);
    }

    interface View {

        void displayAuctionItems(List<AuctionItem> auctionItemList);

        void onRequestStart(byte resCode);
    }

    public interface Presenter {
        void requestAuctionItemsList(int index);

        void onAuctionItemsListResult(List<AuctionItem> itemsList);

        void setView(View view);

        void viewOnCreate();

        void viewOnDestroy();
    }
}
