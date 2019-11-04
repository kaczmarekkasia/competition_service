package com.javagda25.securitytemplate.service;


import com.javagda25.securitytemplate.model.Event;
import com.javagda25.securitytemplate.model.EventStatus;
import com.javagda25.securitytemplate.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
                    eventRepository.save(eventToEditStatus);
                    break;
                default:
                    break;
            }
        }
    }

    public void setLocalization(Long eventId, String localization) {
        if(eventRepository.existsById(eventId)){
            Event eventToEditLocalization = eventRepository.findById(eventId).get();
            eventToEditLocalization.setLocalization(localization);
            eventRepository.save(eventToEditLocalization);
        }
    }

    public Event findById(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if(optionalEvent.isPresent()) {
            return optionalEvent.get();
        }
        throw new EntityNotFoundException();
    }
}
