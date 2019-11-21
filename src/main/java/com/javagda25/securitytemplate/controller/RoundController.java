package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Event;
import com.javagda25.securitytemplate.model.Round;
import com.javagda25.securitytemplate.service.EventService;
import com.javagda25.securitytemplate.service.HeatService;
import com.javagda25.securitytemplate.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;


@Controller
@RequestMapping(path = "/round")
public class RoundController {

    private HeatService heatService;
    private EventService eventService;
    private RoundService roundService;

    @Autowired
    public RoundController(HeatService heatService, EventService eventService, RoundService roundService) {
        this.heatService = heatService;
        this.eventService = eventService;
        this.roundService = roundService;
    }

    @GetMapping("/list")
    public String setRidersToRounds(Model model, @RequestParam(name = "eventId") Long eventId) {
        model.addAttribute("eventId", eventId);

        Event event = eventService.findById(eventId);

        model.addAttribute("event", event);
        Set<Round> eventRoundsSet = event.getRounds();
        model.addAttribute("eventRoundsSet", eventRoundsSet);
        return "round-list";
    }


}
