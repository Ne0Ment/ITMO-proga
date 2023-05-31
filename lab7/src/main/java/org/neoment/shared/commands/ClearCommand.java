package org.neoment.shared.commands;

import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Worker;
import org.neoment.shared.exceptions.NotLoggedInException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Objects;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ClearCommand extends ManagerCommand {
    public ClearCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        super(writer, clientIO);
    }

    public ClearCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException, NotLoggedInException {
        checkLogin();
        var resp = this.clientIO.sendObjects(CommandType.CLEAR, getName(), getPass());
        var status = (CommandStatus) resp[0];
        if (status==CommandStatus.NOT_OK) this.writer.println((String) resp[1]);
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException, SQLException {
        if (!verifyUser((String) in[0], (String) in[1])) return wrap(CommandStatus.NOT_OK, "Couldn't verify user.");
        var user = this.manager.dbManager.getUserByName((String) in[0]);
        this.manager.filterWorkers((Worker worker) -> (worker.getSalary() <= -1) || !Objects.equals(worker.getCreatorId(), user.id));
        return wrap(CommandStatus.OK);
    }
}
