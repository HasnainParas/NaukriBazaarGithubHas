package com.appstacks.indiannaukribazaar.NewActivities.Models;

public class DeviceDataModel {

    String deviceID,userPhoneNumber,androidOs,deviceName,appVersion;
    boolean valid;

    public DeviceDataModel() {
    }

    public DeviceDataModel(String deviceID, String userPhoneNumber, String androidOs, String deviceName, String appVersion, boolean valid) {
        this.deviceID = deviceID;
        this.userPhoneNumber = userPhoneNumber;
        this.androidOs = androidOs;
        this.deviceName = deviceName;
        this.appVersion = appVersion;
        this.valid = valid;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getAndroidOs() {
        return androidOs;
    }

    public void setAndroidOs(String androidOs) {
        this.androidOs = androidOs;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
