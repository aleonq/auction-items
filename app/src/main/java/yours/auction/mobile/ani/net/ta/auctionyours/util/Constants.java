package yours.auction.mobile.ani.net.ta.auctionyours.util;

/**
 * Created by taru on 5/14/2017.
 */
public class Constants {

    //Login
    public static final byte CODE_SUCCESS_LOGIN = 0;
    public static final byte CODE_FAILURE_INVALID_CREDENTIALS = 1;
    //Register
    public static final byte CODE_SUCCESS_REGISTER = 2;
    public static final byte CODE_FAILURE_CONSTRAINTS = 3;
    public static final byte CODE_FAILURE_INVALID_USER_ID = 4;
    public static final byte CODE_FAILURE_INVALID_USER_NAME = 5;
    public static final byte CODE_FAILURE_INVALID_PASSWORD = 6;

    //Bidding, Auction Item
    public static final byte CODE_FAILURE_VALIDATION_INVALID_BID_AMOUNT = 20;

    public static final byte CODE_FAILURE_VALIDATION_INVALID_ITEM_NAME = 21;

    //Generic
    public static final byte CODE_WAIT_GENERIC = 97;
    public static final byte CODE_SUCCESS_GENERIC = 98;
    public static final byte CODE_FAILURE_GENERIC = 99;

    public static final int AUCTION_ITEM_LIST_PAGE_SIZE = 20;

    public static final String KEY_AUCTION_ITEM = "auction_item";
    public static final int INVALID_VALUE = -1;

    public static final long BID_EXPIRY_DURATION = 50L * 24 * 60 * 60 * 1000; //50 days

    public static final String USER_NAME_RULE = "^[a-zA-Z]{2,50}$";
    public static final String USER_PASSWORD_RULE = "^[a-zA-Z0-9]{4,50}$";
    public static final String USER_ID_RULE = "^[a-zA-Z0-9]{3,50}$";
}
