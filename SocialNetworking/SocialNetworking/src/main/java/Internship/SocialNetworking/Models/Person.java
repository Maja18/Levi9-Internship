package Internship.SocialNetworking.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Person {

@Id
    private long PersonId;
@Column(name="Name")
    private String Name;
    @Column(name="LastName")
    private String Surname;
    @Column(name="Email")
    private String Email;
    @Column(name="Username")
    private String User_name;
    @Column(name="Password")
    private String Password;
    @Column(name= "Role")
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
