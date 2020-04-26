package org.ebuy.config.security;

import org.ebuy.constant.EBuyJwtTokenClaim;
import org.ebuy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.*;

/**
 * Created by Burak Köken on 26.4.2020.
 */
public class EBuyAccessTokenConverter extends DefaultAccessTokenConverter {

    private static final String ISSUER_CLAIM_DEFAULT_VALUE = "auth.ebuy.org";

    private EBuyUserAuthenticationConverter userTokenConverter = new EBuyUserAuthenticationConverter();

    @Autowired
    private UserService userService;


    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> response = new HashMap();
        OAuth2Request clientToken = authentication.getOAuth2Request();

        if(!authentication.isClientOnly()) {
            response.putAll(userTokenConverter.convertUserAuthentication(authentication));
        }

        /* anonid */
        response.put(EBuyJwtTokenClaim.ANONYMOUS_USER_ID, "0");

        /* issue */
        response.put(EBuyJwtTokenClaim.ISSUE, ISSUER_CLAIM_DEFAULT_VALUE);
        response.put(EBuyJwtTokenClaim.ISSUED_AT, Calendar.getInstance().getTimeInMillis() / 1000L);

        if(token.getAdditionalInformation().containsKey(EBuyJwtTokenClaim.JWT_ID)) {
            response.put(EBuyJwtTokenClaim.JWT_ID, token.getAdditionalInformation().get(EBuyJwtTokenClaim.JWT_ID));
        }

        if(token.getExpiration() != null) {
            response.put(EBuyJwtTokenClaim.EXPIRATION, token.getExpiration().getTime() / 1000L);
        }

        if(clientToken.getResourceIds() != null && !clientToken.getResourceIds().isEmpty()) {
            response.put(EBuyJwtTokenClaim.AUDIENCE, clientToken.getResourceIds());
        }

        response.putAll(token.getAdditionalInformation());

        return response;
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(value);
        Map<String, Object> info = new HashMap(map);
        info.remove(EBuyJwtTokenClaim.EXPIRATION);
        info.remove(EBuyJwtTokenClaim.AUDIENCE);
        if(map.containsKey(EBuyJwtTokenClaim.EXPIRATION)) {
            token.setExpiration(new Date(((Long)map.get(EBuyJwtTokenClaim.EXPIRATION)).longValue() * 1000L));
        }
        if(map.containsKey(EBuyJwtTokenClaim.JWT_ID)) {
            info.put(EBuyJwtTokenClaim.JWT_ID, map.get(EBuyJwtTokenClaim.JWT_ID));
        }
        token.setAdditionalInformation(info);
        return token;
    }


    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        Map<String, String> parameters = new HashMap();
        Authentication user = userTokenConverter.extractAuthentication(map);

        Set<String> resourceIds = new LinkedHashSet((Collection)(map.containsKey(EBuyJwtTokenClaim.AUDIENCE) ?
                getAudience(map):Collections.emptySet()));

        /* duzeltilecek */
        OAuth2Request request = new OAuth2Request(parameters, "WEB_CLIENT_ID", null, true, null, resourceIds, (String)null, (Set)null, (Map)null);
        return new OAuth2Authentication(request, user);
    }

    private Collection<String> getAudience(Map<String, ?> map) {
        Object auds = map.get(EBuyJwtTokenClaim.AUDIENCE);
        if(auds instanceof Collection) {
            Collection<String> result = (Collection)auds;
            return result;
        } else {
            return Collections.singleton((String)auds);
        }
    }

}
