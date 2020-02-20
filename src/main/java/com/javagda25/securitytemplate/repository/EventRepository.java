package com.javagda25.securitytemplate.repository;

import com.javagda25.securitytemplate.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event, Long> {

    boolean existsByName(String eventName);

    Event findByName(String eventName);
}
