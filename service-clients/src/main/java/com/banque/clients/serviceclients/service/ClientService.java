package com.banque.clients.serviceclients.service;

import com.banque.clients.serviceclients.entitiy.Client;
import com.banque.clients.serviceclients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    // Pagination
    public Page<Client> getClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    // Création d'un client
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    // Mise à jour d'un client
    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        client.setNom(clientDetails.getNom());
        client.setEmail(clientDetails.getEmail());
        client.setTelephone(clientDetails.getTelephone());
        client.setDateNaissance(clientDetails.getDateNaissance());
        client.setAdresse(clientDetails.getAdresse());
        client.setActive(clientDetails.isActive());

        return clientRepository.save(client);
    }

    // Suppression
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    // Activation / Désactivation
    public Client setActiveStatus(Long id, boolean status) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        client.setActive(status);
        return clientRepository.save(client);
    }

    // Export CSV
    public ByteArrayInputStream exportClientsToCSV() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStreamWriter osWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             PrintWriter writer = new PrintWriter(osWriter)) {

            writer.println("Nom;Email;Telephone;DateNaissance;Adresse;Active");
            int chunkSize = 500;
            int page = 0;
            Page<Client> clientPage;

            do {
                clientPage = clientRepository.findAll(Pageable.ofSize(chunkSize).withPage(page));

                for (Client client : clientPage.getContent()) {
                    String date = client.getDateNaissance() != null
                            ? client.getDateNaissance().format(DateTimeFormatter.ISO_DATE)
                            : "";
                    writer.printf("%s;%s;%s;%s;%s;%s%n",
                            escapeCsv(client.getNom()),
                            escapeCsv(client.getEmail()),
                            escapeCsv(client.getTelephone()),
                            escapeCsv(date),
                            escapeCsv(client.getAdresse()),
                            client.isActive()
                    );
                }

                page++;
            } while (clientPage.hasNext());

            writer.flush();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Erreur export CSV", e);
        }
    }

    private String escapeCsv(Object value) {
        if (value == null) return "";
        String str = value.toString();
        if (str.contains(";") || str.contains("\"") || str.contains("\n")) {
            str = str.replace("\"", "\"\"");
            return "\"" + str + "\"";
        }
        return str;
    }
}
