package ese.storagewrite;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static String TAG = "ese.storagewrite";

    private static int REQUEST_IMAGE_CAPTURE = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 1 read and write file into internal storage
        // Starting with API version 19 use try with finally
        // No special permissions required
//        Writer writer = null;
//        try {
//            // only MODE_PRIVATE supported with > 7.0
//            // or with MODE_APPEND to append content
//            writer = new OutputStreamWriter(openFileOutput("internal_storage_test.txt", Context.MODE_PRIVATE | Context.MODE_APPEND));
//            writer.write("Hallo World");
//        } catch (IOException e) {
//            // Attention do some real exception handling here
//            Log.e(TAG, "onCreate: Error writing file", e);
//        } finally {
//            if (writer != null)
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    Log.e(TAG, "onCreate: Error closing file writer", e);
//                }
//        }
//
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new InputStreamReader(openFileInput("internal_storage_test.txt")));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Log.i(TAG, "onCreate: " + line);
//            }
//        } catch (IOException e) {
//            // Attention do some real exception handling here
//            Log.e(TAG, "onCreate: Error reading file", e);
//        } finally {
//            if (reader != null)
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    Log.e(TAG, "onCreate: Error closing file reader", e);
//                }
//        }

        // == 02 file list
//        for (String file : fileList()) {
//            Log.i(TAG, "onCreate: " + file);
//        }

        // == 03 delete files
//        deleteFile("internal_storage_test.txt");
//        Log.i(TAG, "onCreate: files found: " + fileList().length);

        // == 04 absolute path of the internal file dir
//        Log.i(TAG, "onCreate: files dir path: " + getFilesDir().getAbsolutePath());
        // adb shell; su 0; ls ......

        // == 05 create or get your own 'files' directory
//        File testDir = getDir("TestDir", MODE_PRIVATE);
//        Log.i(TAG, "onCreate: " + testDir.getAbsolutePath());

        // == 06 read static resource
        // create /res/raw/test.txt
//        BufferedReader resourceReader = null;
//        try {
//            resourceReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.test)));
//            Log.i(TAG, "onCreate: " + resourceReader.readLine());
//        } catch (IOException e) {
//            Log.e(TAG, "onCreate: Error reading resource file", e);
//        } finally {
//            if (resourceReader != null)
//                try {
//                    resourceReader.close();
//                } catch (IOException e) {
//                    Log.e(TAG, "onCreate: Error closing resource file reader", e);
//                }
//        }

        // == 07 cache dir
        // The system my delete files in this dir at any time!
//        File cacheDir = getCacheDir();
//        Log.i(TAG, "onCreate: cache dir - " + cacheDir.getAbsolutePath());
//        writeTextInto("Hello World", cacheDir, "test.txt");

        // == 08 code cache dir (API > 21)
        // Store generated code etc here
        // File codeCacheDir = getCodeCacheDir();

        // == 09 external storage

        // getExternalFilesDir - these dirs get removed if we uninstall the app
//        Log.i(TAG, "onCreate: External storage state: " + (Environment.getExternalStorageState()));
//        Log.i(TAG, "onCreate: ROOT - " + getExternalFilesDir(null)); // null means root!
//        Log.i(TAG, "onCreate: DIRECTORY_PICTURES - " + getExternalFilesDir(Environment.DIRECTORY_PICTURES));
//        Log.i(TAG, "onCreate: DIRECTORY_MUSIC - " + getExternalFilesDir(Environment.DIRECTORY_MUSIC));
//        // no special permission are needed to access these dirs
//        writeTextInto("aaaaa very good song", getExternalFilesDir(Environment.DIRECTORY_MUSIC), "aaa very good song.mp3");


        // public storage
//        Log.i(TAG, "onCreate: public DIRECTORY_PICTURES - " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));

        // Permissions have to be granted in order to access public storage
        //        <manifest ...>
        //            <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        //            ...
        //        </manifest>
        //
        //  Currently all apps have read access but the following should be added because this will change
        //  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            int result = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                // async. Do the action in onRequestPermissionResult!
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 42);
//            }
//            Log.i(TAG, "onCreate: permission granted: " + (result == PackageManager.PERMISSION_GRANTED));
//
//        }

//        writeTextInto("aaaaa very good song",  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "aaa very good song.mp3");

        // Exercise Take a picture and store it into public / private storage.
        // List all images in the folder and show previews
        // see ba_take_pictures
    }


    // == 09 Runtime permissions
    // Activity implement ActivityCompat.OnRequestPermissionsResultCallback
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult: " + requestCode + " results: " + Arrays.toString(grantResults));
    }

    private void writeTextInto(String text, File dir, String fileName) {
        String path = dir.getAbsolutePath() + File.separator + fileName;
        Writer writer = null;
        try {
            writer = new FileWriter(path);
            writer.write(text);
        } catch (IOException e) {
            // Attention do some real exception handling here
            Log.e(TAG, "onCreate: Error writing file: " + path, e);
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    Log.e(TAG, "onCreate: Error closing file writer for: " + path, e);
                }
        }

    }
}

