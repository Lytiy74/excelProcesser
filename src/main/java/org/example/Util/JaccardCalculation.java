package org.example.Util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JaccardCalculation {
    private static final Logger logger = LoggerFactory.getLogger(JaccardCalculation.class);
    public static final double JACCARD_PASSABLE_VALUE = 0.55;

    /**
     * This method calculates a Jaccard coefficient.
     * The Jaccard coefficient gives a value that represents the similarity of the neighborhoods of two vertices
     *
     * @param set1 first vertices
     * @param set2 vertices
     * @return - the more similar the closer to 1
     */
    public static double jaccardSimilarity(Set<Character> set1, Set<Character> set2) {

        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();

    }

    /**
     * Finds the best match category for a given column name based on the Jaccard similarity score.
     *
     * @param var1   The name of the first variable to find a match for.
     * @param keyMap The inverted target map.
     * @return The best match category for the given column name.
     */
    public static String findBestMatch(String var1, HashMap<String, String> keyMap) {
        logger.debug("Looking for best match for '{}'", var1);
        if (var1 == null) return "UNKNOWN";
        // Convert the column name to a set of characters
        Set<Character> set = stringToLowerCaseCharSet(var1);

        // Initialize variables to store the best match category and score
        String bestMatch = "UNKNOWN";
        double bestScore = Double.MIN_VALUE;

        // Iterate through the entries in the inverted column map
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            // Get the possible column name and its category
            String possibleName = entry.getKey();
            String category = entry.getValue();

            // Convert the possible column name to a set of characters
            Set<Character> possibleSet = stringToLowerCaseCharSet(possibleName);

            // Calculate the Jaccard similarity score between the column name and the possible column name
            double score = JaccardCalculation.jaccardSimilarity(set, possibleSet);

            // If the score is higher than the current best score, update the best match category and score
            logger.trace("Possible match: '{}' , Category: '{}', Score: {}", possibleName, category, score);
            if (score > JACCARD_PASSABLE_VALUE && score > bestScore) {
                bestScore = score;
                bestMatch = category;
            }
        }

        // Return the best match category
        logger.debug("Best match for '{}' is '{}', Score: {}", var1, bestMatch, bestScore);
        return bestMatch;
    }

    /**
     * Finds the best match row in the sheet for the target column names using Jaccard similarity.
     *
     * @param sheet         The sheet to search for the best header row.
     * @param targetColumns The target column names to match against.
     * @return The index of the best matching row.
     */
    public static int findBestMatchRow(Sheet sheet, HashMap<String, List<String>> targetColumns) {
        double bestScore = 0;
        int bestRowIndex = -1;

        HashMap<String, String> columnKeyMap = MapConverter.invertColumnMap(targetColumns);
        List<String> targetColumnNames = new ArrayList<>(columnKeyMap.keySet());

        int numberOfRows = sheet.getLastRowNum();
        for (int i = sheet.getFirstRowNum(); i <= numberOfRows; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            // Extract values from the row
            List<String> rowValues = new ArrayList<>();
            for (Cell cell : row) {
                rowValues.add(cell.toString().trim());
            }
            logger.debug("Looking for best match for row # '{}'", row.getRowNum());
            // Calculate similarity score
            double score = calculateRowSimilarity(rowValues, targetColumnNames);

            // Update the best score and row index
            if (score > JACCARD_PASSABLE_VALUE && score > bestScore) {
                bestScore = score;
                bestRowIndex = i;
            }
        }
        logger.debug("Best match for headerRow is row # '{}', Score: {}", bestRowIndex, bestScore);
        return bestRowIndex;
    }

    /**
     * Calculate the similarity score between row values and target column names.
     *
     * @param rowValues         The values from the row.
     * @param targetColumnNames The target column names to compare against.
     * @return The similarity score.
     */
    private static double calculateRowSimilarity(List<String> rowValues, List<String> targetColumnNames) {
        double totalScore = 0;
        int matchCount = 0;

        for (String rowValue : rowValues) {
            for (String targetColumn : targetColumnNames) {
                double score = jaccardSimilarity(stringToLowerCaseCharSet(rowValue), stringToLowerCaseCharSet(targetColumn));
                if (score > JACCARD_PASSABLE_VALUE) {
                    totalScore += score;
                    matchCount++;
                }
            }
        }

        return matchCount == 0 ? 0 : totalScore / matchCount;
    }


    /**
     * Converts a string to a set of characters.
     *
     * @param str The string to convert.
     * @return A set of characters.
     */
    private static Set<Character> stringToLowerCaseCharSet(String str) {
        Set<Character> charSet = new HashSet<>();

        // Iterate through the characters in the string
        for (char c : str.toLowerCase().toCharArray()) {
            // Add the character to the set
            charSet.add(c);
        }

        // Return the set of characters
        return charSet;
    }
}
