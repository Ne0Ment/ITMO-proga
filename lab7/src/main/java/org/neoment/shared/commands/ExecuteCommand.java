package org.neoment.shared.commands;

import org.neoment.client.*;
import org.neoment.server.CollectionManager;
import org.neoment.server.LocalClientCommandExecutor;
import org.neoment.server.ScriptBuilder;
import org.neoment.server.ServerCommandExecutor;
import org.neoment.server.ClientNetworkIOEmulator;
import org.neoment.client.ScriptScanner;
import org.neoment.shared.exceptions.NotLoggedInException;

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
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        checkLogin();
        String runFilePath = commandArgs[1];
        try {
            var scriptBuilder = new ScriptScanner(new RecursionChecker());
            var scripts = scriptBuilder.buildScript(runFilePath);
            var resp = this.clientIO.sendObjects(CommandType.EXECUTE_SCRIPT, getName(), getPass(), scripts, runFilePath);

            var status = (CommandStatus) resp[0];
            if (status == CommandStatus.OK)
                this.writer.println((String) resp[1]);
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
        var username = (String) in[0];
        var password = (String) in[1];
        if (!verifyUser(username, password)) return wrap(CommandStatus.NOT_OK, "Couldn't verify user.");
        var scripts = (Map<String, String>) in[2];
        var startScript = (String) in[3];

        ClientAccountHandler.setAccount(username, password);
        var scriptBuilder = new ScriptBuilder(new RecursionChecker(), scripts);
        String fullScript = null;
        fullScript = scriptBuilder.buildScript(startScript);
        var localReader = new BufferedReader(new StringReader(fullScript));
        var outStream = new ByteArrayOutputStream();
        var localWriter = new PrintWriter(outStream);

        var clientNetworkIOEmulator = new ClientNetworkIOEmulator(this.serverCommandExecutor);
        var localClientExecutor = new LocalClientCommandExecutor(localReader, localWriter, clientNetworkIOEmulator);
        var localHandler = new ClientIOHandler(localReader, localWriter, localClientExecutor, true);
        logger.log(Level.INFO, "Started script execution");
        localHandler.start();
        logger.log(Level.INFO, "Ended script execution");

        var out = outStream.toString();

        return wrap(CommandStatus.OK, out);
    }
}
