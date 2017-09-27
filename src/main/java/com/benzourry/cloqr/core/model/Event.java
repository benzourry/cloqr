package com.benzourry.cloqr.core.model;

import lombok.Getter;
import lombok.Setter;

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
@Setter
@Getter
public class Event{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name="LOCATION")
    private String location;

    @Column(name="COMP_CLASS_ID")
    private String compClassId;

    @Column(name="CLASS_GROUP")
    private Long classGroup;

    @Column(name="END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name="TOKEN")
    private String token;

    @Column(name="REQUIRE_CHECKOUT")
    private boolean requireCheckout;

   @PrePersist
    public void prePersist() {

        UUID uuid = UUID.randomUUID();
           ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
           bb.putLong(uuid.getMostSignificantBits());
           bb.putLong(uuid.getLeastSignificantBits());
        String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(bb.array());
        this.setToken(base64);
    }

}
