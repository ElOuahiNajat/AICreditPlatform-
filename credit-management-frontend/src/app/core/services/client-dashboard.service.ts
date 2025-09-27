import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DashboardStats {
  totalClients: number;
  activeClients: number;
  inactiveClients: number;
}

@Injectable({
  providedIn: 'root'
})
export class ClientDashboardService {

  private apiUrl = 'http://localhost:8080/api/clients/stats';

  constructor(private http: HttpClient) {}

  getClientStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(this.apiUrl);
  }
}
