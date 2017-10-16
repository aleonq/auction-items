package yours.auction.mobile.ani.net.ta.auctionyours.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by taru on 5/16/2017.
 */

public class BidInfo {
    @SerializedName("user_id")
    public String userId;

    @SerializedName("item_id")
    public long itemId;

    @SerializedName("bid_value")
    public long bidValue;

    public BidInfo(String userId, long itemId, long bidValue) {
        this.userId = userId;
        this.itemId = itemId;
        this.bidValue = bidValue;
    }

    @Override
    public String toString() {
        return "BidInfo{" +
                "userId=" + userId +
                ", itemId=" + itemId +
                ", bidValue=" + bidValue +
                '}';
    }
}

