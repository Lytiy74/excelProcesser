import org.example.Util.MapConverter;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapConverterTest {

    @Test
    public void testInvertColumnMap_singleValue() {
        HashMap<String, List<String>> inputMap = new HashMap<>();
        inputMap.put("A", Arrays.asList("X"));
        HashMap<String, String> expectedOutput = new HashMap<>();
        expectedOutput.put("X", "A");
        assertEquals(expectedOutput, MapConverter.invertColumnMap(inputMap));
    }

    @Test
    public void testInvertColumnMap_multipleValues() {
        HashMap<String, List<String>> inputMap = new HashMap<>();
        inputMap.put("A", Arrays.asList("X", "Y"));
        inputMap.put("B", Arrays.asList("X", "Z"));
        HashMap<String, String> expectedOutput = new HashMap<>();
        expectedOutput.put("X", "B");
        expectedOutput.put("Y", "A");
        expectedOutput.put("Z", "B");
        assertEquals(expectedOutput, MapConverter.invertColumnMap(inputMap));
    }

    @Test
    public void testInvertColumnMap_duplicateValues() {
        HashMap<String, List<String>> inputMap = new HashMap<>();
        inputMap.put("A", Arrays.asList("X", "Y"));
        inputMap.put("B", Arrays.asList("X", "Y"));
        HashMap<String, String> expectedOutput = new HashMap<>();
        expectedOutput.put("X", "B");
        expectedOutput.put("Y", "B");
        assertEquals(expectedOutput, MapConverter.invertColumnMap(inputMap));
    }

    @Test
    public void testInvertColumnMap_emptyMap() {
        HashMap<String, List<String>> inputMap = new HashMap<>();
        HashMap<String, String> expectedOutput = new HashMap<>();
        assertEquals(expectedOutput, MapConverter.invertColumnMap(inputMap));
    }

    @Test
    public void testInvertColumnMap_nullValues() {
        HashMap<String, List<String>> inputMap = new HashMap<>();
        inputMap.put("A", null);
        inputMap.put("B", Arrays.asList("X", null));
        HashMap<String, String> expectedOutput = new HashMap<>();
        expectedOutput.put("X", "B");
        assertEquals(expectedOutput, MapConverter.invertColumnMap(inputMap));
    }
}