package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.shared.exceptions.WorkerDoesntExistException;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class UpdateCommand extends ModifiableCommand {

    enum CommandStep {
        ID_CHECK,
        UPDATE
    }

    public UpdateCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public UpdateCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        if (!this.enoughArgs(3, commandArgs)) return false;
        try {
            var id = (Long) Parser.parseString(Long.class, commandArgs[2]);
            var response = this.clientIO.sendObjects(CommandType.UPDATE, CommandStep.ID_CHECK, id);

            var commandStatus = (CommandStatus) response[0];
            if (commandStatus == CommandStatus.NOT_OK) { writer.println((String) response[1]); return false; }

            var w = (Worker) response[1];
            Worker defaultWorker = new Worker(w.getId(), w.getName(), w.getCoordinates(), w.getCreationDate(), w.getSalary(), w.getStartDate(), w.getEndDate(), w.getPosition(), w.getOrganization());
            var newWorker = this.constructWorkerWithDefaults(defaultWorker);
            this.clientIO.sendObjects(CommandType.UPDATE, CommandStep.UPDATE, newWorker);
        } catch (ParseException | ClassCastException | NumberFormatException e) {
            this.writer.println("Couldn't parse worker id.");
        }
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var commandStep = (CommandStep) in[0];
        if (commandStep == CommandStep.ID_CHECK) {
            var id = (Long) in[1];
            try {
                if (!this.manager.workerExists(new Worker(id))) throw new WorkerDoesntExistException(id);
                Worker defaultWorker = this.manager.getWorkerById(id);
                return wrap(CommandStatus.OK, defaultWorker);
            } catch (WorkerDoesntExistException e) {
                return wrap(CommandStatus.NOT_OK, e.getMessage());
            }
        } else {
            var newWorker = (Worker) in[1];
            this.manager.pop(new Worker(newWorker.getId()));
            this.manager.add(newWorker);
            return wrap(CommandStatus.OK);
        }
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
