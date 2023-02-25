package org.main.exceptions;

/**
 * Exception for when the user hasn't passed the xml file's path.
 * @author neoment
 * @version 0.1
 */
public class FileNotPassedException extends Exception {
    public FileNotPassedException() {
        super("Filepath not passed.");
    }
}
