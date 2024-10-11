package org.example.product.productprocess.composition;

public interface IMaterialProcess {
    String generateCompositionString(String composition);
    String translateComposition(String composition);
    String generateSortedCompositionString(String composition);
}
