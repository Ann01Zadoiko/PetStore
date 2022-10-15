package ua.goIT.library.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ua.goIT.library.http.Response;
import ua.goIT.library.model.*;

import java.util.HashMap;
import java.util.Map;

public class StoreService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Response response;
    private static final String HOST = "https://petstore.swagger.io/v2";
    private static final String STORE_POST = "/store/order";
    private static final String STORE_DELETE = "/store/order/";
    private static final String STORE_GET = "/store/inventory";

    public StoreService(Response response) {
        this.response = response;
    }

    public Order post(Order order){
        String storeGson = GSON.toJson(order);
        return GSON.fromJson(response.post(HOST + STORE_POST, storeGson).body(), Order.class);
    }

    public Order getById(Integer id){
        return GSON.fromJson(response.get(HOST + STORE_DELETE + id).body(), Order.class);
    }

    public Map<String, Integer> getByStatus(){
        return GSON.fromJson(response.get(HOST + STORE_GET).body(), new TypeToken<HashMap<String, Integer>>(){}.getType());
    }

    public ApiResponse delete(Integer id){
        return GSON.fromJson(response.delete(HOST + STORE_DELETE + id).body(), ApiResponse.class);
    }
}
