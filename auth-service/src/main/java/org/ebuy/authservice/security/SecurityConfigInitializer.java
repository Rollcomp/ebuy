package org.ebuy.authservice.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*
* initialize security config class
* */
public class SecurityConfigInitializer extends AbstractSecurityWebApplicationInitializer {
    public SecurityConfigInitializer(){
        super(SecurityConfiguration.class);
    }
}
