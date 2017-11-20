package ese.fileprovider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "ese.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 create a file in internal storage
        File sharedDir = new File(getFilesDir(), "shared");
        if (!sharedDir.exists())
            sharedDir.mkdir();

        Log.i(TAG, "onCreate: dir: " + sharedDir.getAbsolutePath());

        File fileToShare = new File(sharedDir, "hello_world.txt");

        if (!fileToShare.exists()) {
            Writer writer = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(fileToShare));
                writer.write("Hello World");
            } catch (IOException e) {
                Log.e(TAG, "onCreate: Error writing file", e);
            } finally {
                if (writer != null)
                    try {
                        writer.close();
                    } catch (IOException e) {
                        Log.e(TAG, "onCreate: Error closing file writer", e);
                    }
            }
        }

        // == 02 Define FileProvider in manifest
        //  <provider
        //  android:name="android.support.v4.content.FileProvider"
        //  android:authorities="ese.fileprovider"
        //  android:grantUriPermissions="true"
        //  android:exported="false">
        //      <meta-data
        //  android:name="android.support.FILE_PROVIDER_PATHS"
        //  android:resource="@xml/filepaths" />
        //  </provider>

        // == 03 specify dirs to be shared in a filepaths.xml
        //<files-path
        //        name="shared files"
        //        path="shared/" />

        // == 04 Create an URI for the file
        Uri uri = FileProvider.getUriForFile(this, "ese.fileprovider", fileToShare);
        Log.i(TAG, "onCreate: URI: " + uri);

        // == 05 Share the a file

        Intent shareFile = new Intent(Intent.ACTION_SEND);

        shareFile.setType("application/pdf");
        shareFile.putExtra(Intent.EXTRA_STREAM, uri);

        shareFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing " + fileToShare.getName());
        shareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File");

        startActivity(Intent.createChooser(shareFile, "Share File"));

        // Example: Let external Apps choose a file by providing an activity which provides a list of files.
        // Hand over the file to the external app using an URI

    }
}
