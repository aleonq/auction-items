package yours.auction.mobile.ani.net.ta.auctionyours;

import dagger.Component;
import yours.auction.mobile.ani.net.ta.auctionyours.biddingbot.BiddingService;
import yours.auction.mobile.ani.net.ta.auctionyours.dagger2.AppModule;
import yours.auction.mobile.ani.net.ta.auctionyours.dagger2.AuctionItemAddModule;
import yours.auction.mobile.ani.net.ta.auctionyours.dagger2.AuctionItemDetailsModule;
import yours.auction.mobile.ani.net.ta.auctionyours.dagger2.AuctionItemsListModule;
import yours.auction.mobile.ani.net.ta.auctionyours.dagger2.BiddingServiceModule;
import yours.auction.mobile.ani.net.ta.auctionyours.dagger2.LoginModule;
import yours.auction.mobile.ani.net.ta.auctionyours.dagger2.RegisterModule;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemadd.AuctionItemAddActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemdetails.AuctionItemDetailsActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist.AuctionItemsListActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.LoginFragment;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.RegisterFragment;

/**
 * Created by taru on 5/18/2017.
 */
@Component(modules = {AppModule.class, LoginModule.class, RegisterModule.class,
        AuctionItemsListModule.class, AuctionItemDetailsModule.class, AuctionItemAddModule.class, BiddingServiceModule.class})
public interface AppComponent {
    void inject(LoginFragment target);

    void inject(RegisterFragment target);

    void inject(AuctionItemsListActivity target);

    void inject(AuctionItemDetailsActivity target);

    void inject(AuctionItemAddActivity target);

    void inject(BiddingService target);
}
