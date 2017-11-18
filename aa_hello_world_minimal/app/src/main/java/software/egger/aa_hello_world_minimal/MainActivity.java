package software.egger.aa_hello_world_minimal;

import android.app.Activity;
import android.os.Bundle;

// This program has only one activity (single focused thing a user can do)
// Just shows the label "Hello World"
// see also AndroidManifest.xml
// see also res/layout/activity_main.xml
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use res/layout/activity_main.xml to build the view
        setContentView(R.layout.activity_main);
    }
}
