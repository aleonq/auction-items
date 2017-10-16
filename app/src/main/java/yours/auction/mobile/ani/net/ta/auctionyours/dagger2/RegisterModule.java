package yours.auction.mobile.ani.net.ta.auctionyours.dagger2;

import dagger.Module;
import dagger.Provides;
import yours.auction.mobile.ani.net.ta.auctionyours.dataaccess.UserInfoDataManager;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.RegisterModel;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.RegisterMvp;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister.RegisterPresenter;

/**
 * Created by taru on 5/18/2017.
 */
@Module(includes = {UserInfoDataManagerModule.class})
public class RegisterModule {
    @Provides
    public RegisterMvp.Presenter getRegisterPresenter(RegisterMvp.Model model) {
        return new RegisterPresenter(model);
    }

    @Provides
    public RegisterMvp.Model getRegisterModule(UserInfoDataManager dataManager) {
        return new RegisterModel(dataManager);
    }
}
