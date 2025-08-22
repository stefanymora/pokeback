package dev.api.pokestop.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class Config {

    @PostConstruct
    public void initFirebase() {
        try {
            // Leer todas las variables de entorno necesarias para Firebase
            String firebaseJson = String.format(
                    "{" +
                            "\"type\": \"%s\"," +
                            "\"project_id\": \"%s\"," +
                            "\"private_key_id\": \"%s\"," +
                            "\"private_key\": \"%s\"," +
                            "\"client_email\": \"%s\"," +
                            "\"client_id\": \"%s\"," +
                            "\"auth_uri\": \"%s\"," +
                            "\"token_uri\": \"%s\"," +
                            "\"auth_provider_x509_cert_url\": \"%s\"," +
                            "\"client_x509_cert_url\": \"%s\"," +
                            "\"universe_domain\": \"%s\"" +
                            "}",
                    System.getenv("FIREBASE_TYPE"),
                    System.getenv("FIREBASE_PROJECT_ID"),
                    System.getenv("FIREBASE_PRIVATE_KEY_ID"),
                    System.getenv("FIREBASE_PRIVATE_KEY").replace("\\n", "\n"), // importante
                    System.getenv("FIREBASE_CLIENT_EMAIL"),
                    System.getenv("FIREBASE_CLIENT_ID"),
                    System.getenv("FIREBASE_AUTH_URI"),
                    System.getenv("FIREBASE_TOKEN_URI"),
                    System.getenv("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"),
                    System.getenv("FIREBASE_CLIENT_X509_CERT_URL"),
                    System.getenv("FIREBASE_UNIVERSE_DOMAIN")
            );

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8))
                    ))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase inicializado desde variables de entorno.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
