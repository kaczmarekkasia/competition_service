package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.service.AccountService;
import com.javagda25.securitytemplate.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping(path = "/rider/")
public class RiderController {

    private RiderService riderService;
    private AccountService accountService;


    @Autowired
    public RiderController(RiderService riderService, AccountService accountService) {
        this.riderService = riderService;
        this.accountService = accountService;
    }


    @GetMapping("/add")
    public String addRider(Model model, Principal principal){
        model.addAttribute("username", principal.getName());
        Account rider = accountService.findByUsername(principal.getName());
        model.addAttribute("riderId", rider.getId());
        return "rider-form";
    }

    @PostMapping("/add")
    public String addRider(Long riderId, String name, String username, String lycraSize, String riderType, String stance, String kiteBrand, String boardBrand, String city){
        accountService.saveAsRider(riderId, name, username, lycraSize, riderType, stance, kiteBrand, boardBrand, city);
        return "redirect: /";


    }
}
