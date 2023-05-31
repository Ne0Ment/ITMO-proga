package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class ModifiableCommand extends ReaderCommand {
    public ModifiableCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public ModifiableCommand(CollectionManager manager) {
        super(manager);
    }

    public Worker constructWorker() throws IOException {
        Worker worker = new Worker();
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

    public String requestField(Worker.Input inputField) throws IOException{
        if (inputField.cl().isEnum())
            writer.println("Input options: " +
                    String.join(" ", Arrays.stream(inputField.cl().getEnumConstants()).map(Object::toString).toArray(String[]::new)));
        writer.print("Enter " + inputField.description() + " (example: " + Parser.formatExample(inputField.cl()) + "): ");
        writer.flush();
        return reader.readLine();
    }
}
