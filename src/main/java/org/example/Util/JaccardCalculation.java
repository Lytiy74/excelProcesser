package org.example.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JaccardCalculation {
    /**
     * This method calculates a Jaccard coefficient.
     * The Jaccard coefficient gives a value that represents the similarity of the neighborhoods of two vertices
     * @param set1  first vertices
     * @param set2  vertices
     * @return - the more similar the closer to 1
     */
    private static double jaccardSimilarity(Set<Character> set1, Set<Character> set2){
        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();

    }
    /**
     * Finds the best match category for a given column name based on the Jaccard similarity score.
     *
     * @param var1 The name of the first variable to find a match for.
     * @param keyMap The inverted target map.
     * @return The best match category for the given column name.
     */
    public static String findBestMatch(String var1, HashMap<String, String> keyMap){
        if(var1 == null) return "UNKNOWN";
        // Convert the column name to a set of characters
        Set<Character> set = stringToCharSet(var1);

        // Initialize variables to store the best match category and score
        String bestMatch = "UNKNOWN";
        double bestScore = Double.MIN_VALUE;

        // Iterate through the entries in the inverted column map
        for(Map.Entry<String, String> entry : keyMap.entrySet()){
            // Get the possible column name and its category
            String possibleName = entry.getKey();
            String category = entry.getValue();

            // Convert the possible column name to a set of characters
            Set<Character> possibleSet = stringToCharSet(possibleName);

            // Calculate the Jaccard similarity score between the column name and the possible column name
            double score = JaccardCalculation.jaccardSimilarity(set, possibleSet);

            // If the score is higher than the current best score, update the best match category and score
            if (score > 0.5 && score > bestScore){
                bestScore = score;
                bestMatch = category;
            }
        }

        // Return the best match category
        return bestMatch;
    }
    /**
     * Converts a string to a set of characters.
     *
     * @param str The string to convert.
     * @return A set of characters.
     */
    private static Set<Character> stringToCharSet(String str) {
        Set<Character> charSet = new HashSet<>();

        // Iterate through the characters in the string
        for (char c : str.toCharArray()) {
            // Add the character to the set
            charSet.add(c);
        }

        // Return the set of characters
        return charSet;
    }
}
