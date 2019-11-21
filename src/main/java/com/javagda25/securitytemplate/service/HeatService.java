package com.javagda25.securitytemplate.service;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.model.Heat;
import com.javagda25.securitytemplate.repository.AccountRepository;
import com.javagda25.securitytemplate.repository.HeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class HeatService {

    private HeatRepository heatRepository;
    private AccountRepository accountRepository;

    @Autowired
    public HeatService(HeatRepository heatRepository, AccountRepository accountRepository) {
        this.heatRepository = heatRepository;
        this.accountRepository = accountRepository;
    }

    public void save(Heat heat, Long riderId) {
        if(accountRepository.existsById(riderId)){
            Account rider = accountRepository.getOne(riderId);

            heat.getRiders().add(rider);

            heatRepository.save(heat);

        }
        throw new EntityNotFoundException();

    }
}
