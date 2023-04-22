package org.neoment.server.xml;

import org.neoment.shared.Worker;

import java.util.List;

public class SerializableManager {
    public List<Worker> workerList;

    public String creationDate;

    public SerializableManager(List<Worker> workerList, String creationDate) {
        this.workerList = workerList;
        this.creationDate = creationDate;
    }

    public SerializableManager() {}
}
