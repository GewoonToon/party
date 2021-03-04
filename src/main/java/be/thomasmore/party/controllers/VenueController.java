package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    private Logger logger = LoggerFactory.getLogger(VenueController.class);
    /*---------------------------------------------------------------VENUEFUNCTIONS---------------------------------------------------------*/


    @GetMapping({"/venuelist", "/venuelist/{optfilter}"})
    public String venueList(Model model, @PathVariable Optional<String> optfilter,
                            @RequestParam(required=false) Integer minCapacity,
                            @RequestParam(required=false) Integer maxCapacity){


        logger.info(String.format("venueList -- min=%d", minCapacity));
        logger.info(String.format("venueLise -- max=%d", maxCapacity));
        ArrayList<String> errors = new ArrayList<>();
        ArrayList<Venue> venuesfilter = new ArrayList<>();
        boolean filter = false;
        for(Venue venue : venueRepository.findAll()){
            venuesfilter.add(venue);
        }
        if(optfilter.isPresent() && optfilter.get().equals("filter")){
            filter = true;
        }
        else{errors.add("Geef een filter");}

        if(minCapacity!=null){
            venuesfilter.removeIf(venue -> venue.getCapacity() < minCapacity);
            model.addAttribute("min", minCapacity);
        }
        else{model.addAttribute("min", null);}

        if(maxCapacity!=null){
            venuesfilter.removeIf(venue -> venue.getCapacity()> maxCapacity);
            model.addAttribute("max", maxCapacity);
        }
        else{model.addAttribute("max",null);}


        model.addAttribute("count", venueRepository.count());
        model.addAttribute("filter", filter);
        model.addAttribute("venues", venuesfilter);
        model.addAttribute("errors", errors);
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
            errors.add("Geef een nummer tussen 1 en " + venueRepository.count());
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

   /* @GetMapping({"/venuelist/outdoor/{filter}", "/venuelist/outdoor"})
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
*/
}
