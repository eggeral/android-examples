package ese.nfc;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

public class TagDiscoveredActivity extends AppCompatActivity {

    private static String TAG = "ese.nfc.tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_discovered);

        // == 02 read tag info
        Log.i(TAG, "onCreate: called");
        Intent intent = getIntent();
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            for (Parcelable message : rawMessages)
                Log.i(TAG, "onCreate: NFC message: " + message);

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.i(TAG, "onCreate: TAG: " + tag.toString());

            byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            Log.i(TAG, "onCreate: TAG ID: " + Arrays.toString(tagId));

            NdefMessage message = (NdefMessage) rawMessages[0];
            NdefRecord record = message.getRecords()[0];
            Log.i(TAG, "onCreate: " + record.toString());
            Log.i(TAG, "onCreate: " + record.toUri());

        }
    }

}
