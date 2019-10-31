package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.model.Event;
import com.javagda25.securitytemplate.service.AccountService;
import com.javagda25.securitytemplate.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Optional;

@Controller
@RequestMapping(path = "/event/")
public class EventController {

    private EventService eventService;
    private AccountService accountService;

    @Autowired
    public EventController(EventService eventService, AccountService accountService) {
        this.eventService = eventService;
        this.accountService = accountService;
    }


    @GetMapping("/add")
    public String addEvent(Model model, Event event) {
        model.addAttribute("event", event);
        return "event-form";
    }

    @GetMapping("/edit")
    public String editEvent(Model model, @RequestParam (name = "eventId") Long eventId) {
        model.addAttribute("eventId", eventId);
        Optional<Event> optionalEvent = eventService.findById(eventId);
        if (optionalEvent.isPresent()) {
            model.addAttribute("event", optionalEvent.get());
            return "event-form";
        }
        return "redirect:/event/list";
    }

    @PostMapping("/add")
    public String addEvent(Event event) {
        eventService.add(event);

        return "redirect:/event/list";
    }

    @GetMapping("/list")
    public String listEvents(Model model) {
        model.addAttribute("eventList", eventService.listAll());

        return "event-list";
    }

    @GetMapping("/remove")
    public String removeEvent(@RequestParam(name = "eventId") Long eventId) {
        eventService.remove(eventId);
        return "redirect:/event/list";

    }

    @GetMapping("/toggleStatus")
    public String toggleStatus(@RequestParam (name = "eventId") Long eventId){
        eventService.toggleStatus(eventId);
        return "redirect:/event/list";

    }


}
