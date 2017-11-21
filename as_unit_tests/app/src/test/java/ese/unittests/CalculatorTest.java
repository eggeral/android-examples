package ese.unittests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorTest {

    @Test
    public void aCalculatorIsAbleToSumUpTwoNumbers() {
        // given
        Calculator calc = new Calculator(1, 3);

        // when
        int sum = calc.sum();

        // then
        assertThat(sum, is(4));
    }

}
