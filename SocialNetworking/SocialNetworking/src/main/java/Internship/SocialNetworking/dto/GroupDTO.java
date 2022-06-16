package Internship.SocialNetworking.dto;

import Internship.SocialNetworking.models.Person;
import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {
    private Long groupId;

    private String name;

    private String description;

    private boolean isPublic;

    private Long creatorId;

    private List<Person> members;




}
