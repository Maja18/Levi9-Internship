package Internship.SocialNetworking.mappers;


import Internship.SocialNetworking.dto.GroupDTO;
import Internship.SocialNetworking.models.GroupNW;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDTO groupToDto(GroupNW groupNW);

    GroupNW dtoToGroup(GroupDTO groupDTO);



}
