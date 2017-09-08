package com.simercom.motohedge.modules.model;

import android.util.Log;

import com.simercom.motohedge.modules.async.AsyncSoapCall;
import com.simercom.motohedge.modules.controller.ServiceController;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.models.Service;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

/**
 * Created by wmora on 5/06/17.
 */

public class ServiceModel {

    private ServiceController mController;

    public ServiceModel(ServiceController controller){
        mController = controller;
    }

    public void sendRequest(Service serviceObj){

        if(serviceObj!=null){

            SoapObject requestObject = mapService(serviceObj);
            AsyncSoapCall soapCall = new AsyncSoapCall(Constants.SOAP_NAMESPACE,
                    Constants.SOAP_SERVICE_REQUEST_METHOD, Constants.MAIN_SOAP_ACTION, requestObject);

            soapCall.setAsyncTaskCallback(new AsyncSoapCall.AsyncTaskCallback() {
                @Override
                public void onTaskFinished(SoapObject object) {

                    Log.d("Model", "response received on model");

                    try{
                        if (object.getProperty(AsyncSoapCall.RESPONSE) != null){
                            mController.onRequestSuccessful();
                        }
                    }catch(Exception ex){
                        Log.e("Model", "Exception in request: "+ex);
                        mController.onRequestFailure();
                    }

                }
            });

            soapCall.execute();
        }

    }

    private SoapObject mapService(Service service){

        SoapObject obj = new SoapObject();
        obj.addProperty(Constants.KEY_SERVICE_TYPE, service.getServiceType());
        obj.addProperty(Constants.KEY_VEHICLE_TYPE, service.getVehicleType());
        obj.addProperty(Constants.KEY_SERVICE_ADDRESS, service.getServiceAddress());
        obj.addProperty(Constants.KEY_LATITUDE, service.getLatitude());
        obj.addProperty(Constants.KEY_LONGITUDE, service.getLongitude());
        obj.addProperty(Constants.KEY_USER_COMMENTS, service.getUserComments());
        obj.addProperty(Constants.KEY_SERVICE_STATE, service.getServiceState());
        obj.addProperty(Constants.KEY_USER_EMAIL, service.getUserEmail());
        obj.addProperty(Constants.KEY_USER_CELLPHONE, service.getUserCellphone());
        obj.addProperty(Constants.KEY_SERVICE_DATE, service.getDate());

        return obj;
    }

}
