package org.neoment.shared.commands;

import com.google.common.collect.Lists;
import org.neoment.client.ClientNetworkIOInterface;
import org.neoment.server.CollectionManager;
import org.neoment.shared.Worker;
import org.neoment.shared.WorkerBatch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.neoment.shared.commands.CommandUtils.wrap;

public class ManagerCommand implements Command {
    CollectionManager manager;
    PrintWriter writer;

    ClientNetworkIOInterface clientIO;

    public ManagerCommand(PrintWriter writer, ClientNetworkIOInterface clientIO) {
        this.writer = writer;
        this.clientIO = clientIO;
    }

    public ManagerCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean clientExecute(String[] commandArgs) throws IOException, ClassNotFoundException {
        return false;
    }

    @Override
    public Object[][] serverExecute(Object[] in) throws IOException, ClassNotFoundException {
        return wrap("");
    }

    public Boolean enoughArgs(int minArgs, String[] args) {
        if (args.length < minArgs) {
            this.writer.println("Invalid command.");
        }
        return (args.length >= minArgs);
    }

    public boolean handleBatchResponse() throws IOException, ClassNotFoundException {
        List<WorkerBatch> batches = new ArrayList<>();
        Integer batchCount;
        do {
            var responseData = this.clientIO.receiveObjects();
            var status = (CommandStatus) responseData[0];

            if (status == CommandStatus.NOT_OK) {
                var outStr = (String) responseData[1];
                this.writer.println(outStr);
                return false;
            }

            batchCount = (Integer) responseData[1];
            var batch = (WorkerBatch) responseData[2];
            batches.add(batch);
            this.writer.print("\rReceived " + String.format("%.0f%%", (float) batches.size() / (float) batchCount * 100) + " of packets");
        } while (batches.size() != batchCount);
        this.writer.println();
        batches.sort(Comparator.comparing(b -> b.batchNo));
        List<Worker> workers = batches.stream()
                .map(b -> b.workers)
                .flatMap(Stream::of).toList();

        for (var w : workers)
            this.writer.println(w.toString());
        return false;
    }

    static Object[][] getObjects(List<Worker> filteredWorkers, int batchSize) {
        if (filteredWorkers.size()==0)
            return wrap(CommandStatus.NOT_OK, "No workers.");

        List<List<Worker>> workerSplits = Lists.partition(filteredWorkers, batchSize);
        List<WorkerBatch> batches = IntStream
                .range(0, workerSplits.size())
                .mapToObj(i -> new WorkerBatch(workerSplits.get(i).toArray(Worker[]::new), i)).toList();


        return batches.stream()
                .map(b -> new Object[]{CommandStatus.OK, batches.size(), b})
                .toList().toArray(Object[][]::new);
    }
}
