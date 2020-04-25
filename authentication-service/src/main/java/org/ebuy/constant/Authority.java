package org.ebuy.constant;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Burak Köken on 24.4.2020.
 */
public enum Authority implements GrantedAuthority {

    ANONYMOUS_USER(0, "ANONYMOUS_USER"),

    REGISTERED_USER(1, "REGISTERED_USER"),

    MERCHANT_USER(2, "MERCHANT_USER"),

    MARKETING_USER(3, "MARKETING_USER");

    private int id;
    private String tag;

    Authority(int id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public static Authority getRoleById(int id) {
        for(Authority authority : values()) {
            if(authority.id == id) {
                return authority;
            }
        }
        return Authority.ANONYMOUS_USER;
    }

    public static Authority getRoleByTag(String tag) {
        for(Authority authority : values()) {
            if(authority.tag.equals(tag)) {
                return authority;
            }
        }
        return Authority.ANONYMOUS_USER;
    }


    @Override
    public String getAuthority() {
        return name();
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }


}
