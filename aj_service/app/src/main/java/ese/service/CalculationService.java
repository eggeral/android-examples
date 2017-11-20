package ese.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class CalculationService extends IntentService {
    private static final String ACTION_CALC = "ese.service.action.CALC";

    private static final String EXTRA_SECONDS = "ese.service.extra.SECONDS";

    private static final String TAG = "ese.service";

    public CalculationService() {
        super("CalculationService");
    }


    public static void startCalculation(Context context, int seconds) {


        Intent intent = new Intent(context, CalculationService.class);
        intent.setAction(ACTION_CALC);
        intent.putExtra(EXTRA_SECONDS, seconds);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intent); // only use foreground services for API > 26
        else
            context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CALC.equals(action)) {
                final int seconds = intent.getIntExtra(EXTRA_SECONDS, 0);

                // == 02 request the service to run in foreground and show notification

                String channelId = "calculation_channel";

                Intent notificationIntent = new Intent(this, MainActivity.class); // the intent to be called when the notification is pressed
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

                Notification notification;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Calculation", NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("Calculation in progress");
                    NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.createNotificationChannel(channel);
                    notification = new Notification.Builder(this, channelId)
                            .setContentTitle("Calculation")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentText("Calculation in progress")
                            .setContentIntent(pendingIntent)
                            .setTicker("This is a ticker text")
                            .build();

                } else {
                    notification = new NotificationCompat.Builder(this)
                            .setContentTitle("Calculation")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentText("Calculation in progress")
                            .setContentIntent(pendingIntent)
                            .setTicker("This is a ticker text")
                            .build();

                }

                startForeground(42, notification);
                handleActionCalc(seconds);
                stopForeground(true);
            }
        }
    }

    private void handleActionCalc(int seconds) {
        try {
            for (int idx = 0; idx < seconds; idx++) {
                Thread.sleep(1000);
                Log.i(TAG, "handleActionCalc: " + idx);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // == 03 Bound services

    public class CalculationBinder extends Binder {
        CalculationService getService() {
            return CalculationService.this; // return the service instance so we can talk to it.
        }
    }

    private Binder calculationBinder = new CalculationBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return calculationBinder;
    }

    public void play() {
        Log.i(TAG, "play: Called");
    }

    public void pause() {
        Log.i(TAG, "pause: Called");
    }

}
