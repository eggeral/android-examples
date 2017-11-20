package ese.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O) // We only show the newest API here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final NotificationChannel calculationNotificationChannel =
                new NotificationChannel("channel_id", "Calculation", NotificationManager.IMPORTANCE_HIGH);
        calculationNotificationChannel.setDescription("Calculation notifications");
        calculationNotificationChannel.enableLights(true);
        calculationNotificationChannel.setLightColor(Color.RED);
        calculationNotificationChannel.enableVibration(true);
        calculationNotificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(calculationNotificationChannel);

        Button notificationSettingsButton = (Button) findViewById(R.id.notificationSettings);
        notificationSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, calculationNotificationChannel.getId());
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent);

            }
        });

        Button notifyButton = (Button) findViewById(R.id.notify);
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // == 1
//                Notification.Builder notificationBuilder = new Notification.Builder(MainActivity.this, calculationNotificationChannel.getId());
//                notificationBuilder.setSmallIcon(android.R.drawable.stat_notify_chat)
//                        .setContentTitle("Calculation")
//                        .setProgress(100, 0, false)
//                        .setContentText("Calculating the result");
//
//                NotificationManager mNotificationManager =
//                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                mNotificationManager.notify(1, notificationBuilder.build());

                // == 2 setting progress
//                final Notification.Builder notificationBuilder = new Notification.Builder(MainActivity.this, calculationNotificationChannel.getId());
//                notificationBuilder.setSmallIcon(android.R.drawable.stat_notify_chat)
//                        .setContentTitle("Calculation")
//                        .setProgress(100, 0, false)
//                        .setContentText("Calculating the result");
//
//                final NotificationManager notificationManager =
//                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                notificationManager.notify(1, notificationBuilder.build());
//                AsyncTask<Integer, Integer, Integer> calculation = new AsyncTask<Integer, Integer, Integer>() {
//                    @Override
//                    protected Integer doInBackground(Integer... params) {
//
//                        for (int idx = 1; idx <= 100; idx++) {
//                            try {
//                                Thread.sleep(100);
//                                // there is no need to switch to the UI thread in order to update notifications
//                                notificationBuilder.setProgress(100, idx, false);
//                                notificationManager.notify(1, notificationBuilder.build());
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        // == 3 Remove progress bar.
//                        notificationBuilder.setContentText("Calculation complete")
//                                // Removes the progress bar
//                                .setProgress(0,0,false);
//                        notificationManager.notify(1, notificationBuilder.build());
//
//                        return null;
//
//                    }
//
//                };
//
//                calculation.execute(100);

                // == 3 Start an activity when clicking on a notification
                // Add empty show calculation activity
//                Intent showCalculationIntent = new Intent(MainActivity.this, ShowCalculationActivity.class);
//
//                PendingIntent pendingIntent = // wrap the intent so it can be fired by the notification
//                        PendingIntent.getActivity(MainActivity.this, 1, showCalculationIntent, PendingIntent.FLAG_ONE_SHOT);
//                Notification.Builder notificationBuilder =
//                        new Notification.Builder(MainActivity.this, calculationNotificationChannel.getId());
//
//                notificationBuilder.setSmallIcon(android.R.drawable.stat_notify_chat)
//                        .setContentIntent(pendingIntent)
//                        .setContentTitle("Calculation")
//                        .setContentText("Calculating the result");
//
//                NotificationManager mNotificationManager =
//                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                mNotificationManager.notify(1, notificationBuilder.build());


                // == 4 Set the style of the notification

//                Notification.InboxStyle style =
//                        new Notification.InboxStyle();
//                String[] events = {"result 1", "result 2", "result 3", "result 4"};
//                style.setBigContentTitle("Calculation details:");
//                for (String event : events) {
//                    style.addLine(event);
//                }

//                Date now = new Date();
//                Date older = new GregorianCalendar(2017, 6, 1).getTime();
//                Notification.MessagingStyle style = new Notification.MessagingStyle("Me")
//                        .setConversationTitle("Calculation chat")
//                        .addMessage("Finished calculation", now.getTime(), null) // Pass in null for user.
//                        .addMessage("Started calculation", older.getTime(), "Coworker");
//
//                Notification.Builder notificationBuilder =
//                        new Notification.Builder(MainActivity.this, calculationNotificationChannel.getId());
//                notificationBuilder.setSmallIcon(android.R.drawable.stat_notify_chat)
//                        .setStyle(style)
//                        .setContentTitle("Calculation")
//                        .setContentText("Calculating the result");
//
//                NotificationManager mNotificationManager =
//                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                mNotificationManager.notify(1, notificationBuilder.build());


                // == 4 React directly from the notification
                Date now = new Date();
                Date older = new GregorianCalendar(2017, 6, 1).getTime();
                Notification.MessagingStyle style = new Notification.MessagingStyle("Me")
                        .setConversationTitle("Calculation chat")
                        .addMessage("Finished calculation", now.getTime(), null) // Pass in null for user.
                        .addMessage("Started calculation", older.getTime(), "Coworker");


                // Key for the string that's delivered in the action's intent.

                Intent showCalculationIntent = new Intent(MainActivity.this, ShowCalculationActivity.class);
                showCalculationIntent.putExtra("message", "Hello world");
                PendingIntent pendingIntent = // wrap the intent so it can be fired by the notification
                        PendingIntent.getActivity(MainActivity.this, 1, showCalculationIntent, PendingIntent.FLAG_ONE_SHOT);

                Notification.Builder notificationBuilder =
                        new Notification.Builder(MainActivity.this, calculationNotificationChannel.getId());

                Notification.Action showAction = new Notification.Action.Builder(
                        Icon.createWithResource(MainActivity.this, android.R.drawable.ic_dialog_email),
                        "Show",
                        pendingIntent).build();

                notificationBuilder.setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setStyle(style)
                        .addAction(showAction)
                        .setContentTitle("Calculation")
                        .setContentText("Calculating the result");

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(1, notificationBuilder.build());

            }
        });

    }
}
