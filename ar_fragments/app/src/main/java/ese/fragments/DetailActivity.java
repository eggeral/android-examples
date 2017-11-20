package ese.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we don't need this activity.
            finish();
            return;
        }

        // we build the Fragment on our own in order to be able to set the data
        // to be used
        if (savedInstanceState == null) {
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(getIntent().getExtras());

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, detailFragment);
            transaction.commit();
        }
    }
}
