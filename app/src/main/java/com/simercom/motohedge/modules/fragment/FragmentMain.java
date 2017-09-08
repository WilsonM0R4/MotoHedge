package com.simercom.motohedge.modules.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.simercom.motohedge.MainActivity;
import com.simercom.motohedge.R;

/**
 * Created by wmora on 8/05/17.
 */

public class FragmentMain extends Fragment {

    private View mainView;
    private Button loginButton, registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        this.mainView = view;

        initViews(view);
    }

    //**** PROPER METHODS
    private void initViews(View rootView){
        loginButton = (Button) rootView.findViewById(R.id.button_login);
        registerButton = (Button) rootView.findViewById(R.id.button_register);

        setListeners();
    }

    private void setListeners(){

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceView(new FragmentLogin(), false);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceView(new FragmentRegister(), false);
            }
        });



    }
}
