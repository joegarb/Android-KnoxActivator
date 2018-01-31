package com.akexorcist.knoxactivator.sample;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akexorcist.knoxactivator.ActivationCallback;
import com.akexorcist.knoxactivator.KnoxActivationManager;
import com.akexorcist.knoxactivator.sample.manager.DialogManager;
import com.akexorcist.knoxactivator.sample.manager.SharedPreferenceManager;
import com.akexorcist.knoxactivator.sample.manager.ToastManager;

public class ActivationActivity extends AppCompatActivity {
    // TODO Put your ELM key here
    private final String LICENSE_KEY = "YOUR_ELM_KEY";
    // TODO Put your KLM key here
    private final String KLM_LICENSE_KEY = "YOUR_KLM_KEY";

    private Dialog dialogLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        checkKnoxSdkSupported();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Register activator manager callback
        KnoxActivationManager.getInstance().register(activationCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Unregister activator manager callback
        KnoxActivationManager.getInstance().unregister();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle activity result from Knox automatically
        KnoxActivationManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    private ActivationCallback activationCallback = new ActivationCallback() {
        @Override
        public void onDeviceAdminActivated() {
            setDeviceAdminActivated();
        }

        @Override
        public void onDeviceAdminActivationCancelled() {
            showDeviceAdminActivationProblem();
        }

        @Override
        public void onDeviceAdminDeactivated() {

        }

        @Override
        public void onKnoxLicenseActivated() {
            hideLoadingDialog();
            saveKLMLicenseActivationStateToSharedPreference();
            showKLMLicenseActivationSuccess();
            activateELMLicense();
        }

        @Override
        public void onKnoxLicenseActivateFailed(int errorType, String errorMessage) {
            hideLoadingDialog();
            showKLMLicenseActivationProblem(errorType, errorMessage);
        }

        @Override
        public void onLicenseActivated() {
            hideLoadingDialog();
            saveELMLicenseActivationStateToSharedPreference();
            showELMLicenseActivationSuccess();
            goToDoSomethingActivity();
        }

        @Override
        public void onLicenseActivateFailed(int errorType, String errorMessage) {
            hideLoadingDialog();
            showELMLicenseActivationProblem(errorType, errorMessage);
        }
    };

    private void checkKnoxSdkSupported() {
        if (KnoxActivationManager.getInstance().isKnoxSdkSupported(this)) {
            activateDeviceAdmin();
        } else {
            showDeviceUnsupportedProblem();
        }
    }

    private void activateDeviceAdmin() {
        if (KnoxActivationManager.getInstance().isDeviceAdminActivated(this)) {
            setDeviceAdminActivated();
        } else {
            KnoxActivationManager.getInstance().activateDeviceAdmin(this);
        }
    }

    private void activateKLMLicense() {
        if (SharedPreferenceManager.isKLMLicenseActivated(this)) {
            showKLMLicenseActivationSuccess();
            activateELMLicense();
        } else {
            showKLMLoadingDialog();
            KnoxActivationManager.getInstance().activateKnoxLicense(this, KLM_LICENSE_KEY);
        }
    }

    private void activateELMLicense() {
        if (SharedPreferenceManager.isELMLicenseActivated(this)) {
            showELMLicenseActivationSuccess();
            goToDoSomethingActivity();
        } else {
            showELMLoadingDialog();
            KnoxActivationManager.getInstance().activateLicense(this, LICENSE_KEY);
        }
    }

    private void setDeviceAdminActivated() {
        showDeviceAdminActivationSuccess();
        activateKLMLicense();
    }

    private void saveKLMLicenseActivationStateToSharedPreference() {
        SharedPreferenceManager.setKLMLicenseActivated(this);
    }

    private void saveELMLicenseActivationStateToSharedPreference() {
        SharedPreferenceManager.setELMLicenseActivated(this);
    }

    private void goToDoSomethingActivity() {
        startActivity(new Intent(this, DoSomethingActivity.class));
        finish();
    }

    private void showKLMLicenseActivationSuccess() {
        ToastManager.showKLMLicenseActivationSuccess(this);
    }

    private void showELMLicenseActivationSuccess() {
        ToastManager.showELMLicenseActivationSuccess(this);
    }

    private void showDeviceAdminActivationSuccess() {
        ToastManager.showDeviceAdminActivationSuccess(this);
    }

    private void showDeviceUnsupportedProblem() {
        DialogManager.showDeviceUnsupportedProblem(this, new DialogManager.OnDialogClickAdapter() {
            @Override
            public void onNeutralClick() {
                finish();
            }
        });
    }

    private void showDeviceAdminActivationProblem() {
        DialogManager.showDeviceAdminActivationProblem(this, new DialogManager.OnDialogClickAdapter() {
            @Override
            public void onPositiveClick() {
                activateDeviceAdmin();
            }

            @Override
            public void onNegativeClick() {
                finish();
            }
        });
    }

    private void showKLMLicenseActivationProblem(int errorType, String errorMessage) {
        DialogManager.showKLMLicenseActivationProblem(this, errorType, errorMessage, new DialogManager.OnDialogClickAdapter() {
            @Override
            public void onPositiveClick() {
                activateKLMLicense();
            }

            @Override
            public void onNegativeClick() {
                activateELMLicense();
            }
        });
    }

    private void showELMLicenseActivationProblem(int errorType, String errorMessage) {
        DialogManager.showELMLicenseActivationProblem(this, errorType, errorMessage, new DialogManager.OnDialogClickAdapter() {
            @Override
            public void onPositiveClick() {
                activateELMLicense();
            }

            @Override
            public void onNegativeClick() {
                finish();
            }
        });
    }

    private void showKLMLoadingDialog() {
        dialogLoading = DialogManager.showKLMLicenseActivationLoading(this);
    }

    private void showELMLoadingDialog() {
        dialogLoading = DialogManager.showELMLicenseActivationLoading(this);
    }

    private void hideLoadingDialog() {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
            dialogLoading = null;
        }
    }
}
