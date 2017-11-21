package ese.unittests;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SystemTest {
    @Test
    public void aUserCanAddTwoNumbers() throws IOException {
        // given
        try (ByteArrayInputStream bis = new ByteArrayInputStream("1\n2\n".getBytes());
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            // when
            Main.launch(bis, bos);

            // then
            assertThat(bos.toString(), is("Result: 3\n"));
        }
    }
}
