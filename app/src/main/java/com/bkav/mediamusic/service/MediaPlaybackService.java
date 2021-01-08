package com.bkav.mediamusic.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bkav.mediamusic.R;
import com.bkav.mediamusic.view.fragment.MediaPlaybackFragment;

import java.io.File;
import java.io.IOException;

public class MediaPlaybackService extends Service {
    private String CHANNEL_ID = "";
    private MediaPlayer mp = new MediaPlayer();
    private NotificationManager mNotificationManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String path = intent.getStringExtra("path");
        try {
            mp.reset();
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showNotification(path);
        return START_NOT_STICKY;
    }

    private void showNotification(String path) {
        Intent notificationIntent = new Intent(this, MediaPlaybackFragment.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("path", path);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            CHANNEL_ID = createNotificationChannel("my_notification","mediaplaybackservice");
        }
        else {
            CHANNEL_ID = "";
        }
        RemoteViews mCustomNotification =  new RemoteViews(getPackageName(), R.layout.playmusic_notification);
        RemoteViews mCusomNotificationBigSize = new RemoteViews(getPackageName(),R.layout.playmusicbigsize_notification);
        @SuppressLint("WrongConstant") Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Playing")
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
//                .setCustomContentView(mCustomNotification)
//                .setCustomBigContentView(mCusomNotificationBigSize)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        startForeground(123, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String createNotificationChannel(String CHANNEL_ID, String CHANNEL_NAME) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(channel);
        return CHANNEL_ID;
    }
}
