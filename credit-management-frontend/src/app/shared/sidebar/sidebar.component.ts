import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="sidebar">
      <div class="logo-container">
        <h2 class="logo"> systeme</h2>
      </div>
      <nav>
        <ul>
          <li><a routerLink="/dashboard" routerLinkActive="active">📊 Dashboard</a></li>
          <li><a routerLink="/clients" routerLinkActive="active">👥 Clients</a></li>
          <li><a routerLink="/credits" routerLinkActive="active">💳 Crédits</a></li>
          <li><a routerLink="/scoring" routerLinkActive="active">📈 Scorings</a></li>
          <li><a routerLink="/notifications" routerLinkActive="active">🔔 Notifications</a></li>
          <li><a routerLink="/chat" routerLinkActive="active">🤖 Assistant IA</a></li>
        </ul>
      </nav>
      <div class="user-section">
        <div class="user-info">
          <div class="user-avatar">
            <i class="fas fa-user"></i>
          </div>
          <div class="user-details">
            <span class="user-name">Admin</span>
            <span class="user-role">Administrateur</span>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .sidebar {
      width: 300px;
      background: linear-gradient(180deg, #1e293b 0%, #0f172a 100%);
      color: white;
      padding: 0;
      height: 100vh;
      display: flex;
      flex-direction: column;
      box-shadow: 0 0 30px rgba(0, 0, 0, 0.25);
      position: relative;
      overflow: hidden;
    }

    .sidebar::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      height: 4px;
      background: linear-gradient(90deg, #38bdf8, #818cf8, #f472b6);
      z-index: 1;
    }

    .logo-container {
      padding: 32px 25px 25px;
      border-bottom: 1px solid rgba(255, 255, 255, 0.1);
      margin-bottom: 15px;
      text-align: center;
      position: relative;
      z-index: 2;
    }

    .logo {
      font-size: 24px;
      font-weight: 700;
      letter-spacing: 0.5px;
      color: #f8fafc;
      margin: 0;
      text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
    }

    nav ul {
      list-style: none;
      padding: 0 20px;
      margin-top: 10px;
    }

    nav ul li {
      margin: 8px 0;
    }

    nav ul li a {
      color: #cbd5e1;
      text-decoration: none;
      font-size: 16px;
      padding: 14px 20px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
      position: relative;
      overflow: hidden;
      gap: 15px;
      backdrop-filter: blur(10px);
    }

    nav ul li a::before {
      content: "";
      position: absolute;
      left: 0;
      top: 0;
      height: 100%;
      width: 4px;
      background: linear-gradient(180deg, #38bdf8, #818cf8);
      opacity: 0;
      transition: opacity 0.3s ease;
    }

    nav ul li a:hover {
      background: rgba(56, 189, 248, 0.15);
      color: #f1f5f9;
      padding-left: 25px;
      transform: translateX(5px);
    }

    nav ul li a:hover::before {
      opacity: 1;
    }

    nav ul li a.active {
      background: rgba(56, 189, 248, 0.2);
      color: #38bdf8;
      font-weight: 600;
      box-shadow: 0 4px 15px rgba(56, 189, 248, 0.2);
    }

    nav ul li a.active::before {
      opacity: 1;
    }

    .user-section {
      margin-top: auto;
      padding: 25px 20px;
      border-top: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(15, 23, 42, 0.7);
      backdrop-filter: blur(10px);
    }

    .user-info {
      display: flex;
      align-items: center;
      gap: 15px;
    }

    .user-avatar {
      width: 45px;
      height: 45px;
      border-radius: 50%;
      background: linear-gradient(135deg, #38bdf8, #0ea5e9);
      display: flex;
      align-items: center;
      justify-content: center;
      box-shadow: 0 4px 10px rgba(56, 189, 248, 0.3);
    }

    .user-avatar i {
      color: white;
      font-size: 18px;
    }

    .user-details {
      display: flex;
      flex-direction: column;
    }

    .user-name {
      font-weight: 600;
      font-size: 15px;
      color: #f8fafc;
      letter-spacing: 0.5px;
    }

    .user-role {
      font-size: 13px;
      color: #94a3b8;
      margin-top: 2px;
    }

    /* Animation pour les icônes */
    nav ul li a i {
      transition: all 0.3s ease;
      font-size: 17px;
      width: 20px;
      text-align: center;
    }

    nav ul li a:hover i {
      transform: scale(1.2);
      color: #38bdf8;
    }

    nav ul li a.active i {
      color: #38bdf8;
    }

    /* Effet de lumière au survol */
    nav ul li a {
      position: relative;
      overflow: hidden;
    }

    nav ul li a::after {
      content: '';
      position: absolute;
      top: -50%;
      left: -50%;
      width: 200%;
      height: 200%;
      background: radial-gradient(circle, rgba(56, 189, 248, 0.2) 0%, transparent 60%);
      opacity: 0;
      transition: opacity 0.3s ease;
      pointer-events: none;
    }

    nav ul li a:hover::after {
      opacity: 1;
    }

    /* Responsive design */
    @media (max-width: 768px) {
      .sidebar {
        width: 260px;
      }

      .logo {
        font-size: 22px;
      }

      nav ul li a {
        font-size: 15px;
        padding: 12px 18px;
      }

      .user-section {
        padding: 20px 15px;
      }
    }

    /* Animation d'entrée */
    .sidebar {
      animation: slideInLeft 0.5s ease-out;
    }

    @keyframes slideInLeft {
      from {
        transform: translateX(-100%);
        opacity: 0;
      }
      to {
        transform: translateX(0);
        opacity: 1;
      }
    }
  `]
})
export class SidebarComponent {}
