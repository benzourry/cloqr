package com.benzourry.cloqr.core.model;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * Created by MohdRazif on 4/10/2015.
 */
@Entity
@Table(name="EVENT")
public class Event implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "eventSeq")
    @SequenceGenerator(name = "eventSeq", sequenceName = "EVENT_SEQ", allocationSize = 1)
    @Column(nullable = false, precision = 0, scale = -127)
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="EVENT_TYPE")
    private String eventType;

    @Column(name="START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name="ORGANIZE_BY")
    private String organizeBy;

    @Column(name="END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name="TOKEN")
    private String token;

    @Column(name="REQUIRE_CHECKOUT")
    private boolean requireCheckout;

//    @Column(name="CHECK_IN_TOKEN")
//    private String checkInToken;
//
//    @Column(name="CHECK_OUT_TOKEN")
//    private String checkOutToken;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<LogEntry> logEntryList;


    public Event() {
    }

    public Event(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOrganizeBy() {
        return organizeBy;
    }

    public void setOrganizeBy(String organizeBy) {
        this.organizeBy = organizeBy;
    }

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch = FetchType.LAZY)
//    @JsonBackReference("event-log")
//    private List<LogEntry> logEntryList;

//    @PrePersist
//    public void prePersist() {
//        String uuid = UUID.randomUUID().toString();
//        String base64 = Base64.getEncoder().encodeToString(uuid.getBytes());
//        this.setToken(base64);
//    }

   @PrePersist
    public void prePersist() {

        UUID uuid = UUID.randomUUID();
           ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
           bb.putLong(uuid.getMostSignificantBits());
           bb.putLong(uuid.getLeastSignificantBits());
        String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(bb.array());
        this.setToken(base64);
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.benzourry.cloqr.Event[ id="+this.id+ " ]";
    }


//    public String getCheckInToken() {
//        return checkInToken;
//    }
//
//    public void setCheckInToken(String checkInToken) {
//        this.checkInToken = checkInToken;
//    }
//
//    public String getCheckOutToken() {
//        return checkOutToken;
//    }
//
//    public void setCheckOutToken(String checkOutToken) {
//        this.checkOutToken = checkOutToken;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getRequireCheckout() {
        return requireCheckout;
    }

    public void setRequireCheckout(boolean requireCheckout) {
        this.requireCheckout = requireCheckout;
    }

//    public List<LogEntry> getLogEntryList() {
//        return logEntryList;
//    }
//
//    public void setLogEntryList(List<LogEntry> logEntryList) {
//        this.logEntryList = logEntryList;
//    }

//    public List<LogEntry> getLogEntryList() {
//        return logEntryList;
//    }
//
//    public void setLogEntryList(List<LogEntry> logEntryList) {
//        this.logEntryList = logEntryList;
//    }
}
