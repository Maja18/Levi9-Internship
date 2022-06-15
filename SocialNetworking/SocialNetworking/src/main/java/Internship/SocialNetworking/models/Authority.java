package Internship.SocialNetworking.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Authority implements GrantedAuthority {

    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    @Column(name ="name")
    private String name;


    public Authority(Long authorityId, String name) {
        this.authorityId = authorityId;
        this.name = name;
    }

    public Authority() {}

    public Long getId() {
        return authorityId;
    }

    public void setId(Long id) {
        this.authorityId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return name;
    }
}
