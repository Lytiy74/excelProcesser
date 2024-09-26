package org.example.util.io;

import java.io.IOException;

public interface Reader {
    String read(String filePath) throws IOException;
}
