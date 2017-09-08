package com.simercom.motohedge.modules.model;

import android.util.Log;

import com.simercom.motohedge.modules.async.AsyncSoapCall;
import com.simercom.motohedge.modules.controller.CommonController;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.Util;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

/**
 * Created by wmora on 17/05/17.
 */

public class CommonModel {

    private CommonController mCommonController;

    public CommonModel(CommonController controller) {
        mCommonController = controller;
    }

    public void checkSession(String email) {
        if (Util.stringValidation(email)) {

            SoapObject requestObject = new SoapObject();
            requestObject.addProperty("userEmail", email);

            AsyncSoapCall soapCall = new AsyncSoapCall(Constants.SOAP_NAMESPACE,
                    Constants.SOAP_CHECK_SESSION_METHOD, Constants.MAIN_SOAP_ACTION, requestObject);

            soapCall.setAsyncTaskCallback(new AsyncSoapCall.AsyncTaskCallback() {
                @Override
                public void onTaskFinished(SoapObject object) {

                    String result;
                    boolean isLoggedIn = false;

                    try {
                        SoapObject response = (SoapObject) object.getProperty(AsyncSoapCall.RESPONSE);
                        if (response.hasProperty("responseInfo")) {
                            SoapObject obj = (SoapObject) response.getProperty("responseInfo");
                            if (obj.hasProperty("responseCode")) {
                                result = obj.getProperty("responseCode").toString();
                                if (result.equals(Constants.KEY_LOGIN_SUCCESS)) {
                                    isLoggedIn = true;
                                }
                            }

                        }

                        mCommonController.onResponseReceived(isLoggedIn, Constants.PROCESS_CHECK_SESSION);
                    } catch (RuntimeException ex) {
                        Log.e("MODEL", ex.getMessage());
                        mCommonController.onResponseReceived(false, Constants.PROCESS_CHECK_SESSION);
                    }


                }
            });

            soapCall.execute();
        }
    }

    public void signOut(String email) {
        if (Util.stringValidation(email)) {

            HashMap<String, Object> param = new HashMap<>();

            SoapObject commonObject = new SoapObject();

            param.put(Constants.SERVICE_KEY_USER_EMAIL, email);

            AsyncSoapCall soapCall = new AsyncSoapCall(Constants.SOAP_NAMESPACE,
                    Constants.SOAP_LOGOUT_METHOD, Constants.MAIN_SOAP_ACTION, commonObject);

            soapCall.setAsyncTaskCallback(new AsyncSoapCall.AsyncTaskCallback() {
                @Override
                public void onTaskFinished(SoapObject object) {

                    String result;
                    boolean isLoggedIn = false;
                    if (object != null && object.hasProperty(AsyncSoapCall.RESPONSE)) {
                        result = object.getProperty(AsyncSoapCall.RESPONSE).toString();
                        if (result.equals(Constants.KEY_LOGIN_SUCCESS)) {
                            isLoggedIn = true;
                        }
                    }

                    mCommonController.onResponseReceived(isLoggedIn, Constants.PROCESS_LOGOUT);

                }
            });

            soapCall.execute();
        }
    }

}
