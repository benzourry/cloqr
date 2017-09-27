package com.benzourry.cloqr.core.helper.security;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by STLING on 12/17/2015.
 */
public class UserDetailsContextMapperImpl implements UserDetailsContextMapper {
    @Override
    public UserDetails mapUserFromContext(DirContextOperations dirContextOperations, String username, Collection<? extends GrantedAuthority> grantedAuthorities) {
        List<GrantedAuthority> mappedAuthorities = new ArrayList();
//        System.out.println("Mapping user details");
        for (final GrantedAuthority granted : grantedAuthorities){
//            System.out.println(grantedAuthorities);

            mappedAuthorities.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "ROLE_" + granted.getAuthority();
                }
            });
        }
        return new User(username, "",true,true,true,true,mappedAuthorities);
    }
    @Override
    public void mapUserToContext(UserDetails userDetails, DirContextAdapter dirContextAdapter) {

    }
}