package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.BidInfoDataManager;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = AppModule.class)
public class BidInfoDataModule {
    @Provides
    public BidInfoDataManager getBidinfoDatamanager(Context context) {
        return new BidInfoDataManager(context);
    }
}