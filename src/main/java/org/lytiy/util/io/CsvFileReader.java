package org.lytiy.util.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CsvFileReader {

    public HashMap<String, String> read(String filePath) throws IOException {
        String line;
        HashMap<String, String> countryTranslationMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String countryName = values[0];
                String alpha2Code = values[1];
                String alpha3Code = values[2];
                String ukrainianName = values[3];

                countryTranslationMap.put(alpha2Code, ukrainianName);
                countryTranslationMap.put(countryName, ukrainianName);
                countryTranslationMap.put(alpha3Code, ukrainianName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryTranslationMap;
    }
}
