package Internship.SocialNetworking.config;
import Internship.SocialNetworking.repository.AuthorityRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.security.TokenUtils;
import Internship.SocialNetworking.security.auth.RestAuthenticationEntryPoint;
import Internship.SocialNetworking.security.auth.TokenAuthenticationFilter;
import Internship.SocialNetworking.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,TokenUtils tokenUtils,RestAuthenticationEntryPoint restAuthenticationEntryPoint,CustomUserDetailsService jwtUserDetailsService,PersonRepository personRepository,AuthorityRepository authorityRepository) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .cors().and()

                .authorizeRequests()
                .antMatchers("/auth/login").hasAnyAuthority("ROLE_USER")
                .antMatchers("/api/auth/authority").hasAnyAuthority("ROLE_USER","ROLE_MEMBER","ROLE_ADMIN")
                .antMatchers("/swagger-ui/**", "/javainuse-openapi/**").permitAll()
                .antMatchers("/api/**").permitAll()

                .anyRequest().authenticated().and().addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService, personRepository, authorityRepository), BasicAuthenticationFilter.class);
        http.csrf().disable();

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){

       return web -> web.ignoring().antMatchers("/api/auth/login", "/swagger-ui/**", "/v3/api-docs/**");


    }

}
