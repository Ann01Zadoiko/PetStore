package ua.goIT.library.command;

import ua.goIT.library.model.ApiResponse;
import ua.goIT.library.model.User;
import ua.goIT.library.service.UserService;
import ua.goIT.library.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserCommands implements Command{
    public static final String USER = "user";
    private final View view;
    private final UserService service;
    private final static String COMMANDS = "add list of users\nadd list of users with given array\nget by username" +
            "\nupdate\ndelete\nlog in\nlog out\nadd";

    public UserCommands(View view, UserService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equals(USER);
    }

    @Override
    public void execute() {
        view.write(COMMANDS);
        view.write("Enter next command");
        String command = view.read();
        switch (command){
            case "add list of users": addListOfUsers(); break;
            case "add list of users with given array": addListOfUsersWithGivenArray(); break;
            case "get by username": getByUsername(); break;
            case "update": update(); break;
            case "delete": delete(); break;
            case "log in": logIn(); break;
            case "log out": logOut(); break;
            case "add": add(); break;
        }
    }

    public void addListOfUsers(){
        List<User> users = new ArrayList<>();
        view.write("Enter count of users");
        for (int i = 0; i < Integer.parseInt(view.read()); i++) {
            users.add(addUser());
        }
        ApiResponse response = service.addListOfUsers(users);
        view.write(response.toString());
    }

    public void addListOfUsersWithGivenArray(){
        view.write("Enter count of users");
        int count = Integer.parseInt(view.read());
        User[] users = new User[count];
        for (int i = 0; i < count; i++) {
            users[i] = addUser();
        }
        ApiResponse response = service.addListOfUsersWithGivenArray(users);
        view.write(response.toString());
    }

    public void getByUsername(){
        view.write("Enter username");
        String username = view.read();
        User user = service.getByUsername(username);
        view.write(user.toString());
    }

    public void update(){
        view.write("Enter username");
        User user = service.getByUsername(view.read());
        view.write("Enter new username");
        user.setUsername(view.read());
        view.write("Enter new first name");
        user.setFirstName(view.read());
        view.write("Enter new second name");
        user.setSecondName(view.read());
        view.write("Enter new email");
        user.setEmail(view.read());
        view.write("Enter user status");
        user.setUserStatus(Integer.valueOf(view.read()));
        view.write("Enter user id");
        user.setId(Integer.valueOf(view.read()));
        view.write("Enter new password");
        user.setPassword(view.read());
        view.write("Enter new phone number");
        user.setPhone(view.read());
        User updateUser = service.update(user);
        view.write(String.format("User %s updated", updateUser.getUsername()));
    }

    public void delete(){
        view.write("Enter username");
        String username = view.read();
        ApiResponse response = service.delete(username);
        view.write(String.format("User %s deleted", username));
    }

    public void logIn(){
        view.write("Enter username");
        String username = view.read();
        view.write("Enter password");
        String password = view.read();
        ApiResponse response = service.logIn(username, password);
        view.write(response.toString());
    }

    public void logOut(){
        ApiResponse response = service.logOut();
        view.write(response.toString());
    }

    public void add(){
//        view.write("Enter username");
//        User user = service.getByUsername(view.read());
//        view.write("Enter username");
//        user.setUsername(view.read());
//        view.write("Enter first name");
//        user.setFirstName(view.read());
//        view.write("Enter second name");
//        user.setSecondName(view.read());
//        view.write("Enter email");
//        user.setEmail(view.read());
//        view.write("Enter user status");
//        user.setUserStatus(Integer.valueOf(view.read()));
//        view.write("Enter user id");
//        user.setId(Integer.valueOf(view.read()));
//        view.write("Enter password");
//        user.setPassword(view.read());
//        view.write("Enter phone number");
//        user.setPhone(view.read());
        User user = addUser();
        User addUser = service.add(user);
        view.write(String.format("User %s updated", addUser.getUsername()));
    }

    private User addUser(){
        view.write("Enter username");
        User user = service.getByUsername(view.read());
        view.write("Enter username");
        user.setUsername(view.read());
        view.write("Enter first name");
        user.setFirstName(view.read());
        view.write("Enter second name");
        user.setSecondName(view.read());
        view.write("Enter email");
        user.setEmail(view.read());
        view.write("Enter user status");
        user.setUserStatus(Integer.valueOf(view.read()));
        view.write("Enter user id");
        user.setId(Integer.valueOf(view.read()));
        view.write("Enter password");
        user.setPassword(view.read());
        view.write("Enter phone number");
        user.setPhone(view.read());
        return user;
    }
}
