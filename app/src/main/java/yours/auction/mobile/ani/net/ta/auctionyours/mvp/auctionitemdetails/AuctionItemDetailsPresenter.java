package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails;

import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;

/**
 * Created by taru on 5/17/2017.
 */

public class AuctionItemDetailsPresenter implements AuctionItemDetailsMvp.Presenter {

    private AuctionItemDetailsMvp.Model model;
    private AuctionItemDetailsMvp.View view;
    private boolean isBidResultAvilable = false;
    private boolean isMinimumBidResultAvilable = false;

    public AuctionItemDetailsPresenter(AuctionItemDetailsMvp.Model model) {
        this.model = model;
        this.model.setPresenter(this);
    }

    @Override
    public void setView(AuctionItemDetailsMvp.View view) {
        this.view = view;
    }

    @Override
    public void initiateBid(long itemId, String userId, int minBid, String userBid) {
        if (isValidBidAmount(minBid, userBid)) {
            view.onRequestStart(Constants.CODE_WAIT_GENERIC);
            model.initiateBidRequest(userId, itemId, Integer.parseInt(userBid));
        } else {
            view.onFailure(Constants.CODE_FAILURE_VALIDATION_INVALID_BID_AMOUNT);
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
    public void onBidResult(byte responseCode) {
        isBidResultAvilable = true;
        if (isResultSetComplete()) {
            isMinimumBidResultAvilable = false;
            isBidResultAvilable = false;
            switch (responseCode) {
                case Constants.CODE_SUCCESS_GENERIC:
                    view.onSuccess(responseCode);
                    break;
                case Constants.CODE_FAILURE_GENERIC:
                    view.onFailure(responseCode);
                    break;
            }
        }
    }

    private boolean isResultSetComplete() {
        return isBidResultAvilable && isMinimumBidResultAvilable;
    }

    @Override
    public void onMinimumBidUpdateResult(byte responseCode) {
        isMinimumBidResultAvilable = true;
        if (isResultSetComplete()) {
            isMinimumBidResultAvilable = false;
            isBidResultAvilable = false;
            switch (responseCode) {
                case Constants.CODE_SUCCESS_GENERIC:
                    view.onSuccess(responseCode);
                    break;
                case Constants.CODE_FAILURE_GENERIC:
                    view.onFailure(responseCode);
                    break;
            }
        }
    }

    private boolean isValidBidAmount(int minBid, String userBid) {
        int userBidVal = Constants.INVALID_VALUE;
        try {
            userBidVal = Integer.parseInt(userBid);
        } catch (NumberFormatException e) {
            return false;
        }
        return userBidVal >= minBid;
    }
}
