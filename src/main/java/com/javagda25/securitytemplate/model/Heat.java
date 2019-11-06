package com.javagda25.securitytemplate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Heat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany( fetch = FetchType.EAGER)
    private Set<UserComment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Round round;

    public Heat(String name) {
        this.name = name;
    }
}
