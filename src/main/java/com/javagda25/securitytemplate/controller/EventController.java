package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.model.Event;
import com.javagda25.securitytemplate.repository.EventRepository;
import com.javagda25.securitytemplate.service.AccountService;
import com.javagda25.securitytemplate.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(path = "/event")
public class EventController {

    private EventService eventService;
    private AccountService accountService;
    private EventRepository eventRepository;

    @Autowired
    public EventController(EventService eventService, AccountService accountService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.accountService = accountService;
        this.eventRepository = eventRepository;
    }


    @GetMapping("/add")
    public String addEvent(Model model, Event event) {
        model.addAttribute("event", event);
        return "event-form";
    }

    @GetMapping("/edit")
    public String editEvent(Model model, @RequestParam(name = "eventId") Long eventId) {
        model.addAttribute("eventId", eventId);
        model.addAttribute("event", eventService.findById(eventId));
        return "event-form";

    }

    @PostMapping("/add")
    public String addEvent(Event event) {
        eventService.add(event);

        return "redirect:/event/list";
    }

    @GetMapping("/list")
    public String listEvents(Model model, Principal principal) {
        model.addAttribute("eventList", eventService.listAll());

        if (principal != null) {
            Account currentlyLoggedIn = accountService.findByUsername(principal.getName());
            model.addAttribute("currentlyLoggedIn", currentlyLoggedIn);
        }

        return "event-list";
    }

    @GetMapping("/remove")
    public String removeEvent(@RequestParam(name = "eventId") Long eventId) {
        eventService.remove(eventId);
        return "redirect:/event/list";

    }

    @GetMapping("/toggleStatus")
    public String toggleStatus(@RequestParam(name = "eventId") Long eventId) {
        eventService.toggleStatus(eventId);
        return "redirect:/event/list";

    }

    @GetMapping("/joinEvent")
    public String joinEvent(Model model, @RequestParam(name = "eventId") Long eventId, Principal principal) {
        model.addAttribute("eventId", eventId);

        Event event = eventService.findById(eventId);
        Account rider = accountService.findByUsername(principal.getName());

        model.addAttribute("rider", rider);

        event.getAccounts().add(rider);
        eventRepository.save(event);


        return "redirect:/event/list";
    }

    @GetMapping("/removeRider")
    public String removeRider(@RequestParam(name = "riderId") Long riderId,
                              @RequestParam(name = "eventId") Long eventId) {

        Event event = eventService.findById(eventId);
        Optional<Account> optionalAccount = accountService.findById(riderId);

        optionalAccount.map(rider -> {
            event.getAccounts().remove(rider);
            eventRepository.save(event);
            return rider;
        }).orElseThrow(() -> new IllegalArgumentException("Invalid account id"));

        return "redirect:/event/list";
    }
}
