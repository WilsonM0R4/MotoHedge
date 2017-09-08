package com.simercom.motohedge.modules.model;

import android.util.Log;

import com.simercom.motohedge.modules.async.AsyncSoapCall;
import com.simercom.motohedge.modules.controller.HistoryController;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.Util;
import com.simercom.motohedge.modules.utils.models.Service;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by wmora on 1/08/17.
 */

public class HistoryModel {

    private HistoryController mController;

    public HistoryModel(HistoryController controller){
        mController = controller;
    }

    public void getServicesHistory(String user){

        if(Util.stringValidation(user)){

            SoapObject requestObject = new SoapObject();
            requestObject.addProperty(Constants.KEY_USER_EMAIL, user);
            requestObject.addProperty("fromIndex", "0");

            AsyncSoapCall soapCall = new AsyncSoapCall(Constants.SOAP_NAMESPACE,
                    Constants.SOAP_SERVICE_LIST_METHOD, Constants.MAIN_SOAP_ACTION, requestObject);

            soapCall.setAsyncTaskCallback(new AsyncSoapCall.AsyncTaskCallback() {
                @Override
                public void onTaskFinished(SoapObject object) {
                    checkResponse(object);
                }
            });

            soapCall.execute();

        }

    }

    private void checkResponse(SoapObject object){

        ArrayList<Service> serviceList = new ArrayList<>();

        if(object.hasProperty(AsyncSoapCall.RESPONSE)){
            if(object.getPropertyCount() > 0){
                for(int c =0; c < object.getPropertyCount(); c++){
                    Vector obj = (Vector) object.getProperty(c);
                    Log.e("Async", "count: "+obj.size());
                    for(int count = 0; count < obj.size(); count++){
                        serviceList.add(mapService((SoapObject) obj.get(count)));
                    }
                }
            }
            Log.e("Async", "list count: "+serviceList.size());
            mController.onResponseReceived(true, serviceList);
            return;
        }
        mController.onResponseReceived(false, null);
    }

    private Service mapService(SoapObject object){
        Service service = new Service();

        Log.e("Model", "Consecutive for this service: "+object.getPropertyAsString(Constants.KEY_SERVICE_CONSECUTIVE));

        service.setConsecutive(object.getPropertyAsString(Constants.KEY_SERVICE_CONSECUTIVE));
        service.setServiceType(object.getPropertyAsString(Constants.KEY_SERVICE_TYPE));
        service.setVehicleType(object.getPropertyAsString(Constants.KEY_VEHICLE_TYPE));
        service.setServiceAddress(object.getPropertyAsString(Constants.KEY_SERVICE_ADDRESS));
        service.setLatitude(object.getPropertyAsString(Constants.KEY_LATITUDE));
        service.setLongitude(object.getPropertyAsString(Constants.KEY_LONGITUDE));
        service.setUserComments(object.getPropertyAsString(Constants.KEY_USER_COMMENTS));
        service.setServiceState(object.getPropertyAsString(Constants.KEY_SERVICE_STATE));
        service.setUserEmail(object.getPropertyAsString(Constants.KEY_USER_EMAIL));
        service.setUserCellphone(object.getPropertyAsString(Constants.KEY_USER_CELLPHONE));
        service.setDate(object.getPropertyAsString(Constants.KEY_SERVICE_DATE));
        return service;
    }

}
