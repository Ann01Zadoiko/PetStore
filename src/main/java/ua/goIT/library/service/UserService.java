package ua.goIT.library.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.goIT.library.http.Response;
import ua.goIT.library.model.ApiResponse;
import ua.goIT.library.model.Order;
import ua.goIT.library.model.Pet;
import ua.goIT.library.model.User;

import java.util.List;

public class UserService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Response response;
    private static final String HOST = "https://petstore.swagger.io/v2";
    private static final String CREATE_WITH_ARRAY = "/user/createWithArray";
    private static final String CREATE_WITH_LIST = "/user/createWithList";
    private static final String GET_PUT_DELETE = "/user/";
    private static final String LOG_IN = "/user/login";
    private static final String LOG_OUT = "/user/logout";
    private static final String POST = "/user";

    public UserService(Response response) {
        this.response = response;
    }

    public ApiResponse addListOfUsers(List<User> users){
        String userGson = GSON.toJson(users);
        return GSON.fromJson(response.post(HOST + CREATE_WITH_LIST, userGson).body(), ApiResponse.class);
    }

    public ApiResponse addListOfUsersWithGivenArray(User[] users){
        String userGson = GSON.toJson(users);
        return GSON.fromJson(response.post(HOST + CREATE_WITH_ARRAY, userGson).body(), ApiResponse.class);
    }

    public User getByUsername(String user){
        return GSON.fromJson(response.get(HOST + GET_PUT_DELETE + user).body(), User.class);
    }

    public User update(User user){
        return GSON.fromJson(response.put(HOST + GET_PUT_DELETE, GSON.toJson(user)).body(), User.class);
    }

    public ApiResponse delete(String user){
        return GSON.fromJson(response.delete(HOST + GET_PUT_DELETE + user).body(), ApiResponse.class);
    }

    public ApiResponse logIn(String username, String password){
        return GSON.fromJson(response.get(HOST + LOG_IN + "?username=" + username + "&password=" + password).body(), ApiResponse.class);
    }

    public ApiResponse logOut(){
        return GSON.fromJson(response.get(HOST + LOG_OUT).body(), ApiResponse.class);
    }

    public User add(User user){
        String userGson = GSON.toJson(user);
        return GSON.fromJson(response.post(HOST + POST, userGson).body(), User.class);
    }
}
