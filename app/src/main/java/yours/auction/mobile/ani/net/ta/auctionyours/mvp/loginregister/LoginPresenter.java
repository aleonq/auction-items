package yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister;

import android.text.TextUtils;

import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;

/**
 * Created by taru on 5/14/2017.
 */

public class LoginPresenter implements LoginMvp.Presenter {

    LoginMvp.View view;
    LoginMvp.Model model;

    public LoginPresenter(LoginMvp.Model model) {
        this.model = model;
        model.setPresenter(this);
    }

    @Override
    public void login(String id, String pwd) {
        view.onRequestStart();
        byte validationResponse = validateUserData(id, pwd);
        if (validationResponse == Constants.CODE_SUCCESS_GENERIC) {
            model.valideteUserCredentials(id, pwd);
        } else {
            view.onFailure(validationResponse);
        }
    }

    private byte validateUserData(String id, String pwd) {
        if (TextUtils.isEmpty(id) || id.matches(Constants.USER_ID_RULE) == false) {
            return Constants.CODE_FAILURE_INVALID_USER_ID;
        } else if (TextUtils.isEmpty(pwd) || pwd.matches(Constants.USER_PASSWORD_RULE) == false) {
            return Constants.CODE_FAILURE_INVALID_PASSWORD;
        }
        return Constants.CODE_SUCCESS_GENERIC;
    }

    @Override
    public void onLoginResult(byte resultCode) {
        switch (resultCode) {
            case Constants.CODE_SUCCESS_LOGIN:
                view.onSuccess(resultCode);
                break;
            case Constants.CODE_FAILURE_INVALID_CREDENTIALS:
                view.onFailure(Constants.CODE_FAILURE_INVALID_CREDENTIALS);
                break;
        }
    }

    @Override
    public void setView(LoginMvp.View view) {
        this.view = view;
    }

    @Override
    public void viewOnCreate() {
        model.setup();
    }

    @Override
    public void viewOnDestroy() {
        model.cleanup();
    }
}
