package com.friday.colini.attach.io;

import java.io.IOException;

public interface Reader {
    byte[] read(final String filePath) throws IOException;
}
