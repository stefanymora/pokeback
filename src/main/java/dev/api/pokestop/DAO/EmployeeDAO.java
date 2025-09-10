package dev.api.pokestop.DAO;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import dev.api.pokestop.DTO.EmployeeDTO;
import dev.api.pokestop.entity.Employee;
import dev.api.pokestop.enums.Positions;
import dev.api.pokestop.enums.Status;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class EmployeeDAO {

    private static final String COLLECTION_NAME = "employees";

    // TRAE TODOS LOS EMPLEADOS

    public List<EmployeeDTO> getAll() throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<EmployeeDTO> employees = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            System.out.println("Raw data: " + document.getData());

            EmployeeDTO employee = new EmployeeDTO();
            employee.setId(document.getId());
            employee.setName((String) document.get("name"));
            employee.setPhone((String) document.get("phone"));
            employee.setEmail((String) document.get("email"));

            Long timestampMillis = document.getLong("date_birthday");
            if (timestampMillis != null) {
                employee.setDate_birthday(new Date(timestampMillis));
            }

            employee.setUsername((String) document.get("username"));
            employee.setUrl_photo((String) document.get("url_photo"));

            Long timestampMillisStart = document.getLong("start_date");
            if (timestampMillisStart != null) {
                employee.setStart_date(new Date(timestampMillisStart));
            }

            Long timestampMillisEnd = document.getLong("end_date");
            if (timestampMillisEnd != null) {
                employee.setEnd_date(new Date(timestampMillisEnd));
            }

            // Aquí parseamos los enums si están como strings
            String positionStr = (String) document.get("position");
            if (positionStr != null) {
                employee.setPosition(Positions.valueOf(positionStr));
            }

            String statusStr = (String) document.get("status");
            if (statusStr != null) {
                employee.setStatus(Status.valueOf(statusStr));
            }

            employee.setComments((String) document.get("comments"));

            employees.add(employee);
        }

        return employees;

    }

    // OBTIENE UN EMPLEADO

    public EmployeeDTO getEmployee(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if(document.exists()){
            EmployeeDTO employee = new EmployeeDTO();
            employee = mapDocumentToEmployee(document);
            employee.setId(document.getId());
            return employee;
        } else {
            return null;
        }

    }

    // GUARDA UN EMPLEADO

    public String saveEmployee(Employee employee) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> employeeData = mapper.convertValue(employee, new TypeReference<Map<String, Object>>() {});
        employeeData.remove("id");
        DocumentReference existingUserRef = db.collection(COLLECTION_NAME).document(employee.getUsername());
        ApiFuture<DocumentSnapshot> existingFuture = existingUserRef.get();
        DocumentSnapshot existingDoc = existingFuture.get();

        if (existingDoc.exists()) {
            throw new Exception("El username '" + employee.getUsername() + "' ya está en uso!");
        }
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(employee.getUsername());
        ApiFuture<WriteResult> result = docRef.set(employeeData);

        return "Empleado con username: " + docRef.getId() + " guardado a las " + result.get().getUpdateTime();
    }




    //ACTUALIZA UN EMPLEADO

    public String updateEmployee(String id, Employee updateEmployee) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()){
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> employeeData = mapper.convertValue(updateEmployee, new TypeReference<Map<String, Object>>() {});
            ApiFuture<WriteResult> writeResult = docRef.update(employeeData);
            return "Empleado actualizado correctamente con el ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ningun empleado con el ID: " + id;
        }

    }

    //BORRA UN EMPLEADO

    public String deleteEmployee(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()){
            ApiFuture<WriteResult> writeResult = docRef.delete();
            return "Empleado eliminado correctamente con ID: " + id + " a las " + writeResult.get().getUpdateTime();
        } else {
            return "No se encontró ningun empleado con el ID: " + id;
        }

    }

    // ---------------------------------------------------------------------------------------

    // LOGIN

    public boolean login(String username, String password) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();

        // Consulta por el campo "username"
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                .whereEqualTo("username", username)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (!documents.isEmpty()) {
            // Tomamos el primer documento que coincida
            DocumentSnapshot document = documents.get(0);

            // Obtenemos la contraseña almacenada
            String storedPassword = document.getString("password");

            // Comparamos con la que nos pasaron
            if (storedPassword != null && storedPassword.equals(password)) {
                return true; // login exitoso
            }
        }

        return false; // usuario no existe o contraseña incorrecta
    }


    public EmployeeDTO getUserByUsername(String username) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                .whereEqualTo("username", username)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (!documents.isEmpty()) {
            DocumentSnapshot document = documents.get(0);
            return mapDocumentToEmployee(document);
        } else {
            return null;
        }
    }



    public EmployeeDTO mapDocumentToEmployee(DocumentSnapshot document) {
        if (document == null || !document.exists()) {
            return null;
        }

        EmployeeDTO employee = new EmployeeDTO();
        employee.setId(document.getId());
        employee.setName(document.getString("name"));
        employee.setPhone(document.getString("phone"));
        employee.setEmail(document.getString("email"));
        employee.setUsername(document.getString("username"));
        employee.setUrl_photo(document.getString("url_photo"));
        employee.setComments(document.getString("comments"));

        // Manejo de fechas
        employee.setDate_birthday(convertToDate(document.get("date_birthday")));
        employee.setStart_date(convertToDate(document.get("start_date")));
        employee.setEnd_date(convertToDate(document.get("end_date")));

        // Manejo de enums (Positions y Status)
        String positionStr = document.getString("position");
        if (positionStr != null) {
            employee.setPosition(Positions.valueOf(positionStr));
        }

        String statusStr = document.getString("status");
        if (statusStr != null) {
            employee.setStatus(Status.valueOf(statusStr));
        }

        return employee;
    }

    // Método auxiliar para convertir a Date
    private Date convertToDate(Object obj) {
        if (obj == null) return null;

        if (obj instanceof com.google.cloud.Timestamp) {
            return ((com.google.cloud.Timestamp) obj).toDate();
        } else if (obj instanceof Long) {
            return new Date((Long) obj);
        } else {
            return null;
        }
    }




}
