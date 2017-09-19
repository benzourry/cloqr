package com.benzourry.cloqr.core.service;

import com.benzourry.cloqr.core.dao.AccountRepository;
import com.benzourry.cloqr.core.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by MohdRazif on 4/10/2015.
 */
public class RegistrationService {
    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account acc){
        return accountRepository.save(acc);
    }


}
