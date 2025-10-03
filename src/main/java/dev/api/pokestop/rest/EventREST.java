package dev.api.pokestop.rest;

import dev.api.pokestop.entity.Event;
import dev.api.pokestop.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventREST {

    @Autowired
    private EventServiceImpl eventServiceImpl;


    @GetMapping
    public ResponseEntity<List<Event>> getAllEvent() {
        List<Event> events = eventServiceImpl.getAllEvent();
        return ResponseEntity.ok(events);
    }


    @GetMapping("/{event}")
    public ResponseEntity<Event> getEventByName(@PathVariable String event) {
        Event event_1 = eventServiceImpl.getEvent(event);

        if (event_1 != null) {
            return ResponseEntity.ok(event_1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{event}")
    public ResponseEntity<String> updateEvent(@PathVariable String event, @RequestBody Event updateEvent) {
        String result = eventServiceImpl.updateEvent(event, updateEvent);

        if (result.startsWith("Evento actualizado")) {
            return ResponseEntity.ok(result);
        } else if (result.contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

    }

    @PostMapping
    public ResponseEntity<String> newEvent(@RequestBody Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre del evento obligatorio.");
        }

        String productMessage = eventServiceImpl.saveEvent(event);
        return ResponseEntity.ok(productMessage);
    }


    @DeleteMapping("/{event}")
    public ResponseEntity<String> deleteEvent(@PathVariable String event) {
        String result = eventServiceImpl.deleteEvent(event);

        if (result.contains("Evento eliminado correctamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }


}
