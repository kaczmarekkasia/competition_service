package com.javagda25.securitytemplate.repository;

import com.javagda25.securitytemplate.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByName(String eventName);
}
