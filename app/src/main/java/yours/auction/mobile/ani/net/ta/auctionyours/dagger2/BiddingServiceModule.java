package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.biddingbot.BiddingHelper;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = AppModule.class)
public class BiddingServiceModule {
    @Provides
    public BiddingHelper getBiddingHelper(Context context) {
        return new BiddingHelper(context);
    }
}
