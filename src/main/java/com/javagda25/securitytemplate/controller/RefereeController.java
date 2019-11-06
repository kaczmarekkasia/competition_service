package com.javagda25.securitytemplate.controller;

import com.javagda25.securitytemplate.model.Rank;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping ("/referee/")
public class RefereeController {

    @GetMapping("/startPage")
    public String refereePanel(@RequestParam (name = "eventId") Long eventId){
        Rank rank = new Rank();
        return "referee-panel";
    }

    @GetMapping("/finalWoman")
    public String finalWomanPanel(){
        return "final-woman-panel";
    }
}
