package ese.instrumentedunittests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MasterDataStorageSpec {
    @Test
    public void masterDataStorageCanReadAndWritePersons() throws Exception {

        // given
        Context appContext = InstrumentationRegistry.getTargetContext();
        MasterDataStorage storage = new MasterDataStorage();
        Person person = new Person("Max", "In der Nähe, 82211 Nirgendwo");

        // when
        int id = storage.writePerson(appContext, person);

        // then
        assertThat(storage.readPerson(appContext, id), is(person));

        // and given
        person = new Person("Otto", "Weit Weg, 84234 Hallo");

        // when
        id = storage.writePerson(appContext, person);

        // then
        assertThat(storage.readPerson(appContext, id), is(person));


    }
}
