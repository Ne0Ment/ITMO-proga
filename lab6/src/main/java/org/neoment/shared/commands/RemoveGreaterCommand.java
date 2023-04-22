package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class RemoveGreaterCommand extends ModifiableCommand {

    public RemoveGreaterCommand(PrintWriter writer, BufferedReader reader, ClientNetworkIOInterface clientIO) {
        super(writer, reader, clientIO);
    }

    public RemoveGreaterCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        try {
            Float maxSalary = (Float) Parser.parseString(Float.class, commandArgs[1]);
            this.clientIO.sendObjects(CommandType.REMOVE_GREATER, maxSalary);
        } catch (ParseException e) {
            this.writer.println("Wrong salary format. ");
        }
        return super.clientExecute(commandArgs);
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var maxSalary = (Float) in[0];
        this.manager.filterWorkers((Worker worker) -> worker.getSalary() <= maxSalary);
        return wrap(CommandStatus.OK);
    }
}
