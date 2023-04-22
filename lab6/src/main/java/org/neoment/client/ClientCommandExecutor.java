package org.neoment.client;

import org.neoment.shared.CommandExecutor;
import org.neoment.shared.commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;


/**
 * Responsible for parsing and executing inputted commands.
 *
 * @author neoment
 * @version 0.1
 */
public class ClientCommandExecutor implements CommandExecutor {

    private BufferedReader reader;
    private PrintWriter writer;
    private ClientNetworkIOInterface networkIO;
    private RecursionChecker recursionChecker;

    private Map<String, Command> commandMap;

    public ClientCommandExecutor(BufferedReader reader, PrintWriter writer, ClientNetworkIOInterface networkIO) {
        this.reader = reader;
        this.writer = writer;
        this.networkIO = networkIO;
        this.commandMap = new HashMap<>();
        commandMap.put("help", new HelpCommand(this.writer, this.networkIO));
        commandMap.put("info", new InfoCommand(this.writer, this.networkIO));
        commandMap.put("show", new ShowCommand(this.writer, this.networkIO));
        commandMap.put("add", new AddCommand(this.writer, this.reader, this.networkIO));
        commandMap.put("update", new UpdateCommand(this.writer, this.reader, this.networkIO));
        commandMap.put("remove_by_id", new RemoveCommand(this.writer, this.reader, this.networkIO));
        commandMap.put("clear", new ClearCommand(this.writer, this.networkIO));
        commandMap.put("exit", new ExitCommand(this.writer, this.networkIO));
        commandMap.put("add_if_max", new AddIfMaxCommand(this.writer, this.reader, this.networkIO));
        commandMap.put("add_if_min", new AddIfMinCommand(this.writer, this.reader, this.networkIO));
        commandMap.put("remove_greater", new RemoveGreaterCommand(this.writer, this.reader, this.networkIO));
        commandMap.put("filter_less_than_position", new FilterLessCommand(this.writer, this.networkIO));
        commandMap.put("print_descending", new PrintDescendingCommand(this.writer, this.networkIO));
        commandMap.put("print_unique_organization", new UniqueOrgCommand(this.writer, this.networkIO));
        commandMap.put("execute_script", new ExecuteCommand(this.writer, this.networkIO));
    }

    public boolean run(String cmd) throws IOException {
        String[] args = cmd.trim().split("\\s+");
        if (args.length == 0) return false;
        try {
            this.networkIO.clearReceivingObjects();
            return this.commandMap.getOrDefault(args[0], new WrongCommand(this.writer)).clientExecute(args);
        } catch (SocketTimeoutException e) { writer.println("Server not responding, try again.");
        } catch (ClassNotFoundException | ClassCastException e) {
            this.networkIO.clearReceivingObjects();
            writer.println("Server sent bad response, try again.");
        }
        return false;
    }
}
