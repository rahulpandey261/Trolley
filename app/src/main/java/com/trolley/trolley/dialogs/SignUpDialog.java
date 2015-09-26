package com.trolley.trolley.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trolley.trolley.R;

/**
 * Created by mihir.shah on 9/26/2015.
 */
public class SignUpDialog extends DialogFragment {

    public static final String TAG = SignUpDialog.class.getSimpleName();

    private InfoUpdater mInfoUpdater;

    String mLocation;

    public SignUpDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mInfoUpdater = (InfoUpdater) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mInfoUpdater = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);

        View view = inflater.inflate(R.layout.sign_up__dialog, null);
        final TextView userNameText = (TextView) view.findViewById(R.id.username);
        final TextView passWordText = (TextView) view.findViewById(R.id.password);
        initView(view);

        builder.setTitle(R.string.register).setView(view)
                .setPositiveButton(R.string.positive_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String userName = userNameText.getText().toString();
                        String password = passWordText.getText().toString();
                        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
                            Toast.makeText(getActivity(), R.string.missing_details_signup_signin, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mInfoUpdater.onSignUpRequested(userNameText.getText().toString(), passWordText.getText().toString(), mLocation);
                    }
                });
        return builder.create();
    }

    void initView(View view) {
        Spinner locationChoice = (Spinner) view.findViewById(R.id.location);
        final String[] locationList = getResources().getStringArray(R.array.location_list);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, locationList);
        locationChoice.setAdapter(locationAdapter);
        locationChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLocation = locationList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        locationChoice.setSelection(0);
    }

    public interface InfoUpdater {
        void onSignUpRequested(String userName, String password, String location);
    }
}
