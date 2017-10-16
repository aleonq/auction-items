package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist.AuctionItemsListModel;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist.AuctionItemsListMvp;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist.AuctionItemsListPresenter;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = AuctionItemsDataModule.class)
public class AuctionItemsListModule {
    @Provides
    public AuctionItemsListMvp.Presenter getAuctionItemsListPresenter(AuctionItemsListMvp.Model model) {
        return new AuctionItemsListPresenter(model);
    }

    @Provides
    public AuctionItemsListMvp.Model getAuctionItemsListModel(AuctionItemsDataManager dataManager) {
        return new AuctionItemsListModel(dataManager);
    }
}
