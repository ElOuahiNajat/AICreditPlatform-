import { Component, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { DashboardService, CreditTotals, CreditStatus, CreditsByClient, MonthlyStats, TotalScores, StatusCount, AverageScore, MinMaxScore, ClientStats, NotificationTotals, NotificationsByClient } from '../../core/services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  providers: [DecimalPipe]
})
export class DashboardComponent implements OnInit {

  // ---- Crédits ----
  creditTotals?: CreditTotals;
  creditByStatus: CreditStatus[] = [];
  creditsByClient: CreditsByClient[] = [];
  monthlyStats: MonthlyStats[] = [];

  // ---- Scoring ----
  totalScores?: TotalScores;
  statusCount?: StatusCount;
  averageScore?: AverageScore;
  minMaxScore?: MinMaxScore;

  // ---- Clients ----
  clientStats?: ClientStats;

  // ---- Notifications ----
  notificationTotals?: NotificationTotals;
  notificationsByClient: NotificationsByClient[] = [];

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    // Crédits
    this.dashboardService.getCreditTotals().subscribe(data => this.creditTotals = data);
    this.dashboardService.getCreditsByStatus().subscribe(data => this.creditByStatus = data);
    this.dashboardService.getCreditsByClient().subscribe(data => this.creditsByClient = data);
    this.dashboardService.getMonthlyStats().subscribe(data => this.monthlyStats = data);

    // Scoring
    this.dashboardService.getTotalScores().subscribe(data => this.totalScores = data);
    this.dashboardService.getStatusCount().subscribe(data => this.statusCount = data);
    this.dashboardService.getAverageScore().subscribe(data => this.averageScore = data);
    this.dashboardService.getMinMaxScore().subscribe(data => this.minMaxScore = data);

    // Clients
    this.dashboardService.getClientStats().subscribe(data => this.clientStats = data);

    // Notifications
    this.dashboardService.getNotificationTotals().subscribe(data => this.notificationTotals = data);
    this.dashboardService.getNotificationsByClient().subscribe(data => this.notificationsByClient = data);
  }
}
