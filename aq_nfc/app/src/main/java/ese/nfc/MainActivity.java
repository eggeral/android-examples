package ese.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import static android.R.attr.data;
import static android.R.attr.tag;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ese.nfc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 register for NFC events
        // <uses-permission android:name="android.permission.NFC" />
        //        <activity android:name=".TagDiscoveredActivity">
        //            <intent-filter>
        //                <action android:name="android.nfc.action.NDEF_DISCOVERED"/>
        //                <category android:name="android.intent.category.DEFAULT"/>
        //                <data android:scheme="geo" />
        //            </intent-filter>
        //        </activity>
        // ... TECH_DISCOVERED and TAG_DISCOVERED see AndroidManifest.xml

        // == 02 get info from the TAG see TagDiscoveredActivity

        // == 03 foreground dispatch system (Declare this activity as top priority)
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    private IntentFilter[] intentFilters = new IntentFilter[]{
            new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
            new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
            new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
    };

    private String[][] techLists = new String[][]{new String[]{NfcA.class.getName()}};

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists);
    }

    public void onNewIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Log.i(TAG, "onNewIntent: " + tagFromIntent);

        // == 04 Write NFC Tag
        NdefRecord geoRecord = new NdefRecord(
                NdefRecord.TNF_ABSOLUTE_URI,
                "geo://test-test-test-test".getBytes(Charset.forName("US-ASCII")),
                new byte[0], new byte[0]);

        NdefMessage message = new NdefMessage(geoRecord);

        Ndef ndef = Ndef.get(tagFromIntent);
        try {
            ndef.connect();
            ndef.writeNdefMessage(message);
            Log.i(TAG, "onNewIntent: Tag written");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }

    }

}
