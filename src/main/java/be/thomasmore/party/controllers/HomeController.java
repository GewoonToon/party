package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private VenueRepository venueRepository;


    private final String[] venueNames = {"De Loods", "De Club", "De Hangar", "Zapoi", "Kuub", "Cuba Libre"};
    private final DayOfWeek[] weekend = {DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
    private final Venue[] venues = {
            new Venue("De Loods", "link", 150, false, true, false, true, "Mechelen", 1),
            new Venue("De club", "link",200, false, false, true, false, "Sint-Katelijne", 4 ),
            new Venue("De hangar", "link" ,80, true, true, true, false, "Duffel", 3)};

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
        Iterable<Venue> venues = venueRepository.findAll();
        model.addAttribute("venues", venues);
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
        Venue venue = null;
        ArrayList<String> errors = new ArrayList<>();
        int venueIndex=0;
        if(optVenueIndex.isPresent()){
            venueIndex = optVenueIndex.get();
        }
        else{errors.add("Geef een nummer");}
        if (venueIndex<0 || venueIndex > venues.length-1){
        errors.add("Geef een nummer dat bestaat");
    }

        if (errors.isEmpty()){
        venue = venues[venueIndex];}
        model.addAttribute("index",venueIndex);
        model.addAttribute("errors", errors);
        model.addAttribute("venue",venue);
        return "venuedetails";
    }
}
