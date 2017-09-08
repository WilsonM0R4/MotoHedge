package com.simercom.motohedge.modules.controller;

import com.simercom.motohedge.modules.model.RegisterModel;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.models.User;

import java.util.HashMap;

/**
 * Created by wmora on 15/05/17.
 */

public class RegisterController {

    private RegisterCallback mRegisterCallback;

    public RegisterController(){

    }

    public void executeRegister(User user){

        if(user != null){

            RegisterModel model = new RegisterModel();
            model.setRegisterController(this);
            model.register(user);
        }else {
            mRegisterCallback.onSignUpFailure(null);
        }

    }

    public void setRegisterCallback(RegisterCallback registerCallback){
        if(registerCallback!=null){
            mRegisterCallback = registerCallback;
        }
    }

    public void onRegisterFinished(boolean isSignedUp, String message){

        if(isSignedUp){
            mRegisterCallback.onSignUpSuccess();
            return;
        }

        mRegisterCallback.onSignUpFailure(message);
    }

    public interface RegisterCallback {

        void onSignUpSuccess();
        void onSignUpFailure(String message);
    }

}
