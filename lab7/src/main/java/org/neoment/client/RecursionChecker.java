package org.neoment.client;

import java.util.HashSet;
import java.util.Set;

public class RecursionChecker {
    private Set<String> runningFiles;

    public RecursionChecker() {
        this.runningFiles = new HashSet<>();
    }

    public boolean startFile(String filePath) {
        return this.runningFiles.add(filePath);
    }

    public void endFile(String filePath) {
        this.runningFiles.remove(filePath);
    }
}
