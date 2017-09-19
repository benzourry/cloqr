package com.benzourry.cloqr.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MohdRazif on 4/10/2015.
 */

@Entity
@Table(name="LOG_ENTRY")
public class LogEntry implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "logEntrySeq")
    @SequenceGenerator(name = "logEntrySeq", sequenceName = "LOG_ENTRY_SEQ", allocationSize = 1)
    @Column(nullable = false, precision = 0, scale = -127)
    private Long id;

    @JoinColumn(name = "EVENT", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Event event;

    @JoinColumn(name = "ACCOUNT", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Account account;

    @Column(name = "CHECK_IN_TIME")
    private String checkInTime;

    @Column(name = "CHECK_OUT_TIME")
    private String checkOutTime;



    public LogEntry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogEntry)) {
            return false;
        }
        LogEntry other = (LogEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    @Override
    public String toString() {
        return "com.benzourry.cloqr.LogEntry[ id="+id+ " ]";
    }

}
