package Internship.SocialNetworking.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Person implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String role;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "authority_id"))
    private List<Authority> authorities;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "group_persons",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    private List<GroupNW> personGroups;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "block_persons",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "post_id"))
    private List<Post> blockedPosts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "person_id"))
    private List<Person> friends;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
