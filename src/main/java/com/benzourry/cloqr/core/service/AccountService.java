package com.benzourry.cloqr.core.service;

import com.benzourry.cloqr.core.dao.AccountRepository;
import com.benzourry.cloqr.core.model.Account;
import org.springframework.stereotype.Service;

/**
 * Created by MohdRazif on 9/26/2017.
 */
@Service
public class AccountService {

    private AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account create(Account account){
        return accountRepository.save(account);
    }

    public Account findByUsername(String username){
        Account account = accountRepository.findOne(username);

        if (account != null){

        }else{

        }

        return accountRepository.findOne(username);
    }

}
