package ese.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ShowCalculationActivity extends AppCompatActivity {

    private static String TAG = "ese.notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_calculation);

        String message = getIntent().getStringExtra("message");
        Log.i(TAG, "onCreate: " + message);

    }
}
