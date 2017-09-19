package com.benzourry.cloqr.core.dao;

import com.benzourry.cloqr.core.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by MohdRazif on 4/13/2015.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    public Account findByUsername(String username);

//    public List<AccountRole>
}
