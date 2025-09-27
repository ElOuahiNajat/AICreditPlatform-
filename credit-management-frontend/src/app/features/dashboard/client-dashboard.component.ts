import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import {ClientDashboardService, DashboardStats} from '../../core/services/client-dashboard.service';

@Component({
  selector: 'app-client-dashboard',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './client-dashboard.component.html',
  styleUrls: ['./client-dashboard.component.css']
})
export class ClientDashboardComponent implements OnInit {

  stats?: DashboardStats;

  constructor(private clientService: ClientDashboardService) {}

  ngOnInit(): void {
    this.clientService.getClientStats().subscribe({
      next: (data) => this.stats = data,
      error: (err) => console.error(err)
    });
  }
}
