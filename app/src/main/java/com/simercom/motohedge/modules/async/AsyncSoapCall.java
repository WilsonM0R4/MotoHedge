package com.simercom.motohedge.modules.async;

import android.os.AsyncTask;
import android.util.Log;

import com.simercom.motohedge.modules.utils.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;

/**
 * Created by wmora on 8/05/17.
 */

public class AsyncSoapCall extends AsyncTask<Void, String, SoapObject> {

    private static final String TAG = "ASYNC";
    public static final String RESPONSE = "receivedResponse";
    private String namespace, method, soapAction;
    private String responseObject = "responseObject";
    private HashMap<String, Object> params;
    private SoapObject soapObject;
    private SoapObject response;
    private AsyncTaskCallback mAsyncTaskCallback;


    public AsyncSoapCall(String namespace, String method, String soapAction, SoapObject soapObject) {
        this.namespace = namespace;
        this.method = method;
        this.soapObject = soapObject;
        this.soapAction = soapAction;
    }

    @Override
    protected SoapObject doInBackground(Void... params) {

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject body = new SoapObject(namespace, method);

        body.addProperty(Constants.PARAMS_TAG, soapObject);

        envelope.dotNet = false;
        envelope.addMapping(namespace, responseObject, HashMap.class);
        envelope.setOutputSoapObject(body);

        HttpTransportSE transport = new HttpTransportSE(Constants.SOAP_SERVICE_URL);
        transport.debug = true;

        response = new SoapObject();

        try {

            transport.call(soapAction, envelope);
            response.addProperty(RESPONSE, envelope.getResponse());


        } catch (Exception e) {
            Log.e(TAG, "Exception in async call: " +e+" -- "+e.getCause());
        }

        //Log.e(TAG, response.toString());

        Log.e(TAG, "request: -- " + transport.requestDump);
        Log.e(TAG, "response: --"+transport.responseDump);

        return response;
    }

    @Override
    protected void onPostExecute(SoapObject result) throws RuntimeException {
        if (result != null) {
            Log.e(TAG, "result is " + result);
            Log.e(TAG, "result is " + result.getName());

            mAsyncTaskCallback.onTaskFinished(result);
        } else {
            Log.e(TAG, "null or empty result");
        }
    }

    //**** proper methods
    public void setAsyncTaskCallback(AsyncTaskCallback asyncTaskCallback) {

        if (asyncTaskCallback != null) {
            mAsyncTaskCallback = asyncTaskCallback;
        }

    }

    public interface AsyncTaskCallback {
        void onTaskFinished(SoapObject object);
    }

}
