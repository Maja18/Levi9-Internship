package Internship.SocialNetworking.mappers;

import Internship.SocialNetworking.dto.MuteRequestDTO;
import Internship.SocialNetworking.models.MuteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MuteRequestMapper {

    MuteRequestMapper INSTANCE = Mappers.getMapper(MuteRequestMapper.class);
    MuteRequest dtoToMuteRequest(MuteRequestDTO muteRequestDTO);

    MuteRequestDTO muteRequestToDto(MuteRequest muteRequest);

}
