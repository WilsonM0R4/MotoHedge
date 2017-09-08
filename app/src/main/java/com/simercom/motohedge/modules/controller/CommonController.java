package com.simercom.motohedge.modules.controller;

import android.content.SharedPreferences;

import com.simercom.motohedge.MainActivity;
import com.simercom.motohedge.modules.model.CommonModel;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.Util;

/**
 * Created by wmora on 17/05/17.
 */

public class CommonController {

    //*** class variables and constants
    private CheckSessionCallback mCheckSessionCallback;
    private CommonModel mCommonModel;
    private MainActivity activity;

    //*** public constructor. Initializes the model object and saves the activity instance reference
    public CommonController(MainActivity activity){
        mCommonModel = new CommonModel(this);
        this.activity = activity;
    }

    /*** runs the check session process
     *
     * @param email - (String) user email
     */
    public void checkSessionStatus(String email){
        if(Util.stringValidation(email))
            mCommonModel.checkSession(email);
        else
            mCheckSessionCallback.onSessionChecked(false, Constants.PROCESS_CHECK_SESSION);
    }

    /***
     * runs the logout process
     */
    public void logout(){

        String email = getPreferences().getString(Constants.USER, null);

        if(Util.stringValidation(email)){
            mCommonModel.signOut(email);
        }
    }

    /*** called when the session callback receives a response
     * @param isLoggedIn - (Boolean) session status
     */
    public void onResponseReceived(boolean isLoggedIn, int process){
        mCheckSessionCallback.onSessionChecked(isLoggedIn, process);
    }

    /*** sets the session callback
     * @param sessionCallback - (CheckSessionCallback) session callback instance
     */
    public void setSessionCallback(CheckSessionCallback sessionCallback){
        if(sessionCallback != null){
            mCheckSessionCallback = sessionCallback;
        }
    }

    /*** returns the latest signed in user
     * @return (String) user
     */
    public String getCurrentUser(){
        return getPreferences().getString(Constants.USER, "");
    }

    /*** saves the latest signed in user
     * @param user - (String) current user with active session
     */
    public void setCurrentUser(String user){
        if (Util.stringValidation(user)){
            SharedPreferences.Editor editor = getPreferences().edit();
            editor.putString(Constants.USER, user);
            editor.apply();
        }
    }

    /*** returns a shared preferences instance
     * @return (SharedPreferences) shared preferences instance
     */
    private SharedPreferences getPreferences(){
        return activity.getSharedPreferences(Constants.PREFERENCES, 0);
    }


    /*** Inner interface
     *  listens for the session status
     */
    public interface CheckSessionCallback {
        void onSessionChecked(boolean isLoggedIn, int process);
    }


}
