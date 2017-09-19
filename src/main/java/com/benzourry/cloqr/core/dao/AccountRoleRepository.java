package com.benzourry.cloqr.core.dao;

import com.benzourry.cloqr.core.model.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by MohdRazif on 4/13/2015.
 */

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

}
