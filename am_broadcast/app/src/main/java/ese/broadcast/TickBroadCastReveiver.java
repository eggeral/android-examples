package ese.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class TickBroadCastReveiver extends BroadcastReceiver {
    private static String TAG = "ese.broadcast";
    @Override
    public void onReceive(Context context, Intent intent) {
        // this is on the main tread. So keep it fast!!!
        Log.i(TAG, "onReceive: Tick");
    }
}
