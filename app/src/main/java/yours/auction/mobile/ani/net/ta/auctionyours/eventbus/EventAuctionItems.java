package yours.auction.mobile.ani.net.ta.auctionyours.eventbus;

import java.util.List;

/**
 * Created by taru on 5/15/2017.
 */

public class EventAuctionItems extends Event {

    public static class EventResponseItemsList<T> implements ListEvent<T> {

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

    public static class EventAuctionItemAdd extends Event {
    }
    public static class EventAuctionItemUpdate extends Event {
    }
}