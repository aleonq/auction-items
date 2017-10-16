package yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister;

import yours.auction.mobile.ani.net.ta.auctionyours.eventbus.EventUserRegister;

/**
 * Created by taru on 5/14/2017.
 */

public class RegisterMvp {
    public interface Model {

        void setPresenter(Presenter presenter);

        void valideteUserData(String id, String name, String key);

        void setup();

        void cleanup();

        void getEvent(EventUserRegister event);
    }

    public interface View {

        void onSuccess(byte resCode);

        void onFailure(byte errorMsg);

        void onRequestStart();
    }

    public interface Presenter {
        void register(String id, String name, String pwd);

        void onRegisterResult(byte resultCode);

        void setView(View view);

        void viewOnCreate();

        void viewOnDestroy();
    }
}
