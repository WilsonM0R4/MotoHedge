package com.simercom.motohedge.modules.model;

import com.simercom.motohedge.modules.async.AsyncSoapCall;
import com.simercom.motohedge.modules.controller.RegisterController;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.models.User;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

/**
 * Created by wmora on 15/05/17.
 */

public class RegisterModel {

    private RegisterController mController;

    public RegisterModel(){

    }

    public void setRegisterController(RegisterController controller){
        if(controller != null){
            mController = controller;
        }

    }

    public void register(User user){

        if(user!=null){
            SoapObject registerObject = new SoapObject();

            registerObject.addProperty(Constants.SERVICE_KEY_NAME, user.getUserName());
            registerObject.addProperty(Constants.SERVICE_KEY_LASTNAME, user.getUserLastName());
            registerObject.addProperty(Constants.SERVICE_KEY_DOCTYPE, user.getDocumentType());
            registerObject.addProperty(Constants.SERVICE_KEY_DOC_NUMBER, user.getUserDocument());
            registerObject.addProperty(Constants.SERVICE_KEY_USER_EMAIL, user.getUserEmail());
            registerObject.addProperty(Constants.SERVICE_KEY_USER_CELLPHONE, user.getUserCellphone());
            registerObject.addProperty(Constants.SERVICE_KEY_USER_TEPELHONE, user.getUserTelephone());
            registerObject.addProperty(Constants.SERVICE_KEY_SESSION_STATE, "1");
            registerObject.addProperty(Constants.SERVICE_KEY_USER_PASSWORD, user.getUserPassword());

            AsyncSoapCall asyncSoapCall = new AsyncSoapCall(Constants.SOAP_NAMESPACE,
                    Constants.SOAP_REGISTER_METHOD,
                    Constants.MAIN_SOAP_ACTION,
                    registerObject);

            asyncSoapCall.setAsyncTaskCallback(new AsyncSoapCall.AsyncTaskCallback() {
                @Override
                public void onTaskFinished(SoapObject object) {
                    registerFinished(object);
                }
            });
            asyncSoapCall.execute();
        }else{
            registerFinished(null);
        }

    }

    private void registerFinished(SoapObject object){

        String result;
        boolean isSignedUp = false;
        if(object!=null && object.hasProperty(AsyncSoapCall.RESPONSE)){

            result = object.getProperty(AsyncSoapCall.RESPONSE).toString();
            if(result.equals(Constants.KEY_SUCCESS_OPERATION)){
                isSignedUp = true;
            }
        }else{
            result = Constants.FAILED_LOGIN_MESSAGE;
        }
        
        mController.onRegisterFinished(isSignedUp, result);
    }


}
