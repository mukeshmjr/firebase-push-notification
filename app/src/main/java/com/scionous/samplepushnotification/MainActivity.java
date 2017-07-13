package com.scionous.samplepushnotification;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getButton = (Button) findViewById(R.id.getButton);

        getButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Handle Button
                try {
                    getNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *  call to get notifications message
     */


    public void getNotification() {

        final Context context = getApplicationContext();

        InstanceIdService ids = new InstanceIdService();

        final String token = ids.getToken(context);

        final String REQ_PUSH = "PUSH_REQ";


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Constant.BASE_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Update Response: " + response);


                    try {

                        JSONObject jObj = new JSONObject(response);


                        final String text = jObj.getString("message");

                        //user successfully
                        if(jObj.getInt("id")==200)
                        {

                            Toast.makeText(context, text, Toast.LENGTH_LONG).show();

                            Log.d(TAG, text);
                        } else {

                            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                            Log.d(TAG, text);

                        }

                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error on get psh: " + error.getMessage());
                }
            }) {

                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("gcm_access", token);
                    params.put("server_key", Constant.SERVER_KEY);
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, REQ_PUSH);

        }

}
