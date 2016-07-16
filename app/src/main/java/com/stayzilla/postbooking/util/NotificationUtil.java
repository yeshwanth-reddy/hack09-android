package com.stayzilla.postbooking.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.stayzilla.postbooking.MainActivity;
import com.stayzilla.postbooking.R;

/**
 * Created by sasikumar on 16/07/16.
 */
public class NotificationUtil {

    public static void showNotification(Context context, String contentTitle, String contentText) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentText(contentText);

        /*if (result != null) {
            NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle(mBuilder);
            bigPicStyle.bigPicture(result);
            bigPicStyle.setSummaryText(bigContentTitle);
            notificationManager.notify(1, bigPicStyle.build());
        } else {*/
            notificationManager.notify(1, mBuilder.build());
//        }

    }

}
