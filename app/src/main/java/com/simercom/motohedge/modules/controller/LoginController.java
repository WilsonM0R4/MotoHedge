package com.simercom.motohedge.modules.controller;

import com.simercom.motohedge.modules.model.LoginModel;

/**
 * Created by wmora on 9/05/17.
 */

public class LoginController {

    private LoginModel model;
    private LoginCallback mLoginCallback;

    public LoginController(){
        model = new LoginModel();
        model.setControllerInstance(this);
    }

    public void executeOperation(String email, String password){
        model.login(email, password);
    }

    public void onLoginFinished(boolean isLoggedIn, String message){

        if(isLoggedIn){
            mLoginCallback.onLoginSuccess("");
            return;
        }

        mLoginCallback.onLoginFailure(message);
    }

    //setters
    public void setLoginCallback(LoginCallback callback){
        if(callback!=null){
            this.mLoginCallback = callback;
        }
    }


    public interface LoginCallback{
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
    }

}
