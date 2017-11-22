package com.mickl.messageservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ResourceServerTokenServices tokenService;

    @Autowired
    public SecurityConfig(ResourceServerTokenServices tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
        manager.setTokenServices(this.tokenService);
        return manager;
    }

}
