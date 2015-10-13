package com.trolley.trolley.utils;

import android.app.Activity;
import android.content.Intent;

import com.trolley.trolley.Activities.LoginActivity;
import com.trolley.trolley.Activities.ProductActivity;

/**
 * Created by mihir.shah on 10/13/2015.
 */
public class UiUtils {

    public static void launchProducts(Activity activity) {
        Intent intent = new Intent(activity, ProductActivity.class);
        activity.startActivity(intent);
    }

    public static void launchRegistration(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
}
