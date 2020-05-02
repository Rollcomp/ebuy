package org.ebuy.config.security;

import org.ebuy.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by Burak Köken on 26.4.2020.
 */
public class EBuyTokenServices extends DefaultTokenServices {

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtTokenStore tokenStore;

    @Override
    public OAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException, InvalidTokenException {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessTokenValue);
        if(accessToken == null) {
            throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
        } else if(accessToken.isExpired()) {
            tokenStore.removeAccessToken(accessToken);
            throw new InvalidTokenException("Access token expired: " + accessTokenValue);
        } else {
            OAuth2Authentication result = tokenStore.readAuthentication(accessToken);
            if(result == null) {
                throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
            } else {
                if(clientService != null) {
                    String clientId = result.getOAuth2Request().getClientId();
                    try {
                        clientService.loadClientByClientId(clientId);
                    } catch (ClientRegistrationException var6) {
                        throw new InvalidTokenException("Client not valid: " + clientId, var6);
                    }
                }
                return result;
            }
        }
    }
}
