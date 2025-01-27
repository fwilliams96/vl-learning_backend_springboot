package com.williamsdreams.vl_learning.gateway_app.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "firebase")
@Validated
public class FirebaseProperties {

    /**
     * Ruta al archivo de cuenta de servicio de Firebase.
     */
    @NotBlank
    private String serviceAccountFile;

    public String getServiceAccountFile() {
        return serviceAccountFile;
    }

    public void setServiceAccountFile(String serviceAccountFile) {
        this.serviceAccountFile = serviceAccountFile;
    }
}
