package Internship.SocialNetworking.service;


import Internship.SocialNetworking.dto.PersonDTO;
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
    private PersonServiceImpl personService;
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

        Assertions.assertEquals(person.getPersonId(),1);
    }
    @Test
    void testAddingPersonToGroup() {
        GroupNW group=new GroupNW();

        group.setGroupId(4L);
        group.setName("GRUPA 4");
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
        Assertions.assertNotNull(personService.addPersonToGroup(group.getGroupId(),person.getPersonId()));
    }
    @Test
    void alterUserInformation() {
        PersonDTO person=new PersonDTO();
        person.setName("Marko");
        person.setSurname("Mihajlovic");
        person.setEmail("marko@gmail.com");
        person.setUsername("marko123");
        person.setPassword(passwordEncoder.encode("markoni123"));

        Assertions.assertNotNull(personService.alterPersonInformation(person,1L));
    }
    @Test
    void testDeletingUserFromGroup() {
        GroupNW group=new GroupNW();

        group.setGroupId(4L);
        group.setName("GRUPA 4");
        group.setIsPublic(true);
        group.setCreatorId(4L);

        Person person=new Person();
        person.setPersonId(2L);
        person.setName("Milan");
        person.setSurname("Milakovic");
        person.setUsername("userName");
        person.setPassword(passwordEncoder.encode("password123"));

        Person loggedPerson=new Person();
        loggedPerson.setPersonId(4L);
        loggedPerson.setName("Gavrilo");

        List<Person> members=new ArrayList<>();
        members.add(person);
        group.setMembers(members);

        Assertions.assertEquals(4L,group.getGroupId());
        Assertions.assertTrue(Objects.equals(group.getGroupId(), group.getCreatorId()));

    }

    @Test
    void getAllPersons() {

        Assertions.assertNotNull(personService.getAllPersons());
        Assertions.assertTrue(personService.getAllPersons().size()!=0);

    }

}
