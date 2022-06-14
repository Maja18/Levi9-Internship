package Internship.SocialNetworking.Models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PersonId;
    private String Name;
    private String Surname;
    private String Email;
    private String User_name;
    private String Password;
    private String Role;
    @ManyToMany
    @JoinTable(name = "PersonGroup",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "PersonId"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "GroupId"))
    private List<GroupNW> ListGroups;

    public Person(){}

    public Person(long personId, String name, String surname, String email, String user_name, String password, String role) {
        PersonId = personId;
        Name = name;
        Surname = surname;
        Email = email;
        User_name = user_name;
        Password = password;
        Role = role;
    }

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

    public List<GroupNW> getListGroups() {
        return ListGroups;
    }

    public void setListGroups(List<GroupNW> listGroups) {
        ListGroups = listGroups;
    }
}
