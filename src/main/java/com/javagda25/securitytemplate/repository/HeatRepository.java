package com.javagda25.securitytemplate.repository;

import com.javagda25.securitytemplate.model.Heat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeatRepository extends JpaRepository<Heat, Long> {
}
