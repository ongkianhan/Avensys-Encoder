import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnigmaTest {

    @org.junit.jupiter.api.Test
    public void encode() {
        Enigma test = new Enigma();
        String input = "HELLO WORLD";
        test.setOffset('B');
        Assertions.assertEquals("BGDKKN VNQKC", test.encode("HELLO WORLD"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> { test.encode(null);});

        String expectedMessage = "Input is not a string!\n";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @org.junit.jupiter.api.Test
    public void decode() {
        Enigma test = new Enigma();
        String input = "BGDKKN VNQKC";
        test.setOffset('B');
        Assertions.assertEquals("HELLO WORLD", test.decode("BGDKKN VNQKC"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> { test.decode(null);});

        String expectedMessage = "Input is not a string!\n";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    
}