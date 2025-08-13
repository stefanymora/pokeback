package dev.api.pokestop.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class Config {

    @PostConstruct
    public void initFirebase() {
        try {
            if (System.getenv("FIREBASE_PROJECT_ID") != null) {
                // Modo Render / Producción: usar variables de entorno
                Map<String, String> env = System.getenv();

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
                        env.get("FIREBASE_TYPE"),
                        env.get("FIREBASE_PROJECT_ID"),
                        env.get("FIREBASE_PRIVATE_KEY_ID"),
                        env.get("FIREBASE_PRIVATE_KEY").replace("\\n", "\n"),
                        env.get("FIREBASE_CLIENT_EMAIL"),
                        env.get("FIREBASE_CLIENT_ID"),
                        env.get("FIREBASE_AUTH_URI"),
                        env.get("FIREBASE_TOKEN_URI"),
                        env.get("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"),
                        env.get("FIREBASE_CLIENT_X509_CERT_URL"),
                        env.get("FIREBASE_UNIVERSE_DOMAIN")
                );

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(
                                new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8))
                        ))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado desde variables de entorno.");
            } else {
                // Modo Local: usar archivo JSON
                FileInputStream serviceAccount =
                        new FileInputStream("pokestop-eae5d-firebase-adminsdk-fbsvc-d518d774f6.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado desde archivo local.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
