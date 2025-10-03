package dev.api.pokestop.DAO;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import dev.api.pokestop.entity.Supply;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class SupplyDAO {

    private static final String COLLECTION_NAME = "supply";

    // TRAE TODOS LOS ARTICULOS
    public List<Supply> getAll() throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Supply> supplys = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            Supply supply = document.toObject(Supply.class);
            supply.setId(document.getId());
            supplys.add(supply);
        }

        return supplys;
    }


    // OBTENER ARTICULOS
    public Supply getSupply (String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            Supply supply = new Supply();
            supply = document.toObject(Supply.class);
            supply.setId(document.getId());
            return supply;
        } else {
            return null;
        }

    }


    // GUARDAR ARTICULOS
    public String saveSupply (Supply supply) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> supplyData = mapper.convertValue(supply, new TypeReference<Map<String, Object>>() {
        });
        supplyData.remove("id");
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(); // ID automático
        ApiFuture<WriteResult> result = docRef.set(supplyData);
        //return "Insumo guardado a las: " + result.get().getUpdateTime();
        return "Insumo número: " + docRef.getId() + " guardado a las " + result.get().getUpdateTime();

    }


    // ACTUALIZAR ARTICULOS
    public String updateSupply (String id, Supply updateSupply) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> supplyData = mapper.convertValue(updateSupply, new TypeReference<Map<String, Object>>() {
            });
            ApiFuture<WriteResult> writeResult = docRef.update(supplyData);
            return "Insumo actualizado correctamente con el ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ningun insumo con el ID: " + id;
        }

    }


    // ELIMINAR ARTICULOS
    public String deleteSupply (String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            ApiFuture<WriteResult> writeResult = docRef.delete();
            return "Insumo eliminado correctamente con ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ningun insumo con el ID: " + id;
        }

    }




}
