import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  NotificationDashboardService,
  NotificationsByClient,
  NotificationTotals
} from '../../core/services/notification-dashboard.service'; // <-- Ajouté

@Component({
  selector: 'app-notification-dashboard',
  standalone: true, // si c’est déjà true
  imports: [CommonModule], // <-- Ajouter ici
  templateUrl: './notification-dashboard.component.html',
  styleUrls: ['./notification-dashboard.component.css']
})
export class NotificationDashboardComponent implements OnInit {
  totals: NotificationTotals | null = null;  // Peut être null au début
  byClient: NotificationsByClient[] = [];

  constructor(private notificationService: NotificationDashboardService) {}

  ngOnInit(): void {
    this.notificationService.getTotals().subscribe({
      next: (data) => this.totals = data,
      error: (err) => console.error(err)
    });

    this.notificationService.getByClient().subscribe({
      next: (data) => this.byClient = data,
      error: (err) => console.error(err)
    });
  }
}
