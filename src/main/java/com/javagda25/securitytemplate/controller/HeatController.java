package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.model.Heat;
import com.javagda25.securitytemplate.model.RiderType;
import com.javagda25.securitytemplate.model.Round;
import com.javagda25.securitytemplate.model.dto.MultipleHeatsDto;
import com.javagda25.securitytemplate.service.AccountService;
import com.javagda25.securitytemplate.service.HeatService;
import com.javagda25.securitytemplate.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping(path = "/heat")
@RequiredArgsConstructor
public class HeatController {

    private final HeatService heatService;
    private final RoundService roundService;
    private final AccountService accountService;

    @GetMapping("/setRiders")
    public String setManRidersToHeats(Model model, @RequestParam(name = "roundId") Long roundId) {
        model.addAttribute("roundId", roundId);

        Round round = roundService.findById(roundId);
        model.addAttribute("round", round);

        List<Heat> heats = new ArrayList<>(round.getHeats());
        model.addAttribute("heatsDto", new MultipleHeatsDto(heats));

        Set<Account> manRiders = accountService.ridersByRiderType(RiderType.MAN, round);
        model.addAttribute("manRiders", manRiders);

        Set<Account> womanRiders = accountService.ridersByRiderType(RiderType.WOMAN, round);
        model.addAttribute("womanRiders", womanRiders);

        Set<Account> juniorRiders = accountService.ridersByRiderType(RiderType.JUNIOR, round);
        model.addAttribute("juniorRiders", juniorRiders);

        return "heat-form";
    }

    @PostMapping("/setRiders")
    public String setRidersToHeats(Model model, Long roundId, MultipleHeatsDto multipleHeatsDto) {
        heatService.save(multipleHeatsDto);

        //TODO: redirect to /round/list?eventId={validId} ??
        return "index";
    }
}
