package com.banque.credits.servicecredits.service;


import com.banque.credits.servicecredits.entity.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-clients", url = "http://localhost:8080/api/clients")
public interface ClientFeignService {

    @GetMapping("/{id}") // maintenant ça devient http://localhost:8080/api/clients/{id}
    ClientDTO getClientById(@PathVariable("id") Long id);
}


