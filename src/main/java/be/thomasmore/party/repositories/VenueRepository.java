package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.repository.CrudRepository;

public interface VenueRepository extends CrudRepository<Venue, Integer> {

    Iterable<Venue> findByOutdoor(boolean outdoor);

    Iterable<Venue> findByIndoor(boolean indoor);

    Iterable<Venue> capacityBetween(int min, int max);

    Iterable<Venue> capacityGreaterThan(int capacity);

}
