import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Notification } from '../../../models/notification.model';
import { NotificationFormModalComponent } from '../notification-form-modal/notification-form-modal.component';
import {NotificationService} from '../../../core/services/notification.service';

@Component({
  selector: 'app-list-notifications',
  standalone: true,
  imports: [CommonModule, NgbModule],
  templateUrl: './list-notifications-component.html'

})
export class ListNotificationsComponent {
  notifications: Notification[] = [];

  constructor(private notificationService: NotificationService, private modalService: NgbModal) {
    this.loadNotifications();
  }

  loadNotifications() {
    this.notificationService.getAll().subscribe(res => this.notifications = res);
  }

  openAddModal(notification?: Notification) {
    const modalRef = this.modalService.open(NotificationFormModalComponent, { centered: true });
    if (notification) modalRef.componentInstance.notification = { ...notification };
    modalRef.result.then(res => { if (res === 'saved') this.loadNotifications(); }).catch(() => {});
  }

  markAsSent(notification: Notification) {
    this.notificationService.updateStatus(notification.id, true).subscribe(() => this.loadNotifications());
  }

  deleteNotification(notification: Notification) {
    if (confirm('Confirmer la suppression ?')) {
      this.notificationService.delete(notification.id).subscribe(() => this.loadNotifications());
    }
  }
}
