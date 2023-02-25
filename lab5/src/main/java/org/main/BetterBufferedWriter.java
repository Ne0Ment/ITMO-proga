package org.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Buffered Writer with auto-flushing functions
 * @author neoment
 * @version 0.1
 */
public class BetterBufferedWriter extends BufferedWriter {
    public BetterBufferedWriter(Writer out) {
        super(out);
    }

    public void print(String s) throws IOException {
        this.write(s);
        this.flush();
    }

    public void printLn(String s) throws IOException {
        this.print(s + "\n");
    }
}
