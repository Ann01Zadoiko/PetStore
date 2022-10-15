package ua.goIT.library.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ua.goIT.library.http.Response;
import ua.goIT.library.model.ApiResponse;
import ua.goIT.library.model.Pet;
import ua.goIT.library.model.PetStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetService{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private  Response response = new Response();
    private static final CloseableHttpClient CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();
    private static final String HOST = "https://petstore.swagger.io/v2";
    private static final String PET_PUT = "/pet";
    private static final String PET_POST = "/pet/";
    private static final String PET_UPLOAD_IMAGE = "/uploadImage";
    private static final String PET_FIND_BY_STATUS = "findByStatus?status=";

    public PetService(Response response) {
        this.response = response;
    }

    public ApiResponse uploadImage(int id, File image){
        HttpPost uploadFile = new HttpPost(URI.create(HOST + PET_POST + id + PET_UPLOAD_IMAGE));
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("additionalMetaData", "photo", ContentType.TEXT_PLAIN);
        try {
            builder.addBinaryBody("file", new FileInputStream(image),
                    ContentType.APPLICATION_OCTET_STREAM, image.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        try {
            CLOSEABLE_HTTP_CLIENT.execute(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GSON.fromJson(String.format("%s%s%d%s", HOST, PET_POST, id, PET_UPLOAD_IMAGE), ApiResponse.class);
        }

    public Pet add(Pet pet){
        String petGson = GSON.toJson(pet);
        return GSON.fromJson(response.post(HOST + PET_PUT, petGson).body(), Pet.class);
    }

    public Pet update(Pet pet){
        return GSON.fromJson(response.put(HOST + PET_PUT, GSON.toJson(pet)).body(), Pet.class);
    }

    public Map<String, Integer> getByStatus(){
        return GSON.fromJson(response.get(HOST + PET_POST + PET_FIND_BY_STATUS).body(), new TypeToken<HashMap<String, Integer>>(){}.getType());
    }

    public Pet getById(Integer id){
        return GSON.fromJson(response.get(HOST + PET_POST + id).body(), Pet.class);
    }

    public ApiResponse updateWithFormData(PetStatus petStatus, Integer id, String name){
        Map<String, String> formData = new HashMap<>();
        formData.put("name", name);
        formData.put("status", petStatus.toString());
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        String body = formBodyBuilder.toString();
        return GSON.fromJson(response.postFormData(HOST + PET_POST + id,body).body(), ApiResponse.class);
    }

    public ApiResponse delete(Integer id){
        return GSON.fromJson(response.delete(HOST + PET_POST + id).body(), ApiResponse.class);
    }
}
