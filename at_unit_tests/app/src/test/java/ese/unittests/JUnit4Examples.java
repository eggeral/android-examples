package ese.unittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JUnit4Examples {

    @Before
    public void before() {
        System.out.println("Executed before each test.");
    }

    @After
    public void after() {
        System.out.println("Executed after each test.");

    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Executed before any test method in a class");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Executed after all test methods in a class");
    }

    @Test
    public void basic() {

        // same asserts as in JUnit3
        assertEquals("test", "test");

        // some new ones.
        assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        assertNotEquals(1.0, 1.1, 0.001);

    }

    @Test(expected = IllegalArgumentException.class)
    public void exception() {
        throw new IllegalArgumentException("test");
    }

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void exceptionWithRules() {
        expected.expect(IllegalArgumentException.class);
        throw new IllegalArgumentException("test");
    }

    @Test
    @Ignore
    public void ignoredTest() {
        fail("This test is ignored");
    }

}
