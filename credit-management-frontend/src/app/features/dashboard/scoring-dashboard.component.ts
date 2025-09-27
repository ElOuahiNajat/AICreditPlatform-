import { Component, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import {
  AverageScore,
  MinMaxScore,
  ScoringDashboardService, StatusCount,
  TotalScores
} from '../../core/services/scoring-dashboard.service';

@Component({
  selector: 'app-scoring-dashboard',
  templateUrl: './scoring-dashboard.component.html',
  styleUrls: ['./scoring-dashboard.component.css'],
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  providers: [DecimalPipe]
})
export class ScoringDashboardComponent implements OnInit {

  totalScores?: TotalScores;
  statusCount?: StatusCount;
  averageScore?: AverageScore;
  minMaxScore?: MinMaxScore;

  constructor(private dashboardService: ScoringDashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getTotalScores().subscribe(data => this.totalScores = data);
    this.dashboardService.getStatusCount().subscribe(data => this.statusCount = data);
    this.dashboardService.getAverageScore().subscribe(data => this.averageScore = data);
    this.dashboardService.getMinMaxScore().subscribe(data => this.minMaxScore = data);
  }
}
