package ese.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ComponentName serviceComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 job scheduler basic implementation
        // Needs API 21 +
        // Background tasks scheduled by the system taking network etc into consideration.

        // <service android:name=".CalculationJobService"
        //   android:permission="android.permission.BIND_JOB_SERVICE"
        //   android:exported="true" />
        serviceComponent = new ComponentName(this, CalculationJobService.class);

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                JobInfo.Builder builder = new JobInfo.Builder(1, serviceComponent);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                builder.setRequiresCharging(true);
                scheduler.schedule(builder.build());
            }
        });

    }

    @Override
    protected void onStop() {
        stopService(new Intent(this, CalculationJobService.class));
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        Intent startServiceIntent = new Intent(this, CalculationJobService.class);
        startService(startServiceIntent);
    }

}
