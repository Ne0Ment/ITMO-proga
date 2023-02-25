package org.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.main.commands.*;
import org.main.data.Organization;
import org.main.data.Position;
import org.main.data.Worker;
import org.main.exceptions.WorkerDoesntExistException;
import org.main.exceptions.WorkerExistsException;

import java.io.*;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.main.Main.logs;

/**
 * Responsible for parsing and executing inputted commands.
 * @author neoment
 * @version 0.1
 */
public class CommandExecutor {

    private BufferedReader reader;
    private BetterBufferedWriter writer;
    private CollectionManager manager;
    private FileHandler fileHandler;
    private String[] args;

    private Map<String, Command> commandMap;

    public CommandExecutor (BufferedReader reader, BetterBufferedWriter writer, CollectionManager manager, FileHandler fileHandler) {
        this.reader = reader;
        this.writer = writer;
        this.manager = manager;
        this.fileHandler = fileHandler;
        this.commandMap = new HashMap<>();
        commandMap.put("help", new HelpCommand(this.writer));
        commandMap.put("info", new InfoCommand(this.writer, this.manager));
        commandMap.put("show", new ShowCommand(this.writer, this.manager));
        commandMap.put("add", new AddCommand(this.writer, this.reader, this.manager));
        commandMap.put("add_if_max", new AddCommand(this.writer, this.reader, this.manager));
        commandMap.put("update", new UpdateCommand(this.writer, this.reader, this.manager));
        commandMap.put("remove_by_id", new RemoveCommand(this.writer, this.reader, this.manager));
        commandMap.put("clear", new ClearCommand(this.writer, this.manager));
        commandMap.put("save", new SaveCommand(this.writer, this.manager, this.fileHandler));
        commandMap.put("exit", new ExitCommand());
        commandMap.put("add_if_min", new AddIfMinCommand(this.writer, this.reader, this.manager));
        commandMap.put("remove_greater", new RemoveGreaterCommand(this.writer, this.reader, this.manager));
        commandMap.put("filter_less_than_position", new FilterLessCommand(this.writer, this.manager));
        commandMap.put("print_descending", new PrintDescendingCommand(this.writer, this.manager));
        commandMap.put("print_unique_organization", new UniqueOrgCommand(this.writer, this.manager));
        commandMap.put("execute_script", new ExecuteCommand(this.writer, this.manager, this.fileHandler));
    }

    public boolean run (String cmd) throws IOException {
        String[] args = cmd.trim().split("\\s+");
        if (args.length==0) return false;
        return this.commandMap.getOrDefault(args[0], new WrongCommand(this.writer)).execute(args);
    }
}
