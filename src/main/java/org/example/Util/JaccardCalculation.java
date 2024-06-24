package org.example.Util;

import java.util.HashSet;
import java.util.Set;

public class JaccardCalculation {
    public static double jaccardSimilarity(Set<Character> set1, Set<Character> set2){
        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();

    }
}
