import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Scoring {
  id?: number;
  clientId: number;
  creditId: number;
  score: number;
  statut: string;
  dateCreation?: string;
  dateMiseAJour?: string;
}

export interface ScoringRequest {
  clientId: number;
  creditId: number;
  revenus: number;
  dettes: number;
}

@Injectable({ providedIn: 'root' })
export class ScoringService {
  private baseUrl = 'http://localhost:8082/api/scoring';

  constructor(private http: HttpClient) {}

  getHistory(clientId: number): Observable<Scoring[]> {
    return this.http.get<Scoring[]>(`${this.baseUrl}/history/${clientId}`);
  }

  calculateScore(request: ScoringRequest): Observable<Scoring> {
    return this.http.post<Scoring>(`${this.baseUrl}/calculate`, request);
  }

  updateStatus(id: number, statut: string): Observable<Scoring> {
    return this.http.patch<Scoring>(`${this.baseUrl}/${id}/update-status?statut=${statut}`, {});
  }

  deleteScoring(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
