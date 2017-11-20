package ese.threads;

import android.Manifest;
import android.app.job.JobInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "ese.threads";
    private int result = 0;

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 Doing things in the main thread which take > 5s causes an Application Not Responding Dialog (sometimes)
        final Button startCalculationButton = (Button) findViewById(R.id.startCalculationButton);
        final TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

//        startCalculationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                resultTextView.setText("Calculating ...."); // we are on the UI thread!! We never see this
//                int result = calculateSomething(10); // UI is blocked
//                resultTextView.setText("Result is: " + result);
//
//            }
//        });


        // == 02 Performing long running tasks in a new thread
//        startCalculationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //resultTextView.setText("Calculating ..."); // Only the original thread that created a view hierarchy can touch its views.
//                        Log.i(TAG, "onClick: Calculating ...");
//                        int result = calculateSomething(3);
//                        Log.i(TAG, "onClick: Result is: " + result);
//
//                    }
//                });
//                thread.start();
//
//            }
//        });

        // == 03 Changes on the UI have to be done on the UI thread
//        startCalculationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                resultTextView.setText("Calculating ...");
//                            }
//                        });
//                        Log.i(TAG, "onClick: Calculating ...");
//                        final int result = calculateSomething(3);
//                        Log.i(TAG, "onClick: Result is: " + result);
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                resultTextView.setText("Result is: " + result);
//                            }
//                        });
//
//                    }
//                });
//                thread.start();
//
//            }
//        });


        // == 04 AsyncTask helps to implement complex threading situations
//        class CalculationTask extends AsyncTask<Integer, Integer, Integer> {
//
//            @Override
//            protected Integer doInBackground(Integer... params) {
//                return calculateSomething(params[0]);
//            }
//
//            @Override
//            protected void onPreExecute() {
//                resultTextView.setText("Calculating ...");
//            }
//
//            @Override
//            protected void onPostExecute(Integer integer) {
//                resultTextView.setText("Result is: " + result);
//            }
//        }
//
//        startCalculationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CalculationTask task = new CalculationTask();
//                task.execute(3); // tasks can only be executed once!
//
//            }
//        });

        // == 05 Use AsyncTask to notify the user about the progress of the calculation
//        class CalculationTask extends AsyncTask<Integer, Integer, Integer> {
//
//            @Override
//            protected Integer doInBackground(Integer... params) {
//                int steps = params[0] * 10;
//                try {
//                    for (int idx = 0; idx < steps; idx++) {
//                        Thread.sleep(100);
//                        publishProgress(idx + 1, steps);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return result++;
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                resultTextView.setText("Calculating (" + values[0] + "/" + values[1] + ")");
//            }
//
//            @Override
//            protected void onPreExecute() {
//                resultTextView.setText("Calculating ...");
//            }
//
//            @Override
//            protected void onPostExecute(Integer integer) {
//                resultTextView.setText("Result is: " + result);
//            }
//        }
//
//
//        startCalculationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CalculationTask task = new CalculationTask();
//                task.execute(3);
//
//            }
//        });

        // == 06 AsyncTasks can be canceled
//        class CalculationTask extends AsyncTask<Integer, Integer, Integer> {
//            @Override
//            protected Integer doInBackground(Integer... params) {
//                int steps = params[0] * 10;
//                try {
//                    for (int idx = 0; idx < steps; idx++) {
//                        Thread.sleep(100);
//                        if (isCancelled()) // We have to react on cancel
//                            return idx;
//                        publishProgress(idx + 1, steps);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return result++;
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                resultTextView.setText("Calculating (" + values[0] + "/" + values[1] + ")");
//            }
//
//            @Override
//            protected void onPreExecute() {
//                resultTextView.setText("Calculating ...");
//            }
//
//            @Override
//            protected void onPostExecute(Integer integer) {
//                resultTextView.setText("Result is: " + integer);
//            }
//
//            @Override
//            protected void onCancelled(Integer integer) {
//                resultTextView.setText("Cancelled - steps calculated " + integer);
//            }
//        }
//
//        final CalculationTask task = new CalculationTask();
//
//        startCalculationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (task.getStatus() == AsyncTask.Status.PENDING) {
//                    task.execute(10);
//                    startCalculationButton.setText("Stop Calculation");
//                } else if (task.getStatus() == AsyncTask.Status.RUNNING) {
//                    task.cancel(false); // true if the Thread should be interrupted.}
//                }
//            }
//        });


        // 07 -- Always remember: race conditions!

        final Object lock = new Object();

        Runnable worker = new Runnable() {
            @Override
            public void run() {
                for (int idx = 0; idx < 100_000; idx++)
                    //synchronized (lock) {
                    counter++;
                //}
            }
        };

        Thread thread1 = new Thread(worker);
        Thread thread2 = new Thread(worker);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "onCreate: " + counter);

        // Example sum up the file sizes of the external storage
        // add     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
        // Request runtime permission

//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE))) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1234);
//            }
//        }
//
//        class FileWalkerTask extends AsyncTask<File, Integer, Double> {
//
//            @Override
//            protected Double doInBackground(File... params) {
//                LinkedList<File> dirsToCheck = new LinkedList<>();
//                dirsToCheck.push(params[0]);
//
//                double currentSize = 0;
//                while (dirsToCheck.size() > 0) {
//                    File currentDir = dirsToCheck.pop();
//                    for (File file : currentDir.listFiles()) {
//
//                        Log.i(TAG, "doInBackground: " + file.getAbsolutePath());
//
//                        if (isCancelled())
//                            return currentSize;
//
//                        if (file.isDirectory())
//                            dirsToCheck.push(file);
//                        else
//                            currentSize += ((double) file.length()) / 1024.0 / 1024.0;
//
//                        publishProgress(dirsToCheck.size());
//
//                    }
//                }
//                return currentSize;
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                resultTextView.setText("Directories to check: " + values[0]);
//            }
//
//            @Override
//            protected void onPreExecute() {
//                resultTextView.setText("Started");
//            }
//
//            @Override
//            protected void onPostExecute(Double result) {
//                resultTextView.setText("Result is: " + result);
//            }
//
//            @Override
//            protected void onCancelled(Double result) {
//                resultTextView.setText("Cancelled - result until now: " + result);
//            }
//        }
//
//        final FileWalkerTask task = new FileWalkerTask();
//
//        startCalculationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (task.getStatus() == AsyncTask.Status.PENDING) {
//                    task.execute(Environment.getExternalStorageDirectory());
//                    startCalculationButton.setText("Stop");
//                } else if (task.getStatus() == AsyncTask.Status.RUNNING) {
//                    task.cancel(false); // true if the Thread should be interrupted.}
//                }
//            }
//        });
//

    }


    private int calculateSomething(int seconds) {
        try {
            for (int idx = 0; idx < seconds; idx++) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result++;
    }
}
