package be.thomasmore.party.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Animal {

    @Id
    private int id;
    private String name, city, bio;


    public Animal() {
    }

    public Animal(int id, String name, String city, String bio) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getBio() {
        return bio;
    }

    @ManyToMany(mappedBy = "animals")
    private Collection<Party> parties;

    public Collection<Party> getParties() {
        return parties;
    }

    public void setParties(Collection<Party> parties) {
        this.parties = parties;
    }
}
