package com.javagda25.securitytemplate.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany( fetch = FetchType.EAGER)
    private Set<UserComment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "round", fetch = FetchType.EAGER)
    private Set<Heat> heats;
}