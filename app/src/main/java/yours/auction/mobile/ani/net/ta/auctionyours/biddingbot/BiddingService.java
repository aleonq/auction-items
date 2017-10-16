package yours.auction.mobile.ani.net.ta.auctionyours.biddingbot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import yours.auction.mobile.ani.net.ta.auctionyours.App;

/**
 * Created by taru on 5/16/2017.
 */

public class BiddingService extends Service {
    private static final String TAG = BiddingService.class.getSimpleName();

    @Inject
    BiddingHelper biddingHelper;

    @Override
    public void onCreate() {
        ((App) getApplication()).getAppComponent().inject(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand bidding service");
        Log.d(TAG, "oncreate bidding service");
        biddingHelper.startBidding();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy bidding service");
        biddingHelper.cleanup();
        biddingHelper = null;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}