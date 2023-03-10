package org.main.commands;

import org.main.CollectionManager;
import org.main.Parser;
import org.main.data.Worker;
import org.main.exceptions.WorkerDoesntExistException;
import org.main.exceptions.WorkerExistsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static org.main.Main.logs;

public class ModifiableCommand extends ManagerCommand {

    private BufferedReader reader;

    public ModifiableCommand(PrintWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, manager);
        this.reader = reader;
    }

    public void addWorker() throws IOException {
        this.addWorker(this.manager.nextId());
    }
    public void addWorker(Long id) throws IOException {
        Worker worker = this.constructWorker(id);
        try {
            this.manager.add(worker);
            if (logs) writer.println(worker.toString());
        } catch (WorkerExistsException e) {
            writer.println(e.getMessage());
        }
    }

    public Worker constructWorker() throws IOException {
        return this.constructWorker(this.manager.nextId());
    }

    public Worker constructWorker(Long id) throws IOException {
        Worker worker = new Worker(id);
        for (int i=0; i<worker.inputs.length; i++) {
            Worker.Input inputField = worker.inputs[i];
            String inp = this.requestField(inputField);
            try {
                Object parsedInp = Parser.parseString(inputField.cl(), inp);
                inputField.setter().set(parsedInp);
            } catch (ParseException | DateTimeParseException | NumberFormatException e) {
                writer.println("Wrong input format, try again.");
                i--;
            } catch (IllegalArgumentException e) {
                writer.println(e.getMessage());
                i--;
            }
        }
        return worker;
    }

    private String requestField(Worker.Input inputField) throws IOException{
        if (inputField.cl().isEnum())
            writer.println("Input options: " +
                    String.join(" ", Arrays.stream(inputField.cl().getEnumConstants()).map(Object::toString).toArray(String[]::new)));
        writer.print("Enter " + inputField.description() + " (example: " + Parser.formatExample(inputField.cl()) + "): ");
        writer.flush();
        return reader.readLine();
    }

    public Boolean removeWorker(Long workerId) {
        try {
            this.manager.pop(new Worker(workerId));
            return true;
        } catch (NumberFormatException e) {
            writer.println("Invalid worker id.");
        } catch (WorkerDoesntExistException e) {
            writer.println(e.getMessage());
        }
        return false;
    }
}