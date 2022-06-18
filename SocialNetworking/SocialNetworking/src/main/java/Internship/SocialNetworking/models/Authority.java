package Internship.SocialNetworking.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Authority  {

    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    @Column(name ="name")
    private String name;

    @Override
    public String toString(){
        return this.name;
    }


}
