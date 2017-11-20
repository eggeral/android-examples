package ese.pictures;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ese.pictures";
    private static final int REQUEST_TAKE_PHOTO = 33;

    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takePicture = findViewById(R.id.take_picture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageName = createNewImageName();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        });
    }

    private String createNewImageName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "img_" + timeStamp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.i(TAG, "onActivityResult: Picture taken");
            Bundle extras = data.getExtras();
            Bitmap preview = (Bitmap) extras.get("data"); // this is the preview
            if (preview == null)
                return;

            File pictureDir = new File(getFilesDir(), "pictures");
            if (!pictureDir.exists())
                pictureDir.mkdir();

            Log.i(TAG, "onCreate: dir: " + pictureDir.getAbsolutePath());

            File fileToShare = new File(pictureDir, imageName + "_preview.jpg");

            OutputStream out = null;
            try {

                out = new FileOutputStream(fileToShare);
                preview.compress(Bitmap.CompressFormat.JPEG, 90, out);
                Log.i(TAG, "onActivityResult: Preview written to: " + fileToShare.getAbsolutePath());
            } catch (IOException e) {
                Log.e(TAG, "onCreate: Error writing file", e);
            } finally {
                if (out != null)
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e(TAG, "onCreate: Error closing file writer", e);
                    }
            }


        }
    }
}
