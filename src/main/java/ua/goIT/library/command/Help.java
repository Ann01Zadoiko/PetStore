package ua.goIT.library.command;

import ua.goIT.library.view.View;

public class Help implements Command{
    private static final String HELP = "help";
    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equals(HELP);
    }

    @Override
    public void execute() {
        view.write(String.format("Enter %s to see all command", Help.HELP));
        view.write(String.format("Enter %s to exit program", Exit.EXIT));
        view.write(String.format("Enter %s and choose a command", PetCommand.PET));
        view.write(String.format("Enter %s and choose a command", UserCommands.USER));
        view.write(String.format("Enter %s and choose a command", StoreCommands.STORE));
    }
}
