import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    private static Hippodrome hippodrome;
    private static List<Horse> horses;

    @BeforeAll
    static void init() {
        horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("name" + i, i, i));
        }
        hippodrome = new Hippodrome(horses);
    }

    @Test
    void nullHorses_ThrowExceptionAndCheckMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "argsForEmptyHorses")
    void emptyHorses_ThrowExceptionAndCheckMessage(List<Horse> horses) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));

        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    public static Stream<List<Horse>> argsForEmptyHorses() {
        return Stream.of(new ArrayList<>(), List.of(new Horse[0]), new LinkedList<>(), new CopyOnWriteArrayList<>());
    }

    @Test
    void getHorses() {
        assertIterableEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> mockedHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockedHorses.add(Mockito.mock(Horse.class));
        }

        new Hippodrome(mockedHorses).move();

        for (Horse horse : mockedHorses) {
            Mockito.verify(horse, VerificationModeFactory.only()).move();
        }
    }

    @Test
    void getWinner() {
        assertSame(horses.get(horses.size()-1), hippodrome.getWinner());
    }
}