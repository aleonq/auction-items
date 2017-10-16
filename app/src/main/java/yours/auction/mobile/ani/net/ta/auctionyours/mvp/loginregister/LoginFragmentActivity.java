package yours.auction.mobile.ani.net.ta.auctionyours.mvp.loginregister;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import yours.auction.mobile.ani.net.ta.auctionyours.R;

/**
 * Created by taru on 1/9/2017.
 */

public class LoginFragmentActivity extends FragmentActivity {

    @BindView(R.id.tabhost)
    FragmentTabHost mTabHost;
    @BindView(R.id.ll_login_screen)
    LinearLayout llParentLayout;
    TextView tvPermissionRational;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mTabHost.setup(this, fragmentManager, R.id.tabcontent);
        mTabHost.addTab(
                mTabHost.newTabSpec("login").setIndicator("Login", null),
                LoginFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("register").setIndicator("Register", null),
                RegisterFragment.class, null);
    }
}