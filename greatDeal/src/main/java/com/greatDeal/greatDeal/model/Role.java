package com.greatDeal.greatDeal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 34546474787L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int roleId;
    private String name;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserRole> userRole = new HashSet<>();



}
