package dev.api.pokestop.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class Config {

    @Value("${FIREBASE_TYPE}")
    private String type;

    @Value("${FIREBASE_PROJECT_ID}")
    private String projectId;

    @Value("${FIREBASE_PRIVATE_KEY_ID}")
    private String privateKeyId;

    @Value("${FIREBASE_PRIVATE_KEY}")
    private String privateKey;

    @Value("${FIREBASE_CLIENT_EMAIL}")
    private String clientEmail;

    @Value("${FIREBASE_CLIENT_ID}")
    private String clientId;

    @Value("${FIREBASE_AUTH_URI}")
    private String authUri;

    @Value("${FIREBASE_TOKEN_URI}")
    private String tokenUri;

    @Value("${FIREBASE_AUTH_PROVIDER_X509_CERT_URL}")
    private String authProviderX509CertUrl;

    @Value("${FIREBASE_CLIENT_X509_CERT_URL}")
    private String clientX509CertUrl;

    @Value("${FIREBASE_UNIVERSE_DOMAIN}")
    private String universeDomain;

    @PostConstruct
    public void initFirebase() {
        try {
            // Reemplazar saltos de línea literales en la private key
            String formattedKey = privateKey.replace("\\n", "\n");

            // Construir JSON de forma segura
            String firebaseJson = "{"
                    + "\"type\":\"" + type + "\","
                    + "\"project_id\":\"" + projectId + "\","
                    + "\"private_key_id\":\"" + privateKeyId + "\","
                    + "\"private_key\":\"" + formattedKey.replace("\"", "\\\"") + "\","
                    + "\"client_email\":\"" + clientEmail + "\","
                    + "\"client_id\":\"" + clientId + "\","
                    + "\"auth_uri\":\"" + authUri + "\","
                    + "\"token_uri\":\"" + tokenUri + "\","
                    + "\"auth_provider_x509_cert_url\":\"" + authProviderX509CertUrl + "\","
                    + "\"client_x509_cert_url\":\"" + clientX509CertUrl + "\","
                    + "\"universe_domain\":\"" + universeDomain + "\""
                    + "}";

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8))
                    ))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase inicializado correctamente desde application.properties.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
