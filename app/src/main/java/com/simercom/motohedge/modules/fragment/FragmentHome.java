package com.simercom.motohedge.modules.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.simercom.motohedge.MainActivity;
import com.simercom.motohedge.R;
import com.simercom.motohedge.modules.utils.Constants;

/**
 * Created by wmora on 12/05/17.
 */

public class FragmentHome extends Fragment {

    private Button btnServices, btnServiceRequest, btnSignOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        initViews(view);
    }

    //***** proper methods
    private void initViews(View rootView){
        btnServices = (Button) rootView.findViewById(R.id.btn_viewServices);
        btnServiceRequest = (Button) rootView.findViewById(R.id.btn_makeRequest);
        btnSignOut = (Button) rootView.findViewById(R.id.btn_signOut);

        setListeners();
    }

    private void setListeners(){
        ((MainActivity) getActivity()).setPermissionsCallback(new MainActivity.PermissionsCallback() {
            @Override
            public void onPermissionsGranted() {
                openServiceFragment();
            }

            @Override
            public void onPermissionsDenied() {

            }
        });

        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceView(new FragmentHistory(), false);
            }
        });

        btnServiceRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openServiceFragment();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).logout();
            }
        });

    }

    private void openServiceFragment(){

        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), permissions, Constants.LOCATION_PERMISSION);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        ((MainActivity) getActivity()).replaceView(new FragmentService(), false);

    }
}
