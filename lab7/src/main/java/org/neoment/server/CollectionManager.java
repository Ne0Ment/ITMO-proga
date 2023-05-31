package org.neoment.server;

import org.neoment.shared.exceptions.WorkerDoesntExistException;
import org.neoment.shared.exceptions.WorkerExistsException;
import org.neoment.shared.Organization;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Handles operations with the Worker Collection.
 * @author neoment
 * @version 0.1
 */
public class CollectionManager {
    private static ReentrantLock locker = new ReentrantLock();
    public interface WorkerFilter {
        boolean filter(Worker worker);
    }
    private TreeSet<Worker> workers;

    private LocalDateTime collectionCreationDate;
    public DBManager dbManager;

    public CollectionManager(DBManager dbManager) {
        workers = new TreeSet<>();
        this.collectionCreationDate = LocalDateTime.now();
        this.dbManager = dbManager;
    }

    public CollectionManager(TreeSet<Worker> workers, LocalDateTime collectionCreationDate, DBManager dbManager) {
        this.workers = workers;
        this.collectionCreationDate = collectionCreationDate;
        this.dbManager = dbManager;
    }

    public void add(Worker newWorker, Long creatorId) throws SQLException {
        locker.lock();
        try {
            if (this.workerExists(newWorker)) throw new WorkerExistsException(newWorker.getId());
            this.workers.add(newWorker);
            this.dbManager.addWorker(newWorker, creatorId);
        }
        finally {
            locker.unlock();
        }
    }

    public void pop(Worker worker, Long userId) throws SQLException {
        if (!this.workerExists(worker)) throw new WorkerDoesntExistException(worker.getId());
        if (Objects.equals(worker.getCreatorId(), userId))
            this.filterWorkers((Worker w) -> !w.equals(worker));
    }

    public Long nextId() {
        return this.dbManager.nextId();
    }

    public String getWorkersInfo() {
        LocalDateTime creationDate = this.getCollectionCreationDate();
        return "Collection stores: Workers \nCollection Length: "+ this.getLength() +
                "\n" + "Collection creation date: " + (creationDate != null ? Parser.genString(creationDate) : "Collection not created.");
    }

    public LocalDateTime getCollectionCreationDate() {
        return collectionCreationDate;
    }

    public Set<Organization> getUniqueOrganizations() {
        locker.lock();
        try {
            Set<Organization> uniqueOrganizations = new HashSet<>();
            Iterator<Worker> workerIterator = this.getIterator();
            while (workerIterator.hasNext()) {
                uniqueOrganizations.add(workerIterator.next().getOrganization());
            }
            return uniqueOrganizations;
        } finally {
            locker.unlock();
        }
    }

    public TreeSet<Worker> getWorkers() {
        return workers;
    }

    public void filterWorkers(WorkerFilter filter) throws SQLException {
        locker.lock();
        try {
            ArrayList<Worker> workers = this.getWorkerList();
            var idsToRemove = (ArrayList<Worker>) workers.stream()
                    .filter((Worker w) -> !filter.filter(w))
                    .collect(Collectors.toList());
            workers = (ArrayList<Worker>) workers.stream()
                    .filter(filter::filter)
                    .collect(Collectors.toList());
            for (var w : idsToRemove)
                this.dbManager.removeWorker(w.getId());
            this.workers = new TreeSet<>(workers);
        } finally {
            locker.unlock();
        }
    }

    public ArrayList<Worker> getWorkerList() {
        locker.lock();
        try {
            ArrayList<Worker> workers = new ArrayList<>();
            Iterator<Worker> workerIterator = this.getIterator();
            workerIterator.forEachRemaining(workers::add);
            return workers;
        } finally {
            locker.unlock();
        }
    }

    public Iterator<Worker> getIterator() {
        return this.workers.iterator();
    }

    public int getLength() {
        return this.workers.size();
    }

    public Boolean workerExists (Worker worker) {
        locker.lock();
        try {
            for (Worker otherWorker : this.workers)
                if (Objects.equals(otherWorker.getId(), worker.getId())) return true;
        } finally {
            locker.unlock();
        }
        return false;
    }

    public Worker getWorkerById (Long id) {
        if (!this.workerExists(new Worker(id))) throw new WorkerDoesntExistException(id);
        return this.workers.stream().filter((Worker w) -> Objects.equals(w.getId(), id)).findFirst().get();
    }

}
