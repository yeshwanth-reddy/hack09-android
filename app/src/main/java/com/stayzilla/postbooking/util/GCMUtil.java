package com.stayzilla.postbooking.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by sasikumar on 17/07/16.
 */
public class GCMUtil {

    public static void registerWithGCMServerInBackground(final Context context) {
        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object[] objects) {
                String msg = "";

                try {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

                    String regId = gcm.register("1042735674850");

                    System.out.println("GCM regId="+regId);

                    SharedPreferences sharedPref = context.getSharedPreferences(
                            "SZ_PREF", Context.MODE_PRIVATE);

                    sharedPref.edit().putString("GCM_DEVICE_TOKEN", regId).commit();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                super.onPostExecute(msg);

            }

        }.execute(null, null, null);
    }

}
