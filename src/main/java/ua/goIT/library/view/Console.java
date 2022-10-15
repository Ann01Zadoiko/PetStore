package ua.goIT.library.view;

import java.util.Scanner;

public class Console implements View{
    Scanner scanner;

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
