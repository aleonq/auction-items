package yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister;

import org.greenrobot.eventbus.Subscribe;

import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.UserInfoDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventUserLogin;

/**
 * Created by taru on 5/14/2017.
 */

public class LoginModel implements LoginMvp.Model {

    UserInfoDataManager userInfoDataManager;
    private LoginMvp.Presenter presenter;

    public LoginModel(UserInfoDataManager userInfoDataManager) {
        this.userInfoDataManager = userInfoDataManager;
    }

    @Override
    public void setPresenter(LoginMvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void valideteUserCredentials(String id, String key) {
        userInfoDataManager.validateUserCredentials(id, key);
    }

    @Override
    public void setup() {
        GlobalEventBus.getsBus().register(this);
    }

    @Override
    public void cleanup() {
        GlobalEventBus.getsBus().unregister(this);
    }

    @Override
    @Subscribe
    public void getEvent(EventUserLogin event) {
        presenter.onLoginResult(event.responseCode);
    }
}
