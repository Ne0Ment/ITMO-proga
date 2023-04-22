package org.neoment.shared.commands;

import org.neoment.client.*;
import org.neoment.server.CollectionManager;
import org.neoment.server.LocalClientCommandExecutor;
import org.neoment.server.ScriptBuilder;
import org.neoment.server.ServerCommandExecutor;
import org.neoment.server.ClientNetworkIOEmulator;
import org.neoment.client.ScriptScanner;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ExecuteCommand extends ManagerCommand {

    ServerCommandExecutor serverCommandExecutor;

    static Logger logger = Logger.getLogger(ServerCommandExecutor.class.getName());

    public ExecuteCommand(CollectionManager manager, ServerCommandExecutor serverCommandExecutor) {
        super(manager);
        this.serverCommandExecutor = serverCommandExecutor;
    }

    public ExecuteCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        String runFilePath = commandArgs[1];
        try {
            var scriptBuilder = new ScriptScanner(new RecursionChecker());
            var scripts = scriptBuilder.buildScript(runFilePath);
            this.clientIO.sendOneWayObjects(CommandType.EXECUTE_SCRIPT, scripts, runFilePath);
        } catch (FileNotFoundException e) {
            writer.println("Script not found.");
        } catch (SecurityException e) {
            writer.println("Script not accessible.");
        } catch (Exception e) {
            writer.println(e.getMessage());
        }
        return super.clientExecute(commandArgs);
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var scripts = (Map<String, String>) in[0];
        var startScript = (String) in[1];

        var t = new Thread(() -> {

            var scriptBuilder = new ScriptBuilder(new RecursionChecker(), scripts);
            String fullScript = null;
            try {
                fullScript = scriptBuilder.buildScript(startScript);
                var localReader = new BufferedReader(new StringReader(fullScript));
                var localWriter = new PrintWriter(OutputStream.nullOutputStream());

                var clientNetworkIOEmulator = new ClientNetworkIOEmulator(this.serverCommandExecutor);
                var localClientExecutor = new LocalClientCommandExecutor(localReader, localWriter, clientNetworkIOEmulator);
                var localHandler = new ClientIOHandler(localReader, localWriter, localClientExecutor, true);
                logger.log(Level.INFO, "Started script execution");
                localHandler.start();
                logger.log(Level.INFO, "Ended script execution");
            } catch (Exception ignored) {}
        });

        t.start();

        return wrap(CommandStatus.OK);
    }
}
