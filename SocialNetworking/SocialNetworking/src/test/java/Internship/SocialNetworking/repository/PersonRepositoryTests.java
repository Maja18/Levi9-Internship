package Internship.SocialNetworking.repository;


import Internship.SocialNetworking.models.Person;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void doesUserExistById() {
        Person testPerson = new Person();
        testPerson.setPersonId(8L);
        testPerson.setName("Miles");
        testPerson.setSurname("Johnson");
        testPerson.setEmail("miles@gmail.com");
        testPerson.setUsername("miles123");
        testPerson.setPassword("123");
        personRepository.save(testPerson);
        Person personToSearch=personRepository.findByPersonId(testPerson.getPersonId());
        assertThat(personToSearch.getPersonId()).isEqualTo(8L);
    }
    @Test
    void doesUserExistByEmail() {
        Person personToSearch=personRepository.findByEmailEquals("milos@gmail.com12");
        assertThat(personToSearch).isNotNull();
    }
    @Test
    void getAllPersons(){
        List<Person> persons = personRepository.findAll();
        assertThat(persons).isNotEmpty();
    }

}
