package com.bloodxtears.phonebook.models;

public class Record {
    private String name;
    private String phone;

    public Record(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
