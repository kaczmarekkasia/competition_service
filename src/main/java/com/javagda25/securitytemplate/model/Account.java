package com.javagda25.securitytemplate.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3)
    private String username;

    @NotEmpty
    @Size(min = 4)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<AccountRole> accountRoles = new HashSet<>();

    private boolean locked;
    private String name;
    private String surname;
    private String lycraSize;

    @Enumerated(value = EnumType.STRING)
    private RiderType riderType;

    @Enumerated(value = EnumType.STRING)
    private Stance stance;

    private String boardBrand;
    private String kiteBrand;
    private String team;
    private String city;
    private byte[] photo;
    private Integer rankNumber;

    @ManyToMany(mappedBy = "accounts",fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<Event> events;

    public boolean isAdmin() {
        return accountRoles.stream()
                .anyMatch(accountRole -> accountRole.getName().equals("ADMIN"));
    }
}
