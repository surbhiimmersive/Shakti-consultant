package com.shakticonsultant.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.shakticonsultant.AppliedJobActivity;
import com.shakticonsultant.MainActivity;
import com.shakticonsultant.NotificationActivity;
import com.shakticonsultant.R;
import com.shakticonsultant.RejectedApplicationActivity;
import com.shakticonsultant.ScheduleInterviewActivity;
import com.shakticonsultant.SignInActivity;
import com.shakticonsultant.utils.AppPrefrences;

import java.util.Map;

public class FirebaseMsg extends FirebaseMessagingService {

    private static final String TAG = "AbleFirebaseService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be:
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            Map<String, String> data = remoteMessage.getData();

            String notify_type = data.get("notify_type").toString();
            Log.d(TAG, "Message Notification Body: " + notify_type);
            // String notify_type = remoteMessage.getData().get("notify_type");
            // String notify_type = remoteMessage.getData().get("notify_type");


            if (notify_type.equals("2")) {
                String text = remoteMessage.getNotification().getBody();
                // text=text.replaceAll("\n","\n");
                //  String[] split = text.split("\\\\n");
                //String[] separated = text.split("\n");
                // this will contain "How are you?"
                text = text.replace("\\n", "\n"); // The first backslash is doubled to find actual backslashes

                AppliedJobNotification(text);


            } else if (notify_type.equals("4")) {
                String text = remoteMessage.getNotification().getBody();
                text = text.replace("\\n", "\n"); // The first backslash is doubled to find actual backslashes

                RejectedNotification(text);


            } else if (notify_type.equals("5")) {
                String text = remoteMessage.getNotification().getBody();
                text = text.replace("\\n", "\n"); // The first backslash is doubled to find actual backslashes

                ScheduleInterviewNotification(text);

            } else if (notify_type.equals("9")) {
                String text = remoteMessage.getNotification().getBody();
                text = text.replace("\\n", "\n"); // The first backslash is doubled to find actual backslashes

                LogoutUserNotofocation(text);
            } else {
                String text = remoteMessage.getNotification().getBody();
                text = text.replace("\\n", "\n"); // The first backslash is doubled to find actual backslashes

                sendNotificationType1(text);
            }

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */


    private void sendNotificationType1(String messageBody) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        // PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

      /*  PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, NotificationActivity.class), 0);*/

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.shakti_consultant_logo)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void RejectedNotification(String messageBody) {
        Intent intent = new Intent(this, RejectedApplicationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        // PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

      /*  PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, NotificationActivity.class), 0);*/
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.shakti_consultant_logo)
                        .setContentTitle("Application Rejected ")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

    private void ScheduleInterviewNotification(String messageBody) {
        Intent intent = new Intent(this, ScheduleInterviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        // PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

      /*  PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, NotificationActivity.class), 0);*/
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.shakti_consultant_logo)
                        .setContentTitle("Interview Scheduled")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

    private void LogoutUserNotofocation(String messageBody) {

        AppPrefrences.setLogin_status(this, false);

       /*  AppPrefrences.setLogin_status(this, false);
        Intent i = new Intent(this, SignInActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
       */

        Intent intent = new Intent(this, SignInActivity.class);

        // Intent intent = new Intent(this, ScheduleInterviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);

        // PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

      /*  PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, NotificationActivity.class), 0);*/

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.shakti_consultant_logo)
                        .setContentTitle("New Device Login")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        startActivity(intent);
    }

    private void AppliedJobNotification(String messageBody) {
        Intent intent = new Intent(this, AppliedJobActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        // PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

      /*  PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, NotificationActivity.class), 0);*/
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.shakti_consultant_logo)
                        //.setContentTitle(getString(R.string.app_name))
                        .setContentTitle("Job Applied")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

    private void sendNotificationType2(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        // PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

      /*  PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, NotificationActivity.class), 0);*/
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.shakti_consultant_logo)
                        // .setContentTitle(getString(R.string.app_name))
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}