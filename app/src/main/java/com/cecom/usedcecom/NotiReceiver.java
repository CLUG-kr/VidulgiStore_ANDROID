package com.cecom.usedcecom;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class NotiReceiver extends Service {
    NotificationCompat.Builder notificationBuilder;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("비둘기장터", "비둘기장터가 구매자를 기다리는 중입니다.", NotificationManager.IMPORTANCE_MIN);
            channel.setImportance(NotificationManager.IMPORTANCE_MIN);
            channel.setDescription("비둘기장터가 구매자를 기다리는 중입니다.");
            notificationManager.createNotificationChannel(channel);
        }
        notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "비둘기장터")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentTitle("비둘기장터")
                .setContentText("비둘기장터가 구매자를 기다리는 중입니다.")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MIN)
                .setAutoCancel(false);
        startForeground(1379, notificationBuilder.build());

        FirebaseMessaging.getInstance().subscribeToTopic("WantBuy");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FirebaseMessaging.getInstance().unsubscribeFromTopic("WantBuy");
        stopForeground(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // onBind
        return onBind(intent);
    }
}
