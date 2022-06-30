package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.MuteRequestDTO;

import Internship.SocialNetworking.mappers.MuteRequestMapper;
import Internship.SocialNetworking.models.GroupNW;

import Internship.SocialNetworking.models.MuteRequest;
import Internship.SocialNetworking.models.Person;

import Internship.SocialNetworking.repository.MuteRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.interface_service.MuteRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MuteRequestServiceImpl implements MuteRequestService {

    private final MuteRequestRepository muteRequestRepository;

    private final PersonRepository personRepository;

    @Override
    public List<MuteRequest> getAllMuteRequests() {
        return muteRequestRepository.findAll();
    }

    @Override
    public void saveMuteRequest(MuteRequest muteRequest) {
        muteRequestRepository.save(muteRequest);
    }

    @Override
    public MuteRequestDTO muteGroup(MuteRequestDTO muteRequestDTO, Long userId) {
        Person user = personRepository.findByPersonId(userId);
        MuteRequest muteRequest = new MuteRequest();
        muteRequest.setGroupId(muteRequestDTO.getGroupId());
        muteRequest.setPersonId(user.getPersonId());
        muteRequest.setMuteStart(LocalDateTime.now());
        muteRequest.setMuteEnd(muteRequestDTO.getMuteEnd());
        muteRequestRepository.save(muteRequest);
        user.getMutedGroups().add(muteRequest);
        personRepository.save(user);

        return MuteRequestMapper.INSTANCE.muteRequestToDto(muteRequest);
    }

    @Override
    public boolean isGroupBlockedPermanently(Long personId, Long groupId) {
        Person person = personRepository.findByPersonId(personId);

        return person.getMutedGroups().stream()
                .filter(request -> request.getMuteEnd().equals("PERMANENT"))
                .anyMatch(request -> request.getGroupId().equals(groupId));
    }

    @Override
    public boolean isGroupBlockedTemporary(Long personId, Long groupId) {
        Person person = personRepository.findByPersonId(personId);

        return person.getMutedGroups().stream()
                .filter(request -> !request.getMuteEnd().equals("PERMANENT"))
                .filter(request ->  LocalDateTime.parse(request.getMuteEnd()).compareTo(LocalDateTime.now()) > 0)
                .anyMatch(request -> request.getGroupId().equals(groupId));
    }
}
