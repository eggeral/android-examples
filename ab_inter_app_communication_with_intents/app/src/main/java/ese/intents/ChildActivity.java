package ese.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static android.app.Activity.RESULT_OK;

public class ChildActivity extends AppCompatActivity {

    private static String TAG = "ese.intents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        // == 02 receive extras
        Log.i(TAG, "got extra. 'message': " + getIntent().getStringExtra("message"));
        Log.i(TAG, "got extra. 'count': " + getIntent().getIntExtra("count", -1));
        Log.i(TAG, "got extra. 'countMessage': " + getIntent().getStringExtra("countMessage"));
        Log.i(TAG, "got extra. '" + Intent.EXTRA_TEXT + "': " + getIntent().getStringExtra(Intent.EXTRA_TEXT));

        // == 03
        Intent intent = new Intent();
        intent.putExtra("result", "Intent result");
        setResult(RESULT_OK, intent);
        finish();
    }
}
