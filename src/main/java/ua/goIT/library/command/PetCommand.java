package ua.goIT.library.command;

import ua.goIT.library.model.*;
import ua.goIT.library.service.PetService;
import ua.goIT.library.view.View;

import java.io.File;
import java.util.Map;

public class PetCommand implements Command{
    public static final String PET = "pet";
    private final View view;
    private final PetService service;
    private static final String COMMANDS = "upload image\nadd\nupdate\nget by status\nget by id\n" +
            "update with form data\ndelete";

    public PetCommand(View view, PetService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equals(PET);
    }

    @Override
    public void execute() {
        view.write(COMMANDS);
        view.write("Enter next command");
        switch (view.read()){
            case "upload image": uploadImage(); break;
            case "add": add(); break;
            case "update": update(); break;
            case "get by status": getByStatus(); break;
            case "get by id": getById();break;
            case "update with form data": updateWithFormData(); break;
            case "delete": delete(); break;
        }
    }

    public void uploadImage(){
        view.write("Enter pet id");
        Pet pet = service.getById(Integer.valueOf(view.read()));
        view.write("Enter filename of image");
        File fileName = new File(view.read());
        if(!fileName.exists()) {
            view.write("File doesn't exist. Please, try again");
        }
        service.uploadImage(pet.getId(),fileName);
        view.write(String.format("%s's photo added",pet.getName()));
    }

    public void add(){
        Pet pet = new Pet();
        Category category = new Category();
        view.write("Enter pet id");
        pet.setId(Integer.valueOf(view.read()));
        view.write("Enter name");
        pet.setName(view.read());
        view.write("Enter pet status");
        pet.setStatus(view.read());
        view.write("Enter category id");
        category.setId(Integer.valueOf(view.read()));
        view.write("Enter category name");
        category.setName(view.read());
        pet.setCategory(category);
        view.write("Enter count of photo urls");
        String[] photoUrls = new String[Integer.valueOf(view.read())];
        view.write("Enter every url in turn");
        for (int i = 0; i < photoUrls.length; i++) {
            photoUrls[i] = view.read();
        }
        pet.setPhotoUrls(photoUrls);
        view.write("Enter count of tags");
        Tag[] tags = new Tag[Integer.valueOf(view.read())];
        for (int i = 0; i < tags.length; i++) {
            Tag tag = new Tag();
            view.write("Enter tag id");
            tag.setId(Integer.valueOf(view.read()));
            view.write("Enter tag name");
            tag.setName(view.read());
            tags[i] = tag;
        }
        pet.setTags(tags);
        Pet newPet = service.add(pet);
        view.write(String.format("Pet %s added", pet.getName()));
    }

    public void update(){
        view.write("Enter pet id");
        Pet pet = service.getById(Integer.valueOf(view.read()));
        Category category = new Category();
        view.write("Enter new name");
        pet.setName(view.read());
        view.write("Enter pet status");
        pet.setStatus(view.read());
        view.write("Enter category id");
        category.setId(Integer.valueOf(view.read()));
        view.write("Enter category name");
        category.setName(view.read());
        pet.setCategory(category);
        view.write("Enter new count of photo urls");
        String[] photoUrls = new String[Integer.valueOf(view.read())];
        view.write("Enter every url in turn");
        for (int i = 0; i < photoUrls.length; i++) {
            photoUrls[i] = view.read();
        }
        pet.setPhotoUrls(photoUrls);
        view.write("Enter new count of tags");
        Tag[] tags = new Tag[Integer.valueOf(view.read())];
        for (int i = 0; i < tags.length; i++) {
            Tag tag = new Tag();
            view.write("Enter tag id");
            tag.setId(Integer.valueOf(view.read()));
            view.write("Enter tag name");
            tag.setName(view.read());
            tags[i] = tag;
        }
        pet.setTags(tags);
        Pet updatePet = service.update(pet);
        view.write(String.format("Pet %s updated", pet.getName()));
    }

    public void getByStatus(){
        Map<String, Integer> status = service.getByStatus();
        status.forEach(((s, integer) -> view.write(s + " " + integer)));
    }

    public void getById(){
        view.write("Enter pet id");
        Integer id = Integer.valueOf(view.read());
        Pet pet = service.getById(id);
        view.write(pet.toString());
    }

    public void updateWithFormData(){
        view.write("Enter pet id");
        Integer id = Integer.valueOf(view.read());
        view.write("Enter pet name");
        String name = view.read();
        view.write("Enter pet status");
        PetStatus status = PetStatus.valueOf(view.read());
        ApiResponse response = service.updateWithFormData(status,id,name);
        view.write(response.getMessage());
    }

    public void delete(){
        view.write("Enter pet id");
        Integer id = Integer.valueOf(view.read());
        ApiResponse apiResponse = service.delete(id);
        Pet pet = service.getById(id);
        view.write(String.format("Pet %s deleted", pet.getName()));
    }
}
