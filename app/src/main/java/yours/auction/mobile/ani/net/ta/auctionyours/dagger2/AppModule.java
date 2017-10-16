package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by taru on 5/18/2017.
 */
@Module
public class AppModule {
    private Context application;

    public AppModule(Context application) {
        this.application = application;
    }

    @Provides
    public Context provideContext() {
        return application;
    }
}
