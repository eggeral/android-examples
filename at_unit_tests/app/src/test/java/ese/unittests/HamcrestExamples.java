package ese.unittests;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class HamcrestExamples {

    @Test
    public void hamcrest() {
        assertThat(true, is(true));
        assertThat(false, is(not(true)));
        assertThat("test", is(equalTo("test")));
        assertThat(true,
                describedAs("Failed because matcher did not match", is(true)));
        assertThat(false, is(anything()));

        assertThat(2, is(anyOf(equalTo(2), equalTo(1), equalTo(3))));

        assertThat(new String("test"), instanceOf(String.class));

        assertThat("test", is(notNullValue()));
        assertThat(null, is(nullValue()));

        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 3);

        assertThat(list, is(list2));
        assertThat(list, hasItem(1));
        assertThat(list, hasItems(1, 3));

        Student student = new Student("Max");
        Student student2 = student;
        assertThat(student2, is(sameInstance(student)));
    }


}
