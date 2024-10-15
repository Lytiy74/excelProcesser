package ExcelProcesserTest.Util;

import org.lytiy.util.MapConverter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapConverterTest {

    @Test
    void shouldHandleEmptyInputMap() {
        // Arrange
        HashMap<String, List<String>> inputMap = new HashMap<>();

        // Act
        HashMap<String, String> result = MapConverter.invertColumnMap(inputMap);

        // Assert
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
}