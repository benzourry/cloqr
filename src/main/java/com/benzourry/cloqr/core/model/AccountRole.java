package com.benzourry.cloqr.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MohdRazif on 4/13/2015.
 */
@Entity
@Setter
@Getter
public class AccountRole implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Column(name = "ROLE", nullable = false, length = 45)
    private String role;

}
