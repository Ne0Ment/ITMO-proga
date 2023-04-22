package org.neoment.client;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScriptScanner {
    private final RecursionChecker recursionChecker;

    public ScriptScanner(RecursionChecker recursionChecker) {
        this.recursionChecker = recursionChecker;
    }

    public Map<String, String> buildScript(String filePath) throws IOException {
        var out = new HashMap<String, String>();
        var started = this.recursionChecker.startFile(filePath);
        if (!started) return out;

        var lines = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        while ((line=reader.readLine()) != null) {
            String[] args = line.trim().split("\\s+");
            if (args[0].equals("execute_script")) {
                if (args.length<2) continue;
                var localBuilder = new ScriptScanner(recursionChecker);
                out.putAll(localBuilder.buildScript(args[1]));
            }
            lines.append(line).append("\n");
        }

        out.put(filePath, lines.toString());

        this.recursionChecker.endFile(filePath);
        return out;
    }
}
