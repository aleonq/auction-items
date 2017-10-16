package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;

/**
 * Created by taru on 5/17/2017.
 */

public class AuctionItemAddPresenter implements AuctionItemAddMvp.Presenter {

    private AuctionItemAddMvp.Model model;
    private AuctionItemAddMvp.View view;

    public AuctionItemAddPresenter(AuctionItemAddMvp.Model model) {
        this.model = model;
        this.model.setPresenter(this);
    }

    @Override
    public void setView(AuctionItemAddMvp.View view) {
        this.view = view;
    }

    @Override
    public void initiateAddItemRequest(String itemName, String itemCategory, String itemDescription, String minBid, String ownerId) {

        byte validationResponse = validateItemData(itemName, minBid);
        if (validationResponse == Constants.CODE_SUCCESS_GENERIC) {
            view.onRequestStart(Constants.CODE_WAIT_GENERIC);
            long time = System.currentTimeMillis();
            String dateCurrent = new SimpleDateFormat("MM-dd-yyyy").format(new Date(time));
            String dateExpiry = new SimpleDateFormat("MM-dd-yyyy").format(new Date(time + Constants.BID_EXPIRY_DURATION));
            model.initiateAddItemRequest(itemName, itemCategory, itemDescription, dateCurrent, dateExpiry, Integer.parseInt(minBid), ownerId);
        } else {
            view.onFailure(validationResponse);
        }
    }

    @Override
    public void viewOnCreate() {
        model.setup();
    }

    @Override
    public void viewOnDestroy() {
        model.cleanup();
    }

    @Override
    public void onAuctionItemSaveResult(byte responseCode) {
        switch (responseCode) {
            case Constants.CODE_SUCCESS_GENERIC:
                view.onSuccess();
                break;
            case Constants.CODE_FAILURE_GENERIC:
                view.onFailure(responseCode);
                break;
        }
    }

    private byte validateItemData(String itemName, String minBid) {
        if (TextUtils.isEmpty(itemName)) {
            return Constants.CODE_FAILURE_VALIDATION_INVALID_ITEM_NAME;
        }
        try {
            if (Integer.parseInt(minBid) > 0) {
                return Constants.CODE_SUCCESS_GENERIC;
            }
            return Constants.CODE_FAILURE_VALIDATION_INVALID_BID_AMOUNT;
        } catch (NumberFormatException e) {
            return Constants.CODE_FAILURE_VALIDATION_INVALID_BID_AMOUNT;
        }
    }
}
