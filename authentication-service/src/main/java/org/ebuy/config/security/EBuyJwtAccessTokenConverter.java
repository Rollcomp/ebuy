package org.ebuy.config.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ebuy.constant.Authority;
import org.ebuy.constant.EBuyJwtTokenClaim;
import org.ebuy.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Created by Burak Köken on 25.4.2020.
 */
public class EBuyJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private static final Log logger = LogFactory.getLog(JwtAccessTokenConverter.class);

    private JsonParser objectMapper = JsonParserFactory.create();
    private String verifierKey = (new RandomValueStringGenerator()).generate();
    private Signer signer;
    private String signingKey;

    @Autowired
    private UserDetailsService userDetailsService;

    public EBuyJwtAccessTokenConverter() {
        this.signer = new MacSigner(this.verifierKey);
        this.signingKey = this.verifierKey;
    }

    private boolean isPublic(String key) {
        return key.startsWith("-----BEGIN");
    }

    @Override
    public void setSigningKey(String key) {
        Assert.hasText(key);
        key = key.trim();
        signingKey = key;
        if(isPublic(key)) {
            signer = new RsaSigner(key);
            logger.info("Configured with RSA signing key");
        } else {
            verifierKey = key;
            signer = new MacSigner(key);
        }
    }

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = this.objectMapper.formatMap(convertAccessToken(accessToken, authentication));
        } catch (Exception var5) {
            throw new IllegalStateException("Cannot convert access token to JSON", var5);
        }

        String token = JwtHelper.encode(content, this.signer).getEncoded();
        return token;
    }

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> response = new HashMap();
        OAuth2Request clientToken = authentication.getOAuth2Request();

        /* user is authenticated */
        if(!authentication.isClientOnly()) {
            if(userDetailsService != null) {
                User user = (User)userDetailsService.loadUserByUsername(authentication.getName());
                /* user id */
                response.put(EBuyJwtTokenClaim.USER_ID, user.getId());
                /* subject */
                response.put(EBuyJwtTokenClaim.SUBJECT, user.getEmail());
                /* email */
                response.put(EBuyJwtTokenClaim.EMAIL, user.getEmail());
                /* role */
                Set<String> authorityList = AuthorityUtils.authorityListToSet(user.getAuthorities());
                Optional<String> authority = authorityList.stream().findFirst();
                authority.ifPresent(s ->
                        response.put(EBuyJwtTokenClaim.ROLE, Authority.getRoleByTag(s).getName())
                );
                /* user status type */
                response.put(EBuyJwtTokenClaim.USER_STATUS_TYPE, user.getUserStatusType());
            }
        } else {

        }

        /* anonid */
        response.put(EBuyJwtTokenClaim.ANONYMOUS_USER_ID, "0");

        /* issue */
        response.put(EBuyJwtTokenClaim.ISSUE, "auth.ebuy.org");
        response.put(EBuyJwtTokenClaim.ISSUED_AT, Calendar.getInstance().getTimeInMillis() / 1000L);

        if(token.getAdditionalInformation().containsKey("jti")) {
            response.put(EBuyJwtTokenClaim.JWT_ID, token.getAdditionalInformation().get("jti"));
        }

        if(token.getExpiration() != null) {
            response.put(EBuyJwtTokenClaim.EXPIRATION, token.getExpiration().getTime() / 1000L);
        }

        if(clientToken.getResourceIds() != null && !clientToken.getResourceIds().isEmpty()) {
            response.put(EBuyJwtTokenClaim.AUDIENCE, clientToken.getResourceIds());
        }

        return response;
    }

}
