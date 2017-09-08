package com.simercom.motohedge.modules.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.simercom.motohedge.R;
import com.simercom.motohedge.modules.controller.HistoryController;
import com.simercom.motohedge.modules.utils.HistoryListAdapter;
import com.simercom.motohedge.modules.utils.models.Service;

import java.util.ArrayList;

/**
 * Created by wmora on 1/08/17.
 */

public class FragmentHistory extends Fragment {

    private ProgressDialog progressDialog;
    private RecyclerView historyRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle instanceState){
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle instanceState){

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Buscando");
        progressDialog.setMessage("Buscando tu historial de servicios");

        historyRecyclerView = (RecyclerView) view.findViewById(R.id.servicesList);

        HistoryController controller = new HistoryController(this);
        controller.getServicesHistory();

        progressDialog.show();
    }

    //***** proper methods
    private void configList(ArrayList<Service> serviceList){
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        HistoryListAdapter adapter = new HistoryListAdapter(serviceList);
        historyRecyclerView.setAdapter(adapter);
        historyRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        historyRecyclerView.setLayoutManager(manager);

    }

    public void manageServicesData(ArrayList<Service> serviceArray){
        Log.e("History", "data found");
        if(progressDialog!=null){
            progressDialog.dismiss();
        }

        configList(serviceArray);
        /*for(int c = 0; c < serviceArray.size(); c++){
            Log.e("History", "consecutive: "+serviceArray.get(c).getConsecutive());
        }*/
    }

    public void onServicesNotFound(){
        Log.e("History", "data not found");
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
        Toast.makeText(getActivity(), "No se encontraron servicios", Toast.LENGTH_SHORT).show();
    }

}
