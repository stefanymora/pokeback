package dev.api.pokestop.DAO;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import dev.api.pokestop.entity.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class EventDAO {

    private static final String COLLECTION_NAME = "events";

    // TRAE TODOS LOS ARTICULOS
    public List<Event> getAll() throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Event> events = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            System.out.println("Doc AQUI: " + document.getId() + " => " + document.getData());

            Event event = new Event();
            event.setId(document.getId());

            // Convertir la fecha guardada como long
            Long millis = document.getLong("date");
            if (millis != null) {
                event.setDate(new Date(millis));
            }

            event.setName(document.getString("name"));
            event.setDescription(document.getString("description"));

            events.add(event);
        }

        return events;
    }


    // OBTENER ARTICULOS
    public Event getEvent(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            Event event = new Event();
            event.setId(document.getId());
            Long millis = document.getLong("date");
            if (millis != null) {
                event.setDate(new Date(millis));
            }

            event.setName(document.getString("name"));
            event.setDescription(document.getString("description"));
            return event;

        } else {
            return null;
        }
    }



    // GUARDAR ARTICULOS
    public String saveEvent(Event event) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> eventData = mapper.convertValue(event, new TypeReference<Map<String, Object>>() {
        });
        eventData.remove("id");
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(); // ID automático
        ApiFuture<WriteResult> result = docRef.set(eventData);
        //return "Evento guardado a las: " + result.get().getUpdateTime();
        return "Evento número: " + docRef.getId() + " guardado a las " + result.get().getUpdateTime();

    }


    // ACTUALIZAR ARTICULOS
    public String updateEvent(String id, Event updateEvent) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> eventData = mapper.convertValue(updateEvent, new TypeReference<Map<String, Object>>() {
            });
            ApiFuture<WriteResult> writeResult = docRef.update(eventData);
            return "Evento actualizado correctamente con el ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ningun evento con el ID: " + id;
        }

    }


    // ELIMINAR ARTICULOS
    public String deleteEvent(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ApiFuture<WriteResult> writeResult = docRef.delete();
            return "Evento eliminado correctamente con ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ningun evento con el ID: " + id;
        }

    }


}







