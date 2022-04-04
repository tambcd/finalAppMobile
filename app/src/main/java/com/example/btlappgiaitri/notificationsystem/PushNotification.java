package com.example.btlappgiaitri.notificationsystem;//package com.example.appgiaitrimobile.notificationsystem;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.os.Build;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.example.appgiaitrimobile.R;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//public class PushNotification extends FirebaseMessagingService {
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage message) {
//        String title =  message.getNotification().getTitle();
//        String text = message.getNotification().getBody();
//        final String CHANNEL = "TIK_TOK";
//        NotificationChannel channel = new NotificationChannel( CHANNEL,"Heads Notifycation ", NotificationManager.IMPORTANCE_HIGH   );
//        getSystemService(NotificationManager.class).createNotificationChannel(channel);
//        Notification.Builder builder = new Notification.Builder(this,CHANNEL)
//                .setContentTitle(title)
//                .setContentText(text)
//                .setSmallIcon(R.drawable.gcm_icon)
//                .setAutoCancel(true);
//
//        NotificationManagerCompat.from(this).notify(1,builder.build());
//
//        super.onMessageReceived(message);
//    }
//}
