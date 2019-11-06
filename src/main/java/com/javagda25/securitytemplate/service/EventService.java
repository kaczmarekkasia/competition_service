package com.javagda25.securitytemplate.service;


import com.javagda25.securitytemplate.model.Event;
import com.javagda25.securitytemplate.model.EventStatus;
import com.javagda25.securitytemplate.model.Heat;
import com.javagda25.securitytemplate.model.Round;
import com.javagda25.securitytemplate.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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

    public void toggleStatus(Long eventId) {
        if (eventRepository.existsById(eventId)) {
            Event eventToEditStatus = eventRepository.findById(eventId).get();
            switch (eventToEditStatus.getStatus()) {
                case PLANNED:
                    eventToEditStatus.setStatus(EventStatus.CURRENT);
                    eventRepository.save(eventToEditStatus);
                    break;
                case CURRENT:
                    eventToEditStatus.setStatus(EventStatus.PAST);

                    creatingNewRoundsAndHeats(eventToEditStatus);


                    eventRepository.save(eventToEditStatus);
                    break;
                default:
                    break;
            }
        }
    }

    private void creatingNewRoundsAndHeats(Event eventToEditStatus) {
        Round round1 = new Round("Round 1 MAN");
        Round round2 = new Round("Final WOMAN");
        Round round3 = new Round("Final JUNIOR");

        Set<Round> roundSet = new HashSet<>(Arrays.asList(round1, round2, round3));

        Heat heat1 = new Heat("1");
        Heat heat2 = new Heat("2");
        Heat heat3 = new Heat("3");
        Heat heat4 = new Heat("4");
        Heat heat5 = new Heat("5");
        Heat heat6 = new Heat("6");
        Heat heat7 = new Heat("7");
        Heat heat8 = new Heat("8");

        Set<Heat> heatSet = new HashSet<>(Arrays.asList(heat1, heat2, heat3, heat4, heat5, heat6, heat7, heat8));

        for (Heat heat : heatSet) {
            round1.getHeats().add(heat);
        }

        for (Round round : roundSet) {
            eventToEditStatus.getRounds().add(round);
        }
    }

//    currentlly unnecessarry
//    public void setLocalization(Long eventId, String localization) {
//        if(eventRepository.existsById(eventId)){
//            Event eventToEditLocalization = eventRepository.findById(eventId).get();
//            eventToEditLocalization.setLocalization(localization);
//            eventRepository.save(eventToEditLocalization);
//        }
//    }

    public Event findById(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if(optionalEvent.isPresent()) {
            return optionalEvent.get();
        }
        throw new EntityNotFoundException();
    }
}
