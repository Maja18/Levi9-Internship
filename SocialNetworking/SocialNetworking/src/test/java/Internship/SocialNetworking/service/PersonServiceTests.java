package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTests {


    @Autowired
    private PersonRepository personRepository;

    @Mock
    private GroupRepository groupRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Test
    void testRegistration() {
            Person person=new Person();
            person.setPersonId(1L);
            person.setName("Milos");
            person.setSurname("Milinovic");
            person.setEmail("milosmilinovic9@gmail.com");
            person.setUsername("milos12345");
            person.setPassword(passwordEncoder.encode("milinovic99"));
            personRepository.save(person);

//        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(null);
        Assertions.assertEquals(person.getPersonId(),1);
    }
    @Test
    void testAddingPersonToGroup() {
        GroupNW group=new GroupNW();

        group.setGroupId(5L);
        group.setName("GRUPA 5");
        group.setIsPublic(true);
        group.setCreatorId(1L);

        Person person=new Person();
        person.setPersonId(1L);
        person.setName("Nikola");
        person.setSurname("Perisic");
        person.setUsername("userName");
        person.setPassword(passwordEncoder.encode("password123"));

        List<Person> members=new ArrayList<>();
        members.add(person);
        group.setMembers(members);

        Assertions.assertEquals(group.getCreatorId(),person.getPersonId());
        Assertions.assertFalse(!Objects.equals(group.getCreatorId(), person.getPersonId()));
        for(Person per : group.getMembers()) {
            Assertions.assertEquals(per.getPersonId(), person.getPersonId());
        }
    }
    @Test
    void alterUserInformation() {
        Person person=new Person();
        person.setPersonId(6L);
        person.setName("Marko");
        person.setSurname("Mihajlovic");
        person.setEmail("marko@gmail.com");
        person.setUsername("marko123");
        person.setPassword(passwordEncoder.encode("markoni123"));

        personRepository.save(person);

        Person findPerson=personRepository.findByPersonId(8L);

        findPerson.setEmail("milosmilinovic9@gmail.com");

        Assertions.assertNotEquals(2L,findPerson.getPersonId());
    }
}
