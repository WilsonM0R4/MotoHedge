package com.simercom.motohedge.modules.utils.models;

import android.support.annotation.NonNull;

/**
 * Created by wmora on 1/06/17.
 */

public class User {

    private String documentType;
    private String userDocument;
    private String userName;
    private String userLastName;
    private String userEmail;
    private String userCellphone;
    private String userTelephone;
    private String sessionState;
    private String userPassword;

    /**
     * Getters
     * */

    public String getUserDocument(){
        return this.userDocument;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getUserLastName(){
        return this.userLastName;
    }

    public String getDocumentType(){
        return this.documentType;
    }

    public String getUserEmail(){
        return this.userEmail;
    }

    public String getUserCellphone(){
        return this.userCellphone;
    }

    public String getUserTelephone(){
        return this.userTelephone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getSessionState(){
        return this.sessionState;
    }

    /**
     * Setters
     * **/

    public void setUserDocument(@NonNull String userDocument){
        this.userDocument = userDocument;
    }

    public void setDocumentType(@NonNull String documentType){
        this.documentType = documentType;
    }

    public void setUserName(@NonNull String userName){
        this.userName = userName;
    }

    public void setUserLastName(@NonNull String userLastName){
        this.userLastName = userLastName;
    }

    public void setUserEmail(@NonNull String userEmail){
        this.userEmail = userEmail;
    }

    public void setUserCellphone(@NonNull String userCellphone){
        this.userCellphone = userCellphone;
    }

    public void setUserTelephone(@NonNull String userTelephone){
        this.userTelephone = userTelephone;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setSessionState(@NonNull String sessionState){
        this.sessionState = sessionState;
    }
}

