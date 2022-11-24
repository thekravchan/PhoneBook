package com.bloodxtears.phonebook;

import com.bloodxtears.phonebook.models.Record;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

class PhoneBookTest {

    @Test
    void constructor(){
        PhoneBook phoneBook = new PhoneBook();

        int expected = 0;
        Assertions.assertEquals(expected, phoneBook.getRecords("","").size());
    }

    @Test
    void add() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add(new Record("Danil","+11111111111"));
        phoneBook.add(new Record("Danil","+11111111112"));
        phoneBook.add(new Record("Andrey","+21111111111"));

        int expected = 3;
        Assertions.assertEquals(expected, phoneBook.getRecords("","").size());
    }

    @Test
    void remove() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add(new Record("Danil","+11111111111"));
        phoneBook.add(new Record("Danil","+11111111112"));
        phoneBook.add(new Record("Andrey","+21111111111"));
        phoneBook.remove(new Record("Danil","+11111111112"));

        int expected = 2;
        Assertions.assertEquals(expected, phoneBook.getRecords("","").size());
    }

    @Test
    void edit() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add(new Record("Danil","+11111111111"));
        phoneBook.add(new Record("Danil","+11111111112"));
        phoneBook.add(new Record("Andrey","+21111111111"));
        phoneBook.edit(new Record("Danil","+11111111112"), new Record("Danil","+11111111113"));

        int expected = 3;
        Assertions.assertEquals(expected, phoneBook.getRecords("","").size());
        expected = 2;
        Assertions.assertEquals(expected, phoneBook.getRecords("Danil","").size());
        expected = 0;
        Assertions.assertEquals(expected, phoneBook.getRecords("Danil","+11111111112").size());
        expected = 1;
        Assertions.assertEquals(expected, phoneBook.getRecords("Danil","+11111111113").size());
    }

    @Test
    void clear() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add(new Record("Danil","+11111111111"));
        phoneBook.add(new Record("Danil","+11111111112"));
        phoneBook.add(new Record("Andrey","+21111111111"));
        phoneBook.clear();

        int expected = 0;
        Assertions.assertEquals(expected, phoneBook.getRecords("","").size());
    }

    @Test
    void getRecords() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add(new Record("Danil","+11111111111"));
        phoneBook.add(new Record("Danil","+11111111112"));
        phoneBook.add(new Record("Andrey","+21111111111"));

        ArrayList<Record> expected = new ArrayList<Record>(){{
            add(new Record("Andrey","+21111111111"));
            add(new Record("Danil","+11111111111"));
            add(new Record("Danil","+11111111112"));
        }};
        Assertions.assertEquals(expected, phoneBook.getRecords("",""));
    }

    @Test
    void writeFile() throws IOException {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add(new Record("Danil","+11111111111"));
        phoneBook.add(new Record("Danil","+11111111112"));
        phoneBook.add(new Record("Andrey","+21111111111"));
        File tempFile = File.createTempFile("phonebook", ".json");
        phoneBook.writeFile(tempFile);
        byte[] encoded = Files.readAllBytes(tempFile.toPath());
        if (!tempFile.delete())
            tempFile.deleteOnExit();

        String actual = new String(encoded, StandardCharsets.UTF_8).replace("\r","");
        String expected = "[ {\n" +
                "  \"name\" : \"Andrey\",\n" +
                "  \"phone\" : \"+21111111111\"\n" +
                "}, {\n" +
                "  \"name\" : \"Danil\",\n" +
                "  \"phone\" : \"+11111111111\"\n" +
                "}, {\n" +
                "  \"name\" : \"Danil\",\n" +
                "  \"phone\" : \"+11111111112\"\n" +
                "} ]";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void contains() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add(new Record("Danil","+11111111111"));
        phoneBook.add(new Record("Danil","+11111111112"));
        phoneBook.add(new Record("Andrey","+21111111111"));

        Assertions.assertTrue(phoneBook.contains(new Record("Danil","+11111111112")));
    }
}