package ua.goIT.library;

import ua.goIT.library.command.*;
import ua.goIT.library.controller.Controller;
import ua.goIT.library.http.Response;
import ua.goIT.library.service.PetService;
import ua.goIT.library.service.StoreService;
import ua.goIT.library.service.UserService;
import ua.goIT.library.view.Console;
import ua.goIT.library.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        View view = new Console(new Scanner(System.in));

        Response response = new Response();
        PetService petService = new PetService(response);
        StoreService storeService = new StoreService(response);
        UserService userService = new UserService(response);

        List<Command> commands = new ArrayList<>();
        commands.add(new Exit(view));
        commands.add(new Help(view));
        commands.add(new PetCommand(view, petService));
        commands.add(new StoreCommands(view, storeService));
        commands.add(new UserCommands(view, userService));

        Controller controller = new Controller(view, commands);
        controller.run();
    }
}
