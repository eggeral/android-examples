package ese.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


public class ClickedBroadCastReveiver extends BroadcastReceiver {
    private static String TAG = "ese.broadcast";

    private int receiveCounter = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        // this is on the main tread. So keep it fast!!!
        Log.i(TAG, "onReceive: Clicked");

        // == 04 blocking the UI thread
//        Log.i(TAG, "onReceive: Start work");
//        try {
//            Thread.sleep(20_000); // the application may be doing too much work in its main thread, UI is blocked
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.i(TAG, "onReceive: Done doing work");

        final PendingResult pendingResult = goAsync();// We need more time. Please do not recycle the BroadcastReceiver until we are done.
        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... taskId) {
                Log.i(TAG, "onReceive: Start work");
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(1_000); // the application may be doing too much work in its main thread, UI is blocked
                        Log.i(TAG, "onReceive: work " + taskId[0] + " - " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onReceive: Done doing work");
                pendingResult.finish(); // signal that we are done an the BroadcastReceiver can be recycled
                return null;
            }
        };
        receiveCounter++;
        asyncTask.execute(receiveCounter);

    }
}
