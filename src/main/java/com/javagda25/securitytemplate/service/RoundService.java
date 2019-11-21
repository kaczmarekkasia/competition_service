package com.javagda25.securitytemplate.service;

import com.javagda25.securitytemplate.model.Event;
import com.javagda25.securitytemplate.model.Round;
import com.javagda25.securitytemplate.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class RoundService {

    private RoundRepository roundRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }


    public Round findById(Long eventId) {
        Optional<Round> optionalEvent = roundRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        }
        throw new EntityNotFoundException();
    }

    /**
     * checking if the heats in rounds in event are full with riders
     * */
    public boolean checkRoundsStatus(Event event) {
       boolean roundsStatus = event.getRounds().stream()
                .anyMatch(round -> round.areRidersSetToHeats().equals(false));
       if (roundsStatus == true){
           return false;
       }
       return true;
    }
}
