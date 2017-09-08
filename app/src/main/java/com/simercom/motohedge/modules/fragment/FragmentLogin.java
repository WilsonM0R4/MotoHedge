package com.simercom.motohedge.modules.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.simercom.motohedge.MainActivity;
import com.simercom.motohedge.R;
import com.simercom.motohedge.modules.controller.LoginController;

/**
 * Created by wmora on 8/05/17.
 */

public class FragmentLogin extends Fragment {

    private Button btnLogin, btnRegister;
    private EditText etEmail, etPassword;
    private LoginController controller;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        controller = new LoginController();
        initViews(view);
    }

    //**** PROPER METHODS
    private void initViews(View rootView){

        etEmail = (EditText) rootView.findViewById(R.id.et_email);
        etPassword = (EditText) rootView.findViewById(R.id.et_password);
        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
        btnRegister = (Button) rootView.findViewById(R.id.btn_register);

        setListeners();
    }

    private void setListeners(){

        controller.setLoginCallback(new LoginController.LoginCallback() {
            @Override
            public void onLoginSuccess(String message) {
                ((MainActivity) getActivity()).saveCurrentUser(email);
                ((MainActivity) getActivity()).replaceView(new FragmentHome(), false);
                ((MainActivity) getActivity()).removeFromStack(FragmentLogin.this);
            }

            @Override
            public void onLoginFailure(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceView(new FragmentRegister(), false);
            }
        });
    }

    private void executeLogin(){

        String password;

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()){
            controller.executeOperation(email, password);
        } else {
            Toast.makeText(getActivity(), R.string.needed_credential_message, Toast.LENGTH_SHORT).show();
        }


    }

}
