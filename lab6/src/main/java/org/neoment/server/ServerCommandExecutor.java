package org.neoment.server;

import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.commands.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ServerCommandExecutor {
    static Logger logger = Logger.getLogger(ServerCommandExecutor.class.getName());
    public static boolean logging = true;
    private final Map<CommandType, Command> commandMap;

    private PrintWriter writer;

    public ServerCommandExecutor(CollectionManager collectionManager, FileHandler fileHandler) {
        this.commandMap = new HashMap<CommandType, Command>();
        commandMap.put(CommandType.HELP, new HelpCommand());
        commandMap.put(CommandType.INFO, new InfoCommand(collectionManager));
        commandMap.put(CommandType.SHOW, new ShowCommand(collectionManager));
        commandMap.put(CommandType.ADD, new AddCommand(collectionManager));
        commandMap.put(CommandType.UPDATE, new UpdateCommand(collectionManager));
        commandMap.put(CommandType.REMOVE_BY_ID, new RemoveCommand(collectionManager));
        commandMap.put(CommandType.CLEAR, new ClearCommand(collectionManager));
        commandMap.put(CommandType.SAVE, new SaveCommand(collectionManager, fileHandler));
        commandMap.put(CommandType.EXIT, new ExitCommand(collectionManager, fileHandler));
        commandMap.put(CommandType.ADD_IF_MAX, new AddIfMaxCommand(collectionManager));
        commandMap.put(CommandType.ADD_IF_MIN, new AddIfMinCommand(collectionManager));
        commandMap.put(CommandType.REMOVE_GREATER, new RemoveGreaterCommand(collectionManager));
        commandMap.put(CommandType.FILTER_LESS_THAN, new FilterLessCommand(collectionManager));
        commandMap.put(CommandType.PRINT_DESC, new PrintDescendingCommand(collectionManager));
        commandMap.put(CommandType.PRINT_UNIQUE_ORG, new UniqueOrgCommand(collectionManager));
        commandMap.put(CommandType.EXECUTE_SCRIPT, new ExecuteCommand(collectionManager, this));
    }

    public Object[][] run(CommandType commandType, Object[] in) throws IOException{
        try {
            var resp = commandMap.getOrDefault(commandType, new WrongCommand()).serverExecute(in);
            if (logging) logger.log(Level.INFO, "Processed command: " + commandType.toString());
            return resp;
        } catch (ClassNotFoundException e) {
            if (logging) logger.log(Level.WARNING, "Error while parsing data: " + e.getMessage());
            return wrap(new Object());
        }
    }
}
