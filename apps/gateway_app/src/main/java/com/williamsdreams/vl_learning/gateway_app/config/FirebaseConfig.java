//package com.williamsdreams.vl_learning.gateway_app.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FirebaseConfig {
//
//    private final FirebaseProperties firebaseProperties;
//
//    @Autowired
//    public FirebaseConfig(FirebaseProperties firebaseProperties) {
//        this.firebaseProperties = firebaseProperties;
//    }
//
//    @PostConstruct
//    public void initialize() {
//        try {
//            FileInputStream serviceAccount =
//                    new FileInputStream(firebaseProperties.getServiceAccountFile());
//
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .build();
//
//            if (FirebaseApp.getApps().isEmpty()) { // Evitar múltiples inicializaciones
//                FirebaseApp.initializeApp(options);
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException("Error al inicializar Firebase Admin SDK", e);
//        }
//    }
//}
