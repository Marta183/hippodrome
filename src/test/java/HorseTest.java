import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class HorseTest {

    private static Horse regularHorse = new Horse("name", 2.4, 10);

    @Test
    void nullNameParam_ThrowExceptionAndCheckMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1));

        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "    "})
    @EmptySource
    void blankNameParam_ThrowExceptionAndCheckMessage(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1));

        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    void negativeSpeed_ThrowExceptionAndCheckMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", -0.1));

        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    void negativeDistance_ThrowExceptionAndCheckMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1, -0.1));

        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    void getName() {
        assertEquals("name", regularHorse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(2.4, regularHorse.getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(10, regularHorse.getDistance());
    }

    @Test
    void getDefaultDistance() {
        assertEquals(0, new Horse("name", 2.4).getDistance());
    }

    @Test
    void move_checkDistance() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(5.0);

            Double expected = 10 + 2.4 * 5.0;
            regularHorse.move();

            assertEquals(expected, regularHorse.getDistance());

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), VerificationModeFactory.only());
        }
    }


}