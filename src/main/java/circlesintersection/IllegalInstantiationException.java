package circlesintersection;

/**
 * Exception to mark a state when instantiation of some object should not be executed.
 */
public class IllegalInstantiationException extends RuntimeException {
    /**
     * Public constructor.
     * @param message message to be printed when an exception is thrown.
     */
    public IllegalInstantiationException(String message) {
        super(message);
    }
}
