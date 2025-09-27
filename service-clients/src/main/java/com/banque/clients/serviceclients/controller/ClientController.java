package com.banque.clients.serviceclients.controller;


import com.banque.clients.serviceclients.entitiy.Client;
import com.banque.clients.serviceclients.entitiy.DashboardStats;
import com.banque.clients.serviceclients.repository.ClientRepository;
import com.banque.clients.serviceclients.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final DashboardService dashboardService;

    private final ClientRepository clientRepository;
    // 1. Statistiques globales
    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }
    // 1. Créer un nouveau client
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        client.setActive(true); // Par défaut actif
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.ok(savedClient);
    }

    // 2. Lister tous les clients avec pagination
    @GetMapping
    public ResponseEntity<Page<Client>> getClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Client> clientsPage = clientRepository.findAll(pageable);
        return ResponseEntity.ok(clientsPage);
    }

    // 3. Récupérer un client par ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Chercher un client par email
    @GetMapping("/search")
    public ResponseEntity<Client> getClientByEmail(@RequestParam String email) {
        Optional<Client> client = clientRepository.findAll()
                .stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
        return client.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Mettre à jour un client
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Client client = optionalClient.get();
        client.setNom(clientDetails.getNom());
        client.setEmail(clientDetails.getEmail());
        client.setTelephone(clientDetails.getTelephone());
        client.setDateNaissance(clientDetails.getDateNaissance());
        client.setAdresse(clientDetails.getAdresse());

        Client updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    // 6. Supprimer un client
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 7. Activer un compte client
    @PutMapping("/{id}/activate")
    public ResponseEntity<Client> activateClient(@PathVariable Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) return ResponseEntity.notFound().build();

        Client client = optionalClient.get();
        client.setActive(true);
        Client updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    // 8. Désactiver un compte client
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Client> deactivateClient(@PathVariable Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) return ResponseEntity.notFound().build();

        Client client = optionalClient.get();
        client.setActive(false);
        Client updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }
}
