package org.example.Util;

import java.util.HashSet;
import java.util.Set;

public class JaccardCalculation {
    /**
     * This method calculates a Jaccard coefficient.
     * The Jaccard coefficient gives a value that represents the similarity of the neighborhoods of two vertices
     * @param set1  first vertices
     * @param set2  vertices
     * @return - the more similar the closer to 1
     */
    public static double jaccardSimilarity(Set<Character> set1, Set<Character> set2){
        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();

    }
}
