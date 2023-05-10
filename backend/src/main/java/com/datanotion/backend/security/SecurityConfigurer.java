package com.datanotion.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.datanotion.backend.middlewares.CORSMiddleware;
import com.datanotion.backend.middlewares.JWTAuthorizationMiddleware;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private DatanotionUserDetailsService datanotionUserDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.userDetailsService(datanotionUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // login and register are public!
        httpSecurity.authorizeRequests().antMatchers("/login").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/users/register").permitAll();

        // swagger is public!
        httpSecurity.authorizeRequests().antMatchers("/swagger-ui.html").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/swagger-ui/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/v3/**").permitAll();

        // all other requests must be authorized
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/projects").hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.DELETE, "/projects/{id}").hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.PUT, "/projects/{id}").hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/projects/{id}/users").hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.DELETE, "/projects/{id}/users").hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/projects/{id}/users/managers")
                .hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/projects/{id}/users/annotators")
                .hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/users/annotators").hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/users/managers").hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/users/annotators").hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/projects/{id}/imports").hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/projects/{id}/imports").hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/projects/{id}/exports").hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/projects/{id}/exports/{export_id}")
                .hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/projects/{id}/exports").hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/projects/{id}/entity").hasAuthority("manager");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.DELETE, "/projects/{id}/entity/{entityId}")
                .hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/projects/{id}/classification")
                .hasAuthority("manager");
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/projects/{id}/classification/{classificationTagId}")
                .hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.DELETE, "/projects/{projectId}/tasks/{taskId}")
                .hasAuthority("manager");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/projects/{id}/statistics")
                .hasAuthority("manager");

        httpSecurity.authorizeRequests().anyRequest().authenticated();

        httpSecurity.addFilter(new JWTAuthenticationFilter(
                authenticationManagerBean()));
        httpSecurity.addFilterBefore(new JWTAuthorizationMiddleware(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(new CORSMiddleware(), JWTAuthorizationMiddleware.class);
    }
}
