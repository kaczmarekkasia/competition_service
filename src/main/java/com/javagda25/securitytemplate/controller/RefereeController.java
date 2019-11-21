package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.*;
import com.javagda25.securitytemplate.repository.AccountRepository;
import com.javagda25.securitytemplate.repository.EventRepository;
import com.javagda25.securitytemplate.service.AccountService;
import com.javagda25.securitytemplate.service.EventService;
import com.javagda25.securitytemplate.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/referee/")
public class RefereeController {

    private EventService eventService;
    private EventRepository eventRepository;
    private AccountService accountService;
    private AccountRepository accountRepository;
    private RoundService roundService;


    @Autowired
    public RefereeController(EventService eventService, EventRepository eventRepository, AccountService accountService, AccountRepository accountRepository, RoundService roundService) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.roundService = roundService;
    }


    @GetMapping("/startPage")
    public String refereePanel(Model model, @RequestParam(name = "eventId") Long eventId) {
        Event event = eventService.findById(eventId);

//      an option when riders are set to rounds->heats and the referee can judge
        if (roundService.checkRoundsStatus(event) == true) {
            model.addAttribute("event", event);
            return "referee-panel";
        }
//        an option when rounds->heats are still not filled with riders completely
        model.addAttribute("event", event);
        return "referee-panel-in-progress";
    }

    @GetMapping("/finalWoman")
    public String finalWomanPanel(Model model, RiderRank rank, @RequestParam(name = "eventId") Long eventId) {

        model.addAttribute("rank", rank);

        Event event = eventService.findById(eventId);
        model.addAttribute("event", event);

//      todo połączyć zawodników z rundami i heatami
        Set<Account> womanRidersSet = event.getAccounts().stream()
                .filter(account -> account.getRiderType().equals(RiderType.WOMAN))
                .collect(Collectors.toSet());

        model.addAttribute("womanRidersSet", womanRidersSet);


        return "final-woman-panel";
    }

    @PostMapping("/finalWoman")
    public String finalWomanPanel(Model model, Long eventId, HttpServletRequest request, Principal principal) {
        Event event = eventService.findById(eventId);
        Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());
        if (optionalAccount.isPresent()) {
            Account referee = optionalAccount.get();
            accountService.setRidersRank(request, event, referee);
//        todo: wymśleć na jaką stronę ma iść przekierowanie
            return "index";
        }
        throw new EntityNotFoundException();
    }


    @GetMapping("/finalJunior")
    public String finalJuniorPanel(Model model, RiderRank rank, @RequestParam(name = "eventId") Long eventId) {
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
    public String round1ManPanel(Model model, RiderRank rank, @RequestParam(name = "eventId") Long eventId) {
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
