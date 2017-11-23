package ese.rest;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonReader {

    public Person readPerson(JsonReader reader) throws IOException {
        Person person = new Person();
        reader.beginObject();
        reader.nextName();
        person.setName(reader.nextString());
        reader.nextName();
        person.setAddress(reader.nextString());
        reader.endObject();
        return person;
    }

    public List<Person> readPersons(JsonReader reader) throws IOException {
        List<Person> result = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            result.add(readPerson(reader));
        }
        reader.endArray();

        return result;

    }
}
