package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.ArtistRepository;
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
    @Autowired
    private ArtistRepository artistRepository;



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

    /*---------------------------------------------------------------VENUEFUNCTIONS---------------------------------------------------------*/

    @GetMapping("/venuelist")
    public String venueList(Model model){
        Iterable<Venue> venues = venueRepository.findAll();
        model.addAttribute("venues", venues);
        return "venuelist";
    }

    @GetMapping({"/venuedetails", "/venuedetails/{id}"})
    public String venueDetailsById(Model model, @PathVariable Optional<Integer> id){
        Venue venue = null;
        ArrayList<String> errors = new ArrayList<>();
        int venueIndex=1;
        if(id.isPresent()){
            venueIndex = id.get();
        }
        else{errors.add("Geef een nummer");}
        if (venueIndex<1 || venueIndex > venueRepository.count()){
            errors.add("Geef een nummer dat bestaat");
        }

        Optional<Venue> optionalVenue = venueRepository.findById(venueIndex);

        if (errors.isEmpty() && optionalVenue.isPresent()){
            venue = optionalVenue.get();}


        model.addAttribute("index",venueIndex);
        model.addAttribute("count",venueRepository.count());
        model.addAttribute("errors", errors);
        model.addAttribute("venue",venue);
        return "venuedetails";
    }

    /*--------------------------------------------------------VENUEFILTERS----------------------------------------------------------------------------*/

    @GetMapping({"/venuelist/outdoor/{filter}", "/venuelist/outdoor"})
    public String venuelistOutdoor(Model model, @PathVariable Optional<String> filter){
        String filterstring = "all";
        ArrayList<String> errors = new ArrayList<>();
        Iterable<Venue> venues = null;

        if(filter.isPresent()){
            filterstring = filter.get();
        }

        if(filterstring.equals("yes")){
            venues = venueRepository.findByOutdoor(true);
        }

        else if(filterstring.equals("no")){
            venues = venueRepository.findByOutdoor(false);
        }

        else{ errors.add("De filter is ongeldig");
        }

        model.addAttribute("venues", venues);
        model.addAttribute("errors", errors);
        model.addAttribute("filtero", filterstring);

        return "venuelist";
    }


    @GetMapping({"/venuelist/indoor/{filter}", "/venuelist/indoor"})
    public String venuelistindoor(Model model, @PathVariable Optional<String> filter){
        String filterstring = "";
        ArrayList<String> errors = new ArrayList<>();
        Iterable<Venue> venues = null;

        if(filter.isPresent()){
            filterstring = filter.get();
        }

        if(filterstring.equals("yes")){
            venues = venueRepository.findByIndoor(true);
        }

        else if(filterstring.equals("no")){
            venues = venueRepository.findByIndoor(false);
        }

        else{ errors.add("De filter is ongeldig");
        }

        model.addAttribute("venues", venues);
        model.addAttribute("errors", errors);
        model.addAttribute("filteri", filterstring);

        return "venuelist";
    }

    @GetMapping({"/venuelist/size/{filter}", "/venuelist/size"})
    public String venuelistsize(Model model, @PathVariable Optional<String> filter){
        String filterstring = "";
        ArrayList<String> errors = new ArrayList<>();
        Iterable<Venue> venues = null;

        if(filter.isPresent()){
            filterstring = filter.get();
        }

        if(filterstring.equals("S")){
            venues = venueRepository.capacityBetween(0,200);
        }

        else if(filterstring.equals("M")){
            venues = venueRepository.capacityBetween(200,500);
        }

        else if(filterstring.equals("L")){
            venues = venueRepository.capacityGreaterThan(600);
        }

        else{ errors.add("De filter is ongeldig");
        }

        model.addAttribute("venues", venues);
        model.addAttribute("errors", errors);
        model.addAttribute("filters", filterstring);

        return "venuelist";
    }





/*------------------------------------------------------------ARTIST FUNCTIONS------------------------------------------------------------*/


    @GetMapping("/artistlist")
    public String artistlist(Model model){
        Iterable<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        return "artistlist";
    }


    @GetMapping({"/artistdetails", "/artistdetails/{id}"})
    public String artistDetailsById(Model model, @PathVariable Optional<Integer> id){
        Artist artist = null;
        ArrayList<String> errors = new ArrayList<>();
        int artistIndex=1;
        if(id.isPresent()){
            artistIndex = id.get();
        }
        else{errors.add("Geef een nummer");}
        if (artistIndex<1 || artistIndex > artistRepository.count()){
            errors.add("Geef een nummer dat bestaat");
        }

        Optional<Artist> optionalArtist = artistRepository.findById(artistIndex);

        if (errors.isEmpty() && optionalArtist.isPresent()){
            artist = optionalArtist.get();}


        model.addAttribute("index",artistIndex);
        model.addAttribute("count",artistRepository.count());
        model.addAttribute("errors", errors);
        model.addAttribute("artist",artist);
        return "artistdetails";
    }

/*-------------------------------------------------------------------------Other functions-----------------------------------------------------------*/

    public boolean isWeekend(DayOfWeek[] weekend){
        for (DayOfWeek day : weekend){
            if (LocalDate.now().getDayOfWeek().equals(day)){return true;}

        }
        return false;
    }

}
