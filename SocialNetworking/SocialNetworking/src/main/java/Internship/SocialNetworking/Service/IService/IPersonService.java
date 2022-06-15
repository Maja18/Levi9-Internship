package Internship.SocialNetworking.Service.IService;

import Internship.SocialNetworking.Models.Person;

import java.util.List;

public interface IPersonService {

    public Person AddPerson(Person person);

    public List<Person> GetAllPersons();

    public Person GetPerson(Long PersonId);
}
