package ese.instrumentedunittests;


import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MasterDataStorage {


    public int writePerson(Context context, Person person) throws IOException {
        SharedPreferences prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        int id = prefs.getInt("nextId", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("nextId", id++);
        editor.commit();

        try (OutputStreamWriter writer = new OutputStreamWriter(
                context.openFileOutput("person" + id, Context.MODE_PRIVATE))) {
            writer.write(person.getName());
            writer.write("\n");
            writer.write(person.getAddress());
            writer.write("\n");
        }

        return id;
    }

    public Person readPerson(Context context, int id) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.openFileInput("person" + id)))) {
            Person person = new Person();
            person.setName(reader.readLine());
            person.setAddress(reader.readLine());
            return person;
        }
    }
}
