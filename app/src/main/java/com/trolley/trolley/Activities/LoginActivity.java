package com.trolley.trolley.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trolley.trolley.Application.TrolleyApp;
import com.trolley.trolley.Constants.TrolleyConstants;
import com.trolley.trolley.Fragments.TrolleyDialogs;
import com.trolley.trolley.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TrolleyDialogs.TrolleyDialogsListener {
    Button mSignUp, mSkip;
    ImageButton mSignIn;
    boolean islogin = false;
    private Toolbar toolbar;
    private TextInputLayout inputUsername;
    private TextInputLayout inputPassword;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        //toolbar.setLogo(android.R.drawable.ic_menu_close_clear_cancel);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);

        mSignIn = (ImageButton) findViewById(R.id.login);
        mSignUp = (Button) findViewById(R.id.sign_up);
        mSkip = (Button) findViewById(R.id.skip);

        inputUsername = (TextInputLayout) findViewById(R.id.usernameWrapper);
        inputPassword = (TextInputLayout) findViewById(R.id.passwordWrapper);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mSkip.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:
               // Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                String username = inputUsername.getEditText().getText().toString();
                String password = inputPassword.getEditText().getText().toString();
                // Check for empty data in the form
                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    // login user
                    checkLogin(username, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.sign_up:
                Toast.makeText(LoginActivity.this, "sign up", Toast.LENGTH_SHORT).show();
                showSignUpDialog();
                break;
            case R.id.skip:
                Toast.makeText(LoginActivity.this, "Skip", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    private void showSignUpDialog() {
        DialogFragment newFragment = new TrolleyDialogs();
        newFragment.show(getSupportFragmentManager(), "login");

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Dialog dialogView = dialog.getDialog();
        TextInputLayout textInputLayout = (TextInputLayout) dialogView.findViewById(R.id.usernameWrapper);
        Toast.makeText(LoginActivity.this, textInputLayout.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
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
    private void checkLogin(final String username, final String password) {
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, TrolleyConstants.sWebUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(LoginActivity.this, jObj.getString("user_location"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, jObj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Rahul", "Login Error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("user_name", username);
                params.put("user_password", password);
                return params;
            }
        };
        TrolleyApp.getInstance().addToReqQueue(strReq);
    }
}