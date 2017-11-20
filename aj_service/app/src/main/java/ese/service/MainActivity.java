package ese.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "ese.service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 General service
        // For things running in background longer than a few seconds (media player etc)
        // Most common case: Extend IntentService (A thread queue is in place).
        // Extend Service  -> Runs on main thread. Implement you own thread handling. Eg. for
        // situations where you have to do multiple things at once.
        // New -> Service (IntentService) -> With helper methods
        // See AndroidManifest.xml to see the service was added there
        // See CalculationService
        // CalculationService.startCalculation(this, 10);
        // The service continues even if the current foreground app is changed, but
        // it is killed if the app is closed.

        // == 02 Foreground service. Notable by the user. Must display status bar icon
        // Foreground services do not get killed in low mem situations
        // request service to run in foreground -> see CalculationService

        // == 03 Bound services
        // Interact with a service (simple example. Complex examples use AIDL ...)
        // API > 21 maybe use JobScheduler
        CalculationService.startCalculation(this, 10);
        // 1. implement onBind in CalculationService
        // Bind the service in onStart - unbind the service in onStop
        // Implement play pause buttons
        Button playButton = (Button) findViewById(R.id.playButton);
        Button pauseButton = (Button) findViewById(R.id.pauseButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calculationService != null)
                    calculationService.play();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calculationService != null)
                    calculationService.pause();
            }
        });

        // == 04 Background services -> do not use any more use JobScheduler
    }

    private CalculationService calculationService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            calculationService = ((CalculationService.CalculationBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            calculationService = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, CalculationService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
