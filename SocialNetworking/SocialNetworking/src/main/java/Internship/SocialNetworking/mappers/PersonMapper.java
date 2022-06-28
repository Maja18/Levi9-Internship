package Internship.SocialNetworking.mappers;

import Internship.SocialNetworking.dto.FriendInfoDTO;
import Internship.SocialNetworking.dto.PersonDTO;
import Internship.SocialNetworking.models.Person;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD
)
public interface PersonMapper {
    FriendInfoDTO personToFriendInfoDTO(Person person);
    PersonDTO personToPersonDTO(Person entity);
    Person personDTOtoPerson(PersonDTO dto);
}
