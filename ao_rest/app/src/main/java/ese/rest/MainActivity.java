package ese.rest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import android.util.Log;

import com.example.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "ese.rest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 Simple HTTP connections
        // <uses-permission android:name="android.permission.INTERNET" />

//        AsyncTask<String, Integer, Void> httpTask = new AsyncTask<String, Integer, Void>() {
//            @Override
//            protected Void doInBackground(String... params) {
//                try {
//                    URL url = new URL("http://192.168.134.250:8080/rest/echo/hello_world");
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                    try {
//                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                        InputStreamReader reader = new InputStreamReader(in);
//                        char[] buffer = new char[1024];
//                        int read;
//                        StringBuilder body = new StringBuilder();
//                        while ((read = reader.read(buffer)) >= 0) {
//                            body.append(buffer, 0, read);
//                        }
//                        Log.i(TAG, "onCreate: " + body.toString());
//
//                    } finally {
//                        urlConnection.disconnect();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//        httpTask.execute();


        // == 02 working with JSONWriter JSONReader

//        try {
//            StringWriter stringWriter = new StringWriter();
//
//            JsonWriter writer = new JsonWriter(stringWriter);
//            writer.setIndent("  ");
//            writer.beginObject();
//            writer.name("id");
//            writer.value(1234);
//            writer.name("items");
//            writer.beginArray();
//            writer.value("item1");
//            writer.value("item2");
//            writer.value("item3");
//            writer.endArray();
//            writer.endObject();
//            writer.flush();
//            writer.close();
//
//            String jsonString = stringWriter.toString();
//            Log.i(TAG, "onCreate: " + jsonString);
//
//            JsonReader reader = new JsonReader(new StringReader(jsonString));
//            reader.beginObject();
//            Log.i(TAG, "onCreate: nextName: " + reader.nextName());
//            Log.i(TAG, "onCreate: id: " + reader.nextInt());
//            Log.i(TAG, "onCreate: nextName: " + reader.nextName());
//            reader.beginArray();
//            while (reader.hasNext()) {
//                Log.i(TAG, "onCreate: item: " + reader.nextString());
//            }
//            reader.endArray();
//            reader.endObject();
//            reader.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // == 03 working with JSONObject

//        try {
//
//            JSONObject json = new JSONObject();
//            json.put("id", 1234);
//            JSONArray items = new JSONArray();
//            items.put("item1");
//            items.put("item2");
//            items.put("item3");
//            json.put("items", items);
//
//            String jsonString = json.toString(2);
//            Log.i(TAG, "onCreate: " + jsonString);
//
//            JSONObject parsed = new JSONObject(jsonString);
//            Log.i(TAG, "onCreate: id:" + parsed.getInt("id"));
//
//            JSONArray parsedJsonArray = parsed.getJSONArray("items");
//            for (int idx = 0; idx < parsedJsonArray.length(); idx++) {
//                Log.i(TAG, "onCreate: item:" + parsedJsonArray.getString(idx));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        // 04 client server roundtrip using DTOs
        // new Java lib common with a Person DTO
        // Persons Rest service

        // Add a person
//        final Person person = new Person("Max", "Wien");
//
//        AsyncTask<Person, Integer, Void> addPersonTask = new AsyncTask<Person, Integer, Void>() {
//            @Override
//            protected Void doInBackground(Person... params) {
//                try {
//                    URL url = new URL("http://192.168.134.250:8080/rest/persons");
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setRequestProperty("Content-Type", "application/json");
//                    urlConnection.setDoOutput(true);
//                    urlConnection.setRequestMethod("PUT");
//                    try {
//
//                        OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
//                        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(out));
//                        jsonWriter.beginObject();
//                        jsonWriter.name("name");
//                        jsonWriter.value(person.getName());
//                        jsonWriter.name("address");
//                        jsonWriter.value(person.getAddress());
//                        jsonWriter.endObject();
//                        jsonWriter.flush();
//                        jsonWriter.close();
//
//                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                        InputStreamReader reader = new InputStreamReader(in);
//                        char[] buffer = new char[1024];
//                        int read;
//                        StringBuilder body = new StringBuilder();
//                        while ((read = reader.read(buffer)) >= 0) {
//                            body.append(buffer, 0, read);
//                        }
//                        Log.i(TAG, "onCreate: " + body.toString());
//                        reader.close();
//
//                    } finally {
//                        urlConnection.disconnect();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//        addPersonTask.execute();


        AsyncTask<String, Integer, Void> httpTask = new AsyncTask<String, Integer, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    URL url = new URL("http://192.168.134.250:8080/rest/persons");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        JsonReader reader = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));
                        List<Person> persons = new ArrayList<>();
                        reader.beginArray();
                        while (reader.hasNext()) {
                            Person person = new Person();
                            reader.beginObject();
                            reader.nextName();
                            person.setName(reader.nextString());
                            reader.nextName();
                            person.setAddress(reader.nextString());
                            persons.add(person);
                            reader.endObject();
                        }
                        reader.endArray();
                        reader.close();

                        Log.i(TAG, "onCreate: received - " + persons);

                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        httpTask.execute();

        // Exercise shopping list with REST services and UI
    }
}
