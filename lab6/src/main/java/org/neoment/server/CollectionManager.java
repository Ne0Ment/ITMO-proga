package org.neoment.server;

import org.neoment.shared.exceptions.WorkerDoesntExistException;
import org.neoment.shared.exceptions.WorkerExistsException;
import org.neoment.shared.Organization;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Handles operations with the Worker Collection.
 * @author neoment
 * @version 0.1
 */
public class CollectionManager {

    public interface WorkerFilter {
        boolean filter(Worker worker);
    }
    private TreeSet<Worker> workers;

    private LocalDateTime collectionCreationDate;
    private Long currentId;

    public CollectionManager() {
        workers = new TreeSet<>();
        currentId = 1L;
        this.collectionCreationDate = LocalDateTime.now();
    }

    public CollectionManager(TreeSet<Worker> workers, Long currentId, LocalDateTime collectionCreationDate) {
        this.workers = workers;
        this.currentId = currentId;
        this.collectionCreationDate = collectionCreationDate;
    }

    public void add(Worker newWorker) {
        if (this.workerExists(newWorker)) throw new WorkerExistsException(newWorker.getId());
        this.workers.add(newWorker);
    }

    public void pop(Worker worker) {
        if (!this.workerExists(worker)) throw new WorkerDoesntExistException(worker.getId());
        this.filterWorkers((Worker w) -> !w.equals(worker));
    }

    public Long nextId() {
        this.currentId += 1;
        return this.currentId;
    }

    public String getWorkersInfo() {
        LocalDateTime creationDate = LocalDateTime.MAX;
        for (Worker w : this.workers)
            if (w.getCreationDate().compareTo(creationDate) < 0)
                creationDate=w.getCreationDate();
        return "Collection stores: Workers \nCollection Length: "+ this.getLength() +
                "\n" + "Collection creation date: " + (creationDate != LocalDateTime.MAX ? Parser.genString(creationDate) : "Collection not created.");
    }

    public LocalDateTime getCollectionCreationDate() {
        return collectionCreationDate;
    }

    public Set<Organization> getUniqueOrganizations() {
        Set<Organization> uniqueOrganizations = new HashSet<>();
        Iterator<Worker> workerIterator = this.getIterator();
        while (workerIterator.hasNext()) {
            uniqueOrganizations.add(workerIterator.next().getOrganization());
        }
        return uniqueOrganizations;
    }

    public TreeSet<Worker> getWorkers() {
        return workers;
    }

    public void filterWorkers(WorkerFilter filter) {
        ArrayList<Worker> workers = this.getWorkerList();
        workers = (ArrayList<Worker>) workers.stream()
                .filter(filter::filter)
                .collect(Collectors.toList());
        this.workers = new TreeSet<>(workers);
    }

    public ArrayList<Worker> getWorkerList() {
        ArrayList<Worker> workers = new ArrayList<>();
        Iterator<Worker> workerIterator = this.getIterator();
        workerIterator.forEachRemaining(workers::add);
        return workers;
    }

    public Iterator<Worker> getIterator() {
        return this.workers.iterator();
    }

    public int getLength() {
        return this.workers.size();
    }

    public Boolean workerExists (Worker worker) {
        for (Worker otherWorker : this.workers)
            if (Objects.equals(otherWorker.getId(), worker.getId())) return true;
        return false;
    }

    public Worker getWorkerById (Long id) {
        return this.workers.stream().filter((Worker w) -> Objects.equals(w.getId(), id)).findFirst().get();
    }

    public void clear() {
        this.workers.clear();
    }

    public Long getCurrentId() {
        return currentId;
    }
}
