package org.main;

import org.main.data.Organization;
import org.main.data.Worker;
import org.main.exceptions.WorkerDoesntExistException;
import org.main.exceptions.WorkerExistsException;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Handles operations with the Worker Collection.
 * @author neoment
 * @version 0.1
 */
public class CollectionManager {

    public interface WorkerFilter {
        boolean filter(Worker worker);
    }
    private final TreeSet<Worker> workers;
    private Long currentId;

    public CollectionManager() {
        workers = new TreeSet<>();
        currentId = 1L;
    }

    public CollectionManager(TreeSet<Worker> workers, Long currentId) {
        this.workers = workers;
        this.currentId = currentId;
    }

    public void add(Worker newWorker) {
        if (this.workerExists(newWorker)) throw new WorkerExistsException(newWorker.getId());
        this.workers.add(newWorker);
    }

    public void pop(Worker worker) {
        if (!this.workerExists(worker)) throw new WorkerDoesntExistException(worker.getId());
        this.filterWorkers((Worker w) -> !Objects.equals(w.getId(), worker.getId()));
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
        for (Worker worker : workers) {
            if (!filter.filter(worker)) { this.workers.remove(worker); }
        }
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
        if (worker.getSalary() != null)
            return this.workers.contains(worker);
        for (Worker otherWorker : this.workers)
            if (Objects.equals(otherWorker.getId(), worker.getId())) return true;
        return false;
    }

    public void clear() {
        this.workers.clear();
    }

    public Long getCurrentId() {
        return currentId;
    }
}
