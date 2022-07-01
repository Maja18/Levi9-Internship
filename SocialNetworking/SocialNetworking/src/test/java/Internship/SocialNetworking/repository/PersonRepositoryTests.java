package Internship.SocialNetworking.repository;


import Internship.SocialNetworking.models.Person;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PersonRepositoryTests {

    @Resource
    private PersonRepository personRepository;

    @Test
    void doesUserExistByEmail() {
        Person testPerson = new Person();
        testPerson.setPersonId(8L);
        testPerson.setName("Miles");
        testPerson.setSurname("Johnson");
        testPerson.setEmail("miles@gmail.com");
        testPerson.setUsername("miles123");
        testPerson.setPassword("123");
        personRepository.save(testPerson);
        Person personToFind=personRepository.findByEmailEquals(testPerson.getEmail());
        Assertions.assertTrue(personToFind!=null);
    }
    @Test
    void doesUserNotExistById() {
        Person personToSearch=personRepository.findByPersonId(9L);
        assertThat(personToSearch).isNull();
    }
    @Test
    void getAllPersons(){
        List<Person> persons = personRepository.findAll();
        assertThat(persons).isNotEmpty();
    }

}
