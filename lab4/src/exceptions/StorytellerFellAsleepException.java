package exceptions;

public class StorytellerFellAsleepException extends RuntimeException {
    public StorytellerFellAsleepException() {
        super("Рассказчик уснул.");
    }
}
