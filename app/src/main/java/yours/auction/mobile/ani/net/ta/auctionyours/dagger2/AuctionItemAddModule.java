package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd.AuctionItemAddModel;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd.AuctionItemAddMvp;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd.AuctionItemAddPresenter;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = AuctionItemsDataModule.class)
public class AuctionItemAddModule {
    @Provides
    public AuctionItemAddMvp.Presenter getAuctionItemAddPresenter(AuctionItemAddMvp.Model model) {
        return new AuctionItemAddPresenter(model);
    }

    @Provides
    public AuctionItemAddMvp.Model getAuctionItemAddModel(AuctionItemsDataManager dataManager) {
        return new AuctionItemAddModel(dataManager);
    }
}
