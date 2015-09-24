package com.trolley.trolley.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trolley.trolley.Application.TrolleyApp;
import com.trolley.trolley.Constants.TrolleyConstants;
import com.trolley.trolley.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, TrolleyConstants.sWebUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    mProgressBar.setVisibility(View.GONE);
                    if (jsonObject.getBoolean("success")) {
                        Toast.makeText(SplashActivity.this, "You are In ...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        // Lunch Next Activity
                    } else {
                        Toast.makeText(SplashActivity.this, "Please Check Your Connection...", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(SplashActivity.this, "Internal Error Please try after sometime...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TrolleyConstants.sTag, "splash");      //use to identify the function calling in index.php
                return params;
            }
        };

        TrolleyApp.getInstance().addToReqQueue(stringRequest);
    }
}
