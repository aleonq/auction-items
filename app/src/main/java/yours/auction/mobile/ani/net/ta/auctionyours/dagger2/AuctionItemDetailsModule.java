package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.BidInfoDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails.AuctionItemDetailsModel;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails.AuctionItemDetailsMvp;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails.AuctionItemDetailsPresenter;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = {AuctionItemsDataModule.class, BidInfoDataModule.class})
public class AuctionItemDetailsModule {
    @Provides
    public AuctionItemDetailsMvp.Presenter getAuctionItemDetailsPresenter(AuctionItemDetailsMvp.Model model) {
        return new AuctionItemDetailsPresenter(model);
    }

    @Provides
    public AuctionItemDetailsMvp.Model getAuctionItemDetailsModel(BidInfoDataManager bidInfoDataManager, AuctionItemsDataManager dataManager) {
        return new AuctionItemDetailsModel(bidInfoDataManager, dataManager);
    }
}