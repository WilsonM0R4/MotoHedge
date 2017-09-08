package com.simercom.motohedge.modules.model;

import android.util.Log;

import com.simercom.motohedge.modules.async.AsyncSoapCall;
import com.simercom.motohedge.modules.controller.LoginController;
import com.simercom.motohedge.modules.utils.Constants;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

/**
 * Created by wmora on 9/05/17.
 */

public class LoginModel {

    private AsyncSoapCall call;
    private LoginController mController;

    public LoginModel() {
        //empty constructor
    }

    public void login(String email, String password) {

        SoapObject loginObject = new SoapObject();
        loginObject.addProperty("userEmail", email);
        loginObject.addProperty("userPassword", password);

        call = new AsyncSoapCall(
                Constants.SOAP_NAMESPACE,
                Constants.SOAP_LOGIN_METHOD,
                Constants.MAIN_SOAP_ACTION,
                loginObject);

        call.setAsyncTaskCallback(new AsyncSoapCall.AsyncTaskCallback() {
            @Override
            public void onTaskFinished(SoapObject object) {
                loginFinished(object);
            }
        });

        call.execute();
    }

    public void setControllerInstance(LoginController controller) {
        mController = controller;
    }

    private void loginFinished(SoapObject object) {

        String result = Constants.FAILED_LOGIN_MESSAGE;
        boolean isLoggedIn = false;
        if (object != null) {

            try {
                SoapObject response = (SoapObject) object.getProperty(AsyncSoapCall.RESPONSE);
                if(response.hasProperty("responseInfo")){
                    SoapObject obj = (SoapObject) response.getProperty("responseInfo");
                    if(obj.hasProperty("responseCode")){
                        result = obj.getProperty("responseCode").toString();
                        if (result.equals(Constants.KEY_LOGIN_SUCCESS)) {
                            isLoggedIn = true;
                        }
                    }

                }

            } catch (RuntimeException ex) {
                Log.e("Model", ex.getMessage());
            }


        } else {
            result = Constants.FAILED_LOGIN_MESSAGE;
        }

        mController.onLoginFinished(isLoggedIn, result);
    }

}
