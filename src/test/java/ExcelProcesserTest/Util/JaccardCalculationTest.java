package ExcelProcesserTest.Util;

import org.lytiy.util.JaccardCalculation;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JaccardCalculationTest {
    private static final Logger logger = LoggerFactory.getLogger(JaccardCalculationTest.class);

    @Test
    public void findBestMatch_nullInputForVar1_returnsUnknown() {
        // Arrange
        String var1 = null;
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("lytiy", "Category1");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("N/A", result);
    }

    @Test
    public void findBestMatch_emptyStringForVar1_returnsUnknown() {
        // Arrange
        String var1 = "";
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("lytiy", "Category1");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("N/A", result);
    }

    @Test
    public void findBestMatch_whitespaceForVar1_returnsUnknown() {
        // Arrange
        String var1 = "   ";
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("lytiy", "Category1");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("N/A", result);
    }

    @Test
    public void findBestMatch_specialCharactersForVar1_returnsUnknown() {
        // Arrange
        String var1 = "!@#$%^&*()";
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("lytiy", "Category1");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("N/A", result);
    }

    @Test
    public void findBestMatch_numbersForVar1_returnsUnknown() {
        // Arrange
        String var1 = "123456";
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("lytiy", "Category1");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("N/A", result);
    }

    @Test
    public void findBestMatch_matchingStrings_returnsCorrectCategory() {
        // Arrange
        String var1 = "lytiy";
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("lytiy", "Category1");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("Category1", result);
    }

    @Test
    public void findBestMatch_partiallyMatchingStrings_returnsCorrectCategory() {
        // Arrange
        String var1 = "exampl";
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("example", "Category1");
        keyMap.put("examination", "Category2");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("Category1", result);
    }

    @Test
    public void findBestMatch_nonMatchingStrings_returnsUnknown() {
        // Arrange
        String var1 = "nonmatching";
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("lytiy", "Category1");
        keyMap.put("examination", "Category2");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("N/A", result);
    }

    @Test
    public void findBestMatch_differentLetterCasesForVar1_returnsCorrectCategory() {
        // Arrange
        String var1 = "EXaMpLe";
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("example", "Category1");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("Category1", result);
    }

    @Test
    public void findBestMatch_nonEnglishCharactersForVar1_returnsCorrectCategory() {
        // Arrange
        String var1 = "ТЕСТ"; // Example non-English characters
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("English", "Category1");
        keyMap.put("тест", "Category2");

        // Act
        String result = JaccardCalculation.findBestMatch(var1, keyMap);

        // Assert
        assertEquals("Category2", result);
    }
}