import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CreditTotals {
  totalCredits: number;
  approvedCredits: number;
  pendingCredits: number;
  rejectedCredits: number;
}

export interface CreditStatus {
  status: string;
  count: number;
}

export interface CreditsByClient {
  clientId: number;
  count: number;
}

export interface MonthlyStats {
  month: string; // exemple: "2025-09"
  count: number;
}

@Injectable({
  providedIn: 'root'
})
export class CreditDashboardService {
  private apiUrl = 'http://localhost:8081/api/dashboard';

  constructor(private http: HttpClient) {}

  getTotals(): Observable<CreditTotals> {
    return this.http.get<CreditTotals>(`${this.apiUrl}/totals`);
  }

  getCreditsByStatus(): Observable<CreditStatus[]> {
    return this.http.get<CreditStatus[]>(`${this.apiUrl}/credits-by-status`);
  }

  getCreditsByClient(): Observable<CreditsByClient[]> {
    return this.http.get<CreditsByClient[]>(`${this.apiUrl}/credits-by-client`);
  }

  getMonthlyStats(year?: number): Observable<MonthlyStats[]> {
    return this.http.get<MonthlyStats[]>(`${this.apiUrl}/monthly-stats`, { params: year ? { year: year.toString() } : {} });
  }
}
