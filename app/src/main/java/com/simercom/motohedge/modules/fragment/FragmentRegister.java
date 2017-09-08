package com.simercom.motohedge.modules.fragment;

import android.app.Fragment;
import android.os.Bundle;
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

import com.simercom.motohedge.MainActivity;
import com.simercom.motohedge.R;
import com.simercom.motohedge.modules.controller.RegisterController;
import com.simercom.motohedge.modules.utils.Constants;
import com.simercom.motohedge.modules.utils.models.User;

import java.util.HashMap;

/**
 * Created by wmora on 12/05/17.
 */

public class FragmentRegister extends Fragment {

    private Spinner documentSpinner;
    private EditText etName, etLastName,
            etNumDocument, etRegisterEmail,
            etCellphone, etTelephone, etRegPassword,
            etConfigPassword;
    private Button btnRegister;
    private String[] docTypes;
    private HashMap <String, String> params;
    private User user;
    private String selectedDocType;
    private RegisterController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        controller = new RegisterController();
        params = new HashMap<>();
        docTypes = getResources().getStringArray(R.array.document_types);
        selectedDocType = "0";
        initViews(view);
    }

    //**** proper methods
    private void initViews(View root){

        etName = (EditText) root.findViewById(R.id.etName);
        etLastName = (EditText) root.findViewById(R.id.etLastName);
        documentSpinner = (Spinner) root.findViewById(R.id.spinnerDocType);
        etNumDocument = (EditText) root.findViewById(R.id.etNumDocument);
        etRegisterEmail = (EditText) root.findViewById(R.id.etRegisterEmail);
        etCellphone = (EditText) root.findViewById(R.id.etCellphone);
        etTelephone = (EditText) root.findViewById(R.id.etTelephone);
        etRegPassword = (EditText) root.findViewById(R.id.etRegPassword);
        etConfigPassword = (EditText) root.findViewById(R.id.etConfirmPassword);
        btnRegister = (Button) root.findViewById(R.id.btnRegister);

        ArrayAdapter<CharSequence> docAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.document_types,
                android.R.layout.simple_spinner_item);

        docAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        documentSpinner.setAdapter(docAdapter);

        setListeners();

    }

    private void setListeners(){

        controller.setRegisterCallback(new RegisterController.RegisterCallback() {
            @Override
            public void onSignUpSuccess() {
                ((MainActivity) getActivity()).resetStack();
                ((MainActivity) getActivity()).replaceView(new FragmentHome(), false);
            }

            @Override
            public void onSignUpFailure(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });

        documentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //params.put(Constants.SERVICE_KEY_DOCTYPE, String.valueOf(position));
                selectedDocType = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing to do
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultParams = getRegisterParams();
                if(resultParams.equals("ok")){
                    Log.e("Register", "params :"+user.getUserEmail());
                    controller.executeRegister(user);
                } else{
                    Toast.makeText(getActivity(), resultParams, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private String getRegisterParams(){

        user = new User();

        if(!etName.getText().toString().isEmpty()){
            user.setUserName(etName.getText().toString());
            //params.put(Constants.SERVICE_KEY_NAME, etName.getText().toString());
        }else{
            return "el nombre no puede ir vacío";
        }

        if(!etLastName.getText().toString().isEmpty()){
            user.setUserLastName(etLastName.getText().toString());
            //params.put(Constants.SERVICE_KEY_LASTNAME, etLastName.getText().toString());
        }else{
            return "el apellido no puede ir vacío";
        }

        if(selectedDocType.equals(docTypes[0])){
           return "debes seleccionar un tipo de documento";
        }else{
            user.setDocumentType(selectedDocType);
        }

        if(!etNumDocument.getText().toString().isEmpty()){
            user.setUserDocument(etNumDocument.getText().toString());
            //params.put(Constants.SERVICE_KEY_DOC_NUMBER, etNumDocument.getText().toString());
        }else{
            return "el número de documento no puede ir vacío";
        }

        if(!etRegisterEmail.getText().toString().isEmpty()){
            user.setUserEmail(etRegisterEmail.getText().toString());
            //params.put(Constants.SERVICE_KEY_USER_EMAIL, etRegisterEmail.getText().toString());
        } else {
            return "el correo no puede ir vacío";
        }

        if(!etCellphone.getText().toString().isEmpty()){
            user.setUserCellphone(etCellphone.getText().toString());
            //params.put(Constants.SERVICE_KEY_USER_CELLPHONE, etCellphone.getText().toString());
        } else {
            return "el número de celular no puede ir vacío";
        }

        if(!etTelephone.getText().toString().isEmpty()){
            user.setUserTelephone(etTelephone.getText().toString());
            //params.put(Constants.SERVICE_KEY_USER_TEPELHONE, etTelephone.getText().toString());
        }else{
            user.setUserTelephone("0");
            //params.put(Constants.SERVICE_KEY_USER_TEPELHONE, "0");
        }

        if(passwordValidation()){
            user.setUserPassword(etRegPassword.getText().toString());
            //params.put(Constants.SERVICE_KEY_USER_PASSWORD, etRegPassword.getText().toString());
        } else {
            return "las contraseñas no coinciden";
        }

        return "ok";
    }

    private boolean passwordValidation(){

        String pass, confPass;
        pass = etRegPassword.getText().toString();
        confPass = etConfigPassword.getText().toString();

        if(!pass.isEmpty() && !confPass.isEmpty()){
            if(pass.equals(confPass)){
                return true;
            }
        }

        return false;
    }

}
