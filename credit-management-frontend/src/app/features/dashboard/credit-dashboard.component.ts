import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

interface CreditTotalsDTO {
  totalDemandes: number;
  totalRembourse: number;
}

interface CreditStatusDTO {
  statut: string;
  count: number;
}

interface CreditsByClientDTO {
  clientId: number;
  count: number;
}

interface MonthlyStatsDTO {
  month: string;
  count: number;
}

@Component({
  selector: 'app-credit-dashboard',
  templateUrl: './credit-dashboard.component.html',
  standalone: true,
  imports: [CommonModule, HttpClientModule]
})
export class CreditDashboardComponent implements OnInit {

  totals: CreditTotalsDTO = { totalDemandes: 0, totalRembourse: 0 };
  byStatus: CreditStatusDTO[] = [];
  byClient: CreditsByClientDTO[] = [];
  monthlyStats: MonthlyStatsDTO[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadTotals();
    this.loadByStatus();
    this.loadByClient();
    this.loadMonthlyStats();
  }

  loadTotals(): void {
    this.http.get<CreditTotalsDTO>('http://localhost:8081/api/dashboard/totals')
      .subscribe(data => this.totals = data);
  }

  loadByStatus(): void {
    this.http.get<CreditStatusDTO[]>('http://localhost:8081/api/dashboard/credits-by-status')
      .subscribe(data => this.byStatus = data);
  }

  loadByClient(): void {
    this.http.get<CreditsByClientDTO[]>('http://localhost:8081/api/dashboard/credits-by-client')
      .subscribe(data => this.byClient = data);
  }

  loadMonthlyStats(): void {
    this.http.get<MonthlyStatsDTO[]>('http://localhost:8081/api/dashboard/monthly-stats')
      .subscribe(data => this.monthlyStats = data);
  }
}
