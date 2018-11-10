package shared;

/**
 * Created by Kim Säther on 08-Nov-18.
 */
public class MessageException extends RuntimeException {
    public MessageException(String msg) {
        super(msg);
    }
    public MessageException(Throwable rootCause) {
        super(rootCause);
    }
}
