package com.benzourry.cloqr.core.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;


/**
 * Created by MohdRazif on 4/13/2015.
 */
@Entity
@Table(name = "ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Setter
@Getter
public class Account implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String username;

    private String fullname;

    private String universityId;

    private String email;

    private String contactNo;

    @JsonIgnore
    private String password;

    private boolean enabled;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private Set<AccountRole> accountRole = new HashSet<>(0);

//    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
//    @JsonBackReference("log-account")
//    private List<LogEntry> logEntryList;

    public Account(){

    }

    public Account(String username) {
        this.username = username;
    }

    public Account(String username, String password, boolean enabled) {
        this.setUsername(username);
        this.password = password;
        this.enabled = enabled;
    }

    public Account(String username, String password,
                   boolean enabled, Set<AccountRole> accountRole) {
        this.setUsername(username);
        this.password = password;
        this.enabled = enabled;
        this.accountRole = accountRole;
    }

//    public String getUsername() {
//        return this.username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return this.password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public boolean isEnabled() {
//        return this.enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public Set<AccountRole> getAccountRole() {
//        return this.accountRole;
//    }
//
//    public void setAccountRole(Set<AccountRole> accountRole) {
//        this.accountRole = accountRole;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getFullname() {
//        return fullname;
//    }
//
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }

//    public List<LogEntry> getLogEntryList() {
//        return logEntryList;
//    }
//
//    public void setLogEntryList(List<LogEntry> logEntryList) {
//        this.logEntryList = logEntryList;
//    }


//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (username != null ? username.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Account)) {
//            return false;
//        }
//        Account other = (Account) object;
//        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
//            return false;
//        }
//        return true;
//    }
//    @Override
//    public String toString() {
//        return "com.benzourry.cloqr.Account[ id=" + username + " ]";
//    }

}