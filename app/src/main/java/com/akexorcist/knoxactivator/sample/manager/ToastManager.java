package com.akexorcist.knoxactivator.sample.manager;

import android.content.Context;
import android.widget.Toast;

import com.akexorcist.knoxactivator.sample.R;


/**
 * Created by Akexorcist on 4/22/2016 AD.
 */
public class ToastManager {
    public static void showKLMLicenseActivationSuccess(Context context) {
        showToast(context, R.string.klm_license_activation_success);
    }

    public static void showELMLicenseActivationSuccess(Context context) {
        showToast(context, R.string.elm_license_activation_success);
    }

    public static void showDeviceAdminActivationSuccess(Context context) {
        showToast(context, R.string.device_admin_activation_success);
    }

    public static void showToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
