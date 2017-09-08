package com.simercom.motohedge;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.simercom.motohedge.modules.controller.CommonController;
import com.simercom.motohedge.modules.controller.LoginController;
import com.simercom.motohedge.modules.fragment.FragmentHome;
import com.simercom.motohedge.modules.fragment.FragmentMain;
import com.simercom.motohedge.modules.fragment.FragmentService;
import com.simercom.motohedge.modules.utils.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_FRAGMENT = "currentFragment";
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    //private Fragment currentFragment;
    private ArrayList<Fragment> stack;
    private CommonController controller;
    private PermissionsCallback mPermissionsCallback;
    private OnBackCallback mOnBackCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        stack = new ArrayList<>();

        controller = new CommonController(this);
        controller.setSessionCallback(new CommonController.CheckSessionCallback() {
            @Override
            public void onSessionChecked(boolean isLoggedIn, int process) {
                if (isLoggedIn) {

                    if (process == Constants.PROCESS_CHECK_SESSION) {
                        replaceView(new FragmentHome(), false);
                        return;
                    } else if (process == Constants.PROCESS_LOGOUT) {
                        replaceView(new FragmentMain(), true);
                        return;
                    }

                }

                replaceView(new FragmentMain(), false);

            }
        });

        //check session status
        controller.checkSessionStatus(controller.getCurrentUser());

        //currentFragment = new FragmentMain();
        //replaceView(currentFragment, false);
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);

        switch (requestCode) {
            case Constants.LOCATION_PERMISSION:
                checkPermissionResult(grantResult, requestCode);
                break;
            case 2:
                break;

            default:
                mPermissionsCallback.onPermissionsDenied();
                break;
        }


    }

    //******* PROPER METHODS

    public void setPermissionsCallback(PermissionsCallback callback) {
        if (callback != null) {
            mPermissionsCallback = callback;
        }
    }

    private void checkPermissionResult(int[] grantResult, int requestCode) {

        if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
            mPermissionsCallback.onPermissionsGranted();
            return;
        }

        mPermissionsCallback.onPermissionsDenied();
    }

    public void replaceView(Fragment fragment, boolean isActionBack) {

        if(stack.isEmpty()){
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, CURRENT_FRAGMENT)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, CURRENT_FRAGMENT)
                    .commit();
            /*fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(CURRENT_FRAGMENT))
                    .add(R.id.fragment_container, fragment, CURRENT_FRAGMENT)
                    .commit();*/
        }

        if(!isActionBack){
            stack.add(fragment);
        }


        /*fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, CURRENT_FRAGMENT)
                .commit();*/
        Log.e(TAG, "Fragment added");


        Log.e(TAG, "stack: " + stack);

    }

    public void removeFromStack(Fragment fragment){
        if(fragment != null && !stack.isEmpty()){
            stack.remove(fragment);
        }
    }

    private boolean isSameView(Fragment stackFragment, Fragment otherFragment) {

        String stackName, theOtherName;

        if (stackFragment != null && otherFragment != null) {

            stackName = stackFragment.getClass().getName();
            theOtherName = otherFragment.getClass().getName();

            return stackName.equals(theOtherName);
        }

        return false;
    }

    public void resetStack() {
        stack = null;
        stack = new ArrayList<>();
    }

    public boolean onBack() {

        if (stack.size() > 1) {
            Log.e(TAG, "last fragment: " + stack.get(stack.size() - 1));
            stack.get(stack.size() - 1).onDestroy();
            stack.remove(stack.size() - 1);
            replaceView(stack.get(stack.size() - 1), true);
            /*fragmentManager
                    .beginTransaction()
                    .remove(currentFragment)
                    .commit();*/
            Log.e(TAG, "last removed, size of " + stack.size());
            return true;
        }

        Log.e(TAG, "no last to remove");
        return false;
    }

    public void saveCurrentUser(String user) {
        if (user != null && !user.isEmpty())
            controller.setCurrentUser(user);
    }

    public void logout() {
        controller.logout();
    }

    //**** Inner interface
    public interface PermissionsCallback {

        void onPermissionsGranted();

        void onPermissionsDenied();

    }

    public interface OnBackCallback {
        void onBack();
    }

}
