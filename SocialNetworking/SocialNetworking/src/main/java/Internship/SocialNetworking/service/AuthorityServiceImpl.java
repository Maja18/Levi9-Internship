package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Authority;
import Internship.SocialNetworking.repository.AuthorityRepository;
import Internship.SocialNetworking.service.iService.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl  implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findById(Long id) {
        return authorityRepository.findByAuthorityId(id);
    }

    @Override
    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }
}
