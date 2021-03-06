package com.javagda25.securitytemplate.model;


import lombok.*;

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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany( fetch = FetchType.EAGER)
    private Set<UserComment> comments;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Round round;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Account> riders;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    private Set<RiderRank> rankSet;


    private Boolean isFullOfRiders = false;

    public Heat(String name) {
        this.name = name;
    }

    public void setHeatAsFullOfRiders(){
        isFullOfRiders = true;
    }
}
