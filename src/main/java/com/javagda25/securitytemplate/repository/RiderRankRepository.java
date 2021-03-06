package com.javagda25.securitytemplate.repository;

import com.javagda25.securitytemplate.model.RiderRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRankRepository extends JpaRepository<RiderRank, Long> {
}
