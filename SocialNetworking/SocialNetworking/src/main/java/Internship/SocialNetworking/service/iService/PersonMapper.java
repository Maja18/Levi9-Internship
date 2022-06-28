package Internship.SocialNetworking.service.iService;


import Internship.SocialNetworking.dto.PersonDTO;
import Internship.SocialNetworking.models.Person;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD
)

public interface PersonMapper {

    PersonDTO personToPersonDTO(Person entity);
    Person personDTOtoPerson(PersonDTO dto);
}
