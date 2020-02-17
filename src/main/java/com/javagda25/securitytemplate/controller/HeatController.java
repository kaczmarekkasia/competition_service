package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.model.Heat;
import com.javagda25.securitytemplate.model.RiderType;
import com.javagda25.securitytemplate.model.Round;
import com.javagda25.securitytemplate.model.dto.MultipleHeatsDto;
import com.javagda25.securitytemplate.service.AccountService;
import com.javagda25.securitytemplate.service.EventService;
import com.javagda25.securitytemplate.service.HeatService;
import com.javagda25.securitytemplate.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;


@Controller
@RequestMapping(path = "/heat")
public class HeatController {

    private HeatService heatService;
    private EventService eventService;
    private RoundService roundService;
    private AccountService accountService;

    @Autowired
    public HeatController(HeatService heatService, EventService eventService, RoundService roundService, AccountService accountService) {
        this.heatService = heatService;
        this.eventService = eventService;
        this.roundService = roundService;
        this.accountService = accountService;
    }

    @GetMapping("/setRiders")
    public String setManRidersToHeats(Model model, @RequestParam(name = "roundId") Long roundId) {
        model.addAttribute("roundId", roundId);

        Round round = roundService.findById(roundId);
        model.addAttribute("round", round);
        model.addAttribute("heats", generateHeats(round));
        model.addAttribute("roundHeatSet", round.getHeats());

        Set<Account> manRiders = accountService.ridersByRiderType(RiderType.MAN, round);
        model.addAttribute("manRiders", manRiders);

        Set<Account> womanRiders = accountService.ridersByRiderType(RiderType.WOMAN, round);
        model.addAttribute("womanRiders", womanRiders);

        Set<Account> juniorRiders = accountService.ridersByRiderType(RiderType.JUNIOR, round);
        model.addAttribute("juniorRiders", juniorRiders);

        return "heat-form";
    }

    private MultipleHeatsDto generateHeats(Round round) {
        MultipleHeatsDto multipleHeatsDto = new MultipleHeatsDto();
        round.getHeats()
                .forEach(heat -> multipleHeatsDto.addHeat(new Heat()));

        return multipleHeatsDto;
    }

    @PostMapping("/setRiders")
    public String setRidersToHeats(HttpServletRequest request, Long heatId) {
        heatService.save(request, heatId);

//        todo na co ma iść przekierowanie???
        return "index";
    }


}
