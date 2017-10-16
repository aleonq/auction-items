package yours.auction.mobile.ani.net.ta.auctionyours.eventbus;

import java.util.List;

/**
 * Created by taru on 5/16/2017.
 */

public class EventBiddingHelperBot extends Event {

    public static class EventUserInfo<T> implements ListEvent<T> {

        private List<T> list;

        @Override
        public void setList(List<T> list) {
            this.list = list;
        }

        @Override
        public List<T> getList() {
            return list;
        }
    }

    public static class EventAuctionItems<T> implements ListEvent<T> {

        private List<T> list;

        @Override
        public void setList(List<T> list) {
            this.list = list;
        }

        @Override
        public List<T> getList() {
            return list;
        }
    }

    public static class EventUserInfoCount extends Event {
        public EventUserInfoCount(int count) {
            this.count = count;
        }

        public long count;
    }

    public static class EventAuctionItemCount extends Event {
        public EventAuctionItemCount(int count) {
            this.count = count;
        }

        public long count;
    }

    public static class EventBidUpdateResponseCode extends Event {
    }
}
