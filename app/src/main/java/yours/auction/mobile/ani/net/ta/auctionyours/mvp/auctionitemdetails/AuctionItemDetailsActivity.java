package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yours.auction.mobile.ani.net.ta.auctionyours.App;
import yours.auction.mobile.ani.net.ta.auctionyours.R;
import yours.auction.mobile.ani.net.ta.auctionyours.beans.AuctionItem;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.BaseActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.LoginFragmentActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Config;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.NotifyUserUtils;

public class AuctionItemDetailsActivity extends BaseActivity implements AuctionItemDetailsMvp.View {

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.ll_bid_input)
    LinearLayout llBidInput;
    @BindView(R.id.et_bid_value)
    EditText etBidValue;
    private String userId;
    private AuctionItem auctionItem;
    private String userBidValue;
    @Inject
    AuctionItemDetailsMvp.Presenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_item_details);
        auctionItem = getIntent().getParcelableExtra(Constants.KEY_AUCTION_ITEM);
        userId = Config.currentUserId;

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbarLayout.setTitle(auctionItem.itemName + getString(R.string._by_) + auctionItem.ownerId);
        showAuctionItemData();
        ((App) getApplication()).getAppComponent().inject(this);
        presenter.setView(this);
        presenter.viewOnCreate();
        setItemImage();
    }

    private void showAuctionItemData() {
        ((TextView) findViewById(R.id.tv_item_name)).setText(getResources().getString(R.string.item_name, auctionItem.itemName));
        ((TextView) findViewById(R.id.tv_item_bid)).setText(getResources().getString(R.string.item_bid, auctionItem.itemMinimumBidValue));
        ((TextView) findViewById(R.id.tv_item_category)).setText(getResources().getString(R.string.item_category, auctionItem.itemCategory));
        ((TextView) findViewById(R.id.tv_item_id)).setText(getResources().getString(R.string.item_id, auctionItem.itemId));
        ((TextView) findViewById(R.id.tv_item_bid_start_date)).setText(getResources().getString(R.string.item_bid_start, auctionItem.itemStartDate));
        ((TextView) findViewById(R.id.tv_item_bid_expiry_date)).setText(getResources().getString(R.string.item_bid_expiry, auctionItem.itemExpiryDate));
        ((TextView) findViewById(R.id.tv_item_description)).setText(getResources().getString(R.string.item_description, auctionItem.itemDescription));
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        bidValueInputPrompt();
    }

    @OnClick(R.id.btn_set_bid)
    public void initiateBid() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llBidInput.getWindowToken(), 0);
        llBidInput.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
        userBidValue = etBidValue.getText().toString();
        presenter.initiateBid(auctionItem.itemId, userId, auctionItem.itemMinimumBidValue, userBidValue);
    }

    private void bidValueInputPrompt() {
        llBidInput.setVisibility(View.VISIBLE);
        etBidValue.setText(auctionItem.itemMinimumBidValue + "");
        fab.setVisibility(View.GONE);
    }

    public void setItemImage() {
        toolbarLayout.setBackgroundResource(R.drawable.ic_gavel_white);
    }

    @Override
    public void onSuccess(final byte responseCode) {
        progressDialog.cancel();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.tv_item_bid)).setText(getResources().getString(R.string.item_bid, Integer.parseInt(userBidValue)));
                NotifyUserUtils.displaySnackbar(llBidInput, AuctionItemDetailsActivity.this, responseCode);
            }
        });
    }

    @Override
    public void onFailure(byte errorCode) {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        NotifyUserUtils.displaySnackbar(llBidInput, AuctionItemDetailsActivity.this, errorCode);
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
