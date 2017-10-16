package yours.auction.mobile.ani.net.ta.auctionyours;

import android.app.Application;

import yours.auction.mobile.ani.net.ta.auctionyours.dagger2.AppModule;

/**
 * Created by taru on 5/13/2017.
 */

public class App extends Application {

    private AppComponent daggerAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        daggerAppComponent = DaggerAppComponent.builder().appModule(new AppModule(getApplicationContext())).build();
    }

    public AppComponent getAppComponent() {
        return daggerAppComponent;
    }
}