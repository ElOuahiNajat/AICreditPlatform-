package com.banque.clients.serviceclients.repository;

import com.banque.clients.serviceclients.entitiy.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    long countByActive(boolean active);

    @Query("SELECT COUNT(c) FROM Client c")
    long countAllClients();}
