package yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yours.auction.mobile.ani.net.ta.auctionyours.R;
import yours.auction.mobile.ani.net.ta.auctionyours.beans.AuctionItem;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails.AuctionItemDetailsActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Config;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Utility;

/**
 * Created by taru on 4/30/2017.
 */

public class AuctionItemsListAdapter extends RecyclerView.Adapter<AuctionItemsListAdapter.ViewHolder> {

    private List<AuctionItem> auctionItemList;
    private ListScrollCallback scrollCallback;

    public AuctionItemsListAdapter(List<AuctionItem> auctionItemList, ListScrollCallback scrollCallback) {
        this.auctionItemList = auctionItemList;
        this.scrollCallback = scrollCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_auction_item, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AuctionItem item = auctionItemList.get(position);
        holder.tvName.setText(String.format("%s : %s", item.itemCategory, item.itemName));
        if (Utility.equalsIgnoreCase(item.ownerId, Config.currentUserId)) {
            holder.tvBidOwner.setText(holder.tvBidOwner.getContext().getResources().getString(R.string.bid_owner_user));
        } else {
            holder.tvBidOwner.setText(holder.tvBidOwner.getContext().getResources().getString(R.string.bid_owner_other, item.ownerId));
        }
        holder.tvCurrentBid.setText(holder.tvName.getContext().getResources().getString(R.string.item_bid, item.itemMinimumBidValue));
        holder.tvName.setTag(auctionItemList.get(position));
        int approachingScale = getItemCount() - position;
        if (approachingScale == 1) {
            scrollCallback.onListApproachingEnd();
        }
    }

    @Override
    public int getItemCount() {
        return auctionItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName;
        public TextView tvBidOwner;
        public TextView tvCurrentBid;
        public ImageView ivThumbnail;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            tvBidOwner = (TextView) v.findViewById(R.id.tv_owner_info);
            tvCurrentBid = (TextView) v.findViewById(R.id.tv_current_bid);
            ivThumbnail = (ImageView) v.findViewById(R.id.iv_thumbnail);
        }

        @Override
        public void onClick(View view) {
            AuctionItem auctionItem = (AuctionItem) view.findViewById(R.id.tv_name).getTag();
            Intent intent = new Intent(view.getContext(), AuctionItemDetailsActivity.class);
            intent.putExtra(Constants.KEY_AUCTION_ITEM, auctionItem);
            view.getContext().startActivity(intent);
        }
    }
}