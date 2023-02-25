package org.main.commands;

import org.main.BetterBufferedWriter;
import org.main.CollectionManager;
import org.main.Parser;
import org.main.data.Position;
import org.main.data.Worker;

import java.io.IOException;
import java.util.ArrayList;

public class FilterLessCommand extends ManagerCommand {
    public FilterLessCommand(BetterBufferedWriter writer, CollectionManager manager) {
        super(writer, manager);
    }

    @Override
    public boolean execute(String[] commandArgs) throws IOException {
        if (!this.enoughArgs(2, commandArgs)) return false;
        try {
            Position position = (Position) Parser.parseString(Position.class, commandArgs[1]);
            ArrayList<Worker> workers = this.manager.getWorkerList();
            for (Worker w : workers)
                if ( w.getPosition().ordinal() < (position != null ? position.ordinal() : 0))
                    this.writer.printLn(w.toString());
        } catch (Exception e) {
            this.writer.printLn("Wrong position value.");
        }
        return false;
    }
}
