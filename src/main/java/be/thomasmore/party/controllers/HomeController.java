package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Animal;
import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.AnimalRepository;
import be.thomasmore.party.repositories.ArtistRepository;
import be.thomasmore.party.repositories.PartyRepository;
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

/*-------------------------------------------------------------------------Other functions-----------------------------------------------------------*/

    public boolean isWeekend(DayOfWeek[] weekend){
        for (DayOfWeek day : weekend){
            if (LocalDate.now().getDayOfWeek().equals(day)){return true;}

        }
        return false;
    }

    @Autowired
    private AnimalRepository animalRepository;


    @GetMapping({"/animaldetails", "/animaldetails/{id}"})
    public String animalDetailsById(Model model, @PathVariable Optional<Integer> id){
        Animal animal = null;
        ArrayList<String> errors = new ArrayList<>();
        int animalIndex=1;
        if(id.isPresent()){
            animalIndex = id.get();
        }
        else{errors.add("Geef een nummer");}
        if (animalIndex<1 || animalIndex > animalRepository.count()){
            errors.add("Geef een nummer tussen 1 en " + animalRepository.count());
        }

        Optional<Animal> optionalAnimal = animalRepository.findById(animalIndex);

        if (errors.isEmpty() && optionalAnimal.isPresent()){
            animal = optionalAnimal.get();}

        model.addAttribute("index",animalIndex);
        model.addAttribute("count",animalRepository.count());
        model.addAttribute("errors", errors);
        model.addAttribute("animal",animal);
        return "animaldetails";
    }

}
