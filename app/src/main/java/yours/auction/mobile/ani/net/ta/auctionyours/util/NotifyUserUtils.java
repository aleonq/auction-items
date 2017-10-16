package yours.auction.mobile.ani.net.ta.auctionyours.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import yours.auction.mobile.ani.net.ta.auctionyours.R;

/**
 * Created by taru on 5/14/2017.
 */

public class NotifyUserUtils {

    public static ProgressDialog displayDialog(byte resCode, Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(resolveMessageType(resCode)));
        dialog.setIndeterminate(true);
        return dialog;
    }

    public static void displaySnackbar(View view, Context context, byte type) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Snackbar snackbar = Snackbar.make(view, context.getResources().getString(resolveMessageType(type)), Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        snackbar.show();
    }

    private static int resolveMessageType(byte type) {
        switch (type) {
            case Constants.CODE_FAILURE_INVALID_CREDENTIALS:
                return R.string.error_msg_user_login;
            case Constants.CODE_SUCCESS_LOGIN:
                return R.string.success_msg_user_login;
            case Constants.CODE_SUCCESS_REGISTER:
                return R.string.success_register;
            case Constants.CODE_FAILURE_CONSTRAINTS:
                return R.string.error_msg_user_register_constraints;
            case Constants.CODE_FAILURE_GENERIC:
                return R.string.error_msg_generic;
            case Constants.CODE_SUCCESS_GENERIC:
                return R.string.success_msg_generic;
            case Constants.CODE_WAIT_GENERIC:
                return R.string.wait_generic;
            case Constants.CODE_FAILURE_VALIDATION_INVALID_BID_AMOUNT:
                return R.string.error_msg_invalid_bid;
            case Constants.CODE_FAILURE_VALIDATION_INVALID_ITEM_NAME:
                return R.string.error_msg_invalid_item_name;
            case Constants.CODE_FAILURE_INVALID_USER_ID:
                return R.string.error_msg_invalid_user_id;
            case Constants.CODE_FAILURE_INVALID_USER_NAME:
                return R.string.error_msg_invalid_user_name;
            case Constants.CODE_FAILURE_INVALID_PASSWORD:
                return R.string.error_msg_invalid_password;
        }
        return R.string.error_msg_generic;
    }
}
