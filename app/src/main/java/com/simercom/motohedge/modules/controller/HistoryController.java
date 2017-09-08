package com.simercom.motohedge.modules.controller;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;

import com.simercom.motohedge.modules.fragment.FragmentHistory;
import com.simercom.motohedge.modules.model.HistoryModel;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.models.Service;

import java.util.ArrayList;

/**
 * Created by wmora on 1/08/17.
 */

public class HistoryController {

    private Fragment mContext;

    public HistoryController(Fragment context){
        mContext = context;

    }

    public void getServicesHistory(){
        SharedPreferences sharedPreferences = mContext.getActivity().getSharedPreferences(Constants.PREFERENCES, 0);
        String email = sharedPreferences.getString(Constants.USER, null);

        HistoryModel mHistoryModel = new HistoryModel(this);
        mHistoryModel.getServicesHistory(email);

    }

    public void onResponseReceived(boolean haveServices, ArrayList<Service> service){
        if(haveServices){
            ((FragmentHistory) mContext).manageServicesData(service);
        }else{
            ((FragmentHistory) mContext).onServicesNotFound();
        }
    }

}
