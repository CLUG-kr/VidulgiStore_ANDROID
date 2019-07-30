package com.cecom.usedcecom;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Locale;

public class FirebaseInstanceIDService extends FirebaseMessagingService {
    private final String dbName = "notifications";
    private final String tableName = "notireceived";

    SQLiteDatabase sampleDB = null;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage);

        try {
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (seller VARCHAR(20), item VARCHAR(20) );");
            sampleDB.execSQL("INSERT INTO " + tableName + " Values ('" + remoteMessage.getData().get("seller") + "', '" + remoteMessage.getData().get("item") + "');");
            sampleDB.close();
        } catch (SQLiteException se) {
            Log.e("SQL WRITE ERROR", se.getMessage());
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String seller = remoteMessage.getData().get("seller");
        String item = remoteMessage.getData().get("item");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("비둘기장터 구매알림", "비둘기장터 구매알림", NotificationManager.IMPORTANCE_MIN);
            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("비둘기장터 구매알림");
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "비둘기장터 구매알림")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentTitle("비둘기장터")
                .setContentText(String.format(Locale.getDefault(), "%s님이 %s을(를) 구매하고 싶어합니다.", seller, item))
                .setOngoing(false)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setAutoCancel(false)
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}