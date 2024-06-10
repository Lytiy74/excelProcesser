package org.example.Util;

import java.util.HashSet;
import java.util.Set;

public class JaccardCalculation {
    public static double jaccardSimilarity(Set<String> set1, Set<String> set2){
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<String> union = new HashSet<>();
        union.addAll(set2);
        return (double) intersection.size() / union.size();

    }
}
