package com.javagda25.securitytemplate.service;


import com.javagda25.securitytemplate.model.*;
import com.javagda25.securitytemplate.repository.EventRepository;
import com.javagda25.securitytemplate.repository.HeatRepository;
import com.javagda25.securitytemplate.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    private EventRepository eventRepository;
    private HeatRepository heatRepository;
    private RoundRepository roundRepository;

    @Autowired
    public EventService(EventRepository eventRepository, HeatRepository heatRepository, RoundRepository roundRepository) {
        this.eventRepository = eventRepository;
        this.heatRepository = heatRepository;
        this.roundRepository = roundRepository;
    }


    public void add(Event event) {
        event.setStatus(EventStatus.PLANNED);
        eventRepository.save(event);
    }

    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    public void remove(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    /**
     * change event status
     */
    public void toggleStatus(Long eventId) {
        if (eventRepository.existsById(eventId)) {
            Event eventToEditStatus = eventRepository.findById(eventId).get();
            switch (eventToEditStatus.getStatus()) {
                case PLANNED:
                    eventToEditStatus.setStatus(EventStatus.CURRENT);
                    eventRepository.save(eventToEditStatus);
                    creatingEventRounds(eventToEditStatus);
                    break;
                case CURRENT:
                    eventToEditStatus.setStatus(EventStatus.PAST);
                    eventRepository.save(eventToEditStatus);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * By changing the Event status from CURRENT to PLANNED this method creates Rounds for three groups:
     * MAN - Round 1 which is starting the jam
     * WOMAN and MAN - just finals, becouse there is no more than 4 riders
     */
    private void creatingEventRounds(Event eventToEditStatus) {

        Round round1_man = new Round("Round 1 MAN");
        Round finalWoman = new Round("Final WOMAN");
        Round finalJunior = new Round("Final JUNIOR");

        Set<Round> roundSet = new HashSet<>(Arrays.asList(round1_man, finalWoman, finalJunior));

        round1_man.setEvent(eventToEditStatus);
        roundRepository.save(round1_man);
        round1_man.setHeats(creatingRoundHeats(eventToEditStatus, RiderType.MAN, round1_man));

        finalWoman.setEvent(eventToEditStatus);
        roundRepository.save(finalWoman);
        finalWoman.setHeats(creatingRoundHeats(eventToEditStatus, RiderType.WOMAN, finalWoman));

        finalJunior.setEvent(eventToEditStatus);
        roundRepository.save(finalJunior);
        finalJunior.setHeats(creatingRoundHeats(eventToEditStatus, RiderType.JUNIOR, finalJunior));

        eventToEditStatus.setRounds(roundSet);
    }

    /**
     * Based on number of riders creates heats to each round; one heat takes four riders
     */
    private Set<Heat> creatingRoundHeats(Event eventToEditStatus, RiderType riderType, Round round) {
        Set<Account> ridersSet = eventToEditStatus.getAccounts().stream()
                .filter(account -> account.getRiderType().equals(riderType))
                .collect(Collectors.toSet());

        Set<Heat> heatSet = new HashSet<>();

        int x;

        x = (int) Math.ceil(ridersSet.size() / 4.0);

        for (int i = 0; i < x; i++) {
            Heat heat = new Heat(eventToEditStatus.getId() + "_Heat_" + (i + 1) + "_" + riderType);
            heat.setRound(round);
            heatRepository.save(heat);
            heatSet.add(heat);
        }
        return heatSet;
    }

    public Event findById(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        }
        throw new EntityNotFoundException();
    }
}
