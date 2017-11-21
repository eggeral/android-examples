package ese.instrumentedunittests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CalculatorSystemTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void aCalculatorCanAddTwoNumbers() {

        // given
        onView(withId(R.id.input_a)).perform(typeText("12.56"));
        onView(withId(R.id.input_b)).perform(typeText("34.12"));

        // when
        onView(withId(R.id.add_button)).perform(click());

        // then
        onView(withId(R.id.result_text)).check(matches(withText("46.68")));
    }

}


