package dev.api.pokestop.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import dev.api.pokestop.entity.Card;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class CardsDAO {

    private static final String COLLECTION_NAME = "cards";

    //TRAE TODAS LAS CARTAS
    public List<Card> getAll() throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Card> cards = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Card card = new Card();
            card = document.toObject(Card.class);
            card.setId(document.getId());
            cards.add(card);
        }
        return cards;
    }


    // OBTENER CARTA ESPECIFICA
    public Card getCard(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if(document.exists()){
            Card card = new Card();
            card = document.toObject(Card.class);
            card.setId(document.getId());
            return card;
        } else {
            return null;
        }

    }


    // GUARDAR CARTA
    public String saveCard(Card card) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        // Convertir Card a Map y remover el campo "id"
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> cardData = mapper.convertValue(card, new TypeReference<Map<String, Object>>() {});
        cardData.remove("id");

        // Guardar en Firestore usando el ID del objeto
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(card.getId());
        ApiFuture<WriteResult> future = docRef.set(cardData);

        return "Carta guardada exitosamente con ID: " + card.getId() + " en " + future.get().getUpdateTime();
    }


    //ACTUALIZAR CARTA
    public String updateCard(String id, Card updateCard) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Convertimos el objeto Card a un Map directamente
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> cardData = mapper.convertValue(updateCard, new TypeReference<Map<String, Object>>() {});

            // No removemos el id si no viene
            ApiFuture<WriteResult> writeResult = docRef.update(cardData);
            return "Carta actualizada correctamente con el ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ninguna carta con el ID: " + id;
        }
    }


    //ELIMINAR CARTA
    public String deleteCard(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Eliminar el documento
            ApiFuture<WriteResult> writeResult = docRef.delete();
            return "Carta eliminada correctamente con ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ninguna carta con el ID: " + id;
        }
    }


}
