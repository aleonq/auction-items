package yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yours.auction.mobile.ani.net.ta.auctionyours.App;
import yours.auction.mobile.ani.net.ta.auctionyours.R;
import yours.auction.mobile.ani.net.ta.auctionyours.mvp.auctionitemlist.AuctionItemsListActivity;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Config;
import yours.auction.mobile.ani.net.ta.auctionyours.util.Constants;
import yours.auction.mobile.ani.net.ta.auctionyours.util.NotifyUserUtils;

/**
 * Created by taru on 1/9/2017.
 */

public class LoginFragment extends Fragment implements LoginMvp.View {

    @Inject
    public LoginMvp.Presenter presenter;
    View viewContainer;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    private ProgressDialog progressDialog;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, viewContainer);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        presenter.viewOnCreate();
        presenter.setView(this);
        return viewContainer;
    }

    @OnClick(R.id.btn_login)
    void onLoginButtonClick() {
        userId = etEmail.getText().toString().trim();
        presenter.login(userId, etPwd.getText().toString().trim());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.viewOnDestroy();
    }

    @Override
    public void onSuccess(byte resCode) {
        Config.currentUserId = userId;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        NotifyUserUtils.displaySnackbar(viewContainer, getActivity(), resCode);
        Intent intent = new Intent(getActivity(), AuctionItemsListActivity.class);
        getActivity().startActivity(intent);
        presenter.viewOnDestroy();
    }

    @Override
    public void onFailure(byte errorMsg) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        NotifyUserUtils.displaySnackbar(viewContainer, getActivity(), errorMsg);
    }

    @Override
    public void onRequestStart() {
        progressDialog = NotifyUserUtils.displayDialog(Constants.CODE_WAIT_GENERIC, getActivity());
    }
}