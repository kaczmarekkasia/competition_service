package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(path = "/rider/")
public class RiderController {

    private AccountService accountService;


    @Autowired
    public RiderController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/add")
    public String addRider(Model model, Principal principal){
        Account rider = accountService.findByUsername(principal.getName());
        model.addAttribute("riderId", rider.getId());
        return "rider-form";
    }

    @PostMapping("/add")
    public String addRider(Account account){
        accountService.saveAsRider(account);
        return "redirect: /";


    }
}
