import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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
export class NotificationDashboardService {

  private baseUrl = 'http://localhost:8085/api/dashboard/notifications';

  constructor(private http: HttpClient) { }

  getTotals(): Observable<NotificationTotals> {
    return this.http.get<NotificationTotals>(`${this.baseUrl}/totals`);
  }

  getByClient(): Observable<NotificationsByClient[]> {
    return this.http.get<NotificationsByClient[]>(`${this.baseUrl}/by-client`);
  }
}
