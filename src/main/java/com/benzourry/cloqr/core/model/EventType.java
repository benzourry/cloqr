package com.benzourry.cloqr.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MohdRazif on 4/14/2015.
 */

@Entity
@Table(name="EVENT_TYPE")
public class EventType implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "logEntrySeq")
    @SequenceGenerator(name = "logEntrySeq", sequenceName = "LOG_ENTRY_SEQ", allocationSize = 1)
    @Column(nullable = false, precision = 0, scale = -127)
    private Long id;

    @Column(name = "CODE", length= 25)
    private String code;

    @Column(name = "NAME", length= 255)
    private String name;

    @Column(name = "DESCRIPTION", length=255)
    private String description;

    public EventType(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
