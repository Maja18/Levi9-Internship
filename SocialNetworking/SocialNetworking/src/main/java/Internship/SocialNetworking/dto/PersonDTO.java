package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonDTO {

    @NotBlank(message = "name is required")
    @Length( min=3,message="name has to have 3 letters at least")
    private String name;
    @NotBlank(message = "surname is required")
    @Length( min=3,message="surname has to have 3 letters at least")
    private String surname;
    @NotBlank(message = "email is required")
    @Email(message = "email is incorrect")
    private String email;
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
}
