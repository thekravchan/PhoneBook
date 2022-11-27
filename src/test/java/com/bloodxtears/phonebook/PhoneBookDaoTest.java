package com.bloodxtears.phonebook;

import com.bloodxtears.phonebook.dao.PhoneBookDao;
import com.bloodxtears.phonebook.models.Record;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

class PhoneBookDaoTest {

    @Test
    void constructor(){
        PhoneBookDao phoneBookDao = new PhoneBookDao();

        int expected = 0;
        Assertions.assertEquals(expected, phoneBookDao.get("","").size());
    }

    @Test
    void add() {
        PhoneBookDao phoneBookDao = new PhoneBookDao();
        phoneBookDao.add(new Record("Danil","+11111111111"));
        phoneBookDao.add(new Record("Danil","+11111111112"));
        phoneBookDao.add(new Record("Andrey","+21111111111"));

        int expected = 3;
        Assertions.assertEquals(expected, phoneBookDao.get("","").size());
    }

    @Test
    void remove() {
        PhoneBookDao phoneBookDao = new PhoneBookDao();
        phoneBookDao.add(new Record("Danil","+11111111111"));
        phoneBookDao.add(new Record("Danil","+11111111112"));
        phoneBookDao.add(new Record("Andrey","+21111111111"));
        phoneBookDao.remove(new Record("Danil","+11111111112"));

        int expected = 2;
        Assertions.assertEquals(expected, phoneBookDao.get("","").size());
    }

    @Test
    void edit() {
        PhoneBookDao phoneBookDao = new PhoneBookDao();
        phoneBookDao.add(new Record("Danil","+11111111111"));
        phoneBookDao.add(new Record("Danil","+11111111112"));
        phoneBookDao.add(new Record("Andrey","+21111111111"));
        phoneBookDao.edit(new Record("Danil","+11111111112"), new Record("Danil","+11111111113"));

        int expected = 3;
        Assertions.assertEquals(expected, phoneBookDao.get("","").size());
        expected = 2;
        Assertions.assertEquals(expected, phoneBookDao.get("Danil","").size());
        expected = 0;
        Assertions.assertEquals(expected, phoneBookDao.get("Danil","+11111111112").size());
        expected = 1;
        Assertions.assertEquals(expected, phoneBookDao.get("Danil","+11111111113").size());
    }

    @Test
    void clear() {
        PhoneBookDao phoneBookDao = new PhoneBookDao();
        phoneBookDao.add(new Record("Danil","+11111111111"));
        phoneBookDao.add(new Record("Danil","+11111111112"));
        phoneBookDao.add(new Record("Andrey","+21111111111"));
        phoneBookDao.clear();

        int expected = 0;
        Assertions.assertEquals(expected, phoneBookDao.get("","").size());
    }

    @Test
    void getRecords() {
        PhoneBookDao phoneBookDao = new PhoneBookDao();
        phoneBookDao.add(new Record("Danil","+11111111111"));
        phoneBookDao.add(new Record("Danil","+11111111112"));
        phoneBookDao.add(new Record("Andrey","+21111111111"));

        ArrayList<Record> expected = new ArrayList<Record>(){{
            add(new Record("Andrey","+21111111111"));
            add(new Record("Danil","+11111111111"));
            add(new Record("Danil","+11111111112"));
        }};
        Assertions.assertEquals(expected, phoneBookDao.get("",""));
    }

    @Test
    void writeFile() throws IOException {
        PhoneBookDao phoneBookDao = new PhoneBookDao();
        phoneBookDao.add(new Record("Danil","+11111111111"));
        phoneBookDao.add(new Record("Danil","+11111111112"));
        phoneBookDao.add(new Record("Andrey","+21111111111"));
        File tempFile = File.createTempFile("phonebook", ".json");
        phoneBookDao.writeFile(tempFile);
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
        PhoneBookDao phoneBookDao = new PhoneBookDao();
        phoneBookDao.add(new Record("Danil","+11111111111"));
        phoneBookDao.add(new Record("Danil","+11111111112"));
        phoneBookDao.add(new Record("Andrey","+21111111111"));

        Assertions.assertTrue(phoneBookDao.contains(new Record("Danil","+11111111112")));
    }
}