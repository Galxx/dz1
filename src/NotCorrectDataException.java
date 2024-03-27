public class NotCorrectDataException extends RuntimeException {

    private final String message;
    public NotCorrectDataException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
