package com.scionous.samplepushnotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
     * Created by Mukesh Singh on 06-Jul-17.
 */

public class InstanceIdService extends FirebaseInstanceIdService {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(Constant.SHARED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constant.GCM_KEY, refreshedToken);
        editor.apply();
        Log.d(TAG, refreshedToken);
    }

    public String getToken(Context context) {

        SharedPreferences sharedPref = context.getSharedPreferences(Constant.SHARED, MODE_PRIVATE);
        String token = sharedPref.getString(Constant.GCM_KEY, null);
        if(token==null) {
            try {
                token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, token);
            } catch (Exception e) {
                token = null;
                e.printStackTrace();
            }

        }

        return token;

    }

}

