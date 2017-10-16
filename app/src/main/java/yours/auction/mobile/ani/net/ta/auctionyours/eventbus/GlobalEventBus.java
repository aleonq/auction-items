package yours.auction.mobile.ani.net.ta.auctionyours.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by taru on 4/30/2017.
 */

public class GlobalEventBus {
    private static EventBus sBus;

    public static EventBus getsBus() {
        if (sBus == null) {
            sBus = EventBus.getDefault();
        }
        return sBus;
    }
}
