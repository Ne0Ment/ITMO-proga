package org.main.exceptions;

/**
 * Exception for when a worker object already exists in the collection.
 * @author neoment
 * @version 0.1
 */
public class WorkerExistsException extends RuntimeException {
    public WorkerExistsException (Long workerId) {
        super("Worker with id=" + workerId.toString() + " already exists.");
    }
}
