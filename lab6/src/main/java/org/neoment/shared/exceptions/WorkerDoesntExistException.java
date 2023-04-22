package org.neoment.shared.exceptions;

/**
 * Exception for when a worker object doesn't exist in the collection.
 * @author neoment
 * @version 0.1
 */
public class WorkerDoesntExistException extends RuntimeException{
    public WorkerDoesntExistException (Long workerId) {
        super("Worker with id=" + workerId.toString() + " doesn't exist.");
    }
}
