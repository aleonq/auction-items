package yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister;

import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventUserLogin;

/**
 * Created by taru on 5/14/2017.
 */

public class LoginMvp {
    public interface Model {

        void setPresenter(Presenter presenter);

        void valideteUserCredentials(String id, String key);

        void setup();

        void cleanup();

        void getEvent(EventUserLogin event);
    }

    public interface View {

        void onSuccess(byte resCode);

        void onFailure(byte errorMsg);

        void onRequestStart();
    }

    public interface Presenter {
        void login(String id, String pwd);

        void onLoginResult(byte resultCode);

        void setView(View view);

        void viewOnCreate();

        void viewOnDestroy();
    }
}
