package yours.auction.mobile.ani.net.ta.auctionyours.mvp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import yours.auction.mobile.ani.net.ta.auctionyours.biddingbot.BiddingService;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Config;

/**
 * Created by taru on 5/17/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onStart() {
        Log.d(TAG, "starting bidding service");
        startBiddingService();
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "stopping bidding service");
        stopBiddingService();
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (Config.currentUserId == null) {
            logout();
        }
        super.onResume();
    }

    public void startBiddingService() {
        Intent intent = new Intent(this, BiddingService.class);
        startService(intent);
    }

    public void stopBiddingService() {
        Intent intent = new Intent(this, BiddingService.class);
        stopService(intent);
    }

    public abstract void logout();
}