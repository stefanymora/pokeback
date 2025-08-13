package dev.api.pokestop.DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import dev.api.pokestop.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class ProductsDAO {

    private static final String COLLECTION_NAME = "products";

    // TRAE TODOS LOS ARTICULOS
    public List<Product> getAll() throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Product> products = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            Product product = document.toObject(Product.class);
            product.setId(document.getId());
            products.add(product);
        }

        return products;
    }

    // OBTENER ARTICULOS
    public Product getProduct(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if(document.exists()){
            Product product = new Product();
            product = document.toObject(Product.class);
            product.setId(document.getId());
            return product;
        } else {
            return null;
        }

    }


    // GUARDAR ARTICULOS
    public String saveProduct(Product product) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> productData = mapper.convertValue(product, new TypeReference<Map<String, Object>>() {});
        productData.remove("id");
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(); // ID automático
        ApiFuture<WriteResult> result = docRef.set(productData);
        //return "Producto guardado a las: " + result.get().getUpdateTime();
        return "Producto número: " + docRef.getId() + " guardado a las " + result.get().getUpdateTime();

    }


    // ACTUALIZAR ARTICULOS
    public String updateProduct(String id, Product updateProduct) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()){
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> productData = mapper.convertValue(updateProduct, new TypeReference<Map<String, Object>>() {});
            ApiFuture<WriteResult> writeResult = docRef.update(productData);
            return "Producto actualizado correctamente con el ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ningun producto con el ID: " + id;
        }

    }



    // ELIMINAR ARTICULOS
    public String deleteProduct(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()){
            ApiFuture<WriteResult> writeResult = docRef.delete();
            return "Producto eliminado correctamente con ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ninguna carta con el ID: " + id;
        }

    }

}
