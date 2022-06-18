package Internship.SocialNetworking.security.auth;

import Internship.SocialNetworking.models.Authority;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.AuthorityRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.security.TokenUtils;
import Internship.SocialNetworking.service.PersonServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;
    private UserDetailsService userDetailsService;

    private PersonRepository personRepository;

    private AuthorityRepository authorityRepository;

    public TokenAuthenticationFilter(TokenUtils tokenUtils, UserDetailsService userDetailsService,
                                     PersonRepository personRepository,  AuthorityRepository authorityRepository) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String mail;
        String authToken = tokenUtils.getToken(request);
        Claims claims = tokenUtils.getAllClaimsFromToken(authToken);
        String subject = (String) claims.get(Claims.SUBJECT);
        String authorities = (String) claims.get("authorities");

        System.out.println("SUBJECT: " + subject);
        System.out.println("roles: " + authorities);
        authorities = authorities.replace("[", "").replace("]", "");
        String[] authoritiesNames = authorities.split(",");



        if (authToken != null) {
            mail = tokenUtils.getMailFromToken(authToken);

            if (mail != null) {
                System.out.println("EMAIL : " + mail);
                Person user = personRepository.findByEmailEquals(mail);
                for (String s : authoritiesNames){
                    user.addNewAuthority(s);
                }
                //personRepository.save(user);
                UserDetails userDetails = userDetailsService.loadUserByUsername(mail);





                // Is token valid
                if (tokenUtils.validateToken(authToken, userDetails)) {
                    // Create authentication
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
