package sudoku.constants;

public enum Messages {
    GAME_COMPLETE("Congratulations, game complete! Wanna try again?"),
    ERROR("Sorry, we get an error. Oops! Wanna start new game?");
    final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
