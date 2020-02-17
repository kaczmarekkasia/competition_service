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

    private String name;

    @OneToMany( fetch = FetchType.EAGER)
    private Set<UserComment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "round", fetch = FetchType.EAGER)
    private Set<Heat> heats;

    public Round(String name) {
        this.name = name;
    }

    /**
     * provides information about heat rider status*/
    public Boolean areRidersSetToHeats(){
        long heatsStatus = heats.stream()
                .filter(heat -> heat.getIsFullOfRiders().equals(false))
                .count();

        return heatsStatus <= 0;
    }
}
