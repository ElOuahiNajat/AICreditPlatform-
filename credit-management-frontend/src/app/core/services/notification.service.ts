import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Notification } from '../../models/notification.model';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiUrl = 'http://localhost:8085/api/notifications';

  constructor(private http: HttpClient) {}

  // 📩 Récupérer toutes les notifications
  getAll(): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.apiUrl);
  }

  // 📩 Récupérer les notifications par client
  getByClient(clientId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${this.apiUrl}/client/${clientId}`);
  }

  // 📩 Créer une nouvelle notification (envoi email)
  create(clientId: number, message: string): Observable<Notification> {
    return this.http.post<Notification>(this.apiUrl, { clientId, message });
  }

  // ✅ Mettre à jour le statut (envoyée ou non)
  updateStatus(id: number, sent: boolean): Observable<Notification> {
    return this.http.patch<Notification>(`${this.apiUrl}/${id}/status?sent=${sent}`, {});
  }

  // ❌ Supprimer une notification
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
