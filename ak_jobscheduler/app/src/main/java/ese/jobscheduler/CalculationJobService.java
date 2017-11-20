package ese.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

public class CalculationJobService extends JobService {

    private static String TAG = "ese.jobscheduler";

    private CalculationTask task;
    private JobParameters parameters;

    @Override
    public boolean onStartJob(JobParameters params) {

        this.parameters = params; // keep the params for jobFinished
        task = new CalculationTask();
        task.execute(1);
        return true; // we started a task. We are not done jet. We will call jobFinished in order to signal we are done

    }

    @Override
    public boolean onStopJob(JobParameters params) {

        task.cancel(false);
        return true; // please reschedule again once the requirements are fulfilled

    }


    class CalculationTask extends AsyncTask<Integer, Integer, Integer> {

        private int result = 0;

        @Override
        protected Integer doInBackground(Integer... params) {
            Log.i(TAG, "doInBackground: Start");
            int steps = params[0] * 10;
            try {
                for (int idx = 0; idx < steps; idx++) {
                    Thread.sleep(100);
                    if (isCancelled()) // We have to react on cancel
                        return idx;
                    publishProgress(idx + 1, steps);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CalculationJobService.this.jobFinished(parameters, false); // we are done
            return result++;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "onProgressUpdate: " + values[0]);
        }
    }

}
