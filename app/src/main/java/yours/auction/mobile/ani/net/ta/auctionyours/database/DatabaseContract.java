package yours.auction.mobile.ani.net.ta.auctionyours.database;

/**
 * Created by taru on 5/13/2017.
 */

public class DatabaseContract {
    public static final String DATABASE_NAME = "auction_local.db";

    public abstract class UserInfoTable {
        public static final String TABLE_NAME = "user_info_table";

        public static final String USER_ID = "_id";
        public static final String USER_NAME = "user_name";
        public static final String USER_KEY = "user_key";

    }

    public abstract class AuctionItemTable {
        public static final String TABLE_NAME = "auction_item_table";

        public static final String ITEM_ID = "_id";
        public static final String ITEM_NAME = "name";
        public static final String ITEM_CATEGORY = "category";
        public static final String ITEM_DESCRIPTION = "decsription";
        public static final String ITEM_IMAGE_URI = "image_uri";
        public static final String ITEM_AUCTION_START_DATE = "start_date";
        public static final String ITEM_AUCTION_EXPIRY_DATE = "expiry_date";
        public static final String ITEM_MIN_BID_VALUE = "min_bid_value";
        public static final String ITEM_OWNER_ID = "item_owner";
    }

    public abstract class BidInfoTable {
        public static final String TABLE_NAME = "bid_info_table";

        public static final String BID_VALUE = "bid_value";
        public static final String USER_ID = "user_id";
        public static final String ITEM_ID = "item_id";
    }
}
