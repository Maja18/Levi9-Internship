package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.MuteRequestDTO;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.MuteRequest;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.MuteRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.MuteRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MuteRequestServiceImpl implements MuteRequestService {

    private final MuteRequestRepository muteRequestRepository;

    private final PersonRepository personRepository;

    private final GroupRepository groupRepository;

    @Override
    public List<MuteRequest> getAllMuteRequests() {
        return muteRequestRepository.findAll();
    }

    @Override
    public void saveMuteRequest(MuteRequest muteRequest) {
        muteRequestRepository.save(muteRequest);
    }

    @Override
    public String muteGroup(MuteRequestDTO muteRequestDTO) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        Person loggedPerson = (Person) currentUser.getPrincipal();
        Person user = personRepository.findByEmailEquals(loggedPerson.getEmail());
        MuteRequest muteRequest = new MuteRequest();
        muteRequest.setGroupId(muteRequestDTO.getGroupId());
        muteRequest.setPersonId(user.getPersonId());
        muteRequest.setMuteStart(LocalDateTime.now());
        muteRequest.setMuteEnd(muteRequestDTO.getMuteEnd());
        muteRequestRepository.save(muteRequest);
        user.getMutedGroups().add(muteRequest);
        personRepository.save(user);

        return "You have successfully muted notifications from group " +
                groupRepository.findByGroupId(muteRequestDTO.getGroupId()).getName();
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
