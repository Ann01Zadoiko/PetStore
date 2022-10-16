package ua.goIT.library.command;

import ua.goIT.library.model.ApiResponse;
import ua.goIT.library.model.Order;
import ua.goIT.library.service.StoreService;
import ua.goIT.library.view.View;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class StoreCommands implements Command{
    public static final String STORE = "store";
    private final View view;
    private final StoreService service;
    private static final String COMMANDS = "post\nget by status\ndelete\nget by id";

    public StoreCommands(View view, StoreService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equals(STORE);
    }

    @Override
    public void execute() {
        view.write(COMMANDS);
        view.write("Enter next command");
        String command = view.read();
        switch (command){
            case "post": post(); break;
            case "get by status": getByStatus(); break;
            case "delete": delete(); break;
            case "get by id": getById(); break;
        }
    }

    public void post(){
        Order order = new Order();
        try {
            view.write("Enter pet id");
            order.setId(Integer.valueOf(view.read()));
            view.write("Write quantity");
            order.setQuantity(Integer.valueOf(view.read()));
            view.write("Enter date (yyyy-mm-dd)");
            //LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(view.read(), DateTimeFormatter.ISO_LOCAL_DATE), LocalTime.of(0,0,0));
            order.setShipDate(LocalDate.parse(view.read()).toString());
            view.write("Enter status (PLACED, APPROVED, DELIVERED)");
            order.setStatus(view.read());
            view.write("Order is complete?");
            order.setComplete(view.read().equalsIgnoreCase("yes"));
            Order placeOrder = service.post(order);
            view.write(String.format("Order %s placed", placeOrder.getId()));
        } catch (DateTimeException  e){
            view.write("The date is in an illegal format");
        } catch (NumberFormatException ex) {
            view.write("Pet id or quantity is in an illegal format");
        } catch (IllegalArgumentException ex) {
            view.write("Status of the order is in an illegal format");
        }
    }

    public void getByStatus(){
        Map<String, Integer> status = service.getByStatus();
        status.forEach(((s, integer) -> view.write(s + " " + integer)));
    }

    public void delete(){
        view.write("Enter order id");
        Integer id = Integer.valueOf(view.read());
        Order order = service.getById(id);
        if (Objects.isNull(order)){
            view.write(String.format("Order with id %s not found", id));
            return;
        } else {
            ApiResponse apiResponse = service.delete(id);
            view.write(String.format("Order %s deleted", id));
        }
    }

    public void getById(){
        view.write("Enter order id");
        Integer id = Integer.valueOf(view.read());
        Order order = service.getById(id);
        if (Objects.isNull(order)){
            view.write(String.format("Order with id %s not found", id));
            return;
        } else {
            view.write(order.toString());
        }
    }
}
