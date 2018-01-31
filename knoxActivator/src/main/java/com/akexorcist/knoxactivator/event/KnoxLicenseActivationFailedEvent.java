package com.akexorcist.knoxactivator.event;

public class KnoxLicenseActivationFailedEvent {
    int errorType;

    public KnoxLicenseActivationFailedEvent(int errorType) {
        this.errorType = errorType;
    }

    public int getErrorType() {
        return errorType;
    }
}
