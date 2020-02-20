package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.model.Event;
import com.javagda25.securitytemplate.service.AccountService;
import com.javagda25.securitytemplate.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(path = "/rider/")
@RequiredArgsConstructor
public class RiderController {

    private final AccountService accountService;
    private final EventService eventService;

    @GetMapping("/add")
    public String addRider(Model model, Principal principal) {
        Account rider = accountService.findByUsername(principal.getName());
        model.addAttribute("id", rider.getId());
        model.addAttribute("rider", rider);

        return "rider-form";
    }

    @PostMapping("/add")
    public String addRider(Account rider, Principal principal) {
        accountService.saveAsRider(rider, principal);

        return "redirect:/event/list";
    }

    @GetMapping("/list")
    public String listRiders(Model model, @RequestParam(name = "eventId", required = false) Long eventId) {
        Event event = eventService.findById(eventId);

        model.addAttribute("event", event);
        model.addAttribute("ridersList", event.getAccounts());

        return "rider-list";
    }

    @GetMapping("/info")
    public String riderInfo(Model model, @RequestParam(name = "riderId") Long riderId,
                            @RequestParam(name = "eventId") Long eventId) {
        Optional<Account> optionalAccount = accountService.findById(riderId);

        Event event = eventService.findById(eventId);

        optionalAccount.map(account -> {
            Account rider = optionalAccount.get();
            model.addAttribute("rider", rider);
            model.addAttribute("event", event);

            return account;
        }).orElseThrow(EntityNotFoundException::new);

        return "rider-info";
    }

}
