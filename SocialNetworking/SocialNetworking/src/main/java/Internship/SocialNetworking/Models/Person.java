package Internship.SocialNetworking.Models;

import java.util.List;

public class Person {

    private long PersonId;
    private String Name;
    private String Surname;
    private String Email;
    private String User_name;
    private String Password;
    private String Role;

    public Person(){}

    public long getPersonId() {
        return PersonId;
    }

    public void setPersonId(long personId) {
        PersonId = personId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

}
