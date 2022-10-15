package ua.goIT.library.command;

import ua.goIT.library.exception.ExitException;
import ua.goIT.library.view.View;

public class Exit implements Command{
    public static final String EXIT = "exit";
    private static final String BYE_MESSAGE = "BYE!";
    private final View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equals(EXIT);
    }

    @Override
    public void execute() {
        view.write(BYE_MESSAGE);
        throw new ExitException();
    }
}
