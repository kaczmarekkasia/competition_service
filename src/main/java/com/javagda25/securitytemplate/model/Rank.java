package com.javagda25.securitytemplate.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value;

//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToMany(mappedBy = "rankSet", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
//    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
//    private Set<Account> accounts;
}
