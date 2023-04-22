package org.neoment.shared;

import java.io.Serializable;

public class WorkerBatch implements Serializable {
    public Integer batchNo;
    public Worker[] workers;

    public WorkerBatch(Worker[] workers, int batchNo) {
        this.batchNo = batchNo;
        this.workers = workers;
    }
}
