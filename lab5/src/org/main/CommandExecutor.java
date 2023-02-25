package org.main;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public CommandExecutor (BufferedReader reader, BetterBufferedWriter writer, CollectionManager manager, FileHandler fileHandler) {
        this.reader = reader;
        this.writer = writer;
        this.manager = manager;
        this.fileHandler = fileHandler;
    }

    public Boolean run (String cmd) throws IOException {
        this.args = cmd.trim().split("\\s+");
        this.enoughArgs(1);
        switch (this.args[0]) {
            case "help" ->
                writer.printLn(this.helpText);
            case "info" ->
                writer.printLn(this.manager.getWorkersInfo());
            case "show" -> {
                if (this.manager.getLength()==0) { writer.printLn("No workers."); break; }
                Iterator<Worker> workers = this.manager.getIterator();
                while (workers.hasNext()) {
                    writer.printLn(workers.next().toString());
                }
            }
            case "add", "add_if_max" ->  //add_if_max is also here since newWorkerId = maxId + 1
                this.addWorker();
            case "update" -> {
                if (!this.enoughArgs(2)) break;
                try {
                    Long id = (Long) Parser.parseString(Long.class, args[2]);
                    Boolean removed = removeWorker(id);
                    if (!removed) break;
                    this.addWorker(id);
                } catch (ParseException e) {
                    this.writer.printLn("Couldn't parse worker id.");
                }
            }
            case "remove_by_id" -> {
                if (!this.enoughArgs(2)) break;
                try {
                    removeWorker((Long) Parser.parseString(Long.class, args[1]));
                } catch (ParseException e) {
                    this.writer.printLn("Couldn't parse worker id.");
                }
            }
            case "clear" ->
                this.manager.clear();
            case "save" -> {
                try {
                    this.fileHandler.saveToXml(this.manager);
                } catch (JsonProcessingException e) {
                    writer.printLn("Failed to serialize xml. " + e.getMessage());
                } catch (Exception e) {
                    writer.printLn(e.getMessage());
                }
            }
            case "execute_script" -> {
                if (!this.enoughArgs(2)) break;
                try {
                    String script = this.fileHandler.readFile(this.args[1]);
                    BufferedReader localReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.args[1])));
                    CommandExecutor localExecutor = new CommandExecutor(localReader, this.writer, this.manager, this.fileHandler);
                    IOHandler localHandler = new IOHandler(localReader, this.writer, localExecutor, true);
                    localHandler.start();
                } catch (Exception e) {
                    writer.printLn(e.getMessage());
                }
            }
            case "exit" -> {
                return true;
            }
            case "add_if_min" -> {
                if (this.manager.getLength()==0) {
                    this.addWorker();
                } else {
                    writer.printLn("New workers always have a higher id.");
                }
            }
            case "remove_greater" -> {
                if (!this.enoughArgs(2)) break;
                try {
                    Long maxId = (Long) Parser.parseString(Long.class, this.args[1]);
                    this.manager.filterWorkers((Worker worker) -> worker.getId() <= maxId);
                } catch (Exception e) {
                    this.writer.printLn("Wrong id format. " + (logs ? e : ""));
                }
            }
            case "filter_less_than_position" -> {
                if (!this.enoughArgs(2)) break;
                try {
                    Position position = (Position) Parser.parseString(Position.class, this.args[1]);
                    ArrayList<Worker> workers = this.manager.getWorkerList();
                    for (Worker w : workers) {
                        if ( w.getPosition().ordinal() < (position != null ? position.ordinal() : 0)) { this.writer.printLn(w.toString()); }
                    }
                } catch (Exception e) {
                    this.writer.printLn("Wrong position value.");
                }
            }
            case "print_descending" -> {
                ArrayList<Worker> workers = this.manager.getWorkerList();
                Collections.reverse(workers);
                for (Worker w : workers) { writer.printLn(w.toString()); }
            }
            case "print_unique_organization" -> {
                Set<Organization> organizations = this.manager.getUniqueOrganizations();
                for (Organization org : organizations) {
                    writer.printLn(org.toString());
                }
            }
            default -> writer.printLn("Invalid command.");
        }
        return false;
    }

    private Boolean removeWorker(Long workerId) throws IOException {
        try {
            this.manager.pop(new Worker(workerId));
            return true;
        } catch (NumberFormatException e) {
            writer.printLn("Invalid worker id.");
        } catch (WorkerDoesntExistException e) {
            writer.printLn(e.getMessage());
        }
        return false;
    }
    private void addWorker() throws IOException {
        this.addWorker(this.manager.nextId());
    }
    private void addWorker(Long id) throws IOException {
        Worker worker = this.constructWorker(id);
        try {
            this.manager.add(worker);
            if (logs) writer.printLn(worker.toString());
        } catch (WorkerExistsException e) {
            writer.printLn(e.getMessage());
        }
    }

    private Worker constructWorker(Long id) throws IOException {
        Worker worker = new Worker(id);
        for (int i=0; i<worker.inputs.length; i++) {
            Worker.Input inputField = worker.inputs[i];
            String inp = this.requestField(inputField);
            try {
                Object parsedInp = Parser.parseString(inputField.cl(), inp);
                inputField.setter().set(parsedInp);
            } catch (ParseException | DateTimeParseException | NumberFormatException e) {
                writer.printLn("Wrong input format, try again.");
                i--;
            } catch (IllegalArgumentException e) {
                writer.printLn(e.getMessage());
                i--;
            }
        }
        return worker;
    }

    private Boolean enoughArgs(int minArgs) throws IOException {
        if (this.args.length < minArgs) {
            this.writer.printLn("Invalid command.");
        }
        return (this.args.length >= minArgs);
    }

    private String requestField(Worker.Input inputField) throws IOException{
        if (inputField.cl().isEnum())
            writer.printLn("Input options: " +
                    String.join(" ", Arrays.stream(inputField.cl().getEnumConstants()).map(Object::toString).toArray(String[]::new)));
        writer.print("Enter " + inputField.description() + " (example: " + Parser.formatExample(inputField.cl()) + "): ");
        return reader.readLine();
    }

    private final String helpText = """
                        help : вывести справку по доступным командам
                        info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                        show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                        add {element} : добавить новый элемент в коллекцию
                        update id {element} : обновить значение элемента коллекции, id которого равен заданному
                        remove_by_id id : удалить элемент из коллекции по его id
                        clear : очистить коллекцию
                        save : сохранить коллекцию в файл
                        execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                        exit : завершить программу (без сохранения в файл)
                        add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
                        add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
                        remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
                        filter_less_than_position position : вывести элементы, значение поля position которых меньше заданного
                        print_descending : вывести элементы коллекции в порядке убывания
                        print_unique_organization : вывести уникальные значения поля organization всех элементов в коллекции
                        """;
}
