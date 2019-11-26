package com.javagda25.securitytemplate.service;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.model.Heat;
import com.javagda25.securitytemplate.repository.AccountRepository;
import com.javagda25.securitytemplate.repository.HeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class HeatService {

    private HeatRepository heatRepository;
    private AccountRepository accountRepository;

    @Autowired
    public HeatService(HeatRepository heatRepository, AccountRepository accountRepository) {
        this.heatRepository = heatRepository;
        this.accountRepository = accountRepository;
    }

    public void save(HttpServletRequest request, Long heatId) {
        Map<String, String[]> formParameters = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : formParameters.entrySet()) {
            if (entry.getKey().contains("_")){
                String[] riders_id = entry.getValue();

                for (String id : riders_id) {
                    Long riderId = Long.parseLong(id);
                    Optional<Account> optionalRider = accountRepository.findById(riderId);
                    if (optionalRider.isPresent()) {
                        Account rider = optionalRider.get();
                        Set<Account> riderSet = new HashSet<>();
                        riderSet.add(rider);

                        Optional<Heat> optionalHeat = findById(heatId);

                        if (optionalHeat.isPresent()) {
                            Heat heat = optionalHeat.get();

                            for (Account r : riderSet) {
                                heat.getRiders().add(r);
                            }

                            heatRepository.save(heat);
                        }
                    }else throw new EntityNotFoundException();

                }
            }
        }
    }

        private Optional<Heat> findById (Long heatId){
            return heatRepository.findById(heatId);
        }
    }
