package com.example.myapplication;



import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private String channelId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
    }

    private void showNotification(String title, String message) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "MyNotifications")
                .setSmallIcon(R.drawable.ic_launcher_background) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(message)// message for notification
                .setAutoCancel(true); // clear notification after click
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, mBuilder.build());
    }
}
