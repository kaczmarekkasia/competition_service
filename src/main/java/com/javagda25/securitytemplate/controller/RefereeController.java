package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.*;
import com.javagda25.securitytemplate.repository.EventRepository;
import com.javagda25.securitytemplate.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.stream.Collectors;

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
    public String refereePanel( Model model, @RequestParam (name = "eventId") Long eventId) {
        Event event = eventService.findById(eventId);
        model.addAttribute("event", event);
        return "referee-panel";
    }

    @GetMapping("/finalWoman")
    public String finalWomanPanel(Model model, RiderRank rank, @RequestParam (name = "eventId") Long eventId){
//        model.addAttribute("eventId", eventId);
        model.addAttribute("rank", rank);

        Event event = eventService.findById(eventId);
        model.addAttribute("event", event);

        Set<Account> womanRidersSet = event.getAccounts().stream()
                .filter(account -> account.getRiderType().equals(RiderType.WOMAN))
                .collect(Collectors.toSet());

        model.addAttribute("womanRidersSet", womanRidersSet);


        return "final-woman-panel";
    }

    @GetMapping("/finalJunior")
    public String finalJuniorPanel(Model model, RiderRank rank, @RequestParam (name = "eventId") Long eventId){
//        model.addAttribute("eventId", eventId);
        model.addAttribute("rank", rank);

        Event event = eventService.findById(eventId);
        model.addAttribute("event", event);

        Set<Account> juniorRidersSet = event.getAccounts().stream()
                .filter(account -> account.getRiderType().equals(RiderType.JUNIOR))
                .collect(Collectors.toSet());

        model.addAttribute("juniorRidersSet", juniorRidersSet);


        return "final-junior-panel";
    }

    @GetMapping("/round1Man")
    public String round1ManPanel(Model model, RiderRank rank, @RequestParam (name = "eventId") Long eventId){
//        model.addAttribute("eventId", eventId);
        model.addAttribute("rank", rank);

        Event event = eventService.findById(eventId);
        model.addAttribute("event", event);

        Set<Account> manRidersSet = event.getAccounts().stream()
                .filter(account -> account.getRiderType().equals(RiderType.MAN))
                .collect(Collectors.toSet());



        model.addAttribute("manRidersSet", manRidersSet);


        return "round1-man-panel";
    }
}
