import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach // This method runs before each test method
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testAdd() {
        assertEquals(8, calculator.add(5, 3), "Addition result is incorrect");
    }

    @Test
    void testSubtract() {
        assertEquals(2, calculator.subtract(5, 3), "Subtraction result is incorrect");
    }

    @Test
    void testMultiply() {
        assertEquals(15, calculator.multiply(5, 3), "Multiplication result is incorrect");
    }

    @Test
    void testDivide() {
        assertEquals(2.0, calculator.divide(6, 3), 0.001, "Division result is incorrect");
    }

    @Test
    void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(10, 0), "Should throw IllegalArgumentException for division by zero");
    }
}