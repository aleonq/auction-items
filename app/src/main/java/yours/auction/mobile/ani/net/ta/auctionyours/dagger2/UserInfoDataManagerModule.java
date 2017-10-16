package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.UserInfoDataManager;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = AppModule.class)
public class UserInfoDataManagerModule {
    @Provides
    public UserInfoDataManager getUserInfoDataManager(Context context) {
        return new UserInfoDataManager(context);
    }
}