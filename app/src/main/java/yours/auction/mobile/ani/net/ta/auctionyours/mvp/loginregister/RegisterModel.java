package yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister;

import org.greenrobot.eventbus.Subscribe;

import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.UserInfoDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.GlobalEventBus;
import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventUserRegister;

/**
 * Created by taru on 5/14/2017.
 */

public class RegisterModel implements RegisterMvp.Model {

    UserInfoDataManager userInfoDataManager;
    private RegisterMvp.Presenter presenter;

    public RegisterModel(UserInfoDataManager userInfoDataManager) {
        this.userInfoDataManager = userInfoDataManager;
    }

    @Override
    public void setPresenter(RegisterMvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void valideteUserData(String id, String name, String key) {
        userInfoDataManager.saveUserIfNotExists(id, name, key);
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
    public void getEvent(EventUserRegister event) {
        presenter.onRegisterResult(event.responseCode);
    }
}
