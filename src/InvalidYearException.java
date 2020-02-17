public class InvalidYearException extends Exception {

    public InvalidYearException() {
    }
    @Override
    public String getMessage() {
        return "Year should be between 1900 to 2100 only.";
    }
}
