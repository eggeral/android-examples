package ese.fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements OverviewFragment.Callback {

    private static String TAG = "ese.fragments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // There are basically two possibilities when working with fragments and two panes.
        // When working in one pane mode we can use 2 activities or we use one activity
        // for both: 1 and 2 pane mode
        // This example shows the first approach
        // TODO show the 1 activity approach.

        // == 01 different layouts for portrait an landscape
        // add new layout file with qualifier landscape
        // rotate the simulator to show how the layout is switched

        // == 02 create a single fragment
        // New blank fragment OverviewFragment
        // add fragment to main_activity in portrait mode


        // == 03 create a second fragment and add both to
        // landscape


        // == 04 react on a button pressed in overview
        // Define Callback interface in OverviewFragment
        // Connect callback in OverviewFragment.onAttach
        // Add "Show Book" button an connect in OnCreateView()

    }

    @Override
    public void onShowBook(String title) {
        Log.i(TAG, "onShowBook: " + title);

        // == 05 update details when a button is pressed in overview
        // Fragments always communicate over an activity never between each other!


        DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detail_fragment);

        if (detailFragment != null && detailFragment.getView() != null) {
            detailFragment.showBook(title);
        } else {
            Log.i(TAG, "onShowBook: Details view currently not shown");
            // == 06 Start DetailActivity
            // Put title into intent extra

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("title", title);

            startActivity(intent);

        }

    }
}
