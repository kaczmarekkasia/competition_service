package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Event;
import com.javagda25.securitytemplate.model.Heat;
import com.javagda25.securitytemplate.model.RiderRank;
import com.javagda25.securitytemplate.model.Round;
import com.javagda25.securitytemplate.repository.EventRepository;
import com.javagda25.securitytemplate.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping ("/referee/")
public class RefereeController {

    private EventService eventService;
    private EventRepository eventRepository;

    @Autowired
    public RefereeController(EventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }


    @GetMapping("/startPage")
    public String refereePanel(@RequestParam (name = "eventId") Long eventId){
        return "referee-panel";
    }

    @GetMapping("/finalWoman")
    public String finalWomanPanel(Model model, RiderRank rank, @RequestParam (name = "eventId") Long eventId){
        model.addAttribute("rank", rank);

        Event event = eventService.findById(eventId);
        model.addAttribute("event", event);

        model.addAttribute("ridersList", event.getAccounts());






        return "final-woman-panel";
    }
}
