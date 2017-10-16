package yours.auction.mobile.ani.net.ta.auctionyours.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by taru on 5/15/2017.
 */
public class AuctionItem implements Parcelable {
    @SerializedName("item_owner")
    public String ownerId;
    @SerializedName("_id")
    public int itemId;
    @SerializedName("name")
    public String itemName;
    @SerializedName("category")
    public String itemCategory;
    @SerializedName("decsription")
    public String itemDescription;
    @SerializedName("image_uri")
    public String itemImageUri;
    @SerializedName("start_date")
    public String itemStartDate;
    @SerializedName("expiry_date")
    public String itemExpiryDate;
    @SerializedName("min_bid_value")
    public int itemMinimumBidValue;


    public AuctionItem(String itemName, String itemCategory, String itemDescription, String itemImageUri,
                       String itemStartDate, String itemExpiryDate, int itemMinimumBidValue, String ownerId) {
        this(-1,itemName, itemCategory, itemDescription, itemImageUri, itemStartDate, itemExpiryDate, itemMinimumBidValue, ownerId);
    }

    public AuctionItem(int itemId, String itemName, String itemCategory, String itemDescription, String itemImageUri,
                       String itemStartDate, String itemExpiryDate, int itemMinimumBidValue, String ownerId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.itemImageUri = itemImageUri;
        this.itemStartDate = itemStartDate;
        this.itemExpiryDate = itemExpiryDate;
        this.itemMinimumBidValue = itemMinimumBidValue;
        this.ownerId = ownerId;
    }

    public AuctionItem(Parcel in) {
        this.itemId = in.readInt();
        this.itemName = in.readString();
        this.itemCategory = in.readString();
        this.itemDescription = in.readString();
        this.itemImageUri = in.readString();
        this.itemStartDate = in.readString();
        this.itemExpiryDate = in.readString();
        this.itemMinimumBidValue = in.readInt();
        this.ownerId = in.readString();
    }

    @Override
    public String toString() {
        return "AuctionItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemImageUri='" + itemImageUri + '\'' +
                ", itemStartDate='" + itemStartDate + '\'' +
                ", itemExpiryDate='" + itemExpiryDate + '\'' +
                ", itemMinimumBidValue=" + itemMinimumBidValue +
                ", ownerId=" + ownerId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemId);
        dest.writeString(this.itemName);
        dest.writeString(this.itemCategory);
        dest.writeString(this.itemDescription);
        dest.writeString(this.itemImageUri);
        dest.writeString(this.itemStartDate);
        dest.writeString(this.itemExpiryDate);
        dest.writeInt(this.itemMinimumBidValue);
        dest.writeString(this.ownerId);
    }

    public static final Parcelable.Creator<AuctionItem> CREATOR = new Creator<AuctionItem>() {

        @Override
        public AuctionItem createFromParcel(Parcel source) {
            return new AuctionItem(source);
        }

        @Override
        public AuctionItem[] newArray(int size) {
            return new AuctionItem[size];
        }
    };
}