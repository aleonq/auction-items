package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.UserInfoDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.LoginModel;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.LoginMvp;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.LoginPresenter;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = {UserInfoDataManagerModule.class})
public class LoginModule {
    @Provides
    public LoginMvp.Presenter getLoginPresenter(LoginMvp.Model model) {
        return new LoginPresenter(model);
    }

    @Provides
    public LoginMvp.Model getLoginModule(UserInfoDataManager dataManager) {
        return new LoginModel(dataManager);
    }
}
