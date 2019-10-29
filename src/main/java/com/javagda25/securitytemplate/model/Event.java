package com.javagda25.securitytemplate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Account> accounts;

    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private String localization;

    @Enumerated(value = EnumType.STRING)
    private EventStatus status;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<UserComment> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (mappedBy = "event", fetch = FetchType.EAGER)
    private Set<Round> rounds;


    public Event(String name, LocalDate date, EventStatus status) {
        this.name = name;
        this.date = date;
        this.status = status;
    }
}
