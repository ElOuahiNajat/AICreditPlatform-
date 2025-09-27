import { Routes } from '@angular/router';
import { ListClientsComponent } from './features/clients/list-clients/list-clients.component';
import { ListCreditsComponent } from './features/credits/list-credits.component/list-credits.component';
import { ListScoringsComponent } from './features/scoring/list-scorings-component/list-scorings-component';
import {
  ListNotificationsComponent
} from './features/notifications/list-notifications-component/list-notifications-component';
import {ChatComponent} from './features/chat/chat.component';
import {ClientDashboardComponent} from './features/dashboard/client-dashboard.component';
import {ScoringDashboardComponent} from './features/dashboard/scoring-dashboard.component';
import {NotificationDashboardComponent} from './features/dashboard/notification-dashboard.component';
import { CreditDashboardComponent } from './features/dashboard/credit-dashboard.component';
import {DashboardComponent} from './features/dashboard/dashboard.component';

export const routes: Routes = [
  { path: 'clients', component: ListClientsComponent },
  { path: 'credits', component: ListCreditsComponent },
  { path: 'scoring', component: ListScoringsComponent },
  { path: 'notifications', component: ListNotificationsComponent }, // route Notifications
  { path: 'chat', component: ChatComponent },
  { path: '', redirectTo: '/clients', pathMatch: 'full' },
  { path: 'client-dashboard', component: ClientDashboardComponent },
  { path: 'scoring-dashboard', component: ScoringDashboardComponent  },
  { path: 'credit-dashboard', component: CreditDashboardComponent },
  { path: 'notifications-dashboard', component: NotificationDashboardComponent },
  { path: 'dashboard', component: DashboardComponent }

];
