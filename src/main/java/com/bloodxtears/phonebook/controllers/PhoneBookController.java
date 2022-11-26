package com.bloodxtears.phonebook.controllers;

import com.bloodxtears.phonebook.PhoneBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("phonebook")
public class PhoneBookController {
    private final PhoneBookDao phoneBook;

    @Autowired
    public PhoneBookController(PhoneBookDao phoneBook) {
        this.phoneBook = phoneBook;
    }
}
