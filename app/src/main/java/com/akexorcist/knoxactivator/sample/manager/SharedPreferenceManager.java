package com.akexorcist.knoxactivator.sample.manager;

import android.content.Context;

/**
 * Created by Akexorcist on 6/9/16 AD.
 */
public class SharedPreferenceManager {
    private static final String PREFERENCE_STATE = "preference_state";
    private static final String KLM_LICENSE_ACTIVATED = "klm_license_activated";
    private static final String ELM_LICENSE_ACTIVATED = "key_license_activated";

    public static void setKLMLicenseActivated(Context context) {
        context.getSharedPreferences(PREFERENCE_STATE, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KLM_LICENSE_ACTIVATED, true)
            .apply();
    }

    public static boolean isKLMLicenseActivated(Context context) {
        return context.getSharedPreferences(PREFERENCE_STATE, Context.MODE_PRIVATE)
            .getBoolean(KLM_LICENSE_ACTIVATED, false);
    }

    public static void setELMLicenseActivated(Context context) {
        context.getSharedPreferences(PREFERENCE_STATE, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(ELM_LICENSE_ACTIVATED, true)
                .apply();
    }

    public static boolean isELMLicenseActivated(Context context) {
        return context.getSharedPreferences(PREFERENCE_STATE, Context.MODE_PRIVATE)
                .getBoolean(ELM_LICENSE_ACTIVATED, false);
    }
}
