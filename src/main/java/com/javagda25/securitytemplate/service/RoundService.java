package com.javagda25.securitytemplate.service;

import com.javagda25.securitytemplate.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoundService {

    private RoundRepository roundRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }
}
