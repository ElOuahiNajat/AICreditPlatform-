package com.banque.clients.serviceclients;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Utilise le profil 'test'
class ServiceClientsApplicationTests {

    @Test
    void contextLoads() {
        // Test vide valide si le contexte se charge
    }
}