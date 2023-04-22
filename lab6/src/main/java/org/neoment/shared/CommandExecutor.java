package org.neoment.shared;

import java.io.IOException;

public interface CommandExecutor {
    public boolean run(String cmd) throws IOException;
}
