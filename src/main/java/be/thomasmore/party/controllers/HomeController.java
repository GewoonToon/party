package be.thomasmore.party.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    private int mySpecialNumber;

    @GetMapping({"/", "/home"})
    public String home(Model model){
        mySpecialNumber = (int) (Math.random()*11+0);
        model.addAttribute("mySpecialNumber", mySpecialNumber);
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        mySpecialNumber = (int) (Math.random()*11+0);
        model.addAttribute("mySpecialNumber", mySpecialNumber);
        return "about";
    }

    @GetMapping("/pay")
    public String pay(Model model){
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("newDate", LocalDate.now().plusDays(30));
        return "pay";
    }

    @GetMapping({"/venuedetails/{venueName}", "/venuedetails"})
    public String venueDetails(Model model, @PathVariable(required = false) String venueName){
        model.addAttribute("venueName", venueName!=null ? venueName : "--No venue chosen--");
        return "venuedetails";
    }

    @GetMapping("/venuelist")
    public String venueList(Model model){
        return "venuelist";
    }
}
