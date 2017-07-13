package com.scionous.samplepushnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 *
 * Created by Mukesh on 06-Jul-17.
 */

public class MessagingService extends FirebaseMessagingService {

    private final String TAG = getClass().getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        final String nTitle = remoteMessage.getNotification().getTitle();
        final String nMessage = remoteMessage.getNotification().getBody();
        Log.d(TAG, "New: " + nMessage);
        sendNotification(nTitle, nMessage);
    }


    private void sendNotification(String nTitle, String nMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", nTitle);
        intent.putExtra("message", nMessage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(nMessage)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());

    }


}
