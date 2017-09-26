package com.benzourry.cloqr.core.controller;

import com.benzourry.cloqr.core.dao.AccountRepository;
import com.benzourry.cloqr.core.dao.LogEntryRepository;
import com.benzourry.cloqr.core.model.Account;
import com.benzourry.cloqr.core.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by MohdRazif on 4/17/2015.
 */
@RestController
@RequestMapping("api/account")
public class UserController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LogEntryRepository logEntryRepository;

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public Account getUser(Principal principal, @PathVariable("username") String username) {
        return accountRepository.findOne(username);
    }

    @RequestMapping(value = "{username}/logs", method = RequestMethod.GET)
    public Page<LogEntry> getUserLogs(Principal principal, @PathVariable("username") String username,
                                      Pageable pageable) {
        return logEntryRepository.findByUsername(username, pageable);
    }



}
