package org.neoment.server;

import org.neoment.client.RecursionChecker;
import org.neoment.client.ScriptScanner;

import java.io.*;
import java.util.Map;

public class ScriptBuilder {

    private RecursionChecker recursionChecker;

    private Map<String, String> scripts;

    public ScriptBuilder(RecursionChecker recursionChecker, Map<String, String> scripts) {
        this.recursionChecker = recursionChecker;
        this.scripts = scripts;
    }

    public String buildScript(String scriptName) throws IOException {
        var out = new StringBuilder();
        var started = this.recursionChecker.startFile(scriptName);
        if (!started) return out.toString();

        BufferedReader reader = new BufferedReader(new StringReader(this.scripts.getOrDefault(scriptName, "")));
        String line;

        while ((line=reader.readLine()) != null) {
            String[] args = line.trim().split("\\s+");
            if (args[0].equals("execute_script")) {
                if (args.length>=2) out.append(this.buildScript(args[1]));
                continue;
            }
            out.append(line).append("\n");
        }

        this.recursionChecker.endFile(scriptName);
        return out.toString();
    }
}
