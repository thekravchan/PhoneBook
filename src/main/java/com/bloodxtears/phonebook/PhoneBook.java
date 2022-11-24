package com.bloodxtears.phonebook;

import com.bloodxtears.phonebook.models.Record;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PhoneBook {
    private final Map<String, Set<String>> phoneBook;

    public PhoneBook() {
        phoneBook = new TreeMap<>();
    }

    public void add(Record record){
        if (!phoneBook.containsKey(record.getName()))
            phoneBook.put(record.getName(), new HashSet<>());

        phoneBook.get(record.getName()).add(record.getPhone());
    }

    public void remove(Record record){
        if (this.contains(record))
            this.phoneBook.get(record.getName()).remove(record.getPhone());
    }

    public void edit(Record currentRecord, Record updatedRecord){
        if (!this.contains(currentRecord))
            throw new IllegalArgumentException("Not found!");
        if (this.contains(updatedRecord))
            throw new IllegalArgumentException("Record already exist!");
        if (!currentRecord.equals(updatedRecord)){
            this.remove(currentRecord);
            this.add(updatedRecord);
        }
    }

    public void clear(){
        phoneBook.clear();
    }

    public ArrayList<Record> getRecords(String name, String phone) {
        ArrayList<Record> records = new ArrayList<>();

        for (Map.Entry<String, Set<String>> item : phoneBook.entrySet()) {
            for (String phoneNumber : item.getValue())
                if (item.getKey().indexOf(name) == 0 && phoneNumber.indexOf(phone) == 0)
                    records.add(new Record(item.getKey(), phoneNumber));
        }

        return records;
    }

    public void writeFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        writer.write(ow.writeValueAsString(this.getRecords("", "")));
        writer.close();
    }

    public boolean contains(Record record){
        return phoneBook.containsKey(record.getName()) &&
                this.phoneBook.get(record.getName()).contains(record.getPhone());
    }
}
