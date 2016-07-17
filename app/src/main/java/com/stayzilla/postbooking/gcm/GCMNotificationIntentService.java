package com.stayzilla.postbooking.gcm;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.stayzilla.postbooking.MainActivity;
import com.stayzilla.postbooking.R;

public class GCMNotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 1;

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (extras != null && (!extras.isEmpty())) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                // May be required in future
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                // May be required in future
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                // This loop represents the service doing some work.
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {

                    }

                }

                System.out.println("GCM message="+extras.toString());
                sendNotification(extras.getString("title"),extras.getString("message"));

                // GCM notification for chat.
                /*if (extras.getString("cta", "").equalsIgnoreCase("CH")) {
                    // Initiated Chat Service. This will handle the notification.
                    Intent chatIntent = new Intent(this, ChatControllerService.class);
                    chatIntent.putExtra(AppConstants.CHAT_SERVICE_CALL_TYPE, AppConstants.CHAT_INITIATE);
                    startService(chatIntent);

                    // Stop the chat service after few minutes
                    NotificationUtil.setStopChatServiceAlarm(this);

                } else if (extras.getString("cta", "").equalsIgnoreCase("PR")) {
                    // For promotions, show notification with image.
                    NotificationUtil.showPromotionNotification(this, getString(R.string.app_name), extras.getString("text"), extras.getString("text"), extras.getString("image_url"), extras.getString("id"));
                } else {
                    // GCM notification for other screens.
                    sendNotification(extras.getString("id"),extras.getString("text"));
                }*/

            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String id, String msg) {
        NotificationManager mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fromNotification", false);
        intent.putExtra("id", id);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.mipmap.ic_launcher)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.sz_pink_color))
                .setContentTitle(getString(R.string.app_name))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }
}
