package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yours.auction.mobile.ani.net.ta.auctionyours.App;
import yours.auction.mobile.ani.net.ta.auctionyours.R;
import yours.auction.mobile.ani.net.ta.auctionyours.beans.AuctionItem;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.BaseActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd.AuctionItemAddActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.LoginFragmentActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Config;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.NotifyUserUtils;

public class AuctionItemsListActivity extends BaseActivity
        implements AuctionItemsListMvp.View, ListScrollCallback {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recycler_view_aurction_items)
    RecyclerView recyclerView;

    @Inject
    AuctionItemsListMvp.Presenter presenter;

    private Context context;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<AuctionItem> auctionItemList = new ArrayList<>();
    private int pageNoToRequest = 0;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_auction_items);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userId = Config.currentUserId;
        setupRecyclerView();
        ((App) getApplication()).getAppComponent().inject(this);
        presenter.setView(this);
        presenter.viewOnCreate();
    }

    @Override
    protected void onResume() {
        pageNoToRequest = 0;
        auctionItemList.clear();
        presenter.requestAuctionItemsList(pageNoToRequest);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.viewOnDestroy();
        super.onDestroy();
    }

    private void setupRecyclerView() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AuctionItemsListAdapter(auctionItemList, AuctionItemsListActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        Intent intent = new Intent(this, AuctionItemAddActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.auction_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void logout() {
        Config.currentUserId = null;
        pageNoToRequest = 0;
        auctionItemList.clear();
        super.stopBiddingService();
        Intent intent = new Intent(this, LoginFragmentActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void displayAuctionItems(List<AuctionItem> auctionItemList) {
        if (auctionItemList == null && auctionItemList.isEmpty()) {
            return;
        }
        int listSize = this.auctionItemList.size();
        if (listSize == 0) {
            this.auctionItemList.addAll(auctionItemList);
            notifyDataChangedToAdapter();
            pageNoToRequest = this.auctionItemList.size() / Constants.AUCTION_ITEM_LIST_PAGE_SIZE;
        } else if (this.auctionItemList.get(listSize - 1).itemId >= auctionItemList.get(auctionItemList.size() - 1).itemId) {
            pageNoToRequest = this.auctionItemList.size() / Constants.AUCTION_ITEM_LIST_PAGE_SIZE;
        } else {
            this.auctionItemList.addAll(auctionItemList);
            notifyDataChangedToAdapter();
            pageNoToRequest = this.auctionItemList.size() / Constants.AUCTION_ITEM_LIST_PAGE_SIZE;
        }
    }

    @Override
    public void onRequestStart(byte resCode) {
        NotifyUserUtils.displaySnackbar(recyclerView, this, resCode);
    }

    private void notifyDataChangedToAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter != null)
                    adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onListApproachingEnd() {
        presenter.requestAuctionItemsList(pageNoToRequest + 1);
    }
}
