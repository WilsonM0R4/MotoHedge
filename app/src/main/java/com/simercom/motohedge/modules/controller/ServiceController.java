package com.simercom.motohedge.modules.controller;

import com.simercom.motohedge.modules.model.ServiceModel;
import com.simercom.motohedge.modules.utils.models.Service;

import java.util.HashMap;

/**
 * Created by wmora on 5/06/17.
 */

public class ServiceController {

    private ServiceModel model;
    private OnRequestResponseReceivedCallback mOnRequestResponseReceivedCallback;

    public ServiceController() {
        model = new ServiceModel(this);
    }

    public void sendRequest(Service serviceObj){

        model.sendRequest(serviceObj);

    }

    public void onRequestSuccessful() {
        mOnRequestResponseReceivedCallback.onResponseReceived(true, "petición exitosa");
    }

    public void onRequestFailure() {
        mOnRequestResponseReceivedCallback.onResponseReceived(false,
                "no pudimos realizar la petición, por favor intentalo de nuevo");
    }

    public void setOnRequestResponseReceivedCallback(OnRequestResponseReceivedCallback callback){
        if(callback!=null){
            mOnRequestResponseReceivedCallback = callback;
        }
    }

    public interface OnRequestResponseReceivedCallback {
        void onResponseReceived(boolean isSuccess, String message);
    }

}
