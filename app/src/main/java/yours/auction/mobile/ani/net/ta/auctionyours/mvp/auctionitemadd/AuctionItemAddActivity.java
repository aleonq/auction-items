package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yours.auction.mobile.ani.net.ta.auctionyours.App;
import yours.auction.mobile.ani.net.ta.auctionyours.R;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.BaseActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.LoginFragmentActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Config;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.NotifyUserUtils;

public class AuctionItemAddActivity extends BaseActivity implements AuctionItemAddMvp.View {

    @BindView(R.id.ll_bid_data)
    LinearLayout llBidData;
    private String userId;
    @Inject
    AuctionItemAddMvp.Presenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_item_add);
        userId = Config.currentUserId;

        ButterKnife.bind(this);
        ((App) getApplication()).getAppComponent().inject(this);
        presenter = new AuctionItemAddPresenter(new AuctionItemAddModel(new AuctionItemsDataManager(this)));
        presenter.setView(this);
        presenter.viewOnCreate();
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        initiateBid();
    }

    public void initiateBid() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llBidData.getWindowToken(), 0);
        String itemName = ((EditText) findViewById(R.id.tv_item_name)).getText().toString().trim();
        String itemCategory = ((EditText) findViewById(R.id.tv_item_category)).getText().toString().trim();
        String itemDescription = ((EditText) findViewById(R.id.tv_item_description)).getText().toString().trim();
        String minBidValue = ((EditText) findViewById(R.id.tv_item_bid)).getText().toString();
        presenter.initiateAddItemRequest(itemName, itemCategory, itemDescription, minBidValue, userId);
    }

    @Override
    public void onSuccess() {
        NotifyUserUtils.displaySnackbar(llBidData, this, Constants.CODE_SUCCESS_GENERIC);
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        finish();
    }

    @Override
    public void onFailure(byte errorCode) {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        NotifyUserUtils.displaySnackbar(llBidData, this, errorCode);
    }

    @Override
    public void onRequestStart(byte resCode) {
        progressDialog = NotifyUserUtils.displayDialog(resCode, this);
    }

    @Override
    protected void onDestroy() {
        presenter.viewOnDestroy();
        super.onDestroy();
    }

    @Override
    public void logout() {
        Config.currentUserId = null;
        super.stopBiddingService();
        Intent intent = new Intent(this, LoginFragmentActivity.class);
        startActivity(intent);
        finish();
    }
}
