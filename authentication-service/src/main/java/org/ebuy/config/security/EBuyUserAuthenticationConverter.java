package org.ebuy.config.security;

import org.ebuy.constant.Authority;
import org.ebuy.constant.EBuyJwtTokenClaim;
import org.ebuy.model.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import java.util.*;

/**
 * Created by Burak Köken on 26.4.2020.
 */
public class EBuyUserAuthenticationConverter implements UserAuthenticationConverter {


    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap();
        Object principal = authentication.getPrincipal();
        if(principal instanceof User) {
            User user = (User) authentication.getPrincipal();
            response.put(EBuyJwtTokenClaim.USER_ID, user.getId());
            response.put(EBuyJwtTokenClaim.SUBJECT, user.getEmail());
            response.put(EBuyJwtTokenClaim.EMAIL, user.getEmail());

            Set<String> authorityList = AuthorityUtils.authorityListToSet(user.getAuthorities());
            Optional<String> authority = authorityList.stream().findFirst();
            authority.ifPresent(s ->
                    response.put(EBuyJwtTokenClaim.ROLE, Authority.getRoleByTag(s).getName())
            );
        }
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if(map.containsKey("email")) {
            Object principal = map.get("email");

            List<GrantedAuthority> authorities = new ArrayList(1);
            String roleName = (String) map.get(EBuyJwtTokenClaim.ROLE);
            if(roleName != null) {
                authorities.add(Authority.getRoleByName(roleName));
            }

            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        } else {
            return null;
        }
    }

}
