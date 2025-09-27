import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TotalScores { totalScores: number; }
export interface StatusCount { APPROUVE: number; REFUSE: number; }
export interface AverageScore { averageScore: number; }
export interface MinMaxScore { minScore: number; maxScore: number; }

@Injectable({
  providedIn: 'root'
})
export class ScoringDashboardService {

  private apiUrl = 'http://localhost:8082/api/dashboard';

  constructor(private http: HttpClient) {}

  getTotalScores(): Observable<TotalScores> {
    return this.http.get<TotalScores>(`${this.apiUrl}/total-scores`);
  }

  getStatusCount(): Observable<StatusCount> {
    return this.http.get<StatusCount>(`${this.apiUrl}/status-count`);
  }

  getAverageScore(): Observable<AverageScore> {
    return this.http.get<AverageScore>(`${this.apiUrl}/average-score`);
  }

  getMinMaxScore(): Observable<MinMaxScore> {
    return this.http.get<MinMaxScore>(`${this.apiUrl}/min-max-score`);
  }
}
