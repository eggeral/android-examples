package ese.rest;


import android.util.JsonWriter;

import java.io.IOException;

public class PersonWriter {

    public void writePerson(Person person, JsonWriter writer) throws IOException {

        writer.beginObject();
        writer.name("name");
        writer.value(person.getName());
        writer.name("address");
        writer.value(person.getAddress());
        writer.endObject();

    }

}
