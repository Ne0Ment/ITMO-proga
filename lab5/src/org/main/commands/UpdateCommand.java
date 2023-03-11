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

public class UpdateCommand extends ModifiableCommand {

    public UpdateCommand(PrintWriter writer, BufferedReader reader, CollectionManager manager) {
        super(writer, reader, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (!this.enoughArgs(3, commandArgs)) return false;
        try {
            Long id = (Long) Parser.parseString(Long.class, commandArgs[2]);
            if (!this.manager.workerExists(new Worker(id))) throw new WorkerDoesntExistException(id);
            Worker defaultWorker = this.manager.getWorkerById(id);
            Boolean removed = this.removeWorker(id);
            if (!removed) return false;
            Worker newWorker = this.constructWorkerWithDefaults(defaultWorker);
            this.addWorker(newWorker);
        } catch (ParseException e) {
            this.writer.println("Couldn't parse worker id.");
        } catch (WorkerDoesntExistException e) {
            this.writer.println(e.getMessage());
        }
        return false;
    }

    public Worker constructWorkerWithDefaults(Worker defaultWorker) throws IOException {
        for (int i = 0; i< defaultWorker.inputs.length; i++) {
            Worker.Input inputField = defaultWorker.inputs[i];
            String inp = this.requestField(inputField);
            try {
                Object parsedInp = Parser.parseString(inputField.cl(), inp);
                if (parsedInp != null) {
                    inputField.setter().set(parsedInp);
                }
            } catch (ParseException | DateTimeParseException | NumberFormatException e) {
                writer.println("Wrong input format, try again.");
                i--;
            } catch (IllegalArgumentException e) {
                writer.println(e.getMessage());
                i--;
            }
        }
        return defaultWorker;
    }
}