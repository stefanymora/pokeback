package dev.api.pokestop.DAO;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
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

    public List<Employee> getAll() throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Employee> employees = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            System.out.println("Raw data: " + document.getData());

            Employee employee = new Employee();
            employee.setId(document.getId());
            employee.setName((String) document.get("name"));
            employee.setPhone((String) document.get("phone"));
            employee.setEmail((String) document.get("email"));

            Long timestampMillis = document.getLong("date_birthday");
            if (timestampMillis != null) {
                employee.setDate_birthday(new Date(timestampMillis));
            }

            employee.setUsername((String) document.get("username"));
            employee.setPassword((String) document.get("password"));
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

    public Employee getEmployee(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if(document.exists()){
            Employee employee = new Employee();
            employee = document.toObject(Employee.class);
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
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(); // ID automático
        ApiFuture<WriteResult> result = docRef.set(employeeData);
        return "Empleado número: " + docRef.getId() + " guardado a las " + result.get().getUpdateTime();

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

}
