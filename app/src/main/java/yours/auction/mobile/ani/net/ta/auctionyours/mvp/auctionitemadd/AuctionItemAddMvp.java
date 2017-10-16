package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd;

import org.greenrobot.eventbus.Subscribe;

import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventAuctionItems;

/**
 * Created by taru on 5/17/2017.
 */

public class AuctionItemAddMvp {
    public interface Model {
        void setPresenter(Presenter presenter);

        void setup();

        void cleanup();

        void initiateAddItemRequest(String itemName, String itemCategory, String itemDescription,
                                    String dateCur, String dateExp, int minBid, String ownerId);

        @Subscribe
        void getEvent(EventAuctionItems.EventAuctionItemAdd event);
    }

    public interface View {
        void onSuccess();

        void onFailure(byte errorCode);

        void onRequestStart(byte resCode);
    }

    public interface Presenter {
        void setView(View view);

        void initiateAddItemRequest(String itemName, String itemCategory, String itemDescription, String minBid, String ownerId);

        void viewOnCreate();

        void viewOnDestroy();

        void onAuctionItemSaveResult(byte responseCode);
    }
}
