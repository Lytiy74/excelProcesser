import org.example.Util.JaccardCalculation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

public class JaccardCalculationTest {
    @Test
    public void testFindBestMatch_EmptyInput_ReturnsUnknown() {
        // Given
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("column1", "Category1");
        keyMap.put("column2", "Category2");

        // When
        String result = JaccardCalculation.findBestMatch("", keyMap);

        // Then
        assertEquals("UNKNOWN", result);
    }

    @Test
    public void testFindBestMatch_NullInput_ReturnsUnknown() {
        // Given
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("column1", "Category1");
        keyMap.put("column2", "Category2");

        // When
        String result = JaccardCalculation.findBestMatch(null, keyMap);

        // Then
        assertEquals("UNKNOWN", result);
    }

    @Test
    public void testFindBestMatch_NoMatchFound_ReturnsUnknown() {
        // Given
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("column1", "Category1");
        keyMap.put("column2", "Category2");

        // When
        String result = JaccardCalculation.findBestMatch("nonexistentcolumn", keyMap);

        // Then
        assertEquals("UNKNOWN", result);
    }

    @Test
    public void testFindBestMatch_ExactMatchFound_ReturnsCorrectCategory() {
        // Given
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("column1", "Category1");
        keyMap.put("column2", "Category2");

        // When
        String result = JaccardCalculation.findBestMatch("column1", keyMap);

        // Then
        assertEquals("Category1", result);
    }

    @Test
    public void testFindBestMatch_PartialMatchFound_ReturnsCorrectCategory() {
        // Given
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("column1", "Category1");
        keyMap.put("column2", "Category2");

        // When
        String result = JaccardCalculation.findBestMatch("colum", keyMap);

        // Then
        assertEquals("Category1", result); // Assuming Jaccard similarity is used for partial match
    }
}