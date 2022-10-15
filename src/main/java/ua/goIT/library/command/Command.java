package ua.goIT.library.command;

public interface Command {
    boolean canExecute(String input);
    void execute();
}
