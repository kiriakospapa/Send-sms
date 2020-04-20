package com.example.sendsms;

public class Contact implements  Comparable<Contact>  {

    private  String name;
    private  String phone;
    public Contact(String nameOfContact, String phone) {
        this.name=nameOfContact;
        this.phone=phone;
    }
    public  String getName( ) {
        return  this.name;
    }
    public  String getPhone() {
        return this.phone;
    }


    @Override
    public int compareTo(Contact contact) {


        return this.name.compareTo(contact.getName());
    }
}
