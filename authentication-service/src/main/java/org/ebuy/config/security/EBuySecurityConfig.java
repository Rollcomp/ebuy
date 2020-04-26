package org.ebuy.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by Burak Köken on 23.4.2020.
 */
@Configuration
public class EBuySecurityConfig {

    @Value("${auth.signingKey:ebuy-test}")
    private String signingKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        converter.setAccessTokenConverter(tokenConverter());
        return converter;
    }

    @Bean
    public EBuyAccessTokenConverter tokenConverter() {
        return new EBuyAccessTokenConverter();
    }

    @Bean
    public EBuyUserAuthenticationConverter authenticationConverter() {
        return new EBuyUserAuthenticationConverter();
    }

    @Bean
    public EBuyTokenServices tokenServices() {
        EBuyTokenServices tokenServices = new EBuyTokenServices();
        tokenServices.setTokenEnhancer(accessTokenConverter());
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setAuthenticationManager(authenticationManager);
        return tokenServices;
    }

}
