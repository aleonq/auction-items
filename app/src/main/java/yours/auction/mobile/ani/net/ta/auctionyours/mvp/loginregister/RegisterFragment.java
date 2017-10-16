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

public class RegisterFragment extends Fragment implements RegisterMvp.View {

    @BindView(R.id.et_check_email)
    EditText etCheckEmail;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etCreatePwd;
    View viewContainer;

    private String userId;

    private ProgressDialog progressDialog;

    @Inject
    RegisterMvp.Presenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ButterKnife.bind(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_register, container, false);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        presenter.viewOnCreate();
        presenter.setView(this);
        ButterKnife.bind(this, viewContainer);
        return viewContainer;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.viewOnDestroy();
    }

    @OnClick(R.id.btn_create_account)
    void onRegisterButtonClick() {
        userId = etCheckEmail.getText().toString().trim();
        presenter.register(userId, etUserName.getText().toString().trim(), etCreatePwd.getText().toString().trim());
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