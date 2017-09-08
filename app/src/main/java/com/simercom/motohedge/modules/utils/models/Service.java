package com.simercom.motohedge.modules.utils.models;

/**
 * Created by wmora on 1/08/17.
 */

public class Service {

    private String consecutive;
    private String serviceType;
    private String vehicleType;
    private String serviceAddress;
    private String latitude;
    private String longitude;
    private String userComments;
    private String serviceState;
    private String userEmail;
    private String userCellphone;
    private String date;

    /**
     * getters
     * */
    public String getConsecutive() {
        return consecutive;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getUserComments() {
        return userComments;
    }

    public String getServiceState() {
        return serviceState;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserCellphone() {
        return userCellphone;
    }

    public String getDate() {
        return date;
    }

    /**
     * setters
     * */
    public void setConsecutive(String consecutive) {
        this.consecutive = consecutive;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public void setServiceState(String serviceState) {
        this.serviceState = serviceState;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserCellphone(String userCellphone) {
        this.userCellphone = userCellphone;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
