package com.javagda25.securitytemplate.service;

import com.javagda25.securitytemplate.repository.HeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeatService {

    private HeatRepository heatRepository;

    @Autowired
    public HeatService(HeatRepository heatRepository) {
        this.heatRepository = heatRepository;
    }
}
