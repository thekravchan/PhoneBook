package com.bloodxtears.phonebook.controllers;

import com.bloodxtears.phonebook.dao.PhoneBookDao;
import com.bloodxtears.phonebook.models.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("phonebook")
//TODO phonebook/records?
public class PhoneBookController {
    private final PhoneBookDao phoneBook;

    @Autowired
    public PhoneBookController(PhoneBookDao phoneBook) {
        this.phoneBook = phoneBook;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecords(@RequestParam(defaultValue = "") String name,
                                        @RequestParam(defaultValue = "") String phone) {
        return ResponseEntity.ok(phoneBook.get(name,phone));
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRecord(@RequestBody Map<String,String> requestBody) {
        Record record;
        try {
            record = new Record(requestBody);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        String resource = "/?name="+record.getName()+"&phone="+record.getPhone();
        try {
            phoneBook.add(record);
            return ResponseEntity.created(URI.create(resource)).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).header(HttpHeaders.LOCATION, resource).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editRecord(@RequestParam Map<String,String> requestParam, @RequestBody Map<String,String> requestBody){
        Record currentRecord, updatedRecord;
        try {
            updatedRecord = new Record(requestBody);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Incorrect request body: " + e.getMessage());
        }
        if (requestParam.containsKey("name") && requestParam.containsKey("phone")){
            try {
                currentRecord = new Record(requestParam.get("name"),requestParam.get("phone"));
            } catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body("Incorrect request params: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Request not empty and not contain expected params!");
        }
        try {
            this.phoneBook.edit(currentRecord,updatedRecord);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(updatedRecord);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteHandler(@RequestParam Map<String,String> requestParam){
        if (requestParam.isEmpty()) {
            return deleteAllRecords();
        } else if (requestParam.containsKey("name") && requestParam.containsKey("phone")){
            return deleteRecord(requestParam.get("name"),requestParam.get("phone"));
        } else {
            return ResponseEntity.badRequest().body("Request not empty and not contain expected params!");
        }
    }

    public ResponseEntity<?> deleteRecord(String name, String phone){
        Record record;
        try {
            record = new Record(name, phone);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        try {
            phoneBook.remove(record);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(record);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> deleteAllRecords(){
        phoneBook.clear();
        return ResponseEntity.noContent().build();
    }
}
