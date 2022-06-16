package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Authority;

public interface AuthorityService {
    public Authority findById(Long id);
    public Authority findByName(String name);
}
