package ese.broadcast;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//https://developer.android.com/guide/components/broadcasts.html
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 system events. Intents fired by the system on certain events like battery, network, etc.
        // API >=  26: Can not use Manifest declared receivers.
        // Try to use JobScheduler if possible
        // Create TickBroadCastReceiver
        BroadcastReceiver tickBroadCastReceiver = new TickBroadCastReveiver();

        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        this.registerReceiver(tickBroadCastReceiver, filter);

        // adb shell
        // su
        // am broadcast -a android.intent.action.TIME_TICK

        // don't forget to unregister (when registering in onCreate onDestroy is the right place!

        // == 03 send a broadcast
        Button broadcastButton = (Button) findViewById(R.id.sendBroadcastButton);
        broadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // intent.setAction(Intent.ACTION_TIME_TICK); // we are not allowed to send system broadcasts
                // sendBroadcast(intent); send broadcast globally (avoid because of security, anybody can receive that!)
                intent.setAction("ese.broadcast.CLICKED");
                sendBroadcast(intent);
                // If we want to keep our broadcasts local to our app use LocalBroadCastManager to register and send!
                //LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
            }
        });

        BroadcastReceiver clickedBroadCastReceiver = new ClickedBroadCastReveiver();
        filter = new IntentFilter("ese.broadcast.CLICKED");
        registerReceiver(clickedBroadCastReceiver, filter); // this is not receiving broadcasts from LocalBroadCastManager.sendBroadCast
        //LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(clickedBroadCastReceiver, filter);


        // == 04 handling situations where the receiver takes a long time to process using goAsync()
        // see ClickedBroadCastReceiver

    }

    @Override
    protected void onDestroy() {
        // we should unregister our broadcast receivers here!
        super.onDestroy();
    }
}
