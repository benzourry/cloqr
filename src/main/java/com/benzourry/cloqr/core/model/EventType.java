package com.benzourry.cloqr.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MohdRazif on 4/14/2015.
 */

@Entity
@Table(name="EVENT_TYPE")
@Setter
@Getter
public class EventType implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CODE", length= 25)
    private String code;

    @Column(name = "NAME", length= 255)
    private String name;

    @Column(name = "DESCRIPTION", length=255)
    private String description;

}
