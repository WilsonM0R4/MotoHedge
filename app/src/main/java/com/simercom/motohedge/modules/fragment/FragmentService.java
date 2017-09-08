package com.simercom.motohedge.modules.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.simercom.motohedge.MainActivity;
import com.simercom.motohedge.R;
import com.simercom.motohedge.modules.controller.ServiceController;
import com.simercom.motohedge.modules.utils.AlertMessage;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.Util;
import com.simercom.motohedge.modules.utils.models.Service;

import java.util.HashMap;

/**
 * Created by wmora on 25/05/17.
 */

public class FragmentService extends Fragment implements OnMapReadyCallback {

    private Spinner kindOfServiceSpinner, kindOfVechicleSpinner;
    private EditText etComments;
    private MapView mapView;
    private Button btnCancel, btnSend;
    private GoogleMap mMap;
    private MapFragment mapFragment;
    private LatLng defaultPoint = new LatLng(4.6482837, -74.2478938);
    private LatLng currentPosition;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private long locationRefreshTime = 30;
    private float locationRefreshDistance = 30f;
    private int serviceIndex = 1;
    private int vehicleIndex = 1;
    private ServiceController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.service_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentPosition = defaultPoint;
        controller = new ServiceController();

        /*if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
            String [] permissions = new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION);
        }*/

        initViews(view, savedInstanceState);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initViews(View rootView, Bundle instanceState) {

        kindOfServiceSpinner = (Spinner) rootView.findViewById(R.id.spinner_kindOfService);
        kindOfVechicleSpinner = (Spinner) rootView.findViewById(R.id.spinner_kindOfVehicle);
        //mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapFragment);
        //mapView = (MapView) rootView.findViewById(R.id.mapView);

        etComments = (EditText) rootView.findViewById(R.id.et_description);

        btnCancel = (Button) rootView.findViewById(R.id.cancel_button);
        btnSend = (Button) rootView.findViewById(R.id.send_button);

        //mapView.onCreate(instanceState);
        mapFragment = MapFragment.newInstance();

        getChildFragmentManager().beginTransaction()
                .replace(R.id.mapFragment, mapFragment)
                .commit();

        mapFragment.getMapAsync(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.kindOfService_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindOfServiceSpinner.setAdapter(adapter);

        ArrayAdapter vehicleAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.kindOfVehicle_array, android.R.layout.simple_spinner_item);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindOfVechicleSpinner.setAdapter(vehicleAdapter);
        //kindOfServiceSpinner.setOnItemSelectedListener(getActivity());

        setListeners();
    }

    private void setListeners() {

        ((MainActivity) getActivity()).setPermissionsCallback(new MainActivity.PermissionsCallback() {
            @Override
            public void onPermissionsGranted() {
                setLocation(defaultPoint);
            }

            @Override
            public void onPermissionsDenied() {

            }
        });

        controller.setOnRequestResponseReceivedCallback(new ServiceController.OnRequestResponseReceivedCallback() {
            @Override
            public void onResponseReceived(boolean isSuccess, String message) {

                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                /*if (isSuccess) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }*/
            }
        });

        kindOfServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceIndex = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kindOfVechicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicleIndex = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                    setLocation(currentPosition);
                } else {
                    Log.e("Service", "location is null");
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBack();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.e("Service", "map is ready");

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

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            Bundle bundle = new Bundle();
            bundle.putString(AlertMessage.ALERT_MESSAGE, "Parece que tu GPS está apagado, por favor, enciéndelo");

            AlertMessage alertMessage = new AlertMessage();
            alertMessage.setArguments(bundle);
            alertMessage.show(getFragmentManager(), "alert_message");
        }
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                locationRefreshTime,
                locationRefreshDistance,
                mLocationListener);

        //setLocation(defaultPoint);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mLocationManager.removeUpdates(mLocationListener);
    }

    //**** proper methods
    private void setLocation(LatLng location) {

        mMap.addMarker(new MarkerOptions()
                .title("Ubicación actual")
                .position(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(location.latitude, location.longitude), 15.0f));

    }

    private boolean checkFields() {

        return serviceIndex != 0 && vehicleIndex != 0 && currentPosition.latitude != 0f
                && currentPosition.longitude != 0f && etComments.getText() != null
                && !etComments.getText().toString().isEmpty();
    }

    private void sendRequest() {

        if (checkFields()) {
            Service service = new Service();
            service.setServiceType(String.valueOf(serviceIndex));
            service.setVehicleType(String.valueOf(vehicleIndex));
            service.setServiceAddress("test address");
            service.setLatitude(String.valueOf(currentPosition.latitude));
            service.setLongitude(String.valueOf(currentPosition.longitude));
            service.setUserComments(etComments.getText().toString());
            service.setServiceState("1");
            service.setUserEmail(Util.getCurrentUser(getActivity()));
            service.setUserCellphone("32137511378");
            service.setDate(Util.getCurrentDate());

            controller.sendRequest(service);

            Toast.makeText(getActivity(), "sending request", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "por favor, diligencia todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

}
