package com.trolley.trolley.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trolley.trolley.Application.TrolleyApp;
import com.trolley.trolley.Constants.TrolleyConstants;
import com.trolley.trolley.R;
import com.trolley.trolley.apis.ApiUtils;
import com.trolley.trolley.dialogs.SignUpDialog;
import com.trolley.trolley.utils.PreferenceUtils;
import com.trolley.trolley.utils.UiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, SignUpDialog.InfoUpdater, ApiUtils.Constants {
    Button mSignUp, mSkip;
    View mSignIn;
    private EditText inputUsername, inputPassword;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSignIn = findViewById(R.id.login);
        mSignUp = (Button) findViewById(R.id.sign_up);
        mSkip = (Button) findViewById(R.id.skip);

        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mSkip.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mSignIn.setOnClickListener(this);

        inputUsername.setText(getPhoneNumber());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    checkLogin(username, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.sign_up:
                showSignUpDialog();
                break;
            case R.id.skip:
                break;
        }

    }

    private void showSignUpDialog() {
        DialogFragment registerDialog = new SignUpDialog();
        registerDialog.show(getSupportFragmentManager(), SignUpDialog.TAG);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    //login json request
    private void checkLogin(final String userName, final String password) {
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, TrolleyConstants.sWebUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean(ERROR);
                    if (!error) {
                        Toast.makeText(LoginActivity.this, R.string.welcome, Toast.LENGTH_SHORT).show();
                        PreferenceUtils.updateProfile(LoginActivity.this, userName, password);
                        UiUtils.launchProducts(LoginActivity.this);
                    } else {
                        Toast.makeText(LoginActivity.this, jObj.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TAG, LOGIN);
                params.put(USER_NAME, userName);
                params.put(USER_PASSWORD, password);
                return params;
            }
        };
        TrolleyApp.getInstance().addToReqQueue(strReq);
    }

    @Override
    public void onSignUpRequested(final String userName, final String password, final String location) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean registered = jsonObject.getBoolean(REGISTER);
                    if (!registered) {
                        Toast.makeText(LoginActivity.this, jsonObject.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.registration_successful, Toast.LENGTH_SHORT).show();
                        PreferenceUtils.updateProfile(LoginActivity.this, userName, password);
                        UiUtils.launchProducts(LoginActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, TrolleyConstants.sWebUrl, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TAG, REGISTER);
                params.put(USER_NAME, userName);
                params.put(USER_PASSWORD, password);
                params.put(USER_LOCATION, location);
                return params;
            }
        };
        TrolleyApp.getInstance().addToReqQueue(request);
    }

    String getPhoneNumber() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        return number != null ? number : "";
    }
}