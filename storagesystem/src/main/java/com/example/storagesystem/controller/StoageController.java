package com.example.storagesystem.controller;

import com.example.storagesystem.model.Storage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;

@RestController
public class StoageController {

    ArrayList<Storage> storage = new ArrayList<>();

    @GetMapping(path="/storage")
    public ArrayList<Storage> getStorage(){
        return storage;
    }

    @PostMapping(path="/storage")
    public ResponseEntity<String> addProduct(@RequestBody Storage store){

        if(!checkInput(store)){
            return ResponseEntity.status(400).body("Please send all the fields");
        }
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId().equals(store.getId())) {
                return ResponseEntity.status(400).body("User id already exists ");
            }
        }
        storage.add(store);

        return ResponseEntity.status(200).body(" created");


    }
    @PostMapping("/addAmount/{id}")
    public ResponseEntity<String> addAmount(@PathVariable String id, @RequestBody String amount){

        for (int i = 0; i < storage.size(); i++) {

            if (id.equals(storage.get(i).getId())) {

                storage.get(i).setAmount(parseInt(amount) + storage.get(i).getAmount());
                return ResponseEntity.status(200).body("Amount added successfully");
            }
        }
        return ResponseEntity.status(400).body("Invalid ID");
    }

    @PostMapping("/deleteAmount/{id}")
    private ResponseEntity deleteAmount(@PathVariable String id, @RequestBody String amount) {

        for (int i = 0; i < storage.size(); i++) {
            Storage u=storage.get(i);

            if (id.equals(storage.get(i).getId()) && parseInt(amount) < u.getAmount()) {
                u.setAmount( u.getAmount()- parseInt(amount)  );
                return ResponseEntity.status(200).body("Amount change ");
            }
            return ResponseEntity.status(400).body("the amount less than stack !! Your stack is  "+u.getAmount() );
        }
        return ResponseEntity.status(400).body("user not found");
    }

    @DeleteMapping(path = "deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct( @PathVariable("id") String id) {

        for (int i = 0; i < storage.size() ; i++) {
            if(storage.get(i).getId().equals(id)){
                if(storage.get(i).getAmount()>0){
                    return ResponseEntity.status(400).body("Please sell the all amount before removing the product");
                }
                storage.remove(i);
                break;
            }
        }
        return ResponseEntity.status(200).body("Product Deleted");
    }







    public boolean checkInput(Storage store){
        if(store.getId()==null||
                store.getName()==null
                || store.getYearOfMake() == 0 )
        {
            return false;
        }
        return  true;
    }
}
