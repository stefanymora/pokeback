package dev.api.pokestop.DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import dev.api.pokestop.entity.Sale;
import dev.api.pokestop.enums.PaymentMethods;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
@Repository
public class SaleDAO {

    private static final String COLLECTION_NAME = "sales";

    //OBTENER TODA LA LISTA DE VENTAS
    public List<Sale> getAll() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Sale> sales = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Long timestampMillis = document.getLong("date_hour");

            Sale sale = new Sale();
            if (timestampMillis != null) {
                sale.setDate_hour(new Date(timestampMillis));
            }

            sale.setId_employee( document.getString("id_employee"));
            sale.setTotal_amount( document.getDouble("total_amount"));
            sale.setDiscount(document.getDouble("discount"));
            sale.setSubtotal(document.getDouble("subtotal"));
            sale.setPayment_method(PaymentMethods.valueOf(document.getString("payment_method")));


            sale.setId(document.getId());
            sales.add(sale);
        }
        return sales;

    }
    //OBTENER VENTA EN ESPECÍFICO
    public Sale getSale(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if(document.exists()){
            Sale sale =  document.toObject(Sale.class);
            sale.setId(document.getId());
            return sale;
        } else {
            return null;
        }

    }

    // GUARDAR VENTA
    public String saveSale(Sale sale) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        // Convertir Sale a Map y remover el campo "id"
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(sale);
        Map<String, Object> saleData = mapper.convertValue(sale, new TypeReference<Map<String, Object>>() {});
        saleData.remove("id");

        // Guardar en Firestore usando el ID del objeto
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(sale.getId());
        ApiFuture<WriteResult> future = docRef.set(saleData);

        return "Venta guardada exitosamente con ID: " + sale.getId() + " en " + future.get().getUpdateTime();
    }

    //ELIMINAR VENTA
    public String deleteSale(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Eliminar el documento
            ApiFuture<WriteResult> writeResult = docRef.delete();
            return "Venta eliminada correctamente con ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ninguna venta con el ID: " + id;
        }
    }

}
