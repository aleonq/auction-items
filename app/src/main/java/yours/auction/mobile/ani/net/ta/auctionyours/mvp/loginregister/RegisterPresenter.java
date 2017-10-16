package yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister;

import android.text.TextUtils;

import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;

/**
 * Created by taru on 5/14/2017.
 */

public class RegisterPresenter implements RegisterMvp.Presenter {

    RegisterMvp.View view;
    RegisterMvp.Model model;

    public RegisterPresenter(RegisterMvp.Model model) {
        this.model = model;
        model.setPresenter(this);
    }

    @Override
    public void setView(RegisterMvp.View view) {
        this.view = view;
    }

    @Override
    public void register(String id, String name, String pwd) {
        view.onRequestStart();
        byte validationResponse = validateUserData(id, name, pwd);
        if (validationResponse == Constants.CODE_SUCCESS_GENERIC) {
            model.valideteUserData(id, name, pwd);
        } else {
            view.onFailure(validationResponse);
        }
    }

    private byte validateUserData(String id, String name, String pwd) {
        if (TextUtils.isEmpty(id) || id.matches(Constants.USER_ID_RULE) == false) {
            return Constants.CODE_FAILURE_INVALID_USER_ID;
        } else if (TextUtils.isEmpty(name) || name.matches(Constants.USER_NAME_RULE) == false) {
            return Constants.CODE_FAILURE_INVALID_USER_NAME;
        } else if (TextUtils.isEmpty(pwd) || pwd.matches(Constants.USER_PASSWORD_RULE) == false) {
            return Constants.CODE_FAILURE_INVALID_PASSWORD;
        }
        return Constants.CODE_SUCCESS_GENERIC;
    }

    @Override
    public void onRegisterResult(byte resultCode) {
        switch (resultCode) {
            case Constants.CODE_SUCCESS_REGISTER:
                view.onSuccess(resultCode);
                break;
            case Constants.CODE_FAILURE_CONSTRAINTS:
                view.onFailure(Constants.CODE_FAILURE_CONSTRAINTS);
                break;
            case Constants.CODE_FAILURE_GENERIC:
                view.onFailure(Constants.CODE_FAILURE_GENERIC);
                break;
        }
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
