package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

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
}
