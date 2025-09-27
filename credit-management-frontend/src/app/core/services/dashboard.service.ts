import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// ---- Crédits ----
export interface CreditTotals {
  totalDemandes: number;
  totalRembourse: number;
}

export interface CreditStatus {
  statut: string;
  count: number;
}

export interface CreditsByClient {
  clientId: number;
  count: number;
}

export interface MonthlyStats {
  month: string;
  count: number;
}

// ---- Scoring ----
export interface TotalScores { totalScores: number; }
export interface StatusCount { APPROUVE: number; REFUSE: number; }
export interface AverageScore { averageScore: number; }
export interface MinMaxScore { minScore: number; maxScore: number; }

// ---- Clients ----
export interface ClientStats {
  totalClients: number;
  activeClients: number;
  inactiveClients: number;
}

// ---- Notifications ----
export interface NotificationTotals {
  totalNotifications: number;
  sentNotifications: number;
  unsentNotifications: number;
}

export interface NotificationsByClient {
  clientId: number;
  count: number;
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private creditApiUrl = 'http://localhost:8081/api/dashboard';
  private scoringApiUrl = 'http://localhost:8082/api/dashboard';
  private clientApiUrl = 'http://localhost:8080/api/clients/stats';
  private notificationApiUrl = 'http://localhost:8085/api/dashboard/notifications';

  constructor(private http: HttpClient) {}

  // ---- Crédits ----
  getCreditTotals(): Observable<CreditTotals> {
    return this.http.get<CreditTotals>(`${this.creditApiUrl}/totals`);
  }

  getCreditsByStatus(): Observable<CreditStatus[]> {
    return this.http.get<CreditStatus[]>(`${this.creditApiUrl}/credits-by-status`);
  }

  getCreditsByClient(): Observable<CreditsByClient[]> {
    return this.http.get<CreditsByClient[]>(`${this.creditApiUrl}/credits-by-client`);
  }

  getMonthlyStats(year?: number): Observable<MonthlyStats[]> {
    const url = year ? `${this.creditApiUrl}/monthly-stats?year=${year}` : `${this.creditApiUrl}/monthly-stats`;
    return this.http.get<MonthlyStats[]>(url);
  }

  // ---- Scoring ----
  getTotalScores(): Observable<TotalScores> {
    return this.http.get<TotalScores>(`${this.scoringApiUrl}/total-scores`);
  }

  getStatusCount(): Observable<StatusCount> {
    return this.http.get<StatusCount>(`${this.scoringApiUrl}/status-count`);
  }

  getAverageScore(): Observable<AverageScore> {
    return this.http.get<AverageScore>(`${this.scoringApiUrl}/average-score`);
  }

  getMinMaxScore(): Observable<MinMaxScore> {
    return this.http.get<MinMaxScore>(`${this.scoringApiUrl}/min-max-score`);
  }

  // ---- Clients ----
  getClientStats(): Observable<ClientStats> {
    return this.http.get<ClientStats>(this.clientApiUrl);
  }

  // ---- Notifications ----
  getNotificationTotals(): Observable<NotificationTotals> {
    return this.http.get<NotificationTotals>(`${this.notificationApiUrl}/totals`);
  }

  getNotificationsByClient(): Observable<NotificationsByClient[]> {
    return this.http.get<NotificationsByClient[]>(`${this.notificationApiUrl}/by-client`);
  }
}
