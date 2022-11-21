package com.bloodxtears.phonebook.models;

public class Record {
    private String name;
    private String phone;

    public Record(String name, String phone) {
        setName(name);
        setPhone(phone);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        if (!validateName(name))
            throw new IllegalArgumentException("Illegal name format!");
        this.name = name;
    }

    public void setPhone(String phone) {
        if (!validatePhone(phone))
            throw new IllegalArgumentException("Illegal phone format!");
        this.phone = phone;
    }

    private static boolean validatePhone(String phone){
        return phone.matches("^\\+\\d{11}$");
    }

    private static boolean validateName(String name){
        return name.matches("^([A-Z][a-z]+)( [A-Z][a-z]+)?$");
    }

    @Override
    public String toString(){
        return this.name + " " + this.phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Record)) {
            return false;
        }

        Record record = (Record) o;
        return this.name.equals(record.name) && this.phone.equals(record.phone);
    }
}
