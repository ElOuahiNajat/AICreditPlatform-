// src/app/app.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {SidebarComponent} from './shared/sidebar/sidebar.component';

// src/app/app.component.ts
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, SidebarComponent],
  template: `
    <div class="container">
      <app-sidebar></app-sidebar>
      <div class="content">
        <router-outlet></router-outlet>
      </div>
    </div>
  `,
  styles: [`
    .container { display: flex; height: 100vh; }
    .content { flex: 1; padding: 30px; background: #f8fafc; overflow-y: auto; }
  `]
})
export class AppComponent {}
