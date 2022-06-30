package Internship.SocialNetworking.controller;


import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.models.Authority;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.security.TokenUtils;
import Internship.SocialNetworking.security.auth.JwtAuthenticationRequest;
import Internship.SocialNetworking.service.PostServiceImpl;
import com.nimbusds.jose.proc.SecurityContext;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.Mockito.when;
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

    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    protected WebApplicationContext webAppContext;

    @Test
    void shouldCreateMockMvc(){
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @WithMockUser(username="maja98dragojlovic@gmail.com",roles={"USER","MEMBER"})
    void testGetAllFriendsPosts() throws Exception {
        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Maja");
        person.setSurname("Dragojlovic");
        person.setEmail("maja98dragojlovic@gmail.com");
        person.setPassword("123");
        Authority authority = new Authority();
        authority.setAuthorityId(1L);
        authority.setName("ROLE_USER");
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authority);
        person.setAuthorities(authorityList);

        String jwt = tokenUtils.generateToken(person.getEmail());

        PostDTO post = new PostDTO();
        post.setPostId(1L);
        post.setDescription("Post1");
        post.setCreationDate(LocalDateTime.now());
        post.setCreatorId(1L);
        post.setIsPublic(true);

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Pera");
        List<Person> friends = new ArrayList<>();
        friends.add(friend);
        person.setFriends(friends);
        List<PostDTO> postDTOS = new ArrayList<>();
        postDTOS.add(post);
        when(postService.getAllFriendPosts(person)).thenReturn(postDTOS);
        mockMvc.perform(get("/api/post/friends-posts").
                header(HttpHeaders.AUTHORIZATION, "Bearer "+ jwt))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                // Validate the returned fields
                //.andExpect(MockMvcResultMatchers.jsonPath("$.posts").exists())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.posts[*].postId").isNotEmpty());
    }

}
