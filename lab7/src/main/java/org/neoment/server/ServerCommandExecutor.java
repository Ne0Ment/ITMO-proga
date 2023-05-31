package org.neoment.server;

import org.neoment.shared.NetworkObjectEncoder;
import org.neoment.shared.commands.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ServerCommandExecutor {
    static Logger logger = Logger.getLogger(ServerCommandExecutor.class.getName());
    public static boolean logging = true;
    private final Map<CommandType, Command> commandMap;

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public ServerCommandExecutor(CollectionManager collectionManager) {
        this.commandMap = new HashMap<CommandType, Command>();
        commandMap.put(CommandType.HELP, new HelpCommand());
        commandMap.put(CommandType.INFO, new InfoCommand(collectionManager));
        commandMap.put(CommandType.SHOW, new ShowCommand(collectionManager));
        commandMap.put(CommandType.ADD, new AddCommand(collectionManager));
        commandMap.put(CommandType.UPDATE, new UpdateCommand(collectionManager));
        commandMap.put(CommandType.REMOVE_BY_ID, new RemoveCommand(collectionManager));
        commandMap.put(CommandType.CLEAR, new ClearCommand(collectionManager));
        commandMap.put(CommandType.ADD_IF_MAX, new AddIfMaxCommand(collectionManager));
        commandMap.put(CommandType.ADD_IF_MIN, new AddIfMinCommand(collectionManager));
        commandMap.put(CommandType.REMOVE_GREATER, new RemoveGreaterCommand(collectionManager));
        commandMap.put(CommandType.FILTER_LESS_THAN, new FilterLessCommand(collectionManager));
        commandMap.put(CommandType.PRINT_DESC, new PrintDescendingCommand(collectionManager));
        commandMap.put(CommandType.PRINT_UNIQUE_ORG, new UniqueOrgCommand(collectionManager));
        commandMap.put(CommandType.EXECUTE_SCRIPT, new ExecuteCommand(collectionManager, this));
        commandMap.put(CommandType.REGISTER, new RegisterCommand(collectionManager));
        commandMap.put(CommandType.LOGIN, new LoginCommand(collectionManager));
    }

    public void run(CommandType commandType, Object[] in, InetSocketAddress clientAddress) throws IOException {
        executor.submit(() -> {
            try {
                var toSend = commandMap.getOrDefault(commandType, new WrongCommand()).serverExecute(in);
                MultithreadServerNetworkIO.responseHandler.handleObjectResponse(toSend, clientAddress);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                if (logging) logger.log(Level.SEVERE, "Error while parsing data: " + e.getMessage());
            } catch (SQLException e) {
                if (logging) logger.log(Level.SEVERE, "SQL error: " + e.getMessage());
            }
        });
        if (logging) logger.log(Level.INFO, "Processed command: " + commandType.toString());

    }
}
