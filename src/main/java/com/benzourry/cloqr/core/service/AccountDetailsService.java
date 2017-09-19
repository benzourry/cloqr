package com.benzourry.cloqr.core.service;
import com.benzourry.cloqr.core.dao.AccountRepository;
//import com.benzourry.atcloqrore.model.Account;
import com.benzourry.cloqr.core.model.Account;
import com.benzourry.cloqr.core.model.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by MohdRazif on 4/13/2015.
 */

@Service("accountDetailsService")
public class AccountDetailsService implements UserDetailsService  {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException{

        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            return null;
        }
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        if (username.equals("admin")) {
            auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        }

        String password = account.getPassword();

        return new org.springframework.security.core.userdetails.User(username, password, auth);
    }


    //        List<GrantedAuthority> authorities =
//                buildUserAuthority(account.getAccountRole());
//
//        return buildUserForAuthentication(account, authorities);
    private User buildUserForAuthentication(Account account,
                                            List<GrantedAuthority> authorities) {
        return new User(account.getUsername(), account.getPassword(),
                account.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<AccountRole> accountRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (AccountRole accountRole : accountRoles) {
            setAuths.add(new SimpleGrantedAuthority(accountRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

}