package com.bloodxtears.phonebook.controllers;

import com.bloodxtears.phonebook.PhoneBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
