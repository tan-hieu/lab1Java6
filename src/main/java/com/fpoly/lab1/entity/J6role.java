package com.fpoly.lab1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "J6roles")
public class J6role {
    @Id
    @Column(name = "Id", nullable = false, length = 50)
    private String id;

    @Nationalized
    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<J6userrole> j6userroles = new LinkedHashSet<>();

}