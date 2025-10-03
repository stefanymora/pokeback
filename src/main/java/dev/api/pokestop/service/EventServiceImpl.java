package dev.api.pokestop.service;

import dev.api.pokestop.DAO.EventDAO;
import dev.api.pokestop.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;

    @Override
    public String saveEvent(Event event) {
        try {
            return eventDAO.saveEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Event getEvent(String event) {
        try {
            return eventDAO.getEvent(event);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String deleteEvent(String event) {
        try {
            return eventDAO.deleteEvent(event);
        } catch (Exception e) {
            return "Error al eliminar el evento: " + e.getMessage();
        }
    }

    @Override
    public List<Event> getAllEvent() {
        try {
            return eventDAO.getAll();
        } catch (Exception e) {

            return new ArrayList<>();
        }

    }

    @Override
    public String updateEvent(String id, Event updateEvent) {
        try {
            return eventDAO.updateEvent(id, updateEvent);
        } catch (Exception e) {
            return "Error al actualizar el evento: " + e.getMessage();
        }

    }
}
