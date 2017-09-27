package com.benzourry.cloqr.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MohdRazif on 4/10/2015.
 */

@Entity
@Table(name="LOG_ENTRY")
@Getter
@Setter
public class LogEntry implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Event event;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Account account;

    @Column(name = "CHECK_IN_TIME")
    private String checkInTime;

    @Column(name = "CHECK_OUT_TIME")
    private String checkOutTime;

}
