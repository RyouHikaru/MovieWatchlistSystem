class InvalidDirectorNameException extends Exception {
    public InvalidDirectorNameException() {
    }
    @Override
    public String getMessage() {
        return "Cannot contain apostrophe or all characters whitespace.";
    }
}
