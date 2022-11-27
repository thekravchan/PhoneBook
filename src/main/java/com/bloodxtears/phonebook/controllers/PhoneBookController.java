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
}
