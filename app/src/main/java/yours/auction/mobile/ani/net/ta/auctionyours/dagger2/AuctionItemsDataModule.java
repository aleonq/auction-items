package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.AuctionItemsDataManager;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = AppModule.class)
public class AuctionItemsDataModule {

    @Provides
    public AuctionItemsDataManager getAuctionItemsDatamanager(Context context) {
        return new AuctionItemsDataManager(context);
    }
}
