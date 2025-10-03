package dev.api.pokestop.service;
import dev.api.pokestop.entity.Event;
import java.util.List;

public interface EventService {

    String saveEvent(Event event) ;

    Event getEvent(String event);

    String deleteEvent(String event);

    List<Event> getAllEvent();

    String updateEvent(String id, Event updateEvent);


}
