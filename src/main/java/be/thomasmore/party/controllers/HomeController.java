package be.thomasmore.party.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class HomeController {

    private final String[] venueNames = {"De Loods", "De Club", "De Hangar", "Zapoi", "Kuub", "Cuba Libre"};
    private final DayOfWeek[] weekend = {DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};

    @GetMapping({"/", "/home"})
    public String home(Model model){
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }

    @GetMapping("/pay")
    public String pay(Model model){
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("newDate", LocalDate.now().plusDays(30));
        model.addAttribute("weekend", isWeekend(weekend));
        return "pay";
    }

    /*@GetMapping({"/venuedetails/{venueName}", "/venuedetails"})
    public String venueDetails(Model model, @PathVariable(required = false) String venueName){
        model.addAttribute("venueName", venueName!=null ? venueName : "--No venue chosen--");
        return "venuedetails";
    }*/

    @GetMapping("/venuelist")
    public String venueList(Model model){
        model.addAttribute("venueNames", venueNames);
        return "venuelist";
    }

    public boolean isWeekend(DayOfWeek[] weekend){
        for (DayOfWeek day : weekend){
            if (LocalDate.now().getDayOfWeek().equals(day)){return true;}

        }
        return false;
    }

    @GetMapping({"/venuedetails", "/venuedetails/{optVenueIndex}"})
    public String venueDetailsByIndex(Model model, @PathVariable Optional<Integer> optVenueIndex){
        String venueName = null;
        ArrayList<String> errors = new ArrayList<>();
        int venueIndex=0;
        if(optVenueIndex.isPresent()){
            venueIndex = optVenueIndex.get();
        }
        else{errors.add("Geef een nummer");}
        if (venueIndex<0 || venueIndex > venueNames.length-1){
        errors.add("Geef een nummer dat bestaat");
    }

        if (errors.isEmpty()){
        venueName = venueNames[venueIndex];}
        model.addAttribute("index",venueIndex);
        model.addAttribute("errors", errors);
        model.addAttribute("venueName",venueName);
        return "venuedetails";
    }
}
