package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIO;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class FilterLessCommand extends ManagerCommand {

    public FilterLessCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public FilterLessCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        try {
            Position position = (Position) Parser.parseString(Position.class, commandArgs[1]);
            this.clientIO.sendOneWayObjects(CommandType.FILTER_LESS_THAN, position);

            return handleBatchResponse();
        } catch (Exception e) {
            this.writer.println("Wrong position value.");
        }
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        var position = (Position) in[0];
        ArrayList<Worker> workers = this.manager.getWorkerList();
        var filteredWorkers = workers.stream()
                .filter((Worker w) -> w.getPosition().ordinal() < (position != null ? position.ordinal() : 0))
                .toList();

        return getObjects(filteredWorkers, BATCH_SIZE);
    }

    private static int BATCH_SIZE = 20;
}
