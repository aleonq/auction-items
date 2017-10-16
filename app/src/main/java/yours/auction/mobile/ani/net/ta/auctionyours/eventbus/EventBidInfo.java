package yours.auction.mobile.ani.net.ta.auctionyours.eventbus;

import java.util.List;

import yours.auction.mobile.ani.net.ta.auctionyours.beans.BidInfo;

/**
 * Created by taru on 5/15/2017.
 */

public class EventBidInfo {
    public static class EventBidInfoList {
        public final List<BidInfo> bidInfoList;

        public EventBidInfoList(List<BidInfo> bidInfoList) {
            this.bidInfoList = bidInfoList;
        }
    }

    public static class EventResponseCode extends Event {
    }


}
