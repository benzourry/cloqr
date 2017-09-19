package com.benzourry.cloqr.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MohdRazif on 4/13/2015.
 */
@Entity
public class AccountRole implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "accountRoleSeq")
    @SequenceGenerator(name = "accountRoleSeq", sequenceName = "ACCOUNT_ROLE_SEQ", allocationSize = 1)
    @Column(nullable = false, precision = 0, scale = -127)
    private Long id;

    @JoinColumn(name = "ACCOUNT", referencedColumnName = "USERNAME", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Column(name = "ROLE", nullable = false, length = 45)
    private String role;

    public AccountRole() {
    }

    public AccountRole(Account account, String role) {
        this.account = account;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
