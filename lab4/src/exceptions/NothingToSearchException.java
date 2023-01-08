package exceptions;

public class NothingToSearchException extends Exception {
    public NothingToSearchException() {
        super("Отсутствуют вещи для поиска.");
    }
}
