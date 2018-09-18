package com.example.caucse.ddoyak;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    Context context;
    NotificationHelper helper;

    private int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        //NotificationHelper 객체 생성
        helper = new NotificationHelper(context);

        Toast.makeText(context, "복용시간 알림",Toast.LENGTH_SHORT).show();
        //여러 개 알람 생성 시 count를 1씩 증가시키며 pendingIntent 만들기
        try{
            intent = new Intent(context, HomeActivity.class);
            PendingIntent[] pendingIntent = new PendingIntent[100];
            //requestCode도 1씩 증가시켜 각각 다른 알람을 작동시키는 역할
            pendingIntent[count+1] = PendingIntent.getActivity(context,count+1,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent[count+1].send();
            count++;
        }catch (PendingIntent.CanceledException e){
            e.printStackTrace();
            count++;
        }

        //Notification title, context 지정 및 notify
        Notification.Builder builder = helper.getChannelNotification("복용 알림","약을 복용할 시간입니다:-)");
        helper.getManager().notify(1, builder.build());
    }
}