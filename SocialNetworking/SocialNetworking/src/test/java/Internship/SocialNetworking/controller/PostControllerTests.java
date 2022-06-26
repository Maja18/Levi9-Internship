package Internship.SocialNetworking.controller;


import Internship.SocialNetworking.models.Authority;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.security.TokenUtils;
import Internship.SocialNetworking.security.auth.JwtAuthenticationRequest;
import Internship.SocialNetworking.service.PostServiceImpl;
import com.nimbusds.jose.proc.SecurityContext;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PostServiceImpl postService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    protected WebApplicationContext webAppContext;

    @Test
    void shouldCreateMockMvc(){
        assertThat(mockMvc).isNotNull();
    }

    @BeforeEach
    public void createUser() {
        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Pera");
        person.setSurname("Peric");
        person.setEmail("maja@gmail.com");
        person.setPassword("123");
        Authority authority = new Authority();
        authority.setAuthorityId(1L);
        authority.setName("ROLE_USER");
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authority);
        person.setAuthorities(authorityList);
        System.out.println(person.getPassword());
        System.out.println(person.getUsername());
        System.out.println(person.getAuthorities());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(person.getUsername(),
                        person.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Person p = (Person) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(p.getEmail());
        System.out.println("*********************");
        System.out.println(jwt);
        int expiresIn = tokenUtils.getExpiredIn();
    }

    @Test
    @WithMockUser(username="maja@gmail.com",roles={"USER","MEMBER"})
    void testGetAllFriendsPosts() throws Exception {
        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Maja");
        person.setSurname("Dragojlovic");
        person.setEmail("pera@gmail.com");
        person.setPassword("123");
        Authority authority = new Authority();
        authority.setAuthorityId(1L);
        authority.setName("ROLE_USER");
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authority);
        person.setAuthorities(authorityList);
        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("my first post");
        post.setCreationDate(LocalDateTime.now());
        post.setCreatorId(1L);
        post.setPublic(true);
        doReturn(Lists.newArrayList(post)).when(postService).getAllFriendPosts(person);
        mockMvc.perform(get("/api/post"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/post"))

                // Validate the returned fields
                .andExpect(MockMvcResultMatchers.jsonPath("$.posts").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.posts[*].postId").isNotEmpty());
    }

}
