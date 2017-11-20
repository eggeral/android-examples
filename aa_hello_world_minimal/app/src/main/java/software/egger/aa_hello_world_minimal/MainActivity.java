package software.egger.aa_hello_world_minimal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// This program has only one activity (single focused thing a user can do)
// see also AndroidManifest.xml
// see also res/layout/activity_main.xml
public class MainActivity extends AppCompatActivity { // AppCompatActivity is the default

    private static String TAG = "ese.helloworld";
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // == 01 Use res/layout/activity_main.xml to build the view
        setContentView(R.layout.activity_main);

        // == 02 Add a count button which increments a counter
        // Show the current count in the text
        Button button = (Button) findViewById(R.id.count_button);
        final TextView counterText = (TextView) findViewById(R.id.counter_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                counterText.setText("Count is: " + counter);
            }
        });

        // == 03 show Activity lifecycle
        //onCreate(), onStart(), onResume(), onRestart(), onPause(), onStop(), and onDestroy().
        Log.i(TAG, "onCreate: ");
        // Launching the activity  -> onCreate, onStart, onResume
        // Rotating the activity -> onStop, onDestroy, onCreate, onResume
        // Changing to the home screen -> onPause, onStop
        // Coming back from the home screen -> onRestart, onStart, onResume
        // Kill the activity -> onPause, onStart, onDestroy

        // == 04 Saving the value of the counter
        // override onSaveInstanceState to save the state
        // override onRestoreInstanceState to restore the state
        // or restore the state directly in onCreate
        //
        // Careful! The state is stored until the Activity is destroyed!


        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter");
            counterText.setText("Count is: " + counter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState); // The views of the activities may save their state as well.

        outState.putInt("counter", counter);

    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        Log.i(TAG, "onRestoreInstanceState: ");
//        super.onRestoreInstanceState(savedInstanceState);
//
//        counter = savedInstanceState.getInt("counter");
//        TextView textView = (TextView) findViewById(R.id.counter_text);
//        textView.setText("Count is: " + counter);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart:");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
