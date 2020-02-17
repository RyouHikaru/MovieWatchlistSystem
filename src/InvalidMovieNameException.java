public class InvalidMovieNameException extends Exception {
    public InvalidMovieNameException() {
    }
    @Override
    public String getMessage() {
        return "Cannot contain apostrophe or all characters whitespace.";
    }
}
