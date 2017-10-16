package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails;

import org.greenrobot.eventbus.Subscribe;

import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventAuctionItems;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventBidInfo;

/**
 * Created by taru on 5/17/2017.
 */

public class AuctionItemDetailsMvp {
    public interface Model {
        void setPresenter(Presenter presenter);

        void setup();

        void cleanup();

        void initiateBidRequest(String userId, long itemId, int userBid);

        @Subscribe
        void getEvent(EventBidInfo.EventResponseCode itemsList);

        @Subscribe
        void getEvent(EventAuctionItems.EventAuctionItemUpdate event);
    }

    public interface View {
        void onSuccess(byte responseCode);

        void onFailure(byte errorCode);

        void onRequestStart(byte resCode);
    }

    public interface Presenter {
        void setView(View view);

        void initiateBid(long itemId, String userId, int minBid, String userBid);

        void viewOnCreate();

        void viewOnDestroy();

        void onBidResult(byte responseCode);

        void onMinimumBidUpdateResult(byte responseCode);
    }
}
