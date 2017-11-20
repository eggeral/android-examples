package ese.intents;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

// Intents are used to request action from another app component
// (activity, services, broadcast receivers, content providers)
// https://developer.android.com/guide/

public class MainActivity extends AppCompatActivity {// AppCompatActivity in order to have consistent UX for the app bar -> https://developer.android.com/training/appbar/setting-up.html
    private static final int REQUEST_SELECT_CONTACT = 2;

    private static String TAG = "ese.intents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 start another activity
        //        Intent showChildActivity = new Intent(this, ChildActivity.class);
        //        startActivity(showChildActivity);

        // == 02 pass some data to the activity
                Intent showChildActivity = new Intent(this, ChildActivity.class); // implicit Intent. We specify the class of the receiver
                // showChildActivity.setData(...)  // this sets an URI to the data that should be processed. We are not using that here. -> Later

                showChildActivity.putExtra("message", "Hello world"); // Put in extras one by one

                Bundle extras = new Bundle(); // Bundle extras
                extras.putInt("count", 5);
                extras.putString("countMessage", "Number of items");
                showChildActivity.putExtras(extras);

                // see ChildActivity [== 02] on how to receive data

                startActivity(showChildActivity);

        // == 03 get data back from an activity
        / /        Intent showChildActivity = new Intent(this, ChildActivity.class);
        //        showChildActivity.putExtra("message", "Hello world");
        //        startActivityForResult(showChildActivity, 1); // 1 .. request code which we will get back to identify the request
        // add onActivityResult
        // add returning the result to ChildActivity

        // == 04 Intents can be used to start services. But since Android 5.0 there are better ways to do that. -> Later
        // == 05 Intents can be used to deliver broadcasts (out of scope here)

        // == 06 start another activity using implicit Intents
        // Intent sendMessage = new Intent();
        //        sendMessage.setAction(Intent.ACTION_SEND); // Intent.ACTION_SEND we want to send something
        //        sendMessage.putExtra(Intent.EXTRA_TEXT, "Hello World"); // INTENT.EXTRA_TEXT standard extra name for text
        //        sendMessage.setType("text/plain");
        //
        //        OR ...
        //        sendMessage.setAction(Intent.ACTION_DIAL);
        //        sendMessage.setData(Uri.parse("tel:123456"));
        //
        //        if (sendMessage.resolveActivity(getPackageManager()) != null) {
        //            // always check if there is an activity available in the system.
        //            // Package manager holds information about the apps installed on the device
        //            startActivity(sendMessage);
        //        } else {  // else show some info.
        //            Log.w(TAG, "onCreate: No activity is registered for this intent");
        //        }

        // == 07 Use an app chooser
        //        Intent sendMessage = new Intent();
        //        sendMessage.setAction(Intent.ACTION_SEND);
        //        sendMessage.putExtra(Intent.EXTRA_TEXT, "Hello World");
        //        sendMessage.setType("text/plain");
        //
        //        Intent appChooser = Intent.createChooser(sendMessage, "Send this message with");
        //        if (sendMessage.resolveActivity(getPackageManager()) != null) {
        //            startActivity(appChooser);
        //    } else {  // else show some info.
        //        Log.w(TAG, "onCreate: No activity is registered for this intent" );
        //    }

        // 08 Register activity to react on implicit intents
        // Add intent filter in AndroidManifest.xml
        // see <!-- 08 .... --> there
        //        Intent sendMessage = new Intent();
        //        sendMessage.setAction(Intent.ACTION_SEND); // Intent.ACTION_SEND we want to send something. ACTION_VIEW we want to show something to the user
        //        sendMessage.putExtra(Intent.EXTRA_TEXT, "Hello World"); // INTENT.EXTRA_TEXT standard extra name for text
        //        sendMessage.setType("text/plain");
        //
        //        if (sendMessage.resolveActivity(getPackageManager()) != null) {
        //            startActivity(sendMessage);
        //        } else {  // else show some info.
        //            Log.w(TAG, "onCreate: No activity is registered for this intent");
        //        }


        // 09 Common intents
        // List of common intents
        // Example set an alarm
        // Example take a picture
        // Example play music
        // Example create a note
        // Example select a contact
        // Example search the web
        // Example open bluetooth settings
        // Example open web browser
        // Example select a contact, create calendar entry with the contacts email as invited people
        // https://developer.android.com/guide/components/intents-common.html
//        Intent selectContact = new Intent(Intent.ACTION_PICK);
//        selectContact.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
//
//        if (selectContact.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(selectContact, REQUEST_SELECT_CONTACT);
//        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // == 03
        if (requestCode == 1) { // the requestCode we sent. We not get it back
            if (resultCode == RESULT_OK) { // only ok
                String result = data.getExtras().getString("result");
                Log.i(TAG, "onActivityResult: " + result);
            }
        }
        // == 09
        else if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {

            Uri contactUri = data.getData();
            Log.i(TAG, "onActivityResult: " + contactUri);

            String[] projection = new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {

                int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
                String email = cursor.getString(emailIndex);
                Log.i(TAG, "onActivityResult: Email:" + email);

                Intent createCalendarEntry = new Intent(Intent.ACTION_INSERT);
                createCalendarEntry.setData(CalendarContract.Events.CONTENT_URI);
                createCalendarEntry.putExtra(CalendarContract.Events.TITLE, "Neue Besprechung");
                createCalendarEntry.putExtra(Intent.EXTRA_EMAIL, email);
                startActivity(createCalendarEntry);

            }

        }
    }
}
