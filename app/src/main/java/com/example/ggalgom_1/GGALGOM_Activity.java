package com.example.ggalgom_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

public class GGALGOM_Activity extends AppCompatActivity {

    /*
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setContentTitle("알림 제목");
    builder.setContentText("알람 세부 텍스트");
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggalgom_);
    }

    /*
        // 알림 표시
    NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

    // id값은
    // 정의해야하는 각 알림의 고유한 int값
    notificationManager.notify(1, builder.build());

     */
}
