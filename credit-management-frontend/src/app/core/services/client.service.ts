// src/app/core/services/client.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../../models/client.model';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = 'http://localhost:8080/api/clients';

  constructor(private http: HttpClient) {}

  // Lister avec pagination
  getClients(page: number, size: number, sortBy: string): Observable<any> {
    return this.http.get(`${this.apiUrl}?page=${page}&size=${size}&sortBy=${sortBy}`);
  }

  // Récupérer un client
  getClientById(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/${id}`);
  }

  // Créer un client
  createClient(client: Client): Observable<Client> {
    return this.http.post<Client>(this.apiUrl, client);
  }

  // Mettre à jour un client
  updateClient(id: number, client: Client): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/${id}`, client);
  }

  // Supprimer un client
  deleteClient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Activer/Désactiver
  activateClient(id: number): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/${id}/activate`, {});
  }

  deactivateClient(id: number): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/${id}/deactivate`, {});
  }

  // Recherche par email
  getClientByEmail(email: string): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/search?email=${email}`);
  }
}
