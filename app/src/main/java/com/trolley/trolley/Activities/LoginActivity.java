package com.trolley.trolley.Activities;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.trolley.trolley.Fragments.TrolleyDialogs;
import com.trolley.trolley.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button mSignUp, mSkip;
    ImageButton mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSignIn = (ImageButton) findViewById(R.id.login);
        mSignUp = (Button) findViewById(R.id.sign_up);
        mSkip = (Button) findViewById(R.id.skip);
        mSkip.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:
                Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
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
        TrolleyDialogs trolleyDialogs = new TrolleyDialogs();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, trolleyDialogs).addToBackStack(null).commit();
    }
}
