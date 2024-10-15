package org.lytiy.util.io;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

public interface Reader {
    /**
     * Reads the content of a file and returns it as a String.
     *
     * @param filePath The path of the file to read.
     * @return The content of the file as a String.
     * @throws IOException If an I/O error occurs.
     */
    String read(String filePath) throws IOException;

    /**
     * Reads the content of a JSON file and returns it as an object of the specified type.
     *
     * @param filePath The path of the JSON file to read.
     * @param typeReference The TypeReference for the desired type.
     * @param <T> The type of the object to return.
     * @return The content of the JSON file as an object of the specified type.
     * @throws IOException If an I/O error occurs.
     */
    <T> T readJson(String filePath, TypeReference<T> typeReference) throws IOException;
}